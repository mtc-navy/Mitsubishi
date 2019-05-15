package io.mtc.modules.sap.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class DiscTypeInfoEntity implements Serializable {

    private String UIsTprice;

    private Integer DocEntry;

    private String UItemCode;

    private BigDecimal UDiscBase;

    private BigDecimal UDiscNum;

    private String UDItemCode;

    private BigDecimal UDiscBase2;

    private BigDecimal UDiscNum2;

    private BigDecimal SalFactor1;

    public String getUIsTprice() {
        return UIsTprice;
    }

    public void setUIsTprice(String UIsTprice) {
        this.UIsTprice = UIsTprice;
    }

    public Integer getDocEntry() {
        return DocEntry;
    }

    public void setDocEntry(Integer docEntry) {
        DocEntry = docEntry;
    }

    public String getUItemCode() {
        return UItemCode;
    }

    public void setUItemCode(String UItemCode) {
        this.UItemCode = UItemCode;
    }

    public BigDecimal getUDiscBase() {
        return UDiscBase;
    }

    public void setUDiscBase(BigDecimal UDiscBase) {
        this.UDiscBase = UDiscBase;
    }

    public BigDecimal getUDiscNum() {
        return UDiscNum;
    }

    public void setUDiscNum(BigDecimal UDiscNum) {
        this.UDiscNum = UDiscNum;
    }

    public String getUDItemCode() {
        return UDItemCode;
    }

    public void setUDItemCode(String UDItemCode) {
        this.UDItemCode = UDItemCode;
    }

    public BigDecimal getUDiscBase2() {
        return UDiscBase2;
    }

    public void setUDiscBase2(BigDecimal UDiscBase2) {
        this.UDiscBase2 = UDiscBase2;
    }

    public BigDecimal getUDiscNum2() {
        return UDiscNum2;
    }

    public void setUDiscNum2(BigDecimal UDiscNum2) {
        this.UDiscNum2 = UDiscNum2;
    }

    public BigDecimal getSalFactor1() {
        return SalFactor1;
    }

    public void setSalFactor1(BigDecimal salFactor1) {
        SalFactor1 = salFactor1;
    }
}
