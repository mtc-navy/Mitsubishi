package io.mtc.modules.sap.service.impl;

import io.mtc.common.utils.ShiroUtils;
import io.mtc.modules.app.entity.UserEntity;
import io.mtc.modules.sap.dao.CustomerDao;
import io.mtc.modules.sap.form.CustomerForm;
import io.mtc.modules.sap.form.DiscountForm;
import io.mtc.modules.sap.param.DiscountParam;
import io.mtc.modules.sap.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 客户清单
 */
@Service("customerService")
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerDao customerDao;

    /**
     * 获取客户清单
     *
     * @return
     */
    @Override
    public List<CustomerForm> list() {
        return customerDao.list(ShiroUtils.getUserId());
    }

    /**
     * 客户信用及折扣相关接口
     *
     * @param discountParam
     * @return
     */
    @Override
    public List<DiscountForm> discount(DiscountParam discountParam) {
        return customerDao.discount(ShiroUtils.getUserId(), discountParam.getCardCode(), discountParam.getDocEntry());
    }
}
