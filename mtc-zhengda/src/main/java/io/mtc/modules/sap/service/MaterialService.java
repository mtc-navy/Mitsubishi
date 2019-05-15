package io.mtc.modules.sap.service;

import io.mtc.modules.sap.form.MaterialForm;

import java.util.List;

/**
 * 物料清单
 */
public interface MaterialService {

    /**
     * 获取物料清单
     *
     * @param cardCode
     * @return
     */
    List<MaterialForm> list(String cardCode);

}
