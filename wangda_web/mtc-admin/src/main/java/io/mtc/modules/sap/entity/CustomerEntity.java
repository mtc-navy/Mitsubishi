package io.mtc.modules.sap.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 客户实体
 */
public class CustomerEntity implements Serializable {

    /**
     * 客户代码
     */
    private String cardCode;

    /**
     * 客户名称
     */
    private String cardName;

    /**
     * 收货人编码
     */
    private String cntctPrsn;

    /**
     * 收货人名称
     */
    private String cntctName;

    /**
     * 可用额度
     */
    private BigDecimal canUseCost;

    /**
     * 往来款
     */
    private BigDecimal inOutAmt;

    /**
     * 折扣余额
     */
    private BigDecimal discAmt;

    /**
     * 可用金额
     */
    private BigDecimal canUseAmt;

    /**
     * 简称
     */
    private String shortName;

    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCntctPrsn() {
        return cntctPrsn;
    }

    public void setCntctPrsn(String cntctPrsn) {
        this.cntctPrsn = cntctPrsn;
    }

    public String getCntctName() {
        return cntctName;
    }

    public void setCntctName(String cntctName) {
        this.cntctName = cntctName;
    }

    public BigDecimal getCanUseCost() {
        return canUseCost;
    }

    public void setCanUseCost(BigDecimal canUseCost) {
        this.canUseCost = canUseCost;
    }

    public BigDecimal getInOutAmt() {
        return inOutAmt;
    }

    public void setInOutAmt(BigDecimal inOutAmt) {
        this.inOutAmt = inOutAmt;
    }

    public BigDecimal getDiscAmt() {
        return discAmt;
    }

    public void setDiscAmt(BigDecimal discAmt) {
        this.discAmt = discAmt;
    }

    public BigDecimal getCanUseAmt() {
        return canUseAmt;
    }

    public void setCanUseAmt(BigDecimal canUseAmt) {
        this.canUseAmt = canUseAmt;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
}
