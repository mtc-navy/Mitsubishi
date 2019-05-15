package io.mtc.modules.sap.entity;

import java.io.Serializable;

public class CarNoEntity implements Serializable {

    /**
     * 车牌号
     */
    private String carNo;

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }
}
