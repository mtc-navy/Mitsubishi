package io.mtc.modules.sap.dto;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/**
 * Create by andy.xie 2018.12.28
 */
public class StockTransferDto {
    /**
     * DocEntry
     */
    private BigInteger DocEntry;

    /**
     * 类型：Z 转储; Q 申请
     */
    private String TransferType;

    /**
     * 单号
     */
    private String DocNum;

    /**
     * 交货日期
     */
    private String TaxDate;

    /**
     * 分支
     */
    private Integer BplId;

    /**
     * 分支名称
     */
    private String BplName;

    /**
     * 源仓库代码
     */
    private String Filler;

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
     * 业务类型
     */
    private String BusiType;

    /**
     * 运输方式
     */
    private String TranType;

    /**
     * 司机
     */
    private String Driver;

    /**
     * 车牌号
     */
    private String CarNo;
    /**
     * 过磅单号
     */
    private String BoundNo;
    /**
     * 行明细
     */
    private List<LineInfo> Lines;

    /**
     * 创建人
     */
    private String Creator;

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

    public String getTaxDate() {
        return TaxDate;
    }

    public void setTaxDate(String taxDate) {
        TaxDate = taxDate;
    }

    public Integer getBplId() {
        return BplId;
    }

    public void setBplId(Integer BplId) {
        this.BplId = BplId;
    }

    public String getBplName() {
        return BplName;
    }

    public void setBplName(String BplName) {
        this.BplName = BplName;
    }

    public String getFiller() {
        return Filler;
    }

    public void setFiller(String filler) {
        Filler = filler;
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

    public List<LineInfo> getLines() {
        return Lines;
    }

    public void setLines(List<LineInfo> lines) {
        Lines = lines;
    }

    public String getTransferType() {
        return TransferType;
    }

    public void setTransferType(String transferType) {
        TransferType = transferType;
    }

    public String getBusiType() {
        return BusiType;
    }

    public void setBusiType(String busiType) {
        BusiType = busiType;
    }

    public String getTranType() {
        return TranType;
    }

    public void setTranType(String tranType) {
        TranType = tranType;
    }

    public String getDriver() {
        return Driver;
    }

    public void setDriver(String driver) {
        Driver = driver;
    }

    public String getCarNo() {
        return CarNo;
    }

    public void setCarNo(String carNo) {
        CarNo = carNo;
    }

    public String getBoundNo() {
        return BoundNo;
    }

    public void setBoundNo(String boundNo) {
        BoundNo = boundNo;
    }

    /**
     * 行信息
     */
    public static class LineInfo {

        /**
         * 行号
         */
        private Integer LineNum;

        /**
         * 物料代码
         */
        private String ItemCode;

        /**
         * 物料名称
         */
        private String ItemName;

        /**
         * 件数
         */
        private BigDecimal Factor2;

        /**
         * 件重
         */
        private BigDecimal Factor1;

        /**
         * (KG)
         * 数量
         */
        private BigDecimal Quantity;

        /**
         * 是否标包
         */
        private String IsPackage;

        /**
         * 单包重
         */
        private BigDecimal SalFactor1 = new BigDecimal(1);

        /**
         * 单包数
         */
        private BigDecimal SalFactor2 = new BigDecimal(1);

        /**
         * 规格
         */
        private String Spec;

        /**
         * 库存
         */
        private BigDecimal Stock;

        /**
         * 从仓库
         */
        private String FromWarehouseCode;

        /**
         * 到仓库
         */
        private String WarehouseCode;

        public Integer getLineNum() {
            return LineNum;
        }

        public void setLineNum(Integer lineNum) {
            this.LineNum = lineNum;
        }

        public String getItemCode() {
            return ItemCode;
        }

        public void setItemCode(String itemCode) {
            this.ItemCode = itemCode;
        }

        public String getItemName() {
            return ItemName;
        }

        public void setItemName(String itemName) {
            this.ItemName = itemName;
        }

        public BigDecimal getQuantity() {
            return Quantity;
        }

        public void setQuantity(BigDecimal quantity) {
            this.Quantity = quantity;
        }

        public String getSpec() {
            return Spec;
        }

        public void setSpec(String spec) {
            Spec = spec;
        }

        public BigDecimal getStock() {
            return Stock;
        }

        public void setStock(BigDecimal stock) {
            Stock = stock;
        }

        public String getIsPackage() {
            return IsPackage;
        }

        public void setIsPackage(String isPackage) {
            IsPackage = isPackage;
        }

        public BigDecimal getSalFactor1() {
            return SalFactor1;
        }

        public void setSalFactor1(BigDecimal salFactor1) {
            SalFactor1 = salFactor1;
        }

        public BigDecimal getFactor2() {
            return Factor2;
        }

        public void setFactor2(BigDecimal factor2) {
            Factor2 = factor2;
        }

        public BigDecimal getSalFactor2() {
            return SalFactor2;
        }

        public void setSalFactor2(BigDecimal salFactor2) {
            SalFactor2 = salFactor2;
        }

        public BigDecimal getFactor1() {
            return Factor1;
        }

        public void setFactor1(BigDecimal factor1) {
            Factor1 = factor1;
        }

        public String getWarehouseCode() {
            return WarehouseCode;
        }

        public void setWarehouseCode(String warehouseCode) {
            WarehouseCode = warehouseCode;
        }

        public String getFromWarehouseCode() {
            return FromWarehouseCode;
        }

        public void setFromWarehouseCode(String fromWarehouseCode) {
            FromWarehouseCode = fromWarehouseCode;
        }
    }
}
