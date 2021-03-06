package io.mtc.modules.sap.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class GiftInfoEntity implements Serializable {
    /**
     * 表 : @U_RDR2
     * 对应字段 : Code
     */
    private String code;

    /**
     * 表 : @U_RDR2
     * 对应字段 : Name
     */
    private String name;

    /**
     * 表 : @U_RDR2
     * 对应字段 : U_DItemCode
     */
    private String uDitemcode;

    /**
     * 表 : @U_RDR2
     * 对应字段 : U_DItemName
     */
    private String uDitemname;

    /**
     * 表 : @U_RDR2
     * 对应字段 : U_DocEntry
     */
    private Integer uDocentry;

    /**
     * 表 : @U_RDR2
     * 对应字段 : U_DiscKG
     */
    private BigDecimal uDisckg;

    /**
     * 表 : @U_RDR2
     * 对应字段 : U_DiscPKG
     */
    private Integer uDiscpkg;

    /**
     * 表 : @U_RDR2
     * 对应字段 : U_DiscAmt
     */
    private BigDecimal uDiscamt;

    /**
     * 表 : @U_RDR2
     * 对应字段 : U_DocNum
     */
    private String uDocnum;

    /**
     * 表 : @U_RDR2
     * 对应字段 : U_SODocEntry
     */
    private Integer uSodocentry;

    /**
     * 表 : @U_RDR2
     * 对应字段 : U_ItemCode
     */
    private String uItemcode;

    /**
     * 表 : @U_RDR2
     * 对应字段 : U_ItemName
     */
    private String uItemname;

    /**
     * 表 : @U_RDR2
     * 对应字段 : U_DiscOrder
     */
    private String uDiscorder;

    /**
     * 表 : @U_RDR2
     * 对应字段 : U_ObjType
     */
    private String uObjtype;

    /**
     * 表 : @U_RDR2
     * 对应字段 : U_Remark
     */
    private String uRemark;

    /**
     * 表 : @U_RDR2
     * 对应字段 : U_TrsNumber
     */
    private Integer uTrsnumber;

    /**
     * 表 : @U_RDR2
     * 对应字段 : U_ZKItemCode
     */
    private String uZkitemcode;

    /**
     * 表 : @U_RDR2
     * 对应字段 : U_ZKItemName
     */
    private String uZkitemname;

    /**
     * 表 : @U_RDR2
     * 对应字段 : U_Qty
     */
    private BigDecimal uQty;

    /**
     * 表 : @U_RDR2
     * 对应字段 : U_ReturnEntry
     */
    private Integer uReturnentry;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table @U_RDR2
     *
     * @mbg.generated Wed Sep 26 16:40:42 CST 2018
     */
    private static final long serialVersionUID = 1L;

    /**
     * get method
     *
     * @return @U_RDR2.Code：null
     */
    public String getCode() {
        return code;
    }

    /**
     * set method
     *
     * @param code null
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    /**
     * get method
     *
     * @return @U_RDR2.Name：null
     */
    public String getName() {
        return name;
    }

    /**
     * set method
     *
     * @param name null
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * get method
     *
     * @return @U_RDR2.U_DItemCode：null
     */
    public String getuDitemcode() {
        return uDitemcode;
    }

    /**
     * set method
     *
     * @param uDitemcode null
     */
    public void setuDitemcode(String uDitemcode) {
        this.uDitemcode = uDitemcode == null ? null : uDitemcode.trim();
    }

    /**
     * get method
     *
     * @return @U_RDR2.U_DItemName：null
     */
    public String getuDitemname() {
        return uDitemname;
    }

    /**
     * set method
     *
     * @param uDitemname null
     */
    public void setuDitemname(String uDitemname) {
        this.uDitemname = uDitemname == null ? null : uDitemname.trim();
    }

    /**
     * get method
     *
     * @return @U_RDR2.U_DocEntry：null
     */
    public Integer getuDocentry() {
        return uDocentry;
    }

    /**
     * set method
     *
     * @param uDocentry null
     */
    public void setuDocentry(Integer uDocentry) {
        this.uDocentry = uDocentry;
    }

    /**
     * get method
     *
     * @return @U_RDR2.U_DiscKG：null
     */
    public BigDecimal getuDisckg() {
        return uDisckg;
    }

    /**
     * set method
     *
     * @param uDisckg null
     */
    public void setuDisckg(BigDecimal uDisckg) {
        this.uDisckg = uDisckg;
    }

    /**
     * get method
     *
     * @return @U_RDR2.U_DiscPKG：null
     */
    public Integer getuDiscpkg() {
        return uDiscpkg;
    }

    /**
     * set method
     *
     * @param uDiscpkg null
     */
    public void setuDiscpkg(Integer uDiscpkg) {
        this.uDiscpkg = uDiscpkg;
    }

    /**
     * get method
     *
     * @return @U_RDR2.U_DiscAmt：null
     */
    public BigDecimal getuDiscamt() {
        return uDiscamt;
    }

    /**
     * set method
     *
     * @param uDiscamt null
     */
    public void setuDiscamt(BigDecimal uDiscamt) {
        this.uDiscamt = uDiscamt;
    }

    /**
     * get method
     *
     * @return @U_RDR2.U_DocNum：null
     */
    public String getuDocnum() {
        return uDocnum;
    }

    /**
     * set method
     *
     * @param uDocnum null
     */
    public void setuDocnum(String uDocnum) {
        this.uDocnum = uDocnum == null ? null : uDocnum.trim();
    }

    /**
     * get method
     *
     * @return @U_RDR2.U_SODocEntry：null
     */
    public Integer getuSodocentry() {
        return uSodocentry;
    }

    /**
     * set method
     *
     * @param uSodocentry null
     */
    public void setuSodocentry(Integer uSodocentry) {
        this.uSodocentry = uSodocentry;
    }

    /**
     * get method
     *
     * @return @U_RDR2.U_ItemCode：null
     */
    public String getuItemcode() {
        return uItemcode;
    }

    /**
     * set method
     *
     * @param uItemcode null
     */
    public void setuItemcode(String uItemcode) {
        this.uItemcode = uItemcode == null ? null : uItemcode.trim();
    }

    /**
     * get method
     *
     * @return @U_RDR2.U_ItemName：null
     */
    public String getuItemname() {
        return uItemname;
    }

    /**
     * set method
     *
     * @param uItemname null
     */
    public void setuItemname(String uItemname) {
        this.uItemname = uItemname == null ? null : uItemname.trim();
    }

    /**
     * get method
     *
     * @return @U_RDR2.U_DiscOrder：null
     */
    public String getuDiscorder() {
        return uDiscorder;
    }

    /**
     * set method
     *
     * @param uDiscorder null
     */
    public void setuDiscorder(String uDiscorder) {
        this.uDiscorder = uDiscorder == null ? null : uDiscorder.trim();
    }

    /**
     * get method
     *
     * @return @U_RDR2.U_ObjType：null
     */
    public String getuObjtype() {
        return uObjtype;
    }

    /**
     * set method
     *
     * @param uObjtype null
     */
    public void setuObjtype(String uObjtype) {
        this.uObjtype = uObjtype == null ? null : uObjtype.trim();
    }

    /**
     * get method
     *
     * @return @U_RDR2.U_Remark：null
     */
    public String getuRemark() {
        return uRemark;
    }

    /**
     * set method
     *
     * @param uRemark null
     */
    public void setuRemark(String uRemark) {
        this.uRemark = uRemark == null ? null : uRemark.trim();
    }

    /**
     * get method
     *
     * @return @U_RDR2.U_TrsNumber：null
     */
    public Integer getuTrsnumber() {
        return uTrsnumber;
    }

    /**
     * set method
     *
     * @param uTrsnumber null
     */
    public void setuTrsnumber(Integer uTrsnumber) {
        this.uTrsnumber = uTrsnumber;
    }

    /**
     * get method
     *
     * @return @U_RDR2.U_ZKItemCode：null
     */
    public String getuZkitemcode() {
        return uZkitemcode;
    }

    /**
     * set method
     *
     * @param uZkitemcode null
     */
    public void setuZkitemcode(String uZkitemcode) {
        this.uZkitemcode = uZkitemcode == null ? null : uZkitemcode.trim();
    }

    /**
     * get method
     *
     * @return @U_RDR2.U_ZKItemName：null
     */
    public String getuZkitemname() {
        return uZkitemname;
    }

    /**
     * set method
     *
     * @param uZkitemname null
     */
    public void setuZkitemname(String uZkitemname) {
        this.uZkitemname = uZkitemname == null ? null : uZkitemname.trim();
    }

    /**
     * get method
     *
     * @return @U_RDR2.U_Qty：null
     */
    public BigDecimal getuQty() {
        return uQty;
    }

    /**
     * set method
     *
     * @param uQty null
     */
    public void setuQty(BigDecimal uQty) {
        this.uQty = uQty;
    }

    /**
     * get method
     *
     * @return @U_RDR2.U_ReturnEntry：null
     */
    public Integer getuReturnentry() {
        return uReturnentry;
    }

    /**
     * set method
     *
     * @param uReturnentry null
     */
    public void setuReturnentry(Integer uReturnentry) {
        this.uReturnentry = uReturnentry;
    }
}