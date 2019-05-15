package io.mtc.modules.sap.service;

import io.mtc.modules.sap.entity.CntctPrsnEntity;

import java.util.List;

public interface CntctPrsnService {

    List<CntctPrsnEntity> search(String cardCode, String userName, String filterValue);
}
