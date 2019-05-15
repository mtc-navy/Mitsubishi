package io.mtc.modules.sap.controller;

import io.mtc.common.annotation.SysLog;
import io.mtc.common.exception.RRException;
import io.mtc.common.utils.R;
import io.mtc.modules.sap.dto.PriceOrderDto;
import io.mtc.modules.sap.service.PriceOrderService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 保价单Controller
 */
@RestController
@RequestMapping("/sap/priceorder")
public class PriceOrderController {

    @Autowired
    private PriceOrderService priceOrderService;

    @RequestMapping("/info")
    @RequiresPermissions(value = {"sys:priceorder:info", "sys:priceorder:update"}, logical = Logical.OR)
    public R info(Integer docentry) throws RRException {
        PriceOrderDto priceOrderDto = priceOrderService.info(docentry);
        return R.ok().put("data", priceOrderDto);
    }

    @SysLog("新增保价单")
    @RequestMapping("/add")
    @RequiresPermissions("sys:priceorder:add")
    public R add(@RequestBody PriceOrderDto priceOrderDto) throws RRException {
        priceOrderService.save(priceOrderDto, false);
        return R.ok().put("data", priceOrderDto);
    }

    @SysLog("修改保价单")
    @RequestMapping("/update")
    @RequiresPermissions("sys:priceorder:update")
    public R update(@RequestBody PriceOrderDto priceOrderDto) throws RRException {
        priceOrderService.save(priceOrderDto, true);
        return R.ok().put("data", priceOrderDto);
    }

    @SysLog("取消保价单")
    @RequestMapping("cancel")
    @RequiresPermissions("sys:priceorder:cancel")
    public R cancel(Integer docentry) throws RRException {
        priceOrderService.cancel(docentry);
        return R.ok();
    }

    @SysLog("关闭保价单")
    @RequestMapping("close")
    @RequiresPermissions("sys:priceorder:close")
    public R close(Integer docentry) throws RRException {
        priceOrderService.close(docentry);
        return R.ok();
    }

    @SysLog("保价单生成收款单")
    @RequiresPermissions("sys:priceorder:receipt")
    @RequestMapping("/receipt")
    public R receipt(Integer docentry) throws RRException {
        priceOrderService.receipt(docentry);
        return R.ok();
    }

    @SysLog("保价单取消收款单")
    @RequiresPermissions("sys:priceorder:cancelrpt")
    @RequestMapping("/cancelrpt")
    public R cancelrpt(Integer docentry) throws RRException {
        priceOrderService.cancelrpt(docentry);
        return R.ok();
    }
}
