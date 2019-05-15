package io.mtc.modules.sap.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 物料信息
 */
public class ItemEntity implements Serializable {

    /**
     * 物料代码
     */
    private String itemCode;

    /**
     * 物料名称
     */
    private String itemName;

    /**
     * 出厂价
     */
    private BigDecimal factPrice;

    /**
     * 现折
     */
    private BigDecimal currDisc;

    /**
     * 是否赠料
     */
    private String isGiveGD;

    /**
     * 仓库代码
     */
    private String whsCode;

    /**
     * 仓库名称
     */
    private String whsName;

    /**
     * 是否标包
     */
    private String isPackage;

    /**
     * 规格
     */
    private String spec;

    /**
     * 单包重
     */
    private BigDecimal salFactor1 = new BigDecimal(1);

    /**
     * 单包数
     */
    private BigDecimal salFactor2 = new BigDecimal(1);

    /**
     * 已使用数量
     */
    private BigDecimal ableQty;

    /**
     * 剩余数量
     */
    private BigDecimal useQty;

    private BigDecimal stock;

    /**
     * 简称
     */
    private String shortName;

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public BigDecimal getFactPrice() {
        return factPrice;
    }

    public void setFactPrice(BigDecimal factPrice) {
        this.factPrice = factPrice;
    }

    public BigDecimal getCurrDisc() {
        return currDisc;
    }

    public void setCurrDisc(BigDecimal currDisc) {
        this.currDisc = currDisc;
    }

    public String getIsGiveGD() {
        return isGiveGD;
    }

    public void setIsGiveGD(String isGiveGD) {
        this.isGiveGD = isGiveGD;
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

    public String getIsPackage() {
        return isPackage;
    }

    public void setUIsPackage(String isPackage) {
        this.isPackage = isPackage;
    }

    public BigDecimal getSalFactor1() {
        return salFactor1;
    }

    public void setSalFactor1(BigDecimal salFactor1) {
        this.salFactor1 = salFactor1;
    }

    public BigDecimal getStock() {
        return stock;
    }

    public void setStock(BigDecimal stock) {
        this.stock = stock;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public BigDecimal getSalFactor2() {
        return salFactor2;
    }

    public void setSalFactor2(BigDecimal salFactor2) {
        this.salFactor2 = salFactor2;
    }

    public BigDecimal getUseQty() {
        return useQty;
    }

    public void setUseQty(BigDecimal useQty) {
        this.useQty = useQty;
    }

    public BigDecimal getAbleQty() {
        return ableQty;
    }

    public void setAbleQty(BigDecimal ableQty) {
        this.ableQty = ableQty;
    }

    public void setIsPackage(String isPackage) {
        this.isPackage = isPackage;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
}
