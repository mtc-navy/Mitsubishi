package io.mtc.modules.sap.dto;

import io.mtc.modules.sap.entity.DiscountEntity;
import io.mtc.modules.sap.entity.GiftInfoEntity;
import io.mtc.modules.sap.entity.PaymentEntity;
import io.netty.util.internal.StringUtil;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by majun on 2018/9/12.
 */
public class SaleInvoiceEditDto {

    /**
     * DocEntry
     */
    private Integer docEntry;

    /**
     * 单据号
     */
    private String docNum;

    /**
     * 客户代码
     */
    private String cardCode;

    /**
     * 客户名称
     */
    private String cardName;

    /**
     * 收货人编码
     */
    private String cntctPrsn;

    /**
     * 收货人名称
     */
    private String cntctName;

    /**
     * 单据日期
     */
    private String taxDate;

    /**
     * 车牌号
     */
    private String carNo;

    /**
     * 过磅单号
     */
    private String boundNo;

    /**
     * 销售分支
     */
    private String bplid;

    /**
     * 销售分支登记号
     */
    private String taxIdNum;

    /**
     * 可用额度
     */
    private BigDecimal canUseCost;

    /**
     * 往来款
     */
    private BigDecimal inOutAmt;
    /**
     * 往来款有效值
     */
    private BigDecimal maxInOutAmt;
    /**
     * 折扣余额
     */
    private BigDecimal discAmt;

    /**
     * 可用金额
     */
    private BigDecimal canUseAmt;

    /**
     * 物料信息
     */
    private List<ItemInfo> itemInfos;

    /**
     * 订单状态
     */
    private String docStatus;

    /**
     * 订单状态名称
     */
    private String docStatsName;

    /**
     * 制单人
     */
    private String creator;

    /**
     * 制单日期
     */
    private String createDate;

    /**
     * 制单时间
     */
    private String createTime;

    /**
     * 发货单号
     */
    private String invDocNum;

    /**
     * 关联销售订单
     */
    private String inlineNo;

    /**
     * 打印人
     */
    private String printor;

    /**
     * 打印日期
     */
    private String printDate;

    /**
     * 打印时间
     */
    private String printTime;

    /**
     * 打印次数
     */
    private Integer printNum;

    /**
     * 收款方式1
     */
    private String z040;

    /**
     * 收款方式1金额
     */
    private BigDecimal z028;

    /**
     * 收款方式2
     */
    private String z041;

    /**
     * 收款方式2金额
     */
    private BigDecimal z030;

    /**
     * 收款方式3
     */
    private String z042;

    /**
     * 收款方式3金额
     */
    private BigDecimal z032;

    /**
     * 收款方式4
     */
    private String z043;

    /**
     * 收款方式4金额
     */
    private BigDecimal z034;

    /**
     * 折扣信息
     */
    private List<DiscountEntity> discountEntities;

    /**
     * 备注
     */
    private String remark;

    /**
     * 本次欠款
     */
    private BigDecimal thisArrears;

    /**
     * 源单编号(退货单号)
     */
    private String srcNum;

    /**
     * 提货基地
     */
    private String takeBPLId;

    /**
     * 提货基地名称
     */
    private String takeBPLName;

    /**
     * 提货单号
     */
    private String takeNum;

    /**
     * 内部交易号
     */
    private String fid;

    /**
     * 收款信息
     */
    private List<PaymentEntity> paymentEntities;

    /**
     * 是否随单折
     */
    private boolean isPrsDisc;
    /**
     * 是否销售订单
     */
    private String isOrder;
    /**
     * 基本折扣
     */
    private BigDecimal baseDiscount;
    /**
     * 随单折
     */
    private BigDecimal discount;
    /**
     * 返机费
     */
    private BigDecimal fanjiAmount;
    /**
     * 现金
     */
    private BigDecimal cash;
    /**
     * 源单编号
     */
    private Integer srcEntry;
    /**
     * 运输方式
     */
    private String tranType;
    /**
     * 订料单号
     */
    private String desOrderNum;
    /**
     * 保价单号
     */
    private String priceOrderNum;
    /**
     * 业务类型
     */
    private String busiType;
    /**
     * 司机
     */
    private String driver;
    /**
     * 提货单号
     */
    private String takeNo;

