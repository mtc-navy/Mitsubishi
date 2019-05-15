package io.mtc.modules.sap.controller;

import io.mtc.common.annotation.SysLog;
import io.mtc.common.utils.PageUtils;
import io.mtc.common.utils.R;
import io.mtc.modules.sap.dto.SaleInvoiceEditDto;
import io.mtc.modules.sap.service.SaleReturnService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/sap/salertn")
public class SaleReturnController {

    @Autowired
    private SaleReturnService saleReturnService;

    /**
     * 查询销售退货明细
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:return:list")
    public R list(@RequestParam Map<String, Object> params) throws Exception {
        PageUtils page = saleReturnService.queryPage(params);
        return R.ok().put("page", page);
    }

    @SysLog("取消销售退货")
    @RequestMapping("/cancel")
    @RequiresPermissions("sys:return:cancel")
    public R cancel(Long docEntry) throws Exception {
        saleReturnService.cancel(docEntry);
        return R.ok();
    }

    @SysLog("新增销售退货")
    @RequestMapping("/save")
    @RequiresPermissions("sys:return:save")
    public R save(@RequestBody SaleInvoiceEditDto saleInvoiceEditDto) throws Exception {
        Integer docEntry = saleReturnService.save(saleInvoiceEditDto);
        return R.ok().put("docEntry", docEntry);
    }

    @SysLog("修改销售退货")
    @RequestMapping("/update")
    @RequiresPermissions("sys:return:update")
    public R update(@RequestBody SaleInvoiceEditDto saleInvoiceEditDto) throws Exception {
        saleReturnService.update(saleInvoiceEditDto);
        return R.ok();
    }

    @RequestMapping("/info")
    @RequiresPermissions("sys:return:info")
    public R info(Long docEntry) throws Exception {
        SaleInvoiceEditDto dto = saleReturnService.info(docEntry);
        return R.ok().put("result", dto);
    }

}
