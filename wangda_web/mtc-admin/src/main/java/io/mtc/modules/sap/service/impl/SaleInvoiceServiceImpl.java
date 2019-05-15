package io.mtc.modules.sap.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.mtc.common.exception.RRException;
import io.mtc.common.utils.*;
import io.mtc.modules.sap.constant.DocStatusEnum;
import io.mtc.modules.sap.dao.*;
import io.mtc.modules.sap.dto.SaleInvoiceEditDto;
import io.mtc.modules.sap.dto.SaleTakeNoDto;
import io.mtc.modules.sap.dto.SalesUpdateDto;
import io.mtc.modules.sap.entity.*;
import io.mtc.modules.sap.service.SaleInvoiceService;
import io.mtc.modules.sys.dao.SysConfigDao;
import io.mtc.modules.sys.dao.SysPrintLogDao;
import io.mtc.modules.sys.entity.SysConfigEntity;
import io.mtc.modules.sys.entity.SysPrintLogEntity;
import io.mtc.modules.sys.shiro.ShiroUtils;
import io.mtc.servicelayer.dao.*;
import io.mtc.servicelayer.model.Drafts;
import io.mtc.servicelayer.model.IncomingPayments;
import io.mtc.servicelayer.model.Order;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service("saleInvoiceService")
public class SaleInvoiceServiceImpl extends ServiceImpl<SaleInvoiceDao, SaleInvoiceEntity> implements SaleInvoiceService {

