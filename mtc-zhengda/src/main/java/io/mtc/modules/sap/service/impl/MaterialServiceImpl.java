package io.mtc.modules.sap.service.impl;

import io.mtc.modules.sap.dao.MaterialDao;
import io.mtc.modules.sap.form.MaterialForm;
import io.mtc.modules.sap.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 物料清单
 */
@Service("materialService")
public class MaterialServiceImpl implements MaterialService {

    @Autowired
    private MaterialDao materialDao;

    /**
     * 获取物料清单
     *
     * @param cardCode
     * @return
     */
    @Override
    public List<MaterialForm> list(String cardCode) {
        return materialDao.list(cardCode);
    }

}
