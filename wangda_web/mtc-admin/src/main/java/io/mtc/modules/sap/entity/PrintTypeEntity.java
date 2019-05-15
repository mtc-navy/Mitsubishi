package io.mtc.modules.sap.entity;

import java.io.Serializable;

public class PrintTypeEntity implements Serializable {

    /**
     * 打印类型
     */
    private String printType;

    /**
     * 业务伙伴代码
     */
    private String cardCode;

    /**
     * 业务伙伴名称
     */
    private String cardName;

    public String getPrintType() {
        return printType;
    }

    public void setPrintType(String printType) {
        this.printType = printType;
    }

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
}
