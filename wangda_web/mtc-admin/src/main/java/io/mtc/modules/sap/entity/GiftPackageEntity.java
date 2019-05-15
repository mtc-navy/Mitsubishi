package io.mtc.modules.sap.entity;

import java.math.BigDecimal;

public class GiftPackageEntity {

    private String type;

    private String itemCode;

    private BigDecimal discNum;

    private String dItemCode;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public BigDecimal getDiscNum() {
        return discNum;
    }

    public void setDiscNum(BigDecimal discNum) {
        this.discNum = discNum;
    }

    public String getdItemCode() {
        return dItemCode;
    }

    public void setdItemCode(String dItemCode) {
        this.dItemCode = dItemCode;
    }
}
