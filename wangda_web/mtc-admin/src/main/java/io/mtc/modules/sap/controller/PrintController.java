package io.mtc.modules.sap.controller;

import com.crystaldecisions.sdk.occa.report.application.ParameterFieldController;
import com.crystaldecisions.sdk.occa.report.application.ReportClientDocument;
import com.crystaldecisions.sdk.occa.report.exportoptions.ReportExportFormat;
import com.crystaldecisions.sdk.occa.report.lib.ReportSDKException;
import io.mtc.common.utils.Constant;
import io.mtc.common.utils.R;
import io.mtc.modules.sap.service.SaleInvoiceService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by majun on 2018/9/21.
 */
@Controller
@RequestMapping("/sap/saleinv")
public class PrintController {

    @Autowired
    private SaleInvoiceService saleInvoiceService;

    @Value("${rpt.path}")
    private String rptPath;

    @Value("${sap.servicelayer.company}")
    private String schema;

    @Value("${spring.datasource.druid.first.username}")
    private String username;

    @Value("${spring.datasource.druid.first.password}")
    private String password;

    @RequestMapping(value = {"/print/{docEntry}/{type}","/print/{docEntry}"})
    @RequiresPermissions("sys:invoice:print")
    public void print(@PathVariable("docEntry")Long docEntry,@PathVariable(value = "type",required = false) String type, HttpServletResponse response) throws ReportSDKException, IOException {

        Constant.PDFType pdfType =  null;
        if(StringUtils.isNotEmpty(type)){
            pdfType = Constant.PDFType.value(type);
        }else{
            pdfType = saleInvoiceService.print(docEntry);
        }

        ReportClientDocument doc = null;
        OutputStream ops = null;
        ByteArrayInputStream bis = null;

        try{
            doc = new ReportClientDocument();
            doc.setReportAppServer(ReportClientDocument.inprocConnectionString);
            doc.open(rptPath+pdfType.getValue(), ReportExportFormat._PDF);
            doc.getDatabaseController().logon(username,password);
            response.setContentType("application/pdf"); //请求响应PDF文件

            ParameterFieldController parameterFieldController = doc.getDataDefController().getParameterFieldController();
            parameterFieldController.setCurrentValue("","Dockey@",docEntry);
            parameterFieldController.setCurrentValue("","SCHEMA@",schema);

            bis = (ByteArrayInputStream)doc.getPrintOutputController().export(ReportExportFormat.PDF);
            byte[] buf = new byte[2000*1024];

            ops = response.getOutputStream();
            int nRead = 0;
            while((nRead = bis.read(buf)) != -1){
                ops.write(buf,0,nRead);
            }
            ops.flush();
        }catch (ReportSDKException re){
            throw re;
        }catch (IOException ie){
            throw ie;
        }finally {
            if(bis != null){
                bis.close();
            }
            if(ops != null){
                ops.close();
            }
            if(doc != null){
                doc.close();;
            }
        }
    }

    @RequestMapping(value = {"/vaildPrint/{docEntry}/{type}","/vaildPrint/{docEntry}"})
    @RequiresPermissions("sys:invoice:print")
    @ResponseBody
    public R vaildPrint(@PathVariable("docEntry")Long docEntry,@PathVariable(value = "type",required = false) String type,String docType) throws Exception {

        Constant.PDFType pdfType =  null;
        if(StringUtils.isNotEmpty(type)){
            pdfType = Constant.PDFType.折扣兑现;
        }else{
            pdfType = saleInvoiceService.print(docEntry);
        }

        saleInvoiceService.printLog(docEntry,docType,pdfType);

        ReportClientDocument doc = null;
        ByteArrayInputStream bis = null;
        try{
            doc = new ReportClientDocument();
            doc.setReportAppServer(ReportClientDocument.inprocConnectionString);
            doc.open(rptPath+pdfType.getValue(), ReportExportFormat._PDF);
            doc.getDatabaseController().logon(username,password);

            ParameterFieldController parameterFieldController = doc.getDataDefController().getParameterFieldController();
            parameterFieldController.setCurrentValue("","Dockey@",docEntry);
            parameterFieldController.setCurrentValue("","SCHEMA@",schema);

            bis = (ByteArrayInputStream)doc.getPrintOutputController().export(ReportExportFormat.PDF);

        }catch(ReportSDKException re){
            throw re;
        }finally {
            if(doc != null){
                doc.close();
            }
            if(bis != null){
                bis.close();
            }
        }

        return R.ok();
    }

}
