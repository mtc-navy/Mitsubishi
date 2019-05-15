package io.mtc.modules.sap.service.impl;

import io.mtc.modules.sap.dao.WarehouseDao;
import io.mtc.modules.sap.entity.WarehouseEntity;
import io.mtc.modules.sap.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("wareHouseService")
public class WarehouseImpl implements WarehouseService {

    @Autowired
    private WarehouseDao wareHouseDao;

    @Override
    public List<WarehouseEntity> searchCPByBPL(String bplid, String filterValue) {
        if (filterValue != null)
            filterValue = "%" + filterValue + "%";
        else
            filterValue = "";
        return wareHouseDao.searchCPByBPL(bplid, filterValue);
    }

    @Override
    public List<WarehouseEntity> searchBSCByBPL(String bplid, String filterValue) {
        if (filterValue != null)
            filterValue = "%" + filterValue + "%";
        else
            filterValue = "";
        return wareHouseDao.searchBSCByBPL(bplid, filterValue);
    }

    @Override
    public List<WarehouseEntity> searchByUser(String bplid, String userName, String filterValue) {
        if (filterValue != null)
            filterValue = "%" + filterValue + "%";
        else
            filterValue = "";
        return wareHouseDao.searchByUser(bplid, userName, filterValue);
    }

    @Override
    public List<WarehouseEntity> searchCBByUser(String bplid, String userName, String filterValue) {
        if (filterValue != null)
            filterValue = "%" + filterValue + "%";
        else
            filterValue = "";
        return wareHouseDao.searchCBByUser(bplid, userName, filterValue);
    }

}
