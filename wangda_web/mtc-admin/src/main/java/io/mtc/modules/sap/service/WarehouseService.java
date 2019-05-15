package io.mtc.modules.sap.service;

import io.mtc.modules.sap.entity.WarehouseEntity;

import java.util.List;

public interface WarehouseService {
    List<WarehouseEntity> searchCPByBPL(String bplid, String filterValue);

    List<WarehouseEntity> searchBSCByBPL(String bplid, String filterValue);

    List<WarehouseEntity> searchByUser(String bplid, String userName, String filterValue);

    List<WarehouseEntity> searchCBByUser(String bplid, String userName, String filterValue);
}
