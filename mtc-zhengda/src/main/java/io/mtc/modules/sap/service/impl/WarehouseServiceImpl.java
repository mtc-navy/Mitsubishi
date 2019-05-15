package io.mtc.modules.sap.service.impl;

import io.mtc.modules.sap.dao.WarehouseDao;
import io.mtc.modules.sap.form.WarehouseForm;
import io.mtc.modules.sap.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 仓库列表接口
 */
@Service("warehouseService")
public class WarehouseServiceImpl implements WarehouseService {

    @Autowired
    private WarehouseDao warehouseDao;

    /**
     * 获取仓库列表
     *
     * @return
     */
    @Override
    public List<WarehouseForm> list() {
        return warehouseDao.list();
    }

}