    /**
     * 随单折(赠)信息表（自定义表）
     */
    private List<GiftInfoEntity> giftInfoEntities;

    public Integer getDocEntry() {
        return docEntry;
    }

    public void setDocEntry(Integer docEntry) {
        this.docEntry = docEntry;
    }

    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCntctPrsn() {
        return cntctPrsn;
    }

    public void setCntctPrsn(String cntctPrsn) {
        this.cntctPrsn = cntctPrsn;
    }

    public String getCntctName() {
        return cntctName;
    }

    public void setCntctName(String cntctName) {
        this.cntctName = cntctName;
    }

    public String getTaxDate() {
        if (!StringUtil.isNullOrEmpty(taxDate) && taxDate.length() > 10) {
            return taxDate.substring(0, 10);
        }
        return taxDate;
    }

    public void setTaxDate(String taxDate) {
        this.taxDate = taxDate;
    }

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    public String getBoundNo() {
        return boundNo;
    }

    public void setBoundNo(String boundNo) {
        this.boundNo = boundNo;
    }

    public String getBplid() {
        return bplid;
    }

    public void setBplid(String bplid) {
        this.bplid = bplid;
    }

    public BigDecimal getCanUseCost() {
        return canUseCost;
    }

    public void setCanUseCost(BigDecimal canUseCost) {
        this.canUseCost = canUseCost;
    }

    public BigDecimal getInOutAmt() {
        return inOutAmt;
    }

    public void setInOutAmt(BigDecimal inOutAmt) {
        this.inOutAmt = inOutAmt;
    }

    public BigDecimal getDiscAmt() {
        return discAmt;
    }

    public void setDiscAmt(BigDecimal discAmt) {
        this.discAmt = discAmt;
    }

    public BigDecimal getCanUseAmt() {
        return canUseAmt;
    }

    public void setCanUseAmt(BigDecimal canUseAmt) {
        this.canUseAmt = canUseAmt;
    }

    public List<ItemInfo> getItemInfos() {
        return itemInfos;
    }

    public void setItemInfos(List<ItemInfo> itemInfos) {
        this.itemInfos = itemInfos;
    }

    public List<DiscountEntity> getDiscountEntities() {
        return discountEntities;
    }

    public void setDiscountEntities(List<DiscountEntity> discountEntities) {
        this.discountEntities = discountEntities;
    }

    public List<PaymentEntity> getPaymentEntities() {
        return paymentEntities;
    }

    public String getDocStatus() {
        return docStatus;
    }

    public void setDocStatus(String docStatus) {
        this.docStatus = docStatus;
    }

    public void setPaymentEntities(List<PaymentEntity> paymentEntities) {
        this.paymentEntities = paymentEntities;
    }

    public String getTaxIdNum() {
        return taxIdNum;
    }

