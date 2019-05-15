package io.mtc.modules.sap.entity;

import java.io.Serializable;

/**
 * 收货人
 */
public class CntctPrsnEntity implements Serializable {

    /**
     * 客户编码
     */
    private String cardCode;

    /**
     * 收货人编码
     */
    private String cntctPrsn;

    /**
     * 收货人名称
     */
    private String cntctName;

    /**
     * 收货人联系电话
     */
    private String cntctPhone;

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

    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    public String getCntctPhone() {
        return cntctPhone;
    }

    public void setCntctPhone(String cntctPhone) {
        this.cntctPhone = cntctPhone;
    }
}
