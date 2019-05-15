package io.mtc.modules.sap.controller;

import io.mtc.common.annotation.SysLog;
import io.mtc.common.exception.RRException;
import io.mtc.common.utils.R;
import io.mtc.modules.sap.dto.DesOrderDto;
import io.mtc.modules.sap.service.DesOrderService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订料单Controller
 */
@RestController
@RequestMapping("/sap/desorder")
public class DesOrderController {

    @Autowired
    private DesOrderService desOrderService;

    @RequestMapping("/info")
    @RequiresPermissions(value = {"sys:desorder:info", "sys:desorder:update"}, logical = Logical.OR)
    public R info(Integer docentry) throws RRException {
        DesOrderDto desOrderDto = desOrderService.info(docentry);
        return R.ok().put("data", desOrderDto);
    }

    @SysLog("新增订料单")
    @RequestMapping("/add")
    @RequiresPermissions("sys:desorder:add")
    public R add(@RequestBody DesOrderDto desOrderDto) throws RRException {
        desOrderService.save(desOrderDto, false);
        return R.ok().put("data", desOrderDto);
    }

    @SysLog("修改订料单")
    @RequestMapping("/update")
    @RequiresPermissions("sys:desorder:update")
    public R update(@RequestBody DesOrderDto desOrderDto) throws RRException {
        desOrderService.save(desOrderDto, true);
        return R.ok().put("data", desOrderDto);
    }

    @SysLog("取消订料单")
    @RequestMapping("cancel")
    @RequiresPermissions("sys:desorder:cancel")
    public R cancel(Integer docentry) throws RRException {
        desOrderService.cancel(docentry);
        return R.ok();
    }

    @SysLog("关闭订料单")
    @RequestMapping("close")
    @RequiresPermissions("sys:desorder:close")
    public R close(Integer docentry) throws RRException {
        desOrderService.close(docentry);
        return R.ok();
    }

    @SysLog("订料单生成收款单")
    @RequiresPermissions("sys:desorder:receipt")
    @RequestMapping("/receipt")
    public R receipt(Integer docentry) throws RRException {
        desOrderService.receipt(docentry);
        return R.ok();
    }

    @SysLog("订料单取消收款单")
    @RequiresPermissions("sys:desorder:cancelrpt")
    @RequestMapping("/cancelrpt")
    public R cancelrpt(Integer docentry) throws RRException {
        desOrderService.cancelrpt(docentry);
        return R.ok();
    }
}
