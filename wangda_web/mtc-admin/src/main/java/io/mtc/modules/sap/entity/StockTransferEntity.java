package io.mtc.modules.sap.entity;

import io.mtc.common.utils.DateUtils;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

/**
 * 转储单
 */
public class StockTransferEntity implements Serializable {

    /**
     * DocEntry
     */
    private BigInteger DocEntry;

    /**
     * 类型：Z 转储; R 申请
     */
    private String TransferType;


    /**
     * 类型名称
     */
    private String TransferTypeName;

    /**
     * 单号
     */
    private String DocNum;

    /**
     * 交货日期
     */
    private Date TaxDate;

    /**
     * 分支
     */
    private Integer BPLId;

    /**
     * 分支名称
     */
    private String BPLName;

    /**
     * 源仓库代码
     */
    private String FromWhsCode;

    /**
     * 源仓库名称
     */
    private String FromWhsName;

    /**
     * 目标仓库代码
     */
    private String ToWhsCode;

    /**
     * 目标仓库名称
     */
    private String ToWhsName;

    /**
     * 订单状态
     */
    private String DocStatus;

    /**
     * 订单状态
     */
    private String DocStatusName;

    /**
     * 创建人
     */
    private String Creator;

    /**
     * 备注
     */
    private String Comments;

    public BigInteger getDocEntry() {
        return DocEntry;
    }

    public void setDocEntry(BigInteger docEntry) {
        DocEntry = docEntry;
    }

    public String getDocNum() {
        return DocNum;
    }

    public void setDocNum(String docNum) {
        DocNum = docNum;
    }

    public Date getTaxDate() {
        return TaxDate;
    }

    public String getTaxDateStr() {
        if (TaxDate != null) {
            return DateUtils.format(TaxDate);
        }
        return null;
    }

    public void setTaxDate(Date taxDate) {
        TaxDate = taxDate;
    }

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

    public String getFromWhsCode() {
        return FromWhsCode;
    }

    public void setFromWhsCode(String fromWhsCode) {
        FromWhsCode = fromWhsCode;
    }

    public String getFromWhsName() {
        return FromWhsName;
    }

    public void setFromWhsName(String fromWhsName) {
        FromWhsName = fromWhsName;
    }

    public String getToWhsCode() {
        return ToWhsCode;
    }

    public void setToWhsCode(String toWhsCode) {
        ToWhsCode = toWhsCode;
    }

    public String getToWhsName() {
        return ToWhsName;
    }

    public void setToWhsName(String toWhsName) {
        ToWhsName = toWhsName;
    }

    public String getDocStatus() {
        return DocStatus;
    }

    public void setDocStatus(String docStatus) {
        DocStatus = docStatus;
    }

    public String getDocStatusName() {
        return DocStatusName;
    }

    public void setDocStatusName(String docStatusName) {
        DocStatusName = docStatusName;
    }

    public String getCreator() {
        return Creator;
    }

    public void setCreator(String creator) {
        Creator = creator;
    }

    public String getTransferType() {
        return TransferType;
    }

    public void setTransferType(String transferType) {
        TransferType = transferType;
    }

    public String getTransferTypeName() {
        return TransferTypeName;
    }

    public void setTransferTypeName(String transferTypeName) {
        TransferTypeName = transferTypeName;
    }

    public String getComments() {
        return Comments;
    }

    public void setComments(String comments) {
        Comments = comments;
    }
}
