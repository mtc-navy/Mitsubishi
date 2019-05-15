package io.mtc.modules.sap.entity;

import java.io.Serializable;

/**
 * 分支
 */
public class BPLEntity implements Serializable {

    /**
     * 分支代码
     */
    private Integer BPLId;

    /**
     * 分支名称
     */
    private String BPLName;

    /**
     * 分支登记号
     */
    private Integer TaxIdNum;

    /**
     * 默认客户标识
     */
    private String DflCust;

    /**
     * 默认供应商标识
     */
    private String DflVendor;

    /**
     * 客户名称
     */
    private String CustName;

    /**
     * 客户联系人
     */
    private String CustCntct;

    /**
     * 供应商名称
     */
    private String VendorName;

    /**
     * 供应商联系人
     */
    private String VendorCntct;

    private String dflFlg;

    public Integer getBPLId() {
        return BPLId;
    }

    public void setBPLId(Integer BPLId) {
        this.BPLId = BPLId;
    }

    public String getBPLName() {
        return BPLName;
    }

    public void setBPLName(String BPLName) {
        this.BPLName = BPLName;
    }

    public Integer getTaxIdNum() {
        return TaxIdNum;
    }

    public void setTaxIdNum(Integer taxIdNum) {
        TaxIdNum = taxIdNum;
    }

    public String getDflCust() {
        return DflCust;
    }

    public void setDflCust(String dflCust) {
        DflCust = dflCust;
    }

    public String getDflVendor() {
        return DflVendor;
    }

    public void setDflVendor(String dflVendor) {
        DflVendor = dflVendor;
    }

    public String getCustName() {
        return CustName;
    }

    public void setCustName(String custName) {
        CustName = custName;
    }

    public String getVendorName() {
        return VendorName;
    }

    public void setVendorName(String vendorName) {
        VendorName = vendorName;
    }

    public String getCustCntct() {
        return CustCntct;
    }

    public void setCustCntct(String custCntct) {
        CustCntct = custCntct;
    }

    public String getVendorCntct() {
        return VendorCntct;
    }

    public void setVendorCntct(String vendorCntct) {
        VendorCntct = vendorCntct;
    }

    public String getDflFlg() {
        return dflFlg;
    }

    public void setDflFlg(String dflFlg) {
        this.dflFlg = dflFlg;
    }
}
