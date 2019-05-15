package io.mtc.modules.sap.service;

import io.mtc.modules.sap.entity.BPLEntity;

import java.util.List;

public interface BPLService {

    List<BPLEntity> search(String cardCode, String dataType, String userName, String filterValue);

    List<BPLEntity> searchByUser(String userName, String filterValue);
}
