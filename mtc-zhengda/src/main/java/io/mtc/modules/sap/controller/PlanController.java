package io.mtc.modules.sap.controller;

import io.mtc.common.annotation.SysLog;
import io.mtc.common.exception.RRException;
import io.mtc.common.utils.R;
import io.mtc.modules.sap.entity.MtcWUQTEntity;
import io.mtc.modules.sap.form.EarliestDateForm;
import io.mtc.modules.sap.form.MaterialForm;
import io.mtc.modules.sap.form.PlanForm;
import io.mtc.modules.sap.param.*;
import io.mtc.modules.sap.service.PlanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 计划单接口
 */
@RestController
@RequestMapping("/sap/plan")
@Api("计划单接口")
public class PlanController {

    @Autowired
    private PlanService planService;

    @PostMapping("premeteriallist")
    @ApiOperation("I0007-上次选择物料列表：最后一次计划单所下的物料列表，用于快捷选择")
    @SysLog("I0007-上次选择物料列表")
    public R premeteriallist(@RequestBody ItemParam itemParam) throws RRException {
        List<MaterialForm> materialFormList = planService.premeteriallist(itemParam.getCardCode());
        return R.ok().put("data", materialFormList);
    }

    @PostMapping("calearliestdate")
    @ApiOperation("I0009-提货日期计算：根据不同的存货分类规则设置不同提货日期限制")
    @SysLog("I0009-提货日期计算")
    public R calearliestdate(@RequestBody CalEarliestDateParam calEarliestDateParam) throws RRException {
        List<EarliestDateForm> earliestDateFormList = planService.calearliestdate(calEarliestDateParam);
        return R.ok().put("data", earliestDateFormList);
    }

    @PostMapping("insert")
    @ApiOperation("I0010-计划单生成：根据输入校验后生成计划单")
    @SysLog("I0010-计划单生成")
    public R insert(@RequestBody PlanForm planForm) throws RRException {
        planForm = planService.insert(planForm);
        return R.ok().put("data", planForm);
    }


    @PostMapping("list")
    @ApiOperation("I0011-计划单列表：当前用户下指定条件的计划单列表")
    @SysLog("I0011-计划单列表")
    public R list(@RequestBody SearchParam searchParam) throws RRException {
        List<MtcWUQTEntity> mtcWUQTEntityList = planService.list(searchParam);
        return R.ok().put("data", mtcWUQTEntityList);
    }

    @PostMapping("info")
    @ApiOperation("I0012-查看计划单：查看具体计划单内容")
    @SysLog("I0012-查看计划单")
    public R info(@RequestBody KeyParam keyParam) throws RRException {
        PlanForm planForm = planService.info(keyParam);
        return R.ok().put("data", planForm);
    }

    @PostMapping("update")
    @ApiOperation("I0013-修改计划单：修改计划单时数据校验更新")
    @SysLog("I0013-修改计划单")
    public R update(@RequestBody PlanUpdateParam planUpdateParam) throws RRException {
        String msg = planService.update(planUpdateParam);
        return R.ok().put("data", msg);
    }

    @PostMapping("closeitem")
    @ApiOperation("I0014-关闭物料：关闭指定计划单中的物料")
    @SysLog("I0014-关闭物料")
    public R closeitem(@RequestBody LineKeyParam lineKeyParam) throws RRException {
        String msg = planService.closeitem(lineKeyParam);
        return R.ok().put("data", msg);
    }

    @PostMapping("close")
    @ApiOperation("I0015-关闭计划单：关闭当前计划单")
    @SysLog("I0015-关闭计划单")
    public R close(@RequestBody KeyParam keyParam) throws RRException {
        planService.close(keyParam);
        return R.ok();
    }

    @PostMapping("cancel")
    @ApiOperation("I0026-取消计划单：取消当前计划单")
    @SysLog("I0026-取消计划单")
    public R cancel(@RequestBody KeyParam keyParam) throws RRException {
        planService.cancel(keyParam);
        return R.ok();
    }

    @PostMapping("avgcheck")
    @ApiOperation("I0028-销售量校验：3个月平均销售量校验")
    @SysLog("I0028-销售量校验")
    public R avgcheck(@RequestBody AvgCheckParam avgCheckParam) throws RRException {
        String msg = planService.avgcheck(avgCheckParam);
        return R.ok().put("data", msg);
    }
}
