package io.mtc.servicelayer.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 销售订单
 * Created by majun on 2018/9/3.
 */
public class Order implements Serializable {

    private static final long serialVersionUID = -4825007766546324285L;

    private Integer DocEntry;

    private Integer DocNum;

    private String DocDate;

    private String DocDueDate;

    private String ContactPersonCode;

    private String CardCode;

    private String CardName;

    private BigDecimal DocTotal;

    private String TaxDate;

    private String U_Creator;

    private String U_CName;

    private String U_CreateTime;

    private String U_Printor;

    private String U_PrintTime;

    private String U_PrintNum;

    private String BPLId;

    private String BPL_IDAssignedToInvoice;

    private String VATRegNum;

    private String BPLName;

    private String U_CarNo;

    private String U_WORDREntry;

    private BigDecimal U_Z005;

    private BigDecimal U_Z006;

    private BigDecimal U_Z007;

    private BigDecimal U_Z008;

    private BigDecimal U_Z009;

    private BigDecimal U_Z010;

    private BigDecimal U_Z011;

    private BigDecimal U_Z012;

    private BigDecimal U_Z013;

    private BigDecimal U_Z003;

    private String U_Z040;

    private String U_Z041;

    private String U_Z042;

    private String U_Z043;

    private String U_Z044;

    private String U_Z027;

    private BigDecimal U_Z028;

    private String U_Z029;

    private BigDecimal U_Z030;

    private String U_Z031;

    private BigDecimal U_Z032;

    private String U_Z033;

    private BigDecimal U_Z034;

    private BigDecimal U_Z036;

    private BigDecimal U_Z037;

    private BigDecimal U_Z035;

    private String U_IsDiscount;

    private String Comments;

    private BigDecimal U_CashDisc;

    private String U_TakeBPLId;

    private String U_DItemCode;

    private String U_SrcNum;

    private String U_InlineNo;

    private String U_TakeNo;

    private String U_FID;

    private String U_TrsType;

    private String NumAtCard;

    private String U_TranType;

    private String U_DesOrderNum;

    private String U_PriceOrderNum;

    private String U_BusiType;

    private String U_GUID;

    private Integer Series;

    private List<DocumentLines> DocumentLines;

    public Integer getDocEntry() {
        return DocEntry;
    }

    public void setDocEntry(Integer docEntry) {
        DocEntry = docEntry;
    }

    public Integer getDocNum() {
        return DocNum;
    }

    public void setDocNum(Integer docNum) {
        DocNum = docNum;
    }

    public String getDocDate() {
        return DocDate;
    }

    public void setDocDate(String docDate) {
        DocDate = docDate;
    }

    public String getDocDueDate() {
        return DocDueDate;
    }

    public void setDocDueDate(String docDueDate) {
        DocDueDate = docDueDate;
    }

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

    public BigDecimal getDocTotal() {
        return DocTotal;
    }

    public void setDocTotal(BigDecimal docTotal) {
        DocTotal = docTotal;
    }

    public String getTaxDate() {
        return TaxDate;
    }

    public void setTaxDate(String taxDate) {
        TaxDate = taxDate;
    }

    public String getBPLId() {
        return BPLId;
    }

    public void setBPLId(String BPLId) {
        this.BPLId = BPLId;
    }

    public String getU_CarNo() {
        return U_CarNo;
    }

    public void setU_CarNo(String u_CarNo) {
        U_CarNo = u_CarNo;
    }

    public String getU_WORDREntry() {
        return U_WORDREntry;
    }

    public void setU_WORDREntry(String u_WORDREntry) {
        U_WORDREntry = u_WORDREntry;
    }

    public BigDecimal getU_Z005() {
        return U_Z005;
    }

    public void setU_Z005(BigDecimal u_Z005) {
        U_Z005 = u_Z005;
    }

    public BigDecimal getU_Z006() {
        return U_Z006;
    }

    public void setU_Z006(BigDecimal u_Z006) {
        U_Z006 = u_Z006;
    }

    public BigDecimal getU_Z007() {
        return U_Z007;
    }

    public void setU_Z007(BigDecimal u_Z007) {
        U_Z007 = u_Z007;
    }

    public BigDecimal getU_Z008() {
        return U_Z008;
    }

    public void setU_Z008(BigDecimal u_Z008) {
        U_Z008 = u_Z008;
    }

    public BigDecimal getU_Z009() {
        return U_Z009;
    }

    public void setU_Z009(BigDecimal u_Z009) {
        U_Z009 = u_Z009;
    }

    public BigDecimal getU_Z010() {
        return U_Z010;
    }