    @Autowired
    private SaleInvoiceDao saleInvoiceDao;
    @Autowired
    private OrdersDao ordersDao;
    @Autowired
    private ReturnsDao returnsDao;
    @Autowired
    private DraftsDao draftsDao;
    @Autowired
    private PaymentDao paymentDao;
    @Autowired
    private CustomerAmtDao customerAmtDao;
    @Autowired
    private IncomingPaymentsDao incomingPaymentsDao;
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private PrintDao printDao;
    @Autowired
    private SysPrintLogDao sysPrintLogDao;
    @Autowired
    private ItemPriceDao itemPriceDao;
    @Autowired
    private DeliveryNotesDao deliveryNotesDao;
    @Autowired
    private SysConfigDao sysConfigDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) throws Exception {

        //客户编码或者客户名称（模糊查询）
        String cardCode = ObjectUtil.nullToEmpty((String) params.get("CustomerNo")).trim();
        //单据状态
        String docStatus = ObjectUtil.nullToEmpty((String) params.get("DocStatus")).trim();
        //开始日期
        String startDateStr = ObjectUtil.nullToEmpty((String) params.get("StartDate"));
        Date startDate = null;
        if (StringUtils.isNotEmpty(startDateStr)) {
            startDate = DateUtils.stringToDate(startDateStr, DateUtils.DATE_PATTERN);
        }
        //结束日期
        String endDateStr = ObjectUtil.nullToEmpty((String) params.get("EndDate"));
        Date endDate = null;
        if (StringUtils.isNotEmpty(endDateStr)) {
            endDate = DateUtils.stringToDate(endDateStr, DateUtils.DATE_PATTERN);
        }
        //用户名
        String userName = ShiroUtils.getUserEntity().getUsername().trim();

        //类型，是开票查询订单，还是退货中查询订单
        String type = ObjectUtil.nullToEmpty((String) params.get("type")).trim();
        //销售单号
        String docNum = ObjectUtil.nullToEmpty((String) params.get("DocNum")).trim();
        //提货单号
        String TakeNo = ObjectUtil.nullToEmpty((String) params.get("TakeNo")).trim();

        Page<SaleInvoiceEntity> page = new Query<SaleInvoiceEntity>(params).getPage();
        page.setRecords(saleInvoiceDao.queryList(page,
                cardCode, docStatus, startDate, endDate, userName, type, docNum, TakeNo));

        return new PageUtils(page);

    }

    @Override
    public SaleInvoiceEditDto info(Long docEntry, String docStatus) throws Exception {
        SaleInvoiceEditDto dto = getEditDto(docEntry, docStatus);
        dto.setPrsDisc(false);
        return dto;
    }

    @Override
    public void cancel(Long docEntry, String docStatus) throws Exception {
        //D:草稿
        if ("D".equals(docStatus)) {
            draftsDao.cancel(docEntry);
        } else {
            ordersDao.cancel(docEntry);
        }
    }

    @Override
    public Integer save(SaleInvoiceEditDto saleInvoiceEditDto, boolean isDraft, boolean isEdit) throws Exception {
        return save(saleInvoiceEditDto, isDraft, Constant.DraftType.ORDER, isEdit);
    }

    @Override
    public Integer save(SaleInvoiceEditDto saleInvoiceEditDto, boolean isDraft, Constant.DraftType type, boolean isEdit) throws Exception {
        //保存前检查数据
        Integer docEntry = saleInvoiceEditDto.getDocEntry();
        checkData(saleInvoiceEditDto, isEdit, isDraft);

        //定义草稿和订单状态
        Order order;
        if (isDraft) {
            order = new Drafts();
            ((Drafts) order).setDocObjectCode(type.getCode());
        } else {
            order = new Order();
        }

        order.setBPLId(saleInvoiceEditDto.getBplid());//分支
        order.setBPL_IDAssignedToInvoice(saleInvoiceEditDto.getBplid());
        if (isDraft || type == Constant.DraftType.ORDER) {
            Integer series = paymentDao.getDocumentSeries(saleInvoiceEditDto.getBplid(), Constant.DraftType.ORDER.getCode());
            order.setSeries(series);
        } else if (type == Constant.DraftType.RETURN) {
            Integer series = paymentDao.getDocumentSeries(saleInvoiceEditDto.getBplid(), Constant.DraftType.RETURN.getCode());
            order.setSeries(series);
        }
        if (!isEdit) {
            order.setCardCode(saleInvoiceEditDto.getCardCode());//客户编码
            order.setCardName(saleInvoiceEditDto.getCardName());//客户名称
        }
        order.setDocDate(saleInvoiceEditDto.getTaxDate());//过账日期
        order.setDocDueDate(saleInvoiceEditDto.getTaxDate());//交货日期
        order.setTaxDate(saleInvoiceEditDto.getTaxDate());//单据日期
        String createTime = DateUtils.format(DateUtils.getDate(), DateUtils.DATE_TIME_PATTERN);
        order.setU_CreateTime(createTime);//制单日期
        order.setU_Creator(ShiroUtils.getUserEntity().getUsername().trim());//制单人
        order.setU_CName(ShiroUtils.getUserEntity().getSapusername());//制单人名称
        order.setContactPersonCode(saleInvoiceEditDto.getCntctPrsn());//联系人
        order.setU_CarNo(saleInvoiceEditDto.getCarNo());//车牌号
        order.setU_WORDREntry(saleInvoiceEditDto.getBoundNo());//过磅单号
        order.setU_DesOrderNum(saleInvoiceEditDto.getDesOrderNum());//订料单号
        order.setU_PriceOrderNum(saleInvoiceEditDto.getPriceOrderNum());//保价单号
        order.setU_TranType(saleInvoiceEditDto.getTranType());//运输方式
        order.setU_BusiType(saleInvoiceEditDto.getBusiType());//业务类型
        order.setVATRegNum(saleInvoiceEditDto.getTaxIdNum());//分支登记号
        order.setComments(saleInvoiceEditDto.getRemark());//备注
        order.setU_TakeBPLId(saleInvoiceEditDto.getTakeBPLId());//提货分支
        order.setU_SrcNum(saleInvoiceEditDto.getSrcNum());//源单编号
        order.setU_Driver(saleInvoiceEditDto.getDriver());//司机

        //设定物料信息
        List<SaleInvoiceEditDto.ItemInfo> itemInfoList = saleInvoiceEditDto.getItemInfos();
        List<Order.DocumentLines> documentLinesList = new ArrayList<>();
        //应收款合计
        BigDecimal sumAmt = BigDecimal.ZERO;
        //基础折扣金额
        BigDecimal baseDisc = BigDecimal.ZERO;
        String cardCode = saleInvoiceEditDto.getCardCode();
        String taxIDNum = saleInvoiceEditDto.getTaxIdNum();
        for (int i = 1; i <= itemInfoList.size(); i++) {
            SaleInvoiceEditDto.ItemInfo itemInfo = itemInfoList.get(i - 1);

            //新增一行明细
            Order.DocumentLines documentLines = new Order.DocumentLines();
            if (!isDraft) {
                documentLines.setLineNum(itemInfo.getLineNum());//行号
            }
            documentLines.setItemCode(itemInfo.getItemCode());//物料代码
            documentLines.setItemDescription(itemInfo.getItemName());//物料名称
            documentLines.setUnitPrice(itemInfo.getPrice());//单价
            documentLines.setFactor1(itemInfo.getSalFactor1());//单包重
            documentLines.setFactor2(itemInfo.getPackNum());//包数
            documentLines.setU_DiscNum(itemInfo.getCurrDisc());//基础折扣金额
            documentLines.setU_IsStdPkg(itemInfo.getIsPackage());//是否标包
            documentLines.setU_Realdisc(ObjectUtil.boolToGBK(itemInfo.getRealdisc()));//是否赠包
            //非标包需要保存数量
            if (type == Constant.DraftType.RETURN && ObjectUtil.boolToGBK(itemInfo.getRealdisc()) == "否") {
                documentLines.setQuantity(itemInfo.getQuantity());//数量
            }
            String warehouse = saleInvoiceDao.getWarehouse(itemInfo.getItemCode(), taxIDNum, ShiroUtils.getUserEntity().getUsername());
            documentLines.setWarehouseCode(warehouse);//仓库

            //退货单根据选择的交货单生成时
            if (type == Constant.DraftType.RETURN) {
                //基于交货单的情况下
                if (StringUtils.isNotEmpty(saleInvoiceEditDto.getSrcNum())) {
                    documentLines.setBaseType("15");//基于销售交货单
                    String baseLineNum = saleInvoiceDao.selectDeliveryLineNum(saleInvoiceEditDto.getSrcEntry(), itemInfo.getItemCode());
                    documentLines.setBaseLine(Integer.valueOf(baseLineNum));
                    documentLines.setBaseEntry(saleInvoiceEditDto.getSrcEntry());

                    BigDecimal quantity = itemInfo.getQuantity();
                    BigDecimal lastQty = quantity;

                    //批次信息
                    List<Order.BatchNumbers> batchNumbersList = new ArrayList<>();
                    //获取批次号信息
                    List<DistNumberEntity> distNumberEntityList = itemPriceDao.searchDeliveryDistNumber(
                            String.valueOf(saleInvoiceEditDto.getSrcEntry()), itemInfo.getItemCode(), baseLineNum);
                    for (DistNumberEntity distNumberEntity : distNumberEntityList) {
                        //交货数量小于等于0时弹出循环
                        if (lastQty.compareTo(BigDecimal.ZERO) <= 0) break;

                        Order.BatchNumbers batchNumbers = new Order.BatchNumbers();
                        if (lastQty.compareTo(distNumberEntity.getQuantity()) >= 0) {
                            batchNumbers.setBatchNumber(distNumberEntity.getDistNumber());
                            batchNumbers.setQuantity(distNumberEntity.getQuantity());
                            lastQty = lastQty.subtract(distNumberEntity.getQuantity());
                        } else {
                            batchNumbers.setBatchNumber(distNumberEntity.getDistNumber());
                            batchNumbers.setQuantity(lastQty);
                            lastQty = BigDecimal.ZERO;
                        }
                        batchNumbersList.add(batchNumbers);
                    }
                    documentLines.setBatchNumbers(batchNumbersList);
                } else {
                    BigDecimal quantity = itemInfo.getQuantity();
                    BigDecimal lastQty = quantity;

                    //批次信息
                    List<Order.BatchNumbers> batchNumbersList = new ArrayList<>();
                    //获取批次号信息
                    List<DistNumberEntity> distNumberEntityList = itemPriceDao.searchLastDistNumber(
                            saleInvoiceEditDto.getCardCode(), saleInvoiceEditDto.getBplid(),
                            itemInfo.getItemCode(), itemInfo.getWarehouseCode());
                    for (DistNumberEntity distNumberEntity : distNumberEntityList) {
                        //交货数量小于等于0时弹出循环
                        if (lastQty.compareTo(BigDecimal.ZERO) <= 0) break;

                        Order.BatchNumbers batchNumbers = new Order.BatchNumbers();
                        if (lastQty.compareTo(distNumberEntity.getQuantity()) >= 0) {
                            batchNumbers.setBatchNumber(distNumberEntity.getDistNumber());
                            batchNumbers.setQuantity(distNumberEntity.getQuantity());
                            lastQty = lastQty.subtract(distNumberEntity.getQuantity());
                        } else {
                            batchNumbers.setBatchNumber(distNumberEntity.getDistNumber());
                            batchNumbers.setQuantity(lastQty);
                            lastQty = BigDecimal.ZERO;
                        }
                        batchNumbersList.add(batchNumbers);
                    }
                    documentLines.setBatchNumbers(batchNumbersList);
                }
            }

            documentLinesList.add(documentLines);

            //折扣金额行合计
            BigDecimal lineDiscAmt = itemInfo.getCurrDisc().multiply(itemInfo.getPackNum());
            documentLines.setU_DiscAmt(lineDiscAmt);

            //合计金额
            sumAmt = sumAmt.add((itemInfo.getQuantity().multiply(itemInfo.getPrice())).setScale(2, BigDecimal.ROUND_HALF_UP));
            //合计折扣价
            if (StringUtils.isEmpty(itemInfo.getDiscOrder())) {
                baseDisc = baseDisc.add(lineDiscAmt);
            }
        }
        order.setDocumentLines(documentLinesList);
        order.setU_Z012(baseDisc);//现金折扣金额

        //收款信息
        BigDecimal sumPayAmt = BigDecimal.ZERO;//收款总额
        if (ListUtils.isNotEmpty(saleInvoiceEditDto.getPaymentEntities())) {
            List<PaymentEntity> paymentEntities = saleInvoiceEditDto.getPaymentEntities();
            int payIndex = 0;
            int havePayNum = 0;
            for (int i = 0; i < paymentEntities.size(); i++) {
                if (paymentEntities.get(i).getPayAmt() != null
                        && paymentEntities.get(i).getPayAmt().compareTo(BigDecimal.ZERO) > 0) {
                    if (payIndex == 0) {
                        order.setU_Z027(paymentEntities.get(i).getPayName());//收款方式1
                        order.setU_Z028(paymentEntities.get(i).getPayAmt());//收款方式1金额
                        sumPayAmt = sumPayAmt.add(paymentEntities.get(i).getPayAmt());
                        havePayNum++;
                    }
                    if (payIndex == 1) {
                        order.setU_Z029(paymentEntities.get(i).getPayName());//收款方式2
                        order.setU_Z030(paymentEntities.get(i).getPayAmt());//收款方式2金额
                        sumPayAmt = sumPayAmt.add(paymentEntities.get(i).getPayAmt());
                        havePayNum++;
                    }
                    if (payIndex == 2) {
                        order.setU_Z031(paymentEntities.get(i).getPayName());//收款方式3
                        order.setU_Z032(paymentEntities.get(i).getPayAmt());//收款方式3金额
                        sumPayAmt = sumPayAmt.add(paymentEntities.get(i).getPayAmt());
                        havePayNum++;
                    }
                    if (payIndex == 3) {
                        order.setU_Z033(paymentEntities.get(i).getPayName());//收款方式4
                        order.setU_Z034(paymentEntities.get(i).getPayAmt());//收款方式4金额
                        sumPayAmt = sumPayAmt.add(paymentEntities.get(i).getPayAmt());
                        havePayNum++;
                    }
                    payIndex++;
                }
            }
            if (havePayNum < 4) {
                if (havePayNum == 0) {
                    //收款1
                    order.setU_Z027("");
                    order.setU_Z028(BigDecimal.ZERO);
                    //收款2
                    order.setU_Z029("");
                    order.setU_Z030(BigDecimal.ZERO);
                    //收款3
                    order.setU_Z031("");
                    order.setU_Z032(BigDecimal.ZERO);
                    //收款4
                    order.setU_Z033("");
                    order.setU_Z034(BigDecimal.ZERO);
                }
                if (havePayNum == 1) {
                    //收款2
                    order.setU_Z029("");
                    order.setU_Z030(BigDecimal.ZERO);
                    //收款3
                    order.setU_Z031("");
                    order.setU_Z032(BigDecimal.ZERO);
                    //收款4
                    order.setU_Z033("");
                    order.setU_Z034(BigDecimal.ZERO);
                }
                if (havePayNum == 2) {
                    //收款3
                    order.setU_Z031("");
                    order.setU_Z032(BigDecimal.ZERO);
                    //收款4
                    order.setU_Z033("");
                    order.setU_Z034(BigDecimal.ZERO);
                }
                if (havePayNum == 3) {
                    //收款4
                    order.setU_Z033("");
                    order.setU_Z034(BigDecimal.ZERO);
                }
            }
            if (payIndex > 4) {
                throw new RRException("收款信息不能超过4种");
            }
        }

        //总的结算金额-收款方式金额-使用的折扣
        order.setU_Z037(sumAmt);//应收款总计
        //欠款金额=明细总金额-收款金额
        BigDecimal needPayAmt = sumAmt.subtract(sumPayAmt);
        order.setU_Z035(needPayAmt);//欠款金额
        order.setU_TrsType("W");
        //设定单据唯一标识，防止重复添加单据
        order.setU_GUID(UUID.randomUUID().toString());

        //修改保存
        if (isEdit) {
            if (isDraft) {
                //保存为草稿
                draftsDao.patch(docEntry, (Drafts) order);
            } else {
                if (type == Constant.DraftType.ORDER) {
                    //保存为订单
                    ordersDao.patch(docEntry, order);
                } else {
                    //退货单
                    returnsDao.patch(docEntry, order);
                }
            }
            return docEntry;
        } else {
            //新增保存
            if (isDraft) {
                //保存为草稿
                Drafts result = (Drafts) draftsDao.save((Drafts) order);
                return result.getDocEntry();
            } else {
                if (type == Constant.DraftType.ORDER) {
                    //保存为订单
                    Order result = (Order) ordersDao.save(order);
                    return result.getDocEntry();
                } else {
                    //退货单
                    Order result = (Order) returnsDao.save(order);
                    return result.getDocEntry();
                }
            }
        }
    }

    /**
     * 保存前数据检查
     *
     * @param saleInvoiceEditDto
     * @throws Exception
     */
    private void checkData(SaleInvoiceEditDto saleInvoiceEditDto, boolean isEdit, boolean isDraft) throws Exception {
        if (!isEdit) {
            //新增时候，最大往来款=往来款
            saleInvoiceEditDto.setMaxInOutAmt(saleInvoiceEditDto.getInOutAmt());
        }

        //判断往来款金额
        BigDecimal payAmt = BigDecimal.ZERO;
        if (ListUtils.isNotEmpty(saleInvoiceEditDto.getPaymentEntities())) {
            List<PaymentEntity> paymentEntities = saleInvoiceEditDto.getPaymentEntities().stream().filter((
                    PaymentEntity a) -> "往来款".equals(a.getPayName())).collect(Collectors.toList());
            for (PaymentEntity paymentEntity : paymentEntities) {
                payAmt = payAmt.add(paymentEntity.getPayAmt());
            }
            if (ListUtils.isNotEmpty(paymentEntities)) {
                if (saleInvoiceEditDto.getMaxInOutAmt().compareTo(BigDecimal.ZERO) > 0
                        && saleInvoiceEditDto.getMaxInOutAmt().compareTo(payAmt) < 0) {
                    throw new RRException("收款信息中，往来款金额不能超过" + saleInvoiceEditDto.getMaxInOutAmt().toString());
                }
            }
        }
        //检查保价单或者订料但是，收款方式不能为往来款以外场合
        boolean isDesPriceNum = StringUtils.isNotEmpty(saleInvoiceEditDto.getDesOrderNum()) ||
                StringUtils.isNotEmpty(saleInvoiceEditDto.getPriceOrderNum());
        if (isDesPriceNum) {
            List<PaymentEntity> paymentEntities = saleInvoiceEditDto.getPaymentEntities().stream().filter((
                    PaymentEntity a) -> (!"往来款".equals(a.getPayName()) && BigDecimal.ZERO.compareTo(a.getPayAmt()) < 0)).collect(Collectors.toList());
            if (ListUtils.isNotEmpty(paymentEntities)) {
                throw new RRException("选择订料单或者保价单后，收款方式只能是往来款");
            }
        }
    }

    /**
     * 添加草稿为正式单据
     *
     * @throws Exception
     */
    public Integer draftToDocuments(SaleInvoiceEditDto saleInvoiceEditDto) throws Exception {
        //获取草稿docEntry
        Integer draftDocEntry = saleInvoiceEditDto.getDocEntry();
        //查询草稿数据
        saleInvoiceEditDto.setDocEntry(null);
        saleInvoiceEditDto.setDocNum(null);
        //生成正式单据
        Integer orderDocEntry = save(saleInvoiceEditDto, false, false);
        //关闭草稿
        new Thread(() -> {
            try {
                cancel(draftDocEntry.longValue(), DocStatusEnum.草稿.getCode());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }).start();
        return orderDocEntry;
    }

    /**
     * 添加收款单
     *
     * @param docEntry
     * @throws Exception
     */
    @Override
    public void incomingPayments(String docEntry) throws Exception {
        List<IncomingPayments> incomingPaymentsList = saleInvoiceDao.structPays(docEntry);
        if (ListUtils.isEmpty(incomingPaymentsList)) {
            throw new RRException("没有有效的收款信息，无法生成收款单");
        }

        SysConfigEntity sysConfigEntity = sysConfigDao.queryByKey("Default_Payment_AcctCode");
        if (sysConfigEntity == null || StringUtils.isEmpty(sysConfigEntity.getParamValue())) {
            throw new RRException("请先配置生成收款单时的控制科目：数据字典【Default_Payment_AcctCode】");
        }

        //依次循环取消对应的收款单
        for (IncomingPayments incomingPayments : incomingPaymentsList) {
            incomingPayments.setU_BankName(incomingPayments.getBankName());
            incomingPayments.setJournalRemarks("收款");
            incomingPayments.setDocCurrency("RMB");
            incomingPayments.setTransferDate(incomingPayments.getTaxDate());
            incomingPayments.setDueDate(incomingPayments.getTaxDate());
            incomingPayments.setApplyVAT("tYES");
            incomingPayments.setPayToCode("BillTo1");
            incomingPayments.setPaymentPriority("bopp_Priority_6");
            incomingPayments.setPaymentType("bopt_None");
            incomingPayments.setDocObjectCode("bopot_IncomingPayments");
            incomingPayments.setDocTypte("rCustomer");
            incomingPayments.setDocType("rCustomer");
            Integer series = paymentDao.getSeries(incomingPayments.getBPLID().toString());
            incomingPayments.setSeries(series);
            incomingPayments.setU_BankID(incomingPayments.getBankID());
            incomingPayments.setU_PayCode(incomingPayments.getPayCode());
            incomingPayments.setU_PayName(incomingPayments.getPayName());
            incomingPayments.setU_BankName(incomingPayments.getBankName());
            incomingPayments.setU_BaseEntry(incomingPayments.getBaseEntry());
            incomingPayments.setU_BaseType(incomingPayments.getBaseType());
            incomingPayments.setControlAccount(sysConfigEntity.getParamValue());
            //设定单据唯一标识，防止重复添加单据
            incomingPayments.setU_GUID(UUID.randomUUID().toString());

            List<IncomingPayments.CashFlowAssignments> cashFlowAssignmentsArrayList = new ArrayList<>();
            IncomingPayments.CashFlowAssignments cashFlowAssignments = new IncomingPayments.CashFlowAssignments();
            cashFlowAssignments.setAmountLC((incomingPayments.getTransferSum().add(incomingPayments.getCashSum())));
            cashFlowAssignments.setCredit(Integer.valueOf(0));
            cashFlowAssignments.setAmountFC(BigDecimal.ZERO);
            cashFlowAssignments.setPaymentMeans(incomingPayments.getPaymentMeans());
            cashFlowAssignments.setCashFlowLineItemID(incomingPayments.getCashFlowLineItemID());
            cashFlowAssignments.setJDTLineId(Integer.valueOf(0));
            cashFlowAssignmentsArrayList.add(cashFlowAssignments);
            incomingPayments.setCashFlowAssignments(cashFlowAssignmentsArrayList);

            try {
                incomingPaymentsDao.save(incomingPayments);
            } catch (Exception e) {
                throw new RRException(e.getMessage());
            } finally {
                //更新收款单号
                paymentDao.updatePays(docEntry);
            }
        }
    }

    /**
     * 取消收款单
     *
     * @param docEntry
     * @throws Exception
     */
    @Override
    public void incomingPayCancel(String docEntry) throws Exception {
        //获取所有需要取消的收款单
        List<DocEntryEntity> docEntryEntityList = paymentDao.searchPayments(docEntry);

        if (ListUtils.isEmpty(docEntryEntityList)) {
            throw new RRException("没有生成对应的收款单，无法取消");
        }

        //依次循环取消对应的收款单
        for (DocEntryEntity docEntryEntity : docEntryEntityList) {
            try {
                incomingPaymentsDao.cancel(docEntryEntity.getDocEntry());
            } catch (Exception e) {
                throw new RRException(e.getMessage());
            } finally {
                //更新收款单号
                paymentDao.updatePays(docEntry);
            }
        }
    }

    /**
     * 批量生成收款单
     *
     * @param docEntryList
     * @throws Exception
     */
    @Override
    public String incomingPaymentsBatch(List<String> docEntryList) throws Exception {
        int success = 0;
        int failure = 0;
        int select = docEntryList.size();
        StringBuilder errorLog = new StringBuilder("");
        for (int i = 0; i < select; i++) {
            try {
                incomingPayments(docEntryList.get(i));
                success++;
            } catch (Exception e) {
                failure++;
                errorLog.append("<br>第" + (i + 1) + "条单据生成失败：" + e.getMessage());
            }
        }

        StringBuilder builder = new StringBuilder("");
        builder.append("批量生成收款 单据数：" + select + " ");
        builder.append("成功：" + success + " ");
        builder.append("失败：" + failure + " ");
        builder.append(errorLog.toString());
        return builder.toString();
    }

    /**
     * 批量取消收款单
     *
     * @param docEntryList
     * @throws Exception
     */
    @Override
    public String incomingPayBatchCancel(List<String> docEntryList) throws Exception {
        int success = 0;
        int failure = 0;
        int select = docEntryList.size();
        StringBuilder errorLog = new StringBuilder("");
        for (int i = 0; i < select; i++) {
            try {
                incomingPayCancel(docEntryList.get(i));
                success++;
            } catch (Exception e) {
                failure++;
                errorLog.append("<br>第" + (i + 1) + "条单据取消失败：" + e.getMessage());
            }
        }

        StringBuilder builder = new StringBuilder("");
        builder.append("批量取消收款 单据数：" + select + " ");
        builder.append("成功：" + success + " ");
        builder.append("失败：" + failure + " ");
        builder.append(errorLog.toString());
        return builder.toString();
    }

    /**
     * 批量生成交货单
     *
     * @param docEntryList
     * @throws Exception
     */
    @Override
    public String batchDelivery(List<String> docEntryList) throws Exception {
        int success = 0;
        int failure = 0;
        int select = docEntryList.size();
        StringBuilder errorLog = new StringBuilder("");
        for (int i = 0; i < select; i++) {
            try {
                //获取开票单信息
                Order order = ordersDao.getObjectById(Integer.valueOf(docEntryList.get(i)), Order.class);

                String bplid = saleInvoiceDao.getOrderBPLId(order.getDocEntry());
                order.setBPLId(bplid);
                //设定序列号
                Integer series = paymentDao.getDocumentSeries(order.getBPLId(), Constant.DraftType.DELIVERY.getCode());
                order.setSeries(series);

                List<Order.DocumentLines> documentLinesList = order.getDocumentLines();
                for (Order.DocumentLines documentLines : documentLinesList) {
                    String itemCode = documentLines.getItemCode();
                    String whsCode = documentLines.getWarehouseCode();
                    BigDecimal quantity = documentLines.getQuantity();
                    BigDecimal lastQty = quantity;

                    //批次信息
                    List<Order.BatchNumbers> batchNumbersList = new ArrayList<>();

                    //获取物料仓库对应的批次和数量信息
                    List<DistNumberEntity> distNumberEntityList = itemPriceDao.searchDistNumber(itemCode, whsCode);
                    for (DistNumberEntity distNumberEntity : distNumberEntityList) {
                        //交货数量小于等于0时弹出循环
                        if (lastQty.compareTo(BigDecimal.ZERO) <= 0) break;

                        Order.BatchNumbers batchNumbers = new Order.BatchNumbers();
                        if (lastQty.compareTo(distNumberEntity.getQuantity()) >= 0) {
                            batchNumbers.setBatchNumber(distNumberEntity.getDistNumber());
                            batchNumbers.setQuantity(distNumberEntity.getQuantity());
                            lastQty = lastQty.subtract(distNumberEntity.getQuantity());
                        } else {
                            batchNumbers.setBatchNumber(distNumberEntity.getDistNumber());
                            batchNumbers.setQuantity(lastQty);
                            lastQty = BigDecimal.ZERO;
                        }
                        batchNumbersList.add(batchNumbers);
                    }
                    //设定基于关系
                    documentLines.setBaseEntry(order.getDocEntry());
                    documentLines.setBaseLine(documentLines.getLineNum());
                    documentLines.setBaseType("17");
                    //设定批次号信息
                    documentLines.setBatchNumbers(batchNumbersList);
                }
                order.setU_FID(String.valueOf(order.getDocNum()));
                order.setU_SrcNum(String.valueOf(order.getDocNum()));
                order.setDocEntry(null);
                order.setDocNum(null);
                Order deliveryNotes = (Order) deliveryNotesDao.save(order);
                success++;
            } catch (Exception e) {
                failure++;
                errorLog.append("<br>第" + (i + 1) + "条单据交货失败：" + e.getMessage());
            }
        }

        StringBuilder builder = new StringBuilder("");
        builder.append("批量交货 单据数：" + select + " ");
        builder.append("成功：" + success + " ");
        builder.append("失败：" + failure + " ");
        builder.append(errorLog.toString());
        return builder.toString();
    }


    /**
     * 批量取消交货单
     *
     * @param docEntryList
     * @throws Exception
     */
    @Override
    public String batchCancelDelivery(List<String> docEntryList) throws Exception {
        int success = 0;
        int failure = 0;
        int select = docEntryList.size();
        StringBuilder errorLog = new StringBuilder("");
        for (int i = 0; i < select; i++) {
            try {
                deliveryNotesDao.cancel(Integer.valueOf(docEntryList.get(i)));
                success++;
            } catch (Exception e) {
                failure++;
                errorLog.append("<br>第" + (i + 1) + "条单据取消失败：" + e.getMessage());
            }
        }

        StringBuilder builder = new StringBuilder("");
        builder.append("批量取消交货 单据数：" + select + " ");
        builder.append("成功：" + success + " ");
        builder.append("失败：" + failure + " ");
        builder.append(errorLog.toString());
        return builder.toString();
    }

    /**
     * 打印销售订单接口
     *
     * @param docEntry
     * @return
     */
    @Override
    public Constant.PDFType print(Long docEntry) {
        PrintTypeEntity printTypeEntity = customerDao.getPrintType(docEntry);
        Constant.PDFType printType = Constant.PDFType.value(printTypeEntity.getPrintType());
        if (printType == Constant.PDFType.无) {
            throw new RRException("业务伙伴主数据[" + printTypeEntity.getCardCode() + "]" + printTypeEntity.getCardName()
                    + "未设定打印内容");
        }
        return printType;
    }

    @Override
    public String batchupdatewhs(List<Long> docEntryList) {
        int success = 0;
        int failure = 0;
        int select = docEntryList.size();
        StringBuilder errorLog = new StringBuilder("");
        for (int i = 0; i < select; i++) {
            try {
                Long docEntry = docEntryList.get(i);
                List<SalesUpdateDto> salesUpdateDtoList = saleInvoiceDao.qryBatchUpdateWhs(docEntry);
                Order order = ordersDao.getObjectById(docEntry.intValue(), Order.class);
                List<Order.DocumentLines> documentLinesList = order.getDocumentLines();
                for (Order.DocumentLines documentLines : documentLinesList) {
                    List<SalesUpdateDto> updateDtos = salesUpdateDtoList.stream().filter((SalesUpdateDto a) ->
                            (a.getLineNum().equals(documentLines.getLineNum()) && a.getSrcWhsCode().equals(documentLines.getWarehouseCode()))).collect(Collectors.toList());
                    if (ListUtils.isNotEmpty(updateDtos)) {
                        documentLines.setWarehouseCode(updateDtos.get(0).getTrgtWhsCode());
                    }
                }
                ordersDao.patch(docEntry.intValue(), order);

                success++;
            } catch (Exception e) {
                failure++;
                errorLog.append("<br>第" + (i + 1) + "条单据更新失败：" + e.getMessage());
            }
        }

        StringBuilder builder = new StringBuilder("");
        builder.append("批量更新仓库 单据数：" + select + " ");
        builder.append("成功：" + success + " ");
        builder.append("失败：" + failure + " ");
        builder.append(errorLog.toString());
        return builder.toString();

    }

    @Override
    @Transactional
    public void printLog(Long docEntry, String docType, Constant.PDFType pdfType) throws Exception {
        SaleInvoiceEditDto dto = info(docEntry, "");
        String bplId = dto.getBplid(); //销售分支
        //打印折扣不用判断
        if (pdfType.getValue() != Constant.PDFType.折扣兑现.getValue()) {
            String result = printDao.canPrintOrNot(ShiroUtils.getUserEntity().getUsername(), bplId, docType, docEntry, pdfType.getSapKey());
            if ("N".equals(result)) {
                throw new RRException("超过打印次数，无法打印！");
            }
            SysPrintLogEntity logEntity = new SysPrintLogEntity();
            logEntity.setUserId(ShiroUtils.getUserId().toString());
            logEntity.setUserName(ShiroUtils.getUserEntity().getUsername());
            logEntity.setDocentry(docEntry.intValue());
            logEntity.setDoctype(docType);
            logEntity.setPrintDate(DateUtils.format(new Date(), DateUtils.DATE_PATTERN));
            logEntity.setPrintTime(DateUtils.format(new Date(), DateUtils.TIME_PATTERN));
            logEntity.setPrintType(pdfType.getSapKey());
            sysPrintLogDao.insert(logEntity);

            printDao.updatePrintInfo(ShiroUtils.getUserEntity().getUsername(), DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN), docEntry);
        }
    }

    /**
     * 根据选择的交货单号获取单据
     *
     * @param docEntry
     * @return
     * @throws Exception
     */
    @Override
    public SaleInvoiceEditDto getDelivery(Long docEntry) throws Exception {
        SaleInvoiceEditDto dto = saleInvoiceDao.searchDeliveryMater(docEntry);
        List<SaleInvoiceEditDto.ItemInfo> itemInfos = saleInvoiceDao.searchDeliveryDetail(docEntry);
        dto.setItemInfos(itemInfos);
        return dto;
    }

    /**
     * 根据DocEntry获取当前订单信息
     *
     * @param docEntry
     * @param docStatus
     * @return
     */
    private SaleInvoiceEditDto getEditDto(Long docEntry, String docStatus) throws Exception {
        SaleInvoiceEditDto dto;
        //查询用过滤条件变量定义
        String cardCode = "";
        String bplid = "";
        String userName = ShiroUtils.getUserEntity().getUsername().trim();

        //判断是否为销售订单草稿
        String isDraft = ObjectUtil.boolToString(docStatus.equals("D"));
        //查询主表数据
        dto = saleInvoiceDao.searchMater(docEntry.toString(), isDraft);
        cardCode = dto.getCardCode();
        bplid = dto.getBplid();

        //获取表头往来款金额
        List<CustomerAmtEntity> customerAmtEntityList = customerAmtDao.search(bplid, cardCode);
        if (ListUtils.isNotEmpty(customerAmtEntityList)) {
            dto.setInOutAmt(customerAmtEntityList.get(0).getActBalance());
            dto.setDiscAmt(customerAmtEntityList.get(0).getDiscAmt());
            dto.setCanUseAmt(customerAmtEntityList.get(0).getCustAmt());
        }
        //获取当前往来款最大有效值
        List<CustomerAmtEntity> maxAmtList = customerAmtDao.searchOther(bplid, cardCode, docEntry);
        if (ListUtils.isNotEmpty(maxAmtList)) {
            dto.setMaxInOutAmt(maxAmtList.get(0).getActBalance());
        } else {
            dto.setMaxInOutAmt(BigDecimal.ZERO);
        }

        //查询明细表数据
        List<SaleInvoiceEditDto.ItemInfo> itemInfos = saleInvoiceDao.searchDetail(docEntry.toString(), isDraft);
        dto.setItemInfos(itemInfos);
        //合计总金额
        BigDecimal sumAmt = BigDecimal.ZERO;
        for (SaleInvoiceEditDto.ItemInfo itemInfo : itemInfos) {
            sumAmt = sumAmt.add(itemInfo.getPayAmt());
        }

        //设定付款信息
        List<PaymentEntity> paymentEntities = paymentDao.searchInSO(cardCode, userName, bplid, docEntry.toString(), isDraft);
        if (CollectionUtils.isEmpty(customerAmtEntityList) || customerAmtEntityList.get(0).getActBalance() == null
                || customerAmtEntityList.get(0).getActBalance().doubleValue() <= 0.0) {
            //如果往来款为0，则删除往来款这种收款方式
            Iterator<PaymentEntity> it = paymentEntities.iterator();
            while (it.hasNext()) {
                PaymentEntity paymentEntity = it.next();
                if ("往来款".equals(paymentEntity.getPayName()) && (paymentEntity.getPayAmt() == null || paymentEntity.getPayAmt().doubleValue() <= 0.0)) {
                    it.remove();
                }
            }
        }
        dto.setPaymentEntities(paymentEntities);
        //合计收款金额
        BigDecimal sumPayAmt = BigDecimal.ZERO;
        for (PaymentEntity paymentEntity : paymentEntities) {
            sumPayAmt = sumPayAmt.add(paymentEntity.getPayAmt());
        }

        //总的结算金额-收款方式金额-使用的折扣
        BigDecimal needPayAmt = sumAmt.subtract(sumPayAmt).setScale(2, BigDecimal.ROUND_HALF_UP);
        dto.setThisArrears(needPayAmt);//欠款金额
        return dto;
    }

    @Override
    @Transactional
    public String updateTakeGoods(List<Long> docEntryList, String isCreate) {
        int success = 0;
        int failure = 0;
        int select = docEntryList.size();
        StringBuilder errorLog = new StringBuilder("");
        Map<String, String> driverMap = new HashMap<>();
        for (int i = 0; i < select; i++) {
            try {
                Long docEntry = docEntryList.get(i);
                SaleTakeNoDto saleTakeNoDto = saleInvoiceDao.qryInvDriverInfo(docEntry);
                if (!"O".equals(saleTakeNoDto.getDocStatus())) {
                    errorLog.append("<br>第" + (i + 1) + "条单据【" + saleTakeNoDto.getDocNum() + "】"
                            + (Constant.Yes.equals(isCreate) ? "提货" : "取消提货") + "失败：单据非【未交货】状态");
                    failure++;
                } else if (Constant.Yes.equals(isCreate) && StringUtils.isNotEmpty(saleTakeNoDto.getTakeNo())) {
                    errorLog.append("<br>第" + (i + 1) + "条单据【" + saleTakeNoDto.getDocNum() + "】提货失败：单据已提货");
                    failure++;
                } else if (Constant.No.equals(isCreate) && StringUtils.isEmpty(saleTakeNoDto.getTakeNo())) {
                    errorLog.append("<br>第" + (i + 1) + "条单据【" + saleTakeNoDto.getDocNum() + "】取消提货失败：单据未提货");
                    failure++;
                } else if (driverMap.containsKey(saleTakeNoDto.getDriver())) {
                    saleInvoiceDao.updateSOTakeNo(docEntry, driverMap.get(saleTakeNoDto.getDriver()), isCreate);
                    success++;
                } else {
                    String takeNo = saleInvoiceDao.createTakeNo(saleTakeNoDto.getAliasName());
                    driverMap.put(saleTakeNoDto.getDriver(), takeNo);
                    saleInvoiceDao.updateSOTakeNo(docEntry, driverMap.get(saleTakeNoDto.getDriver()), isCreate);
                    success++;
                }

            } catch (Exception e) {
                failure++;
                errorLog.append("<br>第" + (i + 1) + "条单据" + (Constant.Yes.equals(isCreate) ? "提货" : "取消提货") + "失败：" + e.getMessage());
            }
        }

        StringBuilder builder = new StringBuilder("");
        builder.append((Constant.Yes.equals(isCreate) ? "提货" : "取消提货") + "单据数：" + select + " ");
        builder.append("成功：" + success + " ");
        builder.append("失败：" + failure + " ");
        builder.append(errorLog.toString());
        return builder.toString();
    }
}