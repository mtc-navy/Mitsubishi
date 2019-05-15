package io.mtc.servicelayer.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class IncomingPayments implements Serializable {

    private transient String BankID;
    private transient String PayCode;
    private transient String PayName;
    private transient String SDocEntry;
    private transient String BaseNum;
    private transient String BankName;
    private transient String SEQ;
    private transient String BaseEntry;
    private transient String DepCode;
    private transient String DepName;
    private transient String ReceiveType;
    private transient String PaymentType;
    private transient String BaseType;

    private Integer DocEntry;
    private String DocType;
    private String DocDate;
    private String CardCode;
    private String CardName;
    private String Address;
    private String CashAccount;
    private String DocCurrency;
    private BigDecimal CashSum;
    private String CheckAccount;
    private String TransferAccount;
    private BigDecimal TransferSum;
    private String TransferDate;
    private Integer DocRate;
    private String Reference1;
    private String CounterReference;
    private String JournalRemarks;
    private Integer ContactPersonCode;
    private String ApplyVAT;
    private String TaxDate;
    private Integer Series;
    private Integer DiscountPercent;
    private Integer DeductionPercent;
    private Integer DeductionSum;
    private Integer CashSumFC;
    private Integer CashSumSys;
    private Integer BillOfExchangeAmount;
    private Integer BillOfExchangeAmountFC;
    private Integer BillOfExchangeAmountSC;
    private Integer WTAmount;
    private Integer WTAmountFC;
    private Integer WTAmountSC;
    private Integer WTTaxableAmount;
    private String PayToCode;
    private String PaymentPriority;
    private Object TaxGroup;
    private Integer BankChargeAmount;
    private Integer BankChargeAmountInFC;
    private Integer BankChargeAmountInSC;
    private Integer UnderOverpaymentdifference;
    private Integer UnderOverpaymentdiffSC;
    private Integer WtBaseSum;
    private Integer WtBaseSumFC;
    private Integer WtBaseSumSC;
    private Integer TransferRealAmount;
    private String DocObjectCode;
    private String DocTypte;
    private String DueDate;
    private String ControlAccount;
    private Integer UnderOverpaymentdiffFC;
    private String AuthorizationStatus;
    private Integer BPLID;
    private String BPLName;
    private Integer VATRegNum;
    private transient Integer CashFlowLineItemID;

    private String U_BankID;
    private String U_PayCode;
    private String U_PayName;
    private String U_SDocEntry;
    private String U_BaseNum;
    private String U_BankName;
    private String U_SEQ;
    private String U_BaseEntry;
    private String U_DepCode;
    private String U_DepName;
    private String U_PaymentType;
    private String U_BaseType;
    private String U_GUID;

    public Integer getDocEntry() {
        return DocEntry;
    }

    public void setDocEntry(int docEntry) {
        DocEntry = docEntry;
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

    public String getDocDate() {
        return DocDate;
    }

    public void setDocDate(String docDate) {
        DocDate = docDate;
    }

    public String getTaxDate() {
        return TaxDate;
    }

    public void setTaxDate(String taxDate) {
        TaxDate = taxDate;
    }

    public Integer getBPLID() {
        return BPLID;
    }

    public void setBPLID(int BPLID) {
        this.BPLID = BPLID;
    }

    public Integer getVATRegNum() {
        return VATRegNum;
    }

    public void setVATRegNum(Integer VATRegNum) {
        this.VATRegNum = VATRegNum;
    }

    public String getTransferAccount() {
        return TransferAccount;
    }

    public void setTransferAccount(String transferAccount) {
        TransferAccount = transferAccount;
    }

    public String getControlAccount() {
        return ControlAccount;
    }

    public void setControlAccount(String controlAccount) {
        ControlAccount = controlAccount;
    }

    public BigDecimal getTransferSum() {
        return TransferSum;
    }

    public void setTransferSum(BigDecimal transferSum) {
        TransferSum = transferSum;
    }

    public Integer getSeries() {
        return Series;
    }

    public void setSeries(int series) {
        Series = series;
    }

    public String getPaymentMeans() {
        return PaymentMeans;
    }

    public void setPaymentMeans(String paymentMeans) {
        PaymentMeans = paymentMeans;
    }

    public List<IncomingPayments.CashFlowAssignments> getCashFlowAssignments() {
        return CashFlowAssignments;
    }

    public void setCashFlowAssignments(List<IncomingPayments.CashFlowAssignments> cashFlowAssignments) {
        CashFlowAssignments = cashFlowAssignments;
    }

    private transient String PaymentMeans;

    private List<CashFlowAssignments> CashFlowAssignments;

    public String getBankName() {
        return BankName;
    }

    public void setBankName(String bankName) {
        BankName = bankName;
    }

    public String getCashAccount() {
        return CashAccount;
    }

    public void setCashAccount(String cashAccount) {
        CashAccount = cashAccount;
    }

    public BigDecimal getCashSum() {
        return CashSum;
    }

    public void setCashSum(BigDecimal cashSum) {
        CashSum = cashSum;
    }

    public Integer getCashFlowLineItemID() {
        return CashFlowLineItemID;
    }

    public void setCashFlowLineItemID(Integer cashFlowLineItemID) {
        CashFlowLineItemID = cashFlowLineItemID;
    }

    public String getJournalRemarks() {
        return JournalRemarks;
    }

    public void setJournalRemarks(String journalRemarks) {
        JournalRemarks = journalRemarks;
    }

    public String getDocCurrency() {
        return DocCurrency;
    }

    public void setDocCurrency(String docCurrency) {
        DocCurrency = docCurrency;
    }

    public String getTransferDate() {
        return TransferDate;
    }

    public void setTransferDate(String transferDate) {
        TransferDate = transferDate;
    }

    public String getDueDate() {
        return DueDate;
    }

    public void setDueDate(String dueDate) {
        DueDate = dueDate;
    }

    public String getReference1() {
        return Reference1;
    }

    public void setReference1(String reference1) {
        Reference1 = reference1;
    }

    public String getApplyVAT() {
        return ApplyVAT;
    }

    public void setApplyVAT(String applyVAT) {
        ApplyVAT = applyVAT;
    }

    public String getPayToCode() {
        return PayToCode;
    }

    public void setPayToCode(String payToCode) {
        PayToCode = payToCode;
    }

    public String getPaymentPriority() {
        return PaymentPriority;
    }

    public void setPaymentPriority(String paymentPriority) {
        PaymentPriority = paymentPriority;
    }

    public String getPaymentType() {
        return PaymentType;
    }

    public void setPaymentType(String paymentType) {
        PaymentType = paymentType;
    }

    public String getDocType() {
        return DocType;
    }

    public void setDocType(String docType) {
        DocType = docType;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getCheckAccount() {
        return CheckAccount;
    }

    public void setCheckAccount(String checkAccount) {
        CheckAccount = checkAccount;
    }

    public Integer getDocRate() {
        return DocRate;
    }

    public void setDocRate(Integer docRate) {
        DocRate = docRate;
    }

    public String getCounterReference() {
        return CounterReference;
    }

    public void setCounterReference(String counterReference) {
        CounterReference = counterReference;
    }

    public Integer getContactPersonCode() {
        return ContactPersonCode;
    }

    public void setContactPersonCode(Integer contactPersonCode) {
        ContactPersonCode = contactPersonCode;
    }

    public void setSeries(Integer series) {
        Series = series;
    }

    public Integer getDiscountPercent() {
        return DiscountPercent;
    }

    public void setDiscountPercent(Integer discountPercent) {
        DiscountPercent = discountPercent;
    }

    public Integer getDeductionPercent() {
        return DeductionPercent;
    }

    public void setDeductionPercent(Integer deductionPercent) {
        DeductionPercent = deductionPercent;
    }

    public Integer getDeductionSum() {
        return DeductionSum;
    }

    public void setDeductionSum(Integer deductionSum) {
        DeductionSum = deductionSum;
    }

    public Integer getCashSumFC() {
        return CashSumFC;
    }

    public void setCashSumFC(Integer cashSumFC) {
        CashSumFC = cashSumFC;
    }

    public Integer getCashSumSys() {
        return CashSumSys;
    }

    public void setCashSumSys(Integer cashSumSys) {
        CashSumSys = cashSumSys;
    }

    public Integer getBillOfExchangeAmount() {
        return BillOfExchangeAmount;
    }

    public void setBillOfExchangeAmount(Integer billOfExchangeAmount) {
        BillOfExchangeAmount = billOfExchangeAmount;
    }

    public Integer getBillOfExchangeAmountFC() {
        return BillOfExchangeAmountFC;
    }

    public void setBillOfExchangeAmountFC(Integer billOfExchangeAmountFC) {
        BillOfExchangeAmountFC = billOfExchangeAmountFC;
    }

    public Integer getBillOfExchangeAmountSC() {
        return BillOfExchangeAmountSC;
    }

    public void setBillOfExchangeAmountSC(Integer billOfExchangeAmountSC) {
        BillOfExchangeAmountSC = billOfExchangeAmountSC;
    }

    public Integer getWTAmount() {
        return WTAmount;
    }

    public void setWTAmount(Integer WTAmount) {
        this.WTAmount = WTAmount;
    }

    public Integer getWTAmountFC() {
        return WTAmountFC;
    }

    public void setWTAmountFC(Integer WTAmountFC) {
        this.WTAmountFC = WTAmountFC;
    }

    public Integer getWTAmountSC() {
        return WTAmountSC;
    }

    public void setWTAmountSC(Integer WTAmountSC) {
        this.WTAmountSC = WTAmountSC;
    }

    public Integer getWTTaxableAmount() {
        return WTTaxableAmount;
    }

    public void setWTTaxableAmount(Integer WTTaxableAmount) {
        this.WTTaxableAmount = WTTaxableAmount;
    }

    public Object getTaxGroup() {
        return TaxGroup;
    }

    public void setTaxGroup(Object taxGroup) {
        TaxGroup = taxGroup;
    }

    public Integer getBankChargeAmount() {
        return BankChargeAmount;
    }

    public void setBankChargeAmount(Integer bankChargeAmount) {
        BankChargeAmount = bankChargeAmount;
    }

    public Integer getBankChargeAmountInFC() {
        return BankChargeAmountInFC;
    }

    public void setBankChargeAmountInFC(Integer bankChargeAmountInFC) {
        BankChargeAmountInFC = bankChargeAmountInFC;
    }

    public Integer getBankChargeAmountInSC() {
        return BankChargeAmountInSC;
    }

    public void setBankChargeAmountInSC(Integer bankChargeAmountInSC) {
        BankChargeAmountInSC = bankChargeAmountInSC;
    }

    public Integer getUnderOverpaymentdifference() {
        return UnderOverpaymentdifference;
    }

    public void setUnderOverpaymentdifference(Integer underOverpaymentdifference) {
        UnderOverpaymentdifference = underOverpaymentdifference;
    }

    public Integer getUnderOverpaymentdiffSC() {
        return UnderOverpaymentdiffSC;
    }

    public void setUnderOverpaymentdiffSC(Integer underOverpaymentdiffSC) {
        UnderOverpaymentdiffSC = underOverpaymentdiffSC;
    }

    public Integer getWtBaseSum() {
        return WtBaseSum;
    }

    public void setWtBaseSum(Integer wtBaseSum) {
        WtBaseSum = wtBaseSum;
    }

    public Integer getWtBaseSumFC() {
        return WtBaseSumFC;
    }

    public void setWtBaseSumFC(Integer wtBaseSumFC) {
        WtBaseSumFC = wtBaseSumFC;
    }

    public Integer getWtBaseSumSC() {
        return WtBaseSumSC;
    }

    public void setWtBaseSumSC(Integer wtBaseSumSC) {
        WtBaseSumSC = wtBaseSumSC;
    }

    public Integer getTransferRealAmount() {
        return TransferRealAmount;
    }

    public void setTransferRealAmount(Integer transferRealAmount) {
        TransferRealAmount = transferRealAmount;
    }

    public String getDocObjectCode() {
        return DocObjectCode;
    }

    public void setDocObjectCode(String docObjectCode) {
        DocObjectCode = docObjectCode;
    }

    public String getDocTypte() {
        return DocTypte;
    }

    public void setDocTypte(String docTypte) {
        DocTypte = docTypte;
    }

    public Integer getUnderOverpaymentdiffFC() {
        return UnderOverpaymentdiffFC;
    }

    public void setUnderOverpaymentdiffFC(Integer underOverpaymentdiffFC) {
        UnderOverpaymentdiffFC = underOverpaymentdiffFC;
    }

    public String getAuthorizationStatus() {
        return AuthorizationStatus;
    }

    public void setAuthorizationStatus(String authorizationStatus) {
        AuthorizationStatus = authorizationStatus;
    }

    public void setBPLID(Integer BPLID) {
        this.BPLID = BPLID;
    }

    public String getBPLName() {
        return BPLName;
    }

    public void setBPLName(String BPLName) {
        this.BPLName = BPLName;
    }

    public String getBankID() {
        return BankID;
    }

    public void setBankID(String bankID) {
        BankID = bankID;
    }

    public String getPayCode() {
        return PayCode;
    }

    public void setPayCode(String payCode) {
        PayCode = payCode;
    }

    public String getPayName() {
        return PayName;
    }

    public void setPayName(String payName) {
        PayName = payName;
    }

    public String getSDocEntry() {
        return SDocEntry;
    }

    public void setSDocEntry(String SDocEntry) {
        this.SDocEntry = SDocEntry;
    }

    public String getBaseNum() {
        return BaseNum;
    }

    public void setBaseNum(String baseNum) {
        BaseNum = baseNum;
    }

    public String getSEQ() {
        return SEQ;
    }

    public void setSEQ(String SEQ) {
        this.SEQ = SEQ;
    }

    public String getBaseEntry() {
        return BaseEntry;
    }

    public void setBaseEntry(String baseEntry) {
        BaseEntry = baseEntry;
    }

    public String getDepCode() {
        return DepCode;
    }

    public void setDepCode(String depCode) {
        DepCode = depCode;
    }

    public String getDepName() {
        return DepName;
    }

    public void setDepName(String depName) {
        DepName = depName;
    }

    public String getReceiveType() {
        return ReceiveType;
    }

    public void setReceiveType(String receiveType) {
        ReceiveType = receiveType;
    }

    public void setDocEntry(Integer docEntry) {
        DocEntry = docEntry;
    }

    public String getU_BankID() {
        return U_BankID;
    }

    public void setU_BankID(String u_BankID) {
        U_BankID = u_BankID;
    }

    public String getU_PayCode() {
        return U_PayCode;
    }

    public void setU_PayCode(String u_PayCode) {
        U_PayCode = u_PayCode;
    }

    public String getU_PayName() {
        return U_PayName;
    }

    public void setU_PayName(String u_PayName) {
        U_PayName = u_PayName;
    }

    public String getU_SDocEntry() {
        return U_SDocEntry;
    }

    public void setU_SDocEntry(String u_SDocEntry) {
        U_SDocEntry = u_SDocEntry;
    }

    public String getU_BaseNum() {
        return U_BaseNum;
    }

    public void setU_BaseNum(String u_BaseNum) {
        U_BaseNum = u_BaseNum;
    }

    public String getU_BankName() {
        return U_BankName;
    }

    public void setU_BankName(String u_BankName) {
        U_BankName = u_BankName;
    }

    public String getU_SEQ() {
        return U_SEQ;
    }

    public void setU_SEQ(String u_SEQ) {
        U_SEQ = u_SEQ;
    }

    public String getU_BaseEntry() {
        return U_BaseEntry;
    }

    public void setU_BaseEntry(String u_BaseEntry) {
        U_BaseEntry = u_BaseEntry;
    }

    public String getU_DepCode() {
        return U_DepCode;
    }

    public void setU_DepCode(String u_DepCode) {
        U_DepCode = u_DepCode;
    }

    public String getU_DepName() {
        return U_DepName;
    }

    public void setU_DepName(String u_DepName) {
        U_DepName = u_DepName;
    }

    public String getU_PaymentType() {
        return U_PaymentType;
    }

    public void setU_PaymentType(String u_PaymentType) {
        U_PaymentType = u_PaymentType;
    }

    public String getBaseType() {
        return BaseType;
    }

    public void setBaseType(String baseType) {
        BaseType = baseType;
    }

    public String getU_BaseType() {
        return U_BaseType;
    }

    public void setU_BaseType(String u_BaseType) {
        U_BaseType = u_BaseType;
    }

    public String getU_GUID() {
        return U_GUID;
    }

    public void setU_GUID(String u_GUID) {
        U_GUID = u_GUID;
    }

    public static class CashFlowAssignments {
        private Integer CashFlowLineItemID;
        private Integer Credit;
        private String PaymentMeans;
        private BigDecimal AmountLC;
        private BigDecimal AmountFC;
        private Integer JDTLineId;

        public BigDecimal getAmountLC() {
            return AmountLC;
        }

        public void setAmountLC(BigDecimal amountLC) {
            AmountLC = amountLC;
        }

        public String getPaymentMeans() {
            return PaymentMeans;
        }

        public void setPaymentMeans(String paymentMeans) {
            PaymentMeans = paymentMeans;
        }

        public Integer getCashFlowLineItemID() {
            return CashFlowLineItemID;
        }

        public void setCashFlowLineItemID(Integer cashFlowLineItemID) {
            CashFlowLineItemID = cashFlowLineItemID;
        }

        public Integer getJDTLineId() {
            return JDTLineId;
        }

        public void setJDTLineId(int JDTLineId) {
            this.JDTLineId = JDTLineId;
        }

        public Integer getCredit() {
            return Credit;
        }

        public void setCredit(Integer credit) {
            Credit = credit;
        }

        public BigDecimal getAmountFC() {
            return AmountFC;
        }

        public void setAmountFC(BigDecimal amountFC) {
            AmountFC = amountFC;
        }
    }
}
