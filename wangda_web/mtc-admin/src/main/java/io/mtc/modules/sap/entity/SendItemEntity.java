package io.mtc.modules.sap.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class SendItemEntity implements Serializable {

    /**
     * 选择
     */
    private String select;

    /**
     * 唯一序号
     */
    private String order;

    /**
     * 物料编码
     */
    private String itemCode;

    /**
     * 物料名称
     */
    private String itemName;

    /**
     * 赠包类型
     */
    private String sourceDN;

    /**
     * 折扣项代码
     */
    private String discCode;

    /**
     * 折扣项名称
     */
    private String disctName;

    /**
     * 可用赠包数
     */
    private BigDecimal discPack;

    /**
     * 已承诺赠包数
     */
    private BigDecimal openCreQty;

    /**
     * 使用赠包数
     */
    private BigDecimal useQty;

    /**
     * 仓库
     */
    private String stockName;

    public String getSelect() {
        return select;
    }

    public void setSelect(String select) {
        this.select = select;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getSourceDN() {
        return sourceDN;
    }

    public void setSourceDN(String sourceDN) {
        this.sourceDN = sourceDN;
    }

    public String getDiscCode() {
        return discCode;
    }

    public void setDiscCode(String discCode) {
        this.discCode = discCode;
    }

    public String getDisctName() {
        return disctName;
    }

    public void setDisctName(String disctName) {
        this.disctName = disctName;
    }

    public BigDecimal getDiscPack() {
        return discPack;
    }

    public void setDiscPack(BigDecimal discPack) {
        this.discPack = discPack;
    }

    public BigDecimal getOpenCreQty() {
        return openCreQty;
    }

    public void setOpenCreQty(BigDecimal openCreQty) {
        this.openCreQty = openCreQty;
    }

    public BigDecimal getUseQty() {
        return useQty;
    }

    public void setUseQty(BigDecimal useQty) {
        this.useQty = useQty;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
