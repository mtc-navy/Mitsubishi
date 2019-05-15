package io.mtc.modules.sap.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class DistNumberEntity implements Serializable {

    private String distNumber;

    private String inDate;

    private BigDecimal quantity;

    public String getDistNumber() {
        return distNumber;
    }

    public void setDistNumber(String distNumber) {
        this.distNumber = distNumber;
    }

    public String getInDate() {
        return inDate;
    }

    public void setInDate(String inDate) {
        this.inDate = inDate;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }
}
