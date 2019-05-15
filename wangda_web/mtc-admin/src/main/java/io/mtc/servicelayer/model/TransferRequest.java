package io.mtc.servicelayer.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/**
 * Create by andy.xie(2018.12.28)
 */
public class TransferRequest {
    private BigInteger DocEntry;
    private Integer Series;
    private String Printed;
    private String DocDate;
    private String DueDate;
    private String CardCode;
    private String CardName;
    private String Address;
    private String Reference1;
    private String Reference2;
    private String Comments;
    private String JournalMemo;
    private Integer PriceList;
    private Integer SalesPersonCode;
    private String FromWarehouse;
    private String ToWarehouse;
    private String CreationDate;
    private String UpdateDate;
    private Integer FinancialPeriod;
    private String TransNum;
    private Integer DocNum;
    private String TaxDate;
    private Integer ContactPerson;
    private String FolioPrefixString;
    private String FolioNumber;
    private String DocStringCode;
    private String AuthorizationStatus;
    private Integer BPLID;
    private String BPLName;
    private String VATRegNum;
    private String AuthorizationCode;
    private String StartDeliveryDate;
    private String StartDeliveryTime;
    private String EndDeliveryDate;
    private String EndDeliveryTime;
    private String VehiclePlate;
    private String ATDocumentType;
    private String EDocExportFormat;
    private String PointOfIssueCode;
    private String Letter;
    private String FolioNumberFrom;
    private String FolioNumberTo;
    private String AttachmentEntry;
    private String DocumentStatus;
    private String ShipToCode;
    private String U_Creator;
    private String U_CName;
    private String U_TrsType;
    private String U_Driver;
    private String U_TranType;
    private String U_BusiType;
    private String U_CarNo;
    private String U_WORDREntry;
    private List<StockTransferLine> StockTransferLines;

    public BigInteger getDocEntry() {
        return DocEntry;
    }

    public void setDocEntry(BigInteger DocEntry) {
        this.DocEntry = DocEntry;
    }

    public Integer getSeries() {
        return Series;
    }

    public void setSeries(int Series) {
        this.Series = Series;
    }

    public String getPrinted() {
        return Printed;
    }

    public void setPrinted(String Printed) {
        this.Printed = Printed;
    }

    public String getDocDate() {
        return DocDate;
    }

    public void setDocDate(String DocDate) {
        this.DocDate = DocDate;
    }

    public String getDueDate() {
        return DueDate;
    }

    public void setDueDate(String DueDate) {
        this.DueDate = DueDate;
    }

    public String getCardCode() {
        return CardCode;
    }

    public void setCardCode(String CardCode) {
        this.CardCode = CardCode;
    }

    public String getCardName() {
        return CardName;
    }

