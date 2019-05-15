package io.mtc.modules.sap.entity;

import java.math.BigDecimal;

public class CustomerAmtEntity {

    /**
     * 折扣公斤
     */
    private BigDecimal discPack;

    /**
     * 往来款
     */
    private BigDecimal actBalance;

    /**
     * 折扣余额
     */
    private BigDecimal discAmt;

    /**
     * 可用余额
     */
    private BigDecimal custAmt;

    public BigDecimal getDiscPack() {
        return discPack;
    }

    public void setDiscPack(BigDecimal discPack) {
        this.discPack = discPack;
    }

    public BigDecimal getActBalance() {
        return actBalance;
    }

    public void setActBalance(BigDecimal actBalance) {
        this.actBalance = actBalance;
    }

    public BigDecimal getDiscAmt() {
        return discAmt;
    }

    public void setDiscAmt(BigDecimal discAmt) {
        this.discAmt = discAmt;
    }

    public BigDecimal getCustAmt() {
        return custAmt;
    }

    public void setCustAmt(BigDecimal custAmt) {
        this.custAmt = custAmt;
    }
}
