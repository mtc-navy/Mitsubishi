package io.mtc.modules.sap.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 出厂价
 */
public class ItemPriceEntity implements Serializable {

    private String itemCode;

    /**
     * 出厂价
     */
    private BigDecimal price;

    /**
     * 现折
     */
    private BigDecimal currDisc = new BigDecimal(0);

    /**
     * 是否标包
     */
    private String isPackage;

    /**
     * 单包重
     */
    private BigDecimal salFactor1 = BigDecimal.ONE;

    /**
     * 仓库代码
     */
    private String whsCode;

    /**
     * 仓库名称
     */
    private String whsName;

    /**
     * 提货分支仓库
     */
    private String takeWhsCode;

    private String regSupName;

    public BigDecimal getPrice() {
        if (price == null) {
            return new BigDecimal(0);
        }
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public ItemPriceEntity() {
    }

    public ItemPriceEntity(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getCurrDisc() {
        if (currDisc == null) {
            return new BigDecimal(0);
        }
        return currDisc;
    }

    public void setCurrDisc(BigDecimal currDisc) {
        this.currDisc = currDisc;
    }

    public String getIsPackage() {
        return isPackage;
    }

    public void setIsPackage(String isPackage) {
        this.isPackage = isPackage;
    }

    public BigDecimal getSalFactor1() {
        return salFactor1;
    }

    public void setSalFactor1(BigDecimal salFactor1) {
        this.salFactor1 = salFactor1;
    }

    public String getWhsCode() {
        return whsCode;
    }

    public void setWhsCode(String whsCode) {
        this.whsCode = whsCode;
    }

    public String getWhsName() {
        return whsName;
    }

    public void setWhsName(String whsName) {
        this.whsName = whsName;
    }

    public String getTakeWhsCode() {
        return takeWhsCode;
    }

    public void setTakeWhsCode(String takeWhsCode) {
        this.takeWhsCode = takeWhsCode;
    }

    public String getRegSupName() {
        return regSupName;
    }

    public void setRegSupName(String regSupName) {
        this.regSupName = regSupName;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }
}
