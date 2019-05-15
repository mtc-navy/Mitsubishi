package io.mtc.modules.sap.controller;

import io.mtc.common.annotation.SysLog;
import io.mtc.common.exception.RRException;
import io.mtc.common.utils.R;
import io.mtc.modules.sap.form.WarehouseForm;
import io.mtc.modules.sap.service.WarehouseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 仓库列表接口
 */
@RestController
@RequestMapping("/sap/warehouse")
@Api("仓库列表接口")
public class WarehouseController {

    @Autowired
    private WarehouseService warehouseService;

    @PostMapping("list")
    @ApiOperation("I0008-仓库列表：基地仓库列表")
    @SysLog("I0008-仓库列表")
    public R list() throws RRException {
        List<WarehouseForm> warehouseFormList = warehouseService.list();
        return R.ok().put("data", warehouseFormList);
    }

}
