package io.mtc.modules.sap.controller;

import io.mtc.common.annotation.SysLog;
import io.mtc.common.exception.RRException;
import io.mtc.common.utils.R;
import io.mtc.modules.sap.form.CustomerForm;
import io.mtc.modules.sap.form.DiscountForm;
import io.mtc.modules.sap.param.DiscountParam;
import io.mtc.modules.sap.service.CustomerService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 客户列表
 */
@RestController
@RequestMapping("/sap/customer/")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("list")
    @ApiOperation("I0005-客户列表：当前用户下的客户列表")
    @SysLog("I0005-客户列表")
    public R list() throws RRException {
        List<CustomerForm> customerFormList = customerService.list();
        return R.ok().put("data", customerFormList);
    }

    @PostMapping("discount")
    @ApiOperation("I0017-客户信用及折扣相关：根据客户得到客户信用、可用返利等销售折扣信息")
    @SysLog("I0017-客户信用及折扣相关")
    public R discount(@RequestBody DiscountParam discountParam) throws RRException {
        List<DiscountForm> discountFormList = customerService.discount(discountParam);
        return R.ok().put("data", discountFormList);
    }

}
