package io.mtc.modules.sap.entity;

import java.math.BigDecimal;

public class GiftAmtEntity {

    private Integer SODocEntry;

    private String ItemCode;

    private String ItemName;

    private BigDecimal DiscAmt;

    private BigDecimal Qty;

    private Integer IFFresh;

    public Integer getSODocEntry() {
        return SODocEntry;
    }

    public void setSODocEntry(Integer SODocEntry) {
        this.SODocEntry = SODocEntry;
    }

    public String getItemCode() {
        return ItemCode;
    }

    public void setItemCode(String itemCode) {
        ItemCode = itemCode;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public BigDecimal getDiscAmt() {
        return DiscAmt;
    }

    public void setDiscAmt(BigDecimal discAmt) {
        DiscAmt = discAmt;
    }

    public BigDecimal getQty() {
        return Qty;
    }

    public void setQty(BigDecimal qty) {
        Qty = qty;
    }

    public Integer getIFFresh() {
        return IFFresh;
    }

    public void setIFFresh(Integer IFFresh) {
        this.IFFresh = IFFresh;
    }
}
