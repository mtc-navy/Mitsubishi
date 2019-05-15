package io.mtc.modules.sap.controller;

import io.mtc.common.annotation.SysLog;
import io.mtc.common.exception.RRException;
import io.mtc.common.utils.R;
import io.mtc.modules.sap.form.CarForm;
import io.mtc.modules.sap.service.CarService;
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
@RequestMapping("/sap/car")
@Api("车牌号列表接口")
public class CarController {

    @Autowired
    private CarService carService;

    @PostMapping("list")
    @ApiOperation("I0016-车牌号列表：输出可用车牌号列表")
    @SysLog("I0016-车牌号列表")
    public R list() throws RRException {
        List<CarForm> carFormList = carService.list();
        return R.ok().put("data", carFormList);
    }

}
