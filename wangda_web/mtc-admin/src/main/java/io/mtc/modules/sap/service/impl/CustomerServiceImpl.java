package io.mtc.modules.sap.service.impl;

import io.mtc.common.utils.ObjectUtil;
import io.mtc.modules.sap.dao.CarBoundDao;
import io.mtc.modules.sap.dao.CustomerAmtDao;
import io.mtc.modules.sap.dao.CustomerDao;
import io.mtc.modules.sap.entity.BoundNoEntity;
import io.mtc.modules.sap.entity.CarNoEntity;
import io.mtc.modules.sap.entity.CustomerAmtEntity;
import io.mtc.modules.sap.entity.CustomerEntity;
import io.mtc.modules.sap.service.CustomerService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service("customerService")
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private CarBoundDao carBoundDao;

    @Autowired
    private CustomerAmtDao customerAmtDao;

    @Override
    public List<CustomerEntity> GetList(String userName, String dataType, String filterValue) {
        userName = ObjectUtil.nullToEmpty(userName).trim();
        dataType = ObjectUtil.nullToEmpty(dataType).trim();
        filterValue = ObjectUtil.nullToEmpty(filterValue).trim();

        List<CustomerEntity> customerEntityList = customerDao.getCustomerList(userName, dataType, filterValue);
        return customerEntityList;
    }

    @Override
    public List<CarNoEntity> getCarNoList(String cardCode, String carNo, String taxDate,String salesBranch,String takeBranch) {
        cardCode = ObjectUtil.nullToEmpty(cardCode).trim();
        carNo = ObjectUtil.nullToEmpty(carNo).trim();

        String bplId = takeBranch;
        if(StringUtils.isEmpty(takeBranch) || "NaN".equals(takeBranch)){
            bplId = salesBranch;
        }

        List<CarNoEntity> carNoEntityList = carBoundDao.getCarNoList(cardCode, carNo, taxDate,bplId);
        return carNoEntityList;
    }

    @Override
    public List<BoundNoEntity> getBoundNoList(String cardCode, String carNo, String taxDate) {
        cardCode = ObjectUtil.nullToEmpty(cardCode).trim();
        carNo = ObjectUtil.nullToEmpty(carNo).trim();
        if(StringUtils.isEmpty(carNo)){
            return Collections.EMPTY_LIST;
        }

        List<BoundNoEntity> boundNoEntityList = carBoundDao.getBoundNoList(cardCode, carNo, taxDate);
        return boundNoEntityList;
    }

    @Override
    public List<CustomerAmtEntity> getCustomerAmt(String bplid, String cardCode) {
        bplid = ObjectUtil.nullToEmpty(bplid).trim();
        cardCode = ObjectUtil.nullToEmpty(cardCode).trim();

        List<CustomerAmtEntity> customerAmtEntityList = customerAmtDao.search(bplid, cardCode);
        return customerAmtEntityList;
    }
}
