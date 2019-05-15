package io.mtc.modules.sap.service;

import io.mtc.modules.sap.entity.DiscountEntity;
import io.mtc.modules.sap.entity.PaymentEntity;

import java.util.List;

public interface PayDiscService {

    List<PaymentEntity> searchPay(String cardCode, String userName, String bplid, String filterValue);
}
