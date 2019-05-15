package io.mtc.modules.sap.service;

import io.mtc.modules.sap.form.WarehouseForm;

import java.util.List;

/**
 * 仓库列表接口
 */
public interface WarehouseService {

    /**
     * 获取仓库列表
     *
     * @return
     */
    List<WarehouseForm> list();

}
