package io.mtc.modules.sap.controller;

import io.mtc.common.annotation.SysLog;
import io.mtc.common.exception.RRException;
import io.mtc.common.utils.R;
import io.mtc.modules.sap.form.WorkOrderForm;
import io.mtc.modules.sap.param.KeyParam;
import io.mtc.modules.sap.service.WorkOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 提货单接口
 */
@RestController
@RequestMapping("/sap/workorder")
@Api("提货单接口")
public class WorkOrderController {

    @Autowired
    private WorkOrderService workOrderService;

    @PostMapping("info")
    @ApiOperation("I0020-查看提货单：查看具体提货单内容")
    @SysLog("I0020-查看提货单")
    public R info(@RequestBody KeyParam keyParam) throws RRException {
        WorkOrderForm planForm = workOrderService.info(keyParam);
        return R.ok().put("data", planForm);
    }
}