    public void setU_Z010(BigDecimal u_Z010) {
        U_Z010 = u_Z010;
    }

    public BigDecimal getU_Z011() {
        return U_Z011;
    }

    public void setU_Z011(BigDecimal u_Z011) {
        U_Z011 = u_Z011;
    }

    public BigDecimal getU_Z012() {
        return U_Z012;
    }

    public void setU_Z012(BigDecimal u_Z012) {
        U_Z012 = u_Z012;
    }

    public BigDecimal getU_Z013() {
        return U_Z013;
    }

    public void setU_Z013(BigDecimal u_Z013) {
        U_Z013 = u_Z013;
    }

    public BigDecimal getU_Z003() {
        return U_Z003;
    }

    public void setU_Z003(BigDecimal u_Z003) {
        U_Z003 = u_Z003;
    }

    public String getU_Z040() {
        return U_Z040;
    }

    public void setU_Z040(String u_Z040) {
        U_Z040 = u_Z040;
    }

    public String getU_Z041() {
        return U_Z041;
    }

    public void setU_Z041(String u_Z041) {
        U_Z041 = u_Z041;
    }

    public String getU_Z042() {
        return U_Z042;
    }

    public void setU_Z042(String u_Z042) {
        U_Z042 = u_Z042;
    }

    public String getU_Z043() {
        return U_Z043;
    }

    public void setU_Z043(String u_Z043) {
        U_Z043 = u_Z043;
    }

    public String getU_Z044() {
        return U_Z044;
    }

    public void setU_Z044(String u_Z044) {
        U_Z044 = u_Z044;
    }

    public BigDecimal getU_Z028() {
        return U_Z028;
    }

    public void setU_Z028(BigDecimal u_Z028) {
        U_Z028 = u_Z028;
    }

    public BigDecimal getU_Z030() {
        return U_Z030;
    }

    public void setU_Z030(BigDecimal u_Z030) {
        U_Z030 = u_Z030;
    }

    public BigDecimal getU_Z032() {
        return U_Z032;
    }

    public void setU_Z032(BigDecimal u_Z032) {
        U_Z032 = u_Z032;
    }

    public BigDecimal getU_Z034() {
        return U_Z034;
    }

    public void setU_Z034(BigDecimal u_Z034) {
        U_Z034 = u_Z034;
    }

    public BigDecimal getU_Z036() {
        return U_Z036;
    }

    public void setU_Z036(BigDecimal u_Z036) {
        U_Z036 = u_Z036;
    }

    public BigDecimal getU_Z037() {
        return U_Z037;
    }

    public void setU_Z037(BigDecimal u_Z037) {
        U_Z037 = u_Z037;
    }

    public BigDecimal getU_Z035() {
        return U_Z035;
    }

    public void setU_Z035(BigDecimal u_Z035) {
        U_Z035 = u_Z035;
    }

    public String getComments() {
        return Comments;
    }

    public void setComments(String comments) {
        Comments = comments;
    }

    public List<Order.DocumentLines> getDocumentLines() {
        return DocumentLines;
    }

    public void setDocumentLines(List<Order.DocumentLines> documentLines) {
        DocumentLines = documentLines;
    }

    public String getContactPersonCode() {
        return ContactPersonCode;
    }

    public void setContactPersonCode(String contactPersonCode) {
        ContactPersonCode = contactPersonCode;
    }

    public String getU_IsDiscount() {
        return U_IsDiscount;
    }

    public void setU_IsDiscount(String u_IsDiscount) {
        U_IsDiscount = u_IsDiscount;
    }

    public String getBPL_IDAssignedToInvoice() {
        return BPL_IDAssignedToInvoice;
    }

    public void setBPL_IDAssignedToInvoice(String BPL_IDAssignedToInvoice) {
        this.BPL_IDAssignedToInvoice = BPL_IDAssignedToInvoice;
    }

    public String getBPLName() {
        return BPLName;
    }

    public void setBPLName(String BPLName) {
        this.BPLName = BPLName;
    }

    public String getVATRegNum() {
        return VATRegNum;
    }

    public void setVATRegNum(String VATRegNum) {
        this.VATRegNum = VATRegNum;
    }

    public String getU_Creator() {
        return U_Creator;
    }

    public void setU_Creator(String u_Creator) {
        U_Creator = u_Creator;
    }

    public String getU_CreateTime() {
        return U_CreateTime;
    }

    public void setU_CreateTime(String u_CreateTime) {
        U_CreateTime = u_CreateTime;
    }

    public String getU_Printor() {
        return U_Printor;
    }

