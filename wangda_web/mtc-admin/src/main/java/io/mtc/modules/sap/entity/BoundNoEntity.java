package io.mtc.modules.sap.entity;

import java.io.Serializable;

public class BoundNoEntity implements Serializable {

    /**
     * 过磅单号
     */
    private String boundNo;

    public String getBoundNo() {
        return boundNo;
    }

    public void setBoundNo(String boundNo) {
        this.boundNo = boundNo;
    }
}
