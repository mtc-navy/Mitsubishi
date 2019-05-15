package io.mtc.modules.sap.controller;

import io.mtc.common.annotation.SysLog;
import io.mtc.common.exception.RRException;
import io.mtc.common.utils.R;
import io.mtc.modules.sap.entity.MtcWRDREntity;
import io.mtc.modules.sap.entity.MtcWUQTEntity;
import io.mtc.modules.sap.form.WorkOrderForm;
import io.mtc.modules.sap.param.*;
import io.mtc.modules.sap.service.WorkOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 提货单接口
 */
@RestController
@RequestMapping("/sap/workorder")
@Api("提货单接口")
public class WorkOrderController {

    @Autowired
    private WorkOrderService workOrderService;


    @PostMapping("insert")
    @ApiOperation("I0018-提货单生成：根据计划单生成提货单")
    @SysLog("I0018-提货单生成")
    public R insert(@RequestBody WorkOrderForm workOrderForm) throws RRException {
        workOrderForm = workOrderService.insert(workOrderForm);
        return R.ok().put("data", workOrderForm);
    }

    @PostMapping("list")
    @ApiOperation("I0019-提货单列表：根据条件列出当前用户下所有提货单列表")
    @SysLog("I0019-提货单列表")
    public R list(@RequestBody SearchParam searchParam) throws RRException {
        List<MtcWRDREntity> mtcWRDREntityList = workOrderService.list(searchParam);
        return R.ok().put("data", mtcWRDREntityList);
    }

    @PostMapping("info")
    @ApiOperation("I0020-查看提货单：查看具体提货单内容")
    @SysLog("I0020-查看提货单")
    public R info(@RequestBody KeyParam keyParam) throws RRException {
        WorkOrderForm planForm = workOrderService.info(keyParam);
        return R.ok().put("data", planForm);
    }

    @PostMapping("update")
    @ApiOperation("I0021-修改提货单：修改提货单时数据校验并更新")
    @SysLog("I0021-修改提货单")
    public R update(@RequestBody WorkOrderUpdateParam workOrderUpdateParam) throws RRException {
        String msg = workOrderService.update(workOrderUpdateParam);
        return R.ok().put("data", msg);
    }

    @PostMapping("closeitem")
    @ApiOperation("I0022-关闭物料：关闭指定提货单中的物料")
    @SysLog("I0022-关闭物料")
    public R closeitem(@RequestBody LineKeyParam lineKeyParam) throws RRException {
        String msg = workOrderService.closeitem(lineKeyParam);
        return R.ok().put("data", msg);
    }

    @PostMapping("close")
    @ApiOperation("I0015-关闭计划单：关闭当前计划单")
    @SysLog("I0015-关闭计划单")
    public R close(@RequestBody KeyParam keyParam) throws RRException {
        workOrderService.close(keyParam);
        return R.ok();
    }

    @PostMapping("cancel")
    @ApiOperation("I0026-取消计划单：取消当前计划单")
    @SysLog("I0026-取消计划单")
    public R cancel(@RequestBody KeyParam keyParam) throws RRException {
        workOrderService.cancel(keyParam);
        return R.ok();
    }
}