    public void setU_Printor(String u_Printor) {
        U_Printor = u_Printor;
    }

    public String getU_PrintTime() {
        return U_PrintTime;
    }

    public void setU_PrintTime(String u_PrintTime) {
        U_PrintTime = u_PrintTime;
    }

    public String getU_PrintNum() {
        return U_PrintNum;
    }

    public void setU_PrintNum(String u_PrintNum) {
        U_PrintNum = u_PrintNum;
    }

    public BigDecimal getU_CashDisc() {
        return U_CashDisc;
    }

    public void setU_CashDisc(BigDecimal u_CashDisc) {
        U_CashDisc = u_CashDisc;
    }

    public String getU_TakeBPLId() {
        return U_TakeBPLId;
    }

    public void setU_TakeBPLId(String u_TakeBPLId) {
        U_TakeBPLId = u_TakeBPLId;
    }

    public String getU_DItemCode() {
        return U_DItemCode;
    }

    public void setU_DItemCode(String u_DItemCode) {
        U_DItemCode = u_DItemCode;
    }

    public String getU_SrcNum() {
        return U_SrcNum;
    }

    public void setU_SrcNum(String u_SrcNum) {
        U_SrcNum = u_SrcNum;
    }

    public String getU_InlineNo() {
        return U_InlineNo;
    }

    public void setU_InlineNo(String u_InlineNo) {
        U_InlineNo = u_InlineNo;
    }

    public String getU_TakeNo() {
        return U_TakeNo;
    }

    public void setU_TakeNo(String u_TakeNo) {
        U_TakeNo = u_TakeNo;
    }

    public String getU_FID() {
        return U_FID;
    }

    public void setU_FID(String u_FID) {
        U_FID = u_FID;
    }

    public String getU_TrsType() {
        return U_TrsType;
    }

    public void setU_TrsType(String u_TrsType) {
        U_TrsType = u_TrsType;
    }

    public String getNumAtCard() {
        return NumAtCard;
    }

    public void setNumAtCard(String numAtCard) {
        NumAtCard = numAtCard;
    }

    public String getU_CName() {
        return U_CName;
    }

    public void setU_CName(String u_CName) {
        U_CName = u_CName;
    }

    public String getU_TranType() {
        return U_TranType;
    }

    public void setU_TranType(String u_TranType) {
        U_TranType = u_TranType;
    }

    public String getU_DesOrderNum() {
        return U_DesOrderNum;
    }

    public void setU_DesOrderNum(String u_DesOrderNum) {
        U_DesOrderNum = u_DesOrderNum;
    }

    public String getU_PriceOrderNum() {
        return U_PriceOrderNum;
    }

    public void setU_PriceOrderNum(String u_PriceOrderNum) {
        U_PriceOrderNum = u_PriceOrderNum;
    }

    public String getU_BusiType() {
        return U_BusiType;
    }

    public void setU_BusiType(String u_BusiType) {
        U_BusiType = u_BusiType;
    }

    public String getU_Z027() {
        return U_Z027;
    }

    public void setU_Z027(String u_Z027) {
        U_Z027 = u_Z027;
    }

    public String getU_Z029() {
        return U_Z029;
    }

    public void setU_Z029(String u_Z029) {
        U_Z029 = u_Z029;
    }

    public String getU_Z031() {
        return U_Z031;
    }

    public void setU_Z031(String u_Z031) {
        U_Z031 = u_Z031;
    }

    public String getU_Z033() {
        return U_Z033;
    }

    public void setU_Z033(String u_Z033) {
        U_Z033 = u_Z033;
    }

    public String getU_GUID() {
        return U_GUID;
    }

    public void setU_GUID(String u_GUID) {
        U_GUID = u_GUID;
    }

    public Integer getSeries() {
        return Series;
    }

    public void setSeries(Integer series) {
        Series = series;
    }

    public static class DocumentLines {

        private Integer LineNum;

        private String ItemCode;

        private String ItemDescription;

        private BigDecimal Quantity;

        private BigDecimal Price;

        private BigDecimal UnitPrice;

        private BigDecimal Factor1;

        private BigDecimal Factor2;

        private BigDecimal U_DiscNum;

        private String U_IsStdPkg;

        private BigDecimal LineTotal;

        private BigDecimal U_PayAmt;

        private String U_Realdisc;

        private String WarehouseCode;

        private String U_DiscOrder;

        private String U_DiscEntry;

        private String U_DItemCode;

        private String BaseType;

        private Integer BaseEntry;

        private Integer BaseLine;

        private String U_RegSupName;

