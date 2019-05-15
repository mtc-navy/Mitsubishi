package io.mtc.modules.sap.service.impl;

import io.mtc.common.utils.ObjectUtil;
import io.mtc.modules.sap.dao.BPLDao;
import io.mtc.modules.sap.entity.BPLEntity;
import io.mtc.modules.sap.service.BPLService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("bplService")
public class BPLServiceImpl implements BPLService {

    @Autowired
    private BPLDao bplDao;

    @Override
    public List<BPLEntity> search(String cardCode, String dataType, String userName, String filterValue) {
        cardCode = ObjectUtil.nullToEmpty(cardCode).trim();
        dataType = ObjectUtil.nullToEmpty(dataType).trim();
        userName = ObjectUtil.nullToEmpty(userName).trim();
        filterValue = ObjectUtil.nullToEmpty(filterValue).trim();

        List<BPLEntity> bplEntityList;
        if (StringUtils.isEmpty(filterValue)) {
            bplEntityList = bplDao.search(cardCode, dataType, userName);
        } else {
            bplEntityList = bplDao.searchWithKey(cardCode, dataType, userName, filterValue);
        }
        return bplEntityList;
    }

    @Override
    public List<BPLEntity> searchByUser(String userName, String filterValue) {
        if (filterValue != null)
            filterValue = "%" + filterValue + "%";
        else
            filterValue = "";
        return bplDao.searchByUser(userName, filterValue);
    }
}
