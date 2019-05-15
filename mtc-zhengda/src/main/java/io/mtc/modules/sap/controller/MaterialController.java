package io.mtc.modules.sap.controller;

import io.mtc.common.annotation.SysLog;
import io.mtc.common.exception.RRException;
import io.mtc.common.utils.R;
import io.mtc.modules.sap.form.MaterialForm;
import io.mtc.modules.sap.param.ItemParam;
import io.mtc.modules.sap.service.MaterialService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 物料列表
 */
@RestController
@RequestMapping("/sap/material/")
public class MaterialController {

    @Autowired
    private MaterialService materialService;

    @PostMapping("list")
    @ApiOperation("I0006-物料列表：选定客户下的物料列表")
    @SysLog("I0006-物料列表")
    public R list(@RequestBody ItemParam itemParam) throws RRException {
        List<MaterialForm> materialFormList = materialService.list(itemParam.getCardCode());
        return R.ok().put("data", materialFormList);
    }

}