        private BigDecimal U_DiscAmt;

        private List<BatchNumbers> BatchNumbers;

        public Integer getLineNum() {
            return LineNum;
        }

        public void setLineNum(Integer lineNum) {
            LineNum = lineNum;
        }

        public String getItemCode() {
            return ItemCode;
        }

        public void setItemCode(String itemCode) {
            ItemCode = itemCode;
        }

        public String getItemDescription() {
            return ItemDescription;
        }

        public void setItemDescription(String itemDescription) {
            ItemDescription = itemDescription;
        }

        public BigDecimal getQuantity() {
            return Quantity;
        }

        public void setQuantity(BigDecimal quantity) {
            Quantity = quantity;
        }

        public BigDecimal getPrice() {
            return Price;
        }

        public void setPrice(BigDecimal price) {
            Price = price;
        }

        public BigDecimal getFactor1() {
            return Factor1;
        }

        public void setFactor1(BigDecimal factor1) {
            Factor1 = factor1;
        }

        public BigDecimal getFactor2() {
            return Factor2;
        }

        public void setFactor2(BigDecimal factor2) {
            Factor2 = factor2;
        }


        public BigDecimal getLineTotal() {
            return LineTotal;
        }

        public void setLineTotal(BigDecimal lineTotal) {
            LineTotal = lineTotal;
        }

        public BigDecimal getU_PayAmt() {
            return U_PayAmt;
        }

        public void setU_PayAmt(BigDecimal u_PayAmt) {
            U_PayAmt = u_PayAmt;
        }

        public String getU_Realdisc() {
            return U_Realdisc;
        }

        public void setU_Realdisc(String u_Realdisc) {
            U_Realdisc = u_Realdisc;
        }

        public String getWarehouseCode() {
            return WarehouseCode;
        }

        public void setWarehouseCode(String warehouseCode) {
            WarehouseCode = warehouseCode;
        }

        public String getU_IsStdPkg() {
            return U_IsStdPkg;
        }

        public void setU_IsStdPkg(String u_IsStdPkg) {
            U_IsStdPkg = u_IsStdPkg;
        }

        public String getU_DiscOrder() {
            return U_DiscOrder;
        }

        public void setU_DiscOrder(String u_DiscOrder) {
            U_DiscOrder = u_DiscOrder;
        }

        public BigDecimal getU_DiscNum() {
            return U_DiscNum;
        }

        public void setU_DiscNum(BigDecimal u_DiscNum) {
            U_DiscNum = u_DiscNum;
        }

        public String getU_DItemCode() {
            return U_DItemCode;
        }

        public void setU_DItemCode(String u_DItemCode) {
            U_DItemCode = u_DItemCode;
        }

        public String getU_DiscEntry() {
            return U_DiscEntry;
        }

        public void setU_DiscEntry(String u_DiscEntry) {
            U_DiscEntry = u_DiscEntry;
        }

        public String getBaseType() {
            return BaseType;
        }

        public void setBaseType(String baseType) {
            BaseType = baseType;
        }

        public Integer getBaseEntry() {
            return BaseEntry;
        }

        public void setBaseEntry(Integer baseEntry) {
            BaseEntry = baseEntry;
        }

        public Integer getBaseLine() {
            return BaseLine;
        }

        public void setBaseLine(Integer baseLine) {
            BaseLine = baseLine;
        }

        public BigDecimal getUnitPrice() {
            return UnitPrice;
        }

        public void setUnitPrice(BigDecimal unitPrice) {
            UnitPrice = unitPrice;
        }

        public String getU_RegSupName() {
            return U_RegSupName;
        }

        public void setU_RegSupName(String u_RegSupName) {
            U_RegSupName = u_RegSupName;
        }

        public BigDecimal getU_DiscAmt() {
            return U_DiscAmt;
        }

        public void setU_DiscAmt(BigDecimal u_DiscAmt) {
            U_DiscAmt = u_DiscAmt;
        }

        public List<Order.BatchNumbers> getBatchNumbers() {
            return BatchNumbers;
        }

        public void setBatchNumbers(List<Order.BatchNumbers> batchNumbers) {
            BatchNumbers = batchNumbers;
        }
    }

    public static class BatchNumbers {

        private String BatchNumber;

        private BigDecimal Quantity;

        public String getBatchNumber() {
            return BatchNumber;
        }

        public void setBatchNumber(String batchNumber) {
            BatchNumber = batchNumber;
        }

        public BigDecimal getQuantity() {
            return Quantity;
        }

        public void setQuantity(BigDecimal quantity) {
            Quantity = quantity;
        }
    }
}
