package io.mtc.modules.sap.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 收款方式
 */
public class PaymentEntity implements Serializable {

    /**
     * 收款方式码
     */
    private String payCode;

    /**
     * 收款方式名称
     */
    private String payName;

    /**
     * 金额
     */
    private BigDecimal payAmt;

    /**
     * 收款单号
     */
    private String payDocNum;

    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }

    public String getPayName() {
        return payName;
    }

    public void setPayName(String payName) {
        this.payName = payName;
    }

    public BigDecimal getPayAmt() {
        return payAmt;
    }

    public void setPayAmt(BigDecimal payAmt) {
        this.payAmt = payAmt;
    }

    public String getPayDocNum() {
        return payDocNum;
    }

    public void setPayDocNum(String payDocNum) {
        this.payDocNum = payDocNum;
    }
}
