package io.mtc.modules.sap.service;

import io.mtc.modules.sap.entity.BoundNoEntity;
import io.mtc.modules.sap.entity.CarNoEntity;
import io.mtc.modules.sap.entity.CustomerAmtEntity;
import io.mtc.modules.sap.entity.CustomerEntity;

import java.util.List;

public interface CustomerService {

    List<CustomerEntity> GetList(String userName, String dataType, String filterValue);

    List<CarNoEntity> getCarNoList(String cardCode, String carNo, String taxDate,String salesBranch,String takeBranch);

    List<BoundNoEntity> getBoundNoList(String cardCode, String carNo, String taxDate);

    List<CustomerAmtEntity> getCustomerAmt(String bplid, String cardCode);
}
