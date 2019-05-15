package io.mtc.modules.sap.service;

import io.mtc.modules.sap.form.CarForm;

import java.util.List;

/**
 * 车牌信息列表接口
 */
public interface CarService {

    /**
     * 获取仓库列表
     *
     * @return
     */
    List<CarForm> list();

}
