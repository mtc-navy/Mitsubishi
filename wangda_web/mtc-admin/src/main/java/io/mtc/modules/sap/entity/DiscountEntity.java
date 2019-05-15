package io.mtc.modules.sap.entity;

import java.math.BigDecimal;

public class DiscountEntity {

    /**
     * 折扣代码
     */
    private String discCode;

    /**
     * 折扣名称
     */
    private String discName;

    /**
     * 可用余额
     */
    private BigDecimal canUserAmt;

    /**
     * 本次使用
     */
    private BigDecimal thisUserAmt;

    public String getDiscCode() {
        return discCode;
    }

    public void setDiscCode(String discCode) {
        this.discCode = discCode;
    }

    public String getDiscName() {
        return discName;
    }

    public void setDiscName(String discName) {
        this.discName = discName;
    }

    public BigDecimal getCanUserAmt() {
        return canUserAmt;
    }

    public void setCanUserAmt(BigDecimal canUserAmt) {
        this.canUserAmt = canUserAmt;
    }

    public BigDecimal getThisUserAmt() {
        return thisUserAmt;
    }

    public void setThisUserAmt(BigDecimal thisUserAmt) {
        this.thisUserAmt = thisUserAmt;
    }
}
