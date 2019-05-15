package io.mtc.modules.sap.entity;

import java.math.BigDecimal;

/**
 * 物料组因子
 */
public class ItemFacEntity {

    private String ItemCode;

    private BigDecimal SalFactor1;

    public String getItemCode() {
        return ItemCode;
    }

    public void setItemCode(String itemCode) {
        ItemCode = itemCode;
    }

    public BigDecimal getSalFactor1() {
        return SalFactor1;
    }

    public void setSalFactor1(BigDecimal salFactor1) {
        SalFactor1 = salFactor1;
    }
}
