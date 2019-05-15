package io.mtc.modules.sap.controller;

import io.mtc.common.annotation.SysLog;
import io.mtc.common.utils.PageUtils;
import io.mtc.common.utils.R;
import io.mtc.modules.sap.dto.SaleInvoiceEditDto;
import io.mtc.modules.sap.service.SaleInvoiceService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sap/saleinv")
public class SaleInvoiceController {

    @Autowired
    private SaleInvoiceService saleInvoiceService;

    /**
     * 查询销售发票明细（包括草稿）
     */
    @RequiresPermissions("sys:invoice:list")
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) throws Exception {
        PageUtils page = saleInvoiceService.queryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 修改界面取数
     *
     * @param docEntry
     * @param status
     * @return
     */
    @RequestMapping("/info")
    @RequiresPermissions("sys:invoice:info")
    public R info(Long docEntry, String status) throws Exception {
        SaleInvoiceEditDto dto = saleInvoiceService.info(docEntry, status);
        return R.ok().put("result", dto);
    }

    /**
     * 退货用界面取数
     *
     * @param docEntry
     * @return
     */
    @RequestMapping("/back")
    public R back(Long docEntry) throws Exception {
        SaleInvoiceEditDto dto = saleInvoiceService.getDelivery(docEntry);
        return R.ok().put("result", dto);
    }


    /**
     * 订单取消接口
     *
     * @param docEntry
     * @param status
     * @return
     */
    @SysLog("取消订单")
    @RequestMapping("/cancel")
    @RequiresPermissions("sys:invoice:cancel")
    public R cancel(Long docEntry, String status) throws Exception {
        saleInvoiceService.cancel(docEntry, status);
        return R.ok();
    }

    /**
     * 添加订单
     *
     * @param saleInvoiceEditDto
     * @return
     * @throws Exception
     */
    @SysLog("新增销售开票")
    @RequiresPermissions("sys:invoice:save")
    @RequestMapping("/add")
    public R add(@RequestBody SaleInvoiceEditDto saleInvoiceEditDto) throws Exception {
        Integer docEntry = saleInvoiceService.save(saleInvoiceEditDto, false, false);
        return R.ok().put("docEntry", docEntry);
    }

    /**
     * 修改保存订单
     *
     * @param saleInvoiceEditDto
     * @return
     * @throws Exception
     */
    @SysLog("修改销售开票")
    @RequestMapping("/edit")
    @RequiresPermissions("sys:invoice:update")
    public R edit(@RequestBody SaleInvoiceEditDto saleInvoiceEditDto) throws Exception {
        Integer docEntry = saleInvoiceService.save(saleInvoiceEditDto, false, true);
        return R.ok().put("docEntry", docEntry);
    }

    /**
     * 添加草稿
     *
     * @param saleInvoiceEditDto
     * @return
     * @throws Exception
     */
    @SysLog("新增销售开票草稿")
    @RequestMapping("/draft")
    @RequiresPermissions("sys:invoice:save")
    public R addDraft(@RequestBody SaleInvoiceEditDto saleInvoiceEditDto) throws Exception {
        Integer docEntry = saleInvoiceService.save(saleInvoiceEditDto, true, false);
        return R.ok().put("docEntry", docEntry);
    }

    /**
     * 修改草稿
     *
     * @param saleInvoiceEditDto
     * @return
     * @throws Exception
     */
    @SysLog("修改草稿")
    @RequestMapping("/draftEdit")
    @RequiresPermissions("sys:invoice:update")
    public R editDraft(@RequestBody SaleInvoiceEditDto saleInvoiceEditDto) throws Exception {
        saleInvoiceService.save(saleInvoiceEditDto, true, true);
        return R.ok();
    }

    /**
     * 添加草稿为正式单据
     *
     * @return
     * @throws Exception
     */
    @SysLog("将草稿添加为正式单据")
    @RequestMapping("/draftTodoc")
    @RequiresPermissions("sys:invoice:save")
    public R draftToDocuments(@RequestBody SaleInvoiceEditDto saleInvoiceEditDto) throws Exception {
        Integer docEntry = saleInvoiceService.draftToDocuments(saleInvoiceEditDto);
        return R.ok().put("docEntry", docEntry);
    }

    /**
     * 收款保存
     *
     * @param docEntry
     * @return
     */
    @SysLog("收款")
    @RequestMapping("/inpay")
    @RequiresPermissions("sys:invoice:receive")
    public R incomingPayments(String docEntry) throws Exception {
        saleInvoiceService.incomingPayments(docEntry);
        return R.ok();
    }

    /**
     * 批量收款
     *
     * @param docEntryList
     * @return
     */
    @SysLog("批量收款")
    @RequestMapping("/batchinpay")
    @RequiresPermissions("sys:invoice:batchRec")
    public R incomingPaymentsBatch(@RequestBody List<String> docEntryList) throws Exception {
        String msg = saleInvoiceService.incomingPaymentsBatch(docEntryList);
        return R.ok().put("msg", msg);
    }

    /**
     * 取消收款
     *
     * @param docEntry
     * @return
     */
    @SysLog("取消收款")
    @RequestMapping("/celpay")
    @RequiresPermissions("sys:invoice:cancelReceive")
    public R incomingPayCancel(String docEntry) throws Exception {
        saleInvoiceService.incomingPayCancel(docEntry);
        return R.ok();
    }

    /**
     * 取消收款
     *
     * @param docEntryList
     * @return
     */
    @SysLog("批量取消收款")
    @RequestMapping("/batchcelpay")
    @RequiresPermissions("sys:invoice:batchCancelRec")
    public R incomingPayBatchCancel(@RequestBody List<String> docEntryList) throws Exception {
        String msg = saleInvoiceService.incomingPayBatchCancel(docEntryList);
        return R.ok().put("msg", msg);
    }

    /**
     * 批量交货
     *
     * @param docEntryList
     * @return
     * @throws Exception
     */
    @SysLog("批量交货")
    @RequestMapping("/batchdelivery")
    @RequiresPermissions("sys:invoice:batchDelivery")
    public R batchDelivery(@RequestBody List<String> docEntryList) throws Exception {
        String msg = saleInvoiceService.batchDelivery(docEntryList);
        return R.ok().put("msg", msg);
    }

    /**
     * 批量取消交货
     *
     * @param docEntryList
     * @return
     * @throws Exception
     */
    @SysLog("批量取消交货")
    @RequestMapping("/batchceldelivery")
    @RequiresPermissions("sys:invoice:batchCancelDelivery")
    public R batchCancelDelivery(@RequestBody List<String> docEntryList) throws Exception {
        String msg = saleInvoiceService.batchCancelDelivery(docEntryList);
        return R.ok().put("msg", msg);
    }

    @RequestMapping("/batchupdatewhs")
    public R batchupdatewhs(@RequestBody List<Long> docEntryList) throws Exception {
        String msg = saleInvoiceService.batchupdatewhs(docEntryList);
        return R.ok().put("msg", msg);
    }

    @SysLog("提货")
    @RequestMapping("/takegoods")
    @RequiresPermissions("sys:invoice:takeGoods")
    public R batchTakeGoods(@RequestBody List<Long> docEntryList) throws Exception {
        String msg = saleInvoiceService.updateTakeGoods(docEntryList, "Y");
        return R.ok().put("msg", msg);
    }

    @SysLog("取消提货")
    @RequestMapping("/canceltake")
    @RequiresPermissions("sys:invoice:cancelTakeGoods")
    public R batchCancelTakeGoods(@RequestBody List<Long> docEntryList) throws Exception {
        String msg = saleInvoiceService.updateTakeGoods(docEntryList, "N");
        return R.ok().put("msg", msg);
    }
}
