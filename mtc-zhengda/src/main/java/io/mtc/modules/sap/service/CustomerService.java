package io.mtc.modules.sap.service;

import io.mtc.modules.sap.form.CustomerForm;
import io.mtc.modules.sap.form.DiscountForm;
import io.mtc.modules.sap.param.DiscountParam;

import java.util.List;

/**
 * 客户清单
 */
public interface CustomerService {

    /**
     * 获取客户清单
     *
     * @return
     */
    List<CustomerForm> list();

    /**
     * 客户信用及折扣相关接口
     *
     * @param discountParam
     * @return
     */
    List<DiscountForm> discount(DiscountParam discountParam);

}
