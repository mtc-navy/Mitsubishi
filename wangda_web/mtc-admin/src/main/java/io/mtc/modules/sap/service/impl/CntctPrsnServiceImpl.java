package io.mtc.modules.sap.service.impl;

import io.mtc.common.utils.ObjectUtil;
import io.mtc.modules.sap.dao.CntcnPrsnDao;
import io.mtc.modules.sap.entity.CntctPrsnEntity;
import io.mtc.modules.sap.service.CntctPrsnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("cntctPrsnService")
public class CntctPrsnServiceImpl implements CntctPrsnService {

    @Autowired
    private CntcnPrsnDao cntcnPrsnDao;

    @Override
    public List<CntctPrsnEntity> search(String cardCode, String userName, String filterValue) {
        cardCode = ObjectUtil.nullToEmpty(cardCode).trim();
        userName = ObjectUtil.nullToEmpty(userName).trim();
        filterValue = ObjectUtil.nullToEmpty(filterValue).trim();

        List<CntctPrsnEntity> customerEntityList = cntcnPrsnDao.search(cardCode, userName, filterValue);
        return customerEntityList;
    }

}
