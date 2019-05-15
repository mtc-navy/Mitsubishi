package io.mtc.servicelayer.model;

import java.io.Serializable;

/**
 * 业务伙伴
 * Created by navy.jiang on 2018/9/3.
 */
public class BusinessPartners implements Serializable {

    private static final long serialVersionUID = 5488539678078869987L;

    /**
     * 业务伙伴代码
     */
    private String CardCode;

    /**
     * 业务伙伴名称
     */
    private String CardName;

    /**
     * 类别
     */
    private String CardType;

    /**
     * 联系人
     */
    private String CntctPrsn;

    /**
     * 分支
     */
    private BPBranchAssignment BPBranchAssignment;

    public String getCardCode() {
        return CardCode;
    }

    public void setCardCode(String cardCode) {
        CardCode = cardCode;
    }

    public String getCardName() {
        return CardName;
    }

    public void setCardName(String cardName) {
        CardName = cardName;
    }

    public String getCardType() {
        return CardType;
    }

    public void setCardType(String cardType) {
        CardType = cardType;
    }

    public String getCntctPrsn() {
        return CntctPrsn;
    }

    public void setCntctPrsn(String cntctPrsn) {
        CntctPrsn = cntctPrsn;
    }

    public io.mtc.servicelayer.model.BPBranchAssignment getBPBranchAssignment() {
        return BPBranchAssignment;
    }

    public void setBPBranchAssignment(io.mtc.servicelayer.model.BPBranchAssignment BPBranchAssignment) {
        this.BPBranchAssignment = BPBranchAssignment;
    }
}