    public void setTaxIdNum(String taxIdNum) {
        this.taxIdNum = taxIdNum;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getInvDocNum() {
        return invDocNum;
    }

    public void setInvDocNum(String invDocNum) {
        this.invDocNum = invDocNum;
    }

    public String getInlineNo() {
        return inlineNo;
    }

    public void setInlineNo(String inlineNo) {
        this.inlineNo = inlineNo;
    }

    public String getPrintor() {
        return printor;
    }

    public void setPrintor(String printor) {
        this.printor = printor;
    }

    public String getPrintDate() {
        return printDate;
    }

    public void setPrintDate(String printDate) {
        this.printDate = printDate;
    }

    public String getPrintTime() {
        return printTime;
    }

    public void setPrintTime(String printTime) {
        this.printTime = printTime;
    }

    public Integer getPrintNum() {
        return printNum;
    }

    public void setPrintNum(Integer printNum) {
        this.printNum = printNum;
    }

    public String getDocStatsName() {
        return docStatsName;
    }

    public void setDocStatsName(String docStatsName) {
        this.docStatsName = docStatsName;
    }

    public String getDocNum() {
        return docNum;
    }

    public void setDocNum(String docNum) {
        this.docNum = docNum;
    }

    public String getZ040() {
        return z040;
    }

    public void setZ040(String z040) {
        this.z040 = z040;
    }

    public BigDecimal getZ028() {
        return z028;
    }

    public void setZ028(BigDecimal z028) {
        this.z028 = z028;
    }

    public String getZ041() {
        return z041;
    }

    public void setZ041(String z041) {
        this.z041 = z041;
    }

    public BigDecimal getZ030() {
        return z030;
    }

    public void setZ030(BigDecimal z030) {
        this.z030 = z030;
    }

    public String getZ042() {
        return z042;
    }

    public void setZ042(String z042) {
        this.z042 = z042;
    }

    public BigDecimal getZ032() {
        return z032;
    }

    public void setZ032(BigDecimal z032) {
        this.z032 = z032;
    }

    public String getZ043() {
        return z043;
    }

    public void setZ043(String z043) {
        this.z043 = z043;
    }

    public BigDecimal getZ034() {
        return z034;
    }

    public void setZ034(BigDecimal z034) {
        this.z034 = z034;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public BigDecimal getThisArrears() {
        return thisArrears;
    }

    public void setThisArrears(BigDecimal thisArrears) {
        this.thisArrears = thisArrears;
    }

    public String getSrcNum() {
        return srcNum;
    }

    public void setSrcNum(String srcNum) {
        this.srcNum = srcNum;
    }

    public String getTakeBPLId() {
        return takeBPLId;
    }

    public void setTakeBPLId(String takeBPLId) {
        this.takeBPLId = takeBPLId;
    }

    public String getTakeBPLName() {
        return takeBPLName;
    }

    public void setTakeBPLName(String takeBPLName) {
        this.takeBPLName = takeBPLName;
    }

    public List<GiftInfoEntity> getGiftInfoEntities() {
        return giftInfoEntities;
    }

    public void setGiftInfoEntities(List<GiftInfoEntity> giftInfoEntities) {
        this.giftInfoEntities = giftInfoEntities;
    }

    public String getTakeNum() {
        return takeNum;
    }

    public void setTakeNum(String takeNum) {
        this.takeNum = takeNum;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public boolean isPrsDisc() {
        return isPrsDisc;
    }

    public void setPrsDisc(boolean prsDisc) {
        isPrsDisc = prsDisc;
    }

    public BigDecimal getMaxInOutAmt() {
        return maxInOutAmt;
    }

    public void setMaxInOutAmt(BigDecimal maxInOutAmt) {
        this.maxInOutAmt = maxInOutAmt;
    }

    public String getIsOrder() {
        return isOrder;
    }

    public void setIsOrder(String isOrder) {
        this.isOrder = isOrder;
    }

    public BigDecimal getCash() {
        return cash;
    }

    public void setCash(BigDecimal cash) {
        this.cash = cash;
    }

    public BigDecimal getBaseDiscount() {
        return baseDiscount;
    }

    public void setBaseDiscount(BigDecimal baseDiscount) {
        this.baseDiscount = baseDiscount;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getFanjiAmount() {
        return fanjiAmount;
    }

    public void setFanjiAmount(BigDecimal fanjiAmount) {
        this.fanjiAmount = fanjiAmount;
    }

    public Integer getSrcEntry() {
        return srcEntry;
    }

    public void setSrcEntry(Integer srcEntry) {
        this.srcEntry = srcEntry;
    }

    public String getTranType() {
        return tranType;
    }

    public void setTranType(String tranType) {
        this.tranType = tranType;
    }

    public String getDesOrderNum() {
        return desOrderNum;
    }

    public void setDesOrderNum(String desOrderNum) {
        this.desOrderNum = desOrderNum;
    }

    public String getPriceOrderNum() {
        return priceOrderNum;
    }

    public void setPriceOrderNum(String priceOrderNum) {
        this.priceOrderNum = priceOrderNum;
    }

    public String getBusiType() {
        return busiType;
    }

    public void setBusiType(String busiType) {
        this.busiType = busiType;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getTakeNo() {
        return takeNo;
    }

    public void setTakeNo(String takeNo) {
        this.takeNo = takeNo;
    }

    /**
     * 物料信息
     */
    public static class ItemInfo {

        /**
         * 行号
         */
        private Integer lineNum;

        /**
         * 物料代码
         */
        private String itemCode;

        /**
         * 物料名称
         */
        private String itemName;

        /**
         * 数量
         */
        private BigDecimal quantity;

        /**
         * 包数
         */
        private BigDecimal packNum;

        /**
         * 出厂价
         */
        private BigDecimal price;

        /**
         * 现折
         */
        private BigDecimal currDisc;

        /**
         * 是否标包
         */
        private String isPackage;

        /**
         * 单包重
         */
        private BigDecimal salFactor1;

        /**
         * 结算金额
         */
        private BigDecimal payAmt;

        /**
         * 是否赠料
         */
        private String realdisc;

        /**
         * 仓库代码
         */
        private String warehouseCode;

        /**
         * 仓库名称
         */
        private String warehouseName;

        /**
         * 赠包序号
         */
        private String discOrder;

        /**
         * 赠包单号
         */
        private String discEntry;

        /**
         * 折扣规则物料或物料组
         */
        private String ditemCode;

        private String baseType;

        private Integer baseEntry;

        private Integer baseLine;

        /**
         * 片区经理
         */
        private String regSupName;

        private BigDecimal useQty;

        private BigDecimal ableQty;

        public Integer getLineNum() {
            return lineNum;
        }

        public void setLineNum(Integer lineNum) {
            this.lineNum = lineNum;
        }

        public String getItemCode() {
            return itemCode;
        }

        public void setItemCode(String itemCode) {
            this.itemCode = itemCode;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public BigDecimal getQuantity() {
            return quantity;
        }

        public void setQuantity(BigDecimal quantity) {
            this.quantity = quantity;
        }

        public BigDecimal getPackNum() {
            return packNum;
        }

        public void setPackNum(BigDecimal packNum) {
            this.packNum = packNum;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }

        public BigDecimal getCurrDisc() {
            return currDisc;
        }

        public void setCurrDisc(BigDecimal currDisc) {
            this.currDisc = currDisc;
        }

        public String getIsPackage() {
            return isPackage;
        }

        public void setIsPackage(String isPackage) {
            this.isPackage = isPackage;
        }

        public BigDecimal getSalFactor1() {
            return salFactor1;
        }

        public void setSalFactor1(BigDecimal salFactor1) {
            this.salFactor1 = salFactor1;
        }

        public BigDecimal getPayAmt() {
            if (payAmt != null) {
                return payAmt.setScale(2, BigDecimal.ROUND_HALF_UP);
            }
            return payAmt;
        }

        public void setPayAmt(BigDecimal payAmt) {
            this.payAmt = payAmt;
        }

        public String getRealdisc() {
            if ("是".equals(realdisc)) {
                return "Y";
            }
            if ("否".equals(realdisc)) {
                return "N";
            }
            return realdisc;
        }

        public void setRealdisc(String realdisc) {
            this.realdisc = realdisc;
        }

        public String getWarehouseCode() {
            return warehouseCode;
        }

        public void setWarehouseCode(String warehouseCode) {
            this.warehouseCode = warehouseCode;
        }

        public String getDiscOrder() {
            return discOrder;
        }

        public void setDiscOrder(String discOrder) {
            this.discOrder = discOrder;
        }

        public String getDiscEntry() {
            return discEntry;
        }

        public void setDiscEntry(String discEntry) {
            this.discEntry = discEntry;
        }

        public String getDitemCode() {
            return ditemCode;
        }

        public void setDitemCode(String ditemCode) {
            this.ditemCode = ditemCode;
        }

        public String getBaseType() {
            return baseType;
        }

        public void setBaseType(String baseType) {
            this.baseType = baseType;
        }

        public Integer getBaseEntry() {
            return baseEntry;
        }

        public void setBaseEntry(Integer baseEntry) {
            this.baseEntry = baseEntry;
        }

        public Integer getBaseLine() {
            return baseLine;
        }

        public void setBaseLine(Integer baseLine) {
            this.baseLine = baseLine;
        }

        public String getRegSupName() {
            return regSupName;
        }

        public void setRegSupName(String regSupName) {
            this.regSupName = regSupName;
        }

        public String getWarehouseName() {
            return warehouseName;
        }

        public void setWarehouseName(String warehouseName) {
            this.warehouseName = warehouseName;
        }

        public BigDecimal getUseQty() {
            return useQty;
        }

        public void setUseQty(BigDecimal useQty) {
            this.useQty = useQty;
        }

        public BigDecimal getAbleQty() {
            return ableQty;
        }

        public void setAbleQty(BigDecimal ableQty) {
            this.ableQty = ableQty;
        }
    }
}
