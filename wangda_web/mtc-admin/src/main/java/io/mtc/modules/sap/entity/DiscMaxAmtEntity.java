package io.mtc.modules.sap.entity;

import java.math.BigDecimal;

public class DiscMaxAmtEntity {

    /**
     * 折扣维护单号
     */
    private Integer ZKDocEntry;

    /**
     * 还差多少金额可用进行计算随车赠
     */
    private BigDecimal DiffAmt;

    /**
     * 单价
     */
    private BigDecimal UnitPrice;

    public Integer getZKDocEntry() {
        return ZKDocEntry;
    }

    public void setZKDocEntry(Integer ZKDocEntry) {
        this.ZKDocEntry = ZKDocEntry;
    }

    public BigDecimal getDiffAmt() {
        return DiffAmt;
    }

    public void setDiffAmt(BigDecimal diffAmt) {
        DiffAmt = diffAmt;
    }

    public BigDecimal getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        UnitPrice = unitPrice;
    }
}