    public void setCardName(String CardName) {
        this.CardName = CardName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getReference1() {
        return Reference1;
    }

    public void setReference1(String Reference1) {
        this.Reference1 = Reference1;
    }

    public String getReference2() {
        return Reference2;
    }

    public void setReference2(String Reference2) {
        this.Reference2 = Reference2;
    }

    public String getComments() {
        return Comments;
    }

    public void setComments(String Comments) {
        this.Comments = Comments;
    }

    public String getJournalMemo() {
        return JournalMemo;
    }

    public void setJournalMemo(String JournalMemo) {
        this.JournalMemo = JournalMemo;
    }

    public Integer getPriceList() {
        return PriceList;
    }

    public void setPriceList(int PriceList) {
        this.PriceList = PriceList;
    }

    public Integer getSalesPersonCode() {
        return SalesPersonCode;
    }

    public void setSalesPersonCode(int SalesPersonCode) {
        this.SalesPersonCode = SalesPersonCode;
    }

    public String getFromWarehouse() {
        return FromWarehouse;
    }

    public void setFromWarehouse(String FromWarehouse) {
        this.FromWarehouse = FromWarehouse;
    }

    public String getToWarehouse() {
        return ToWarehouse;
    }

    public void setToWarehouse(String ToWarehouse) {
        this.ToWarehouse = ToWarehouse;
    }

    public String getCreationDate() {
        return CreationDate;
    }

    public void setCreationDate(String CreationDate) {
        this.CreationDate = CreationDate;
    }

    public String getUpdateDate() {
        return UpdateDate;
    }

    public void setUpdateDate(String UpdateDate) {
        this.UpdateDate = UpdateDate;
    }

    public Integer getFinancialPeriod() {
        return FinancialPeriod;
    }

    public void setFinancialPeriod(int FinancialPeriod) {
        this.FinancialPeriod = FinancialPeriod;
    }

    public String getTransNum() {
        return TransNum;
    }

    public void setTransNum(String TransNum) {
        this.TransNum = TransNum;
    }

    public Integer getDocNum() {
        return DocNum;
    }

    public void setDocNum(int DocNum) {
        this.DocNum = DocNum;
    }

    public Integer getContactPerson() {
        return ContactPerson;
    }

    public void setContactPerson(int ContactPerson) {
        this.ContactPerson = ContactPerson;
    }

    public String getFolioPrefixString() {
        return FolioPrefixString;
    }

    public void setFolioPrefixString(String FolioPrefixString) {
        this.FolioPrefixString = FolioPrefixString;
    }

    public String getFolioNumber() {
        return FolioNumber;
    }

    public void setFolioNumber(String FolioNumber) {
        this.FolioNumber = FolioNumber;
    }

    public String getDocStringCode() {
        return DocStringCode;
    }

    public void setDocStringCode(String DocStringCode) {
        this.DocStringCode = DocStringCode;
    }

    public String getAuthorizationStatus() {
        return AuthorizationStatus;
    }

    public void setAuthorizationStatus(String AuthorizationStatus) {
        this.AuthorizationStatus = AuthorizationStatus;
    }

    public Integer getBPLID() {
        return BPLID;
    }

    public void setBPLID(int BPLID) {
        this.BPLID = BPLID;
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

    public String getAuthorizationCode() {
        return AuthorizationCode;
    }

    public void setAuthorizationCode(String AuthorizationCode) {
        this.AuthorizationCode = AuthorizationCode;
    }

    public String getStartDeliveryDate() {
        return StartDeliveryDate;
    }

    public void setStartDeliveryDate(String StartDeliveryDate) {
        this.StartDeliveryDate = StartDeliveryDate;
    }

    public String getStartDeliveryTime() {
        return StartDeliveryTime;
    }

    public void setStartDeliveryTime(String StartDeliveryTime) {
        this.StartDeliveryTime = StartDeliveryTime;
    }

    public String getEndDeliveryDate() {
        return EndDeliveryDate;
    }

    public void setEndDeliveryDate(String EndDeliveryDate) {
        this.EndDeliveryDate = EndDeliveryDate;
    }

    public String getEndDeliveryTime() {
        return EndDeliveryTime;
    }

    public void setEndDeliveryTime(String EndDeliveryTime) {
        this.EndDeliveryTime = EndDeliveryTime;
    }

    public String getVehiclePlate() {
        return VehiclePlate;
    }

    public void setVehiclePlate(String VehiclePlate) {
        this.VehiclePlate = VehiclePlate;
    }

    public String getATDocumentType() {
        return ATDocumentType;
    }

    public void setATDocumentType(String ATDocumentType) {
        this.ATDocumentType = ATDocumentType;
    }

    public String getEDocExportFormat() {
        return EDocExportFormat;
    }

    public void setEDocExportFormat(String EDocExportFormat) {
        this.EDocExportFormat = EDocExportFormat;
    }

    public String getPointOfIssueCode() {
        return PointOfIssueCode;
    }

    public void setPointOfIssueCode(String PointOfIssueCode) {
        this.PointOfIssueCode = PointOfIssueCode;
    }

    public String getLetter() {
        return Letter;
    }

    public void setLetter(String Letter) {
        this.Letter = Letter;
    }

    public String getFolioNumberFrom() {
        return FolioNumberFrom;
    }

    public void setFolioNumberFrom(String FolioNumberFrom) {
        this.FolioNumberFrom = FolioNumberFrom;
    }

    public String getFolioNumberTo() {
        return FolioNumberTo;
    }

    public void setFolioNumberTo(String FolioNumberTo) {
        this.FolioNumberTo = FolioNumberTo;
    }

    public String getAttachmentEntry() {
        return AttachmentEntry;
    }

    public void setAttachmentEntry(String AttachmentEntry) {
        this.AttachmentEntry = AttachmentEntry;
    }

    public String getDocumentStatus() {
        return DocumentStatus;
    }

    public void setDocumentStatus(String DocumentStatus) {
        this.DocumentStatus = DocumentStatus;
    }

    public String getShipToCode() {
        return ShipToCode;
    }

    public void setShipToCode(String ShipToCode) {
        this.ShipToCode = ShipToCode;
    }

    public String getTaxDate() {
        return TaxDate;
    }

    public void setTaxDate(String taxDate) {
        TaxDate = taxDate;
    }

    public List<StockTransferLine> getStockTransferLines() {
        return StockTransferLines;
    }

    public void setStockTransferLines(List<StockTransferLine> stockTransferLines) {
        StockTransferLines = stockTransferLines;
    }

    public String getU_Creator() {
        return U_Creator;
    }

    public void setU_Creator(String u_Creator) {
        U_Creator = u_Creator;
    }

    public String getU_TrsType() {
        return U_TrsType;
    }

    public void setU_TrsType(String u_TrsType) {
        U_TrsType = u_TrsType;
    }

    public String getU_CName() {
        return U_CName;
    }

    public void setU_CName(String u_CName) {
        U_CName = u_CName;
    }

    public String getU_Driver() {
        return U_Driver;
    }

    public void setU_Driver(String u_Driver) {
        U_Driver = u_Driver;
    }

    public String getU_TranType() {
        return U_TranType;
    }

    public void setU_TranType(String u_TranType) {
        U_TranType = u_TranType;
    }

    public String getU_BusiType() {
        return U_BusiType;
    }

    public void setU_BusiType(String u_BusiType) {
        U_BusiType = u_BusiType;
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

    /**
     * 行
     */
    public static class StockTransferLine {

        private Integer LineNum;
        private Integer DocEntry;
        private String ItemCode;
        private String ItemDescription;
        private BigDecimal Quantity;
        private BigDecimal InventoryQuantity;
        private BigDecimal Factor;
        private BigDecimal Factor2;
        private BigDecimal Price;
        private String Currency;
        private BigDecimal Rate;
        private BigDecimal DiscountPercent;
        private String VendorNum;
        private String SerialNumber;
        private String WarehouseCode;
        private String FromWarehouseCode;
        private String ProjectCode;
        private String DistributionRule;
        private String DistributionRule2;
        private String DistributionRule3;
        private String DistributionRule4;
        private String DistributionRule5;
        private String UseBaseUnits;
        private String MeasureUnit;
        private String BaseType;
        private String BaseLine;
        private String BaseEntry;
        private BigDecimal UnitPrice;
        private String LineStatus;
        private List<BatchNumbers> BatchNumbers;

        public Integer getLineNum() {
            return LineNum;
        }

        public void setLineNum(int LineNum) {
            this.LineNum = LineNum;
        }

        public Integer getDocEntry() {
            return DocEntry;
        }

        public void setDocEntry(int DocEntry) {
            this.DocEntry = DocEntry;
        }

        public String getItemCode() {
            return ItemCode;
        }

        public void setItemCode(String ItemCode) {
            this.ItemCode = ItemCode;
        }

        public String getItemDescription() {
            return ItemDescription;
        }

        public void setItemDescription(String ItemDescription) {
            this.ItemDescription = ItemDescription;
        }

        public BigDecimal getQuantity() {
            return Quantity;
        }

        public void setQuantity(BigDecimal Quantity) {
            this.Quantity = Quantity;
        }

        public BigDecimal getPrice() {
            return Price;
        }

        public void setPrice(BigDecimal Price) {
            this.Price = Price;
        }

        public String getCurrency() {
            return Currency;
        }

        public void setCurrency(String Currency) {
            this.Currency = Currency;
        }

        public BigDecimal getRate() {
            return Rate;
        }

        public void setRate(BigDecimal Rate) {
            this.Rate = Rate;
        }

        public BigDecimal getDiscountPercent() {
            return DiscountPercent;
        }

        public void setDiscountPercent(BigDecimal DiscountPercent) {
            this.DiscountPercent = DiscountPercent;
        }

        public String getVendorNum() {
            return VendorNum;
        }

        public void setVendorNum(String VendorNum) {
            this.VendorNum = VendorNum;
        }

        public String getSerialNumber() {
            return SerialNumber;
        }

        public void setSerialNumber(String SerialNumber) {
            this.SerialNumber = SerialNumber;
        }

        public String getWarehouseCode() {
            return WarehouseCode;
        }

        public void setWarehouseCode(String WarehouseCode) {
            this.WarehouseCode = WarehouseCode;
        }

        public String getFromWarehouseCode() {
            return FromWarehouseCode;
        }

        public void setFromWarehouseCode(String FromWarehouseCode) {
            this.FromWarehouseCode = FromWarehouseCode;
        }

        public String getProjectCode() {
            return ProjectCode;
        }

        public void setProjectCode(String ProjectCode) {
            this.ProjectCode = ProjectCode;
        }

        public String getDistributionRule() {
            return DistributionRule;
        }

        public void setDistributionRule(String DistributionRule) {
            this.DistributionRule = DistributionRule;
        }

        public String getDistributionRule2() {
            return DistributionRule2;
        }

        public void setDistributionRule2(String DistributionRule2) {
            this.DistributionRule2 = DistributionRule2;
        }

        public String getDistributionRule3() {
            return DistributionRule3;
        }

        public void setDistributionRule3(String DistributionRule3) {
            this.DistributionRule3 = DistributionRule3;
        }

        public String getDistributionRule4() {
            return DistributionRule4;
        }

        public void setDistributionRule4(String DistributionRule4) {
            this.DistributionRule4 = DistributionRule4;
        }

        public String getDistributionRule5() {
            return DistributionRule5;
        }

        public void setDistributionRule5(String DistributionRule5) {
            this.DistributionRule5 = DistributionRule5;
        }

        public String getUseBaseUnits() {
            return UseBaseUnits;
        }

        public void setUseBaseUnits(String UseBaseUnits) {
            this.UseBaseUnits = UseBaseUnits;
        }

        public String getMeasureUnit() {
            return MeasureUnit;
        }

        public void setMeasureUnit(String MeasureUnit) {
            this.MeasureUnit = MeasureUnit;
        }

        public String getBaseType() {
            return BaseType;
        }

        public void setBaseType(String BaseType) {
            this.BaseType = BaseType;
        }

        public String getBaseLine() {
            return BaseLine;
        }

        public void setBaseLine(String BaseLine) {
            this.BaseLine = BaseLine;
        }

        public String getBaseEntry() {
            return BaseEntry;
        }

        public void setBaseEntry(String BaseEntry) {
            this.BaseEntry = BaseEntry;
        }

        public BigDecimal getUnitPrice() {
            return UnitPrice;
        }

        public void setUnitPrice(BigDecimal UnitPrice) {
            this.UnitPrice = UnitPrice;
        }

        public String getLineStatus() {
            return LineStatus;
        }

        public void setLineStatus(String LineSta1tus) {
            this.LineStatus = LineStatus;
        }

        public BigDecimal getFactor() {
            return Factor;
        }

        public void setFactor(BigDecimal factor) {
            Factor = factor;
        }

        public BigDecimal getFactor2() {
            return Factor2;
        }

        public void setFactor2(BigDecimal factor2) {
            Factor2 = factor2;
        }

        public BigDecimal getInventoryQuantity() {
            return InventoryQuantity;
        }

        public void setInventoryQuantity(BigDecimal inventoryQuantity) {
            InventoryQuantity = inventoryQuantity;
        }

        public List<StockTransferLine.BatchNumbers> getBatchNumbers() {
            return BatchNumbers;
        }

        public void setBatchNumbers(List<StockTransferLine.BatchNumbers> batchNumbers) {
            BatchNumbers = batchNumbers;
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
}
