package io.mtc.modules.sap.service.impl;

import io.mtc.common.utils.ObjectUtil;
import io.mtc.modules.sap.dao.PaymentDao;
import io.mtc.modules.sap.entity.CustomerAmtEntity;
import io.mtc.modules.sap.entity.PaymentEntity;
import io.mtc.modules.sap.service.CustomerService;
import io.mtc.modules.sap.service.PayDiscService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Iterator;
import java.util.List;

@Service("payDiscService")
public class PayDiscServiceImpl implements PayDiscService {

    @Autowired
    private PaymentDao paymentDao;

    @Autowired
    private CustomerService customerService;


    @Override
    public List<PaymentEntity> searchPay(String cardCode, String userName, String bplid, String filterValue) {
        cardCode = ObjectUtil.nullToEmpty(cardCode).trim();
        userName = ObjectUtil.nullToEmpty(userName).trim();
        bplid = ObjectUtil.nullToEmpty(bplid).trim();
        filterValue = ObjectUtil.nullToEmpty(filterValue).trim();
        List<PaymentEntity> paymentEntities = paymentDao.search(cardCode, userName, bplid, filterValue);

        //查询往来款
        List<CustomerAmtEntity> customerAmtEntityList = customerService.getCustomerAmt(bplid, cardCode);
        if(CollectionUtils.isEmpty(customerAmtEntityList) || customerAmtEntityList.get(0).getActBalance() == null
                || customerAmtEntityList.get(0).getActBalance().doubleValue() <= 0.0){
            //如果往来款为0，则删除往来款这种收款方式
            Iterator<PaymentEntity> it = paymentEntities.iterator();
            while(it.hasNext()){
                PaymentEntity paymentEntity = it.next();
                if("往来款".equals(paymentEntity.getPayName())){
                    it.remove();
                }
            }
        }

        return paymentEntities;
    }
}
