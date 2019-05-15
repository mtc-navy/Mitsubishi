package io.mtc.modules.sap.service.impl;

import io.mtc.common.utils.ShiroUtils;
import io.mtc.modules.sap.dao.CarDao;
import io.mtc.modules.sap.form.CarForm;
import io.mtc.modules.sap.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 车牌信息列表接口
 */
@Service("carService")
public class CarServiceImpl implements CarService {

    @Autowired
    private CarDao carDao;

    /**
     * 获取仓库列表
     *
     * @return
     */
    @Override
    public List<CarForm> list() {
        return carDao.list(ShiroUtils.getUserId());
    }

}
