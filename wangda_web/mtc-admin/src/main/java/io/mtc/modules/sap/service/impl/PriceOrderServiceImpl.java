package io.mtc.modules.sap.service.impl;

import io.mtc.common.exception.RRException;
import io.mtc.common.utils.DateUtils;
import io.mtc.common.utils.ListUtils;
import io.mtc.modules.sap.constant.Constant;
import io.mtc.modules.sap.dao.PaymentDao;
import io.mtc.modules.sap.dto.PriceOrderDto;
import io.mtc.modules.sap.entity.*;
import io.mtc.modules.sap.mapper.*;
import io.mtc.modules.sap.service.PriceOrderService;
import io.mtc.modules.sys.dao.SysConfigDao;
import io.mtc.modules.sys.entity.SysConfigEntity;
import io.mtc.modules.sys.shiro.ShiroUtils;
import io.mtc.servicelayer.dao.IncomingPaymentsDao;
import io.mtc.servicelayer.model.IncomingPayments;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 订料单Service
 */
@Service("priceOrderService")
public class PriceOrderServiceImpl implements PriceOrderService {

    @Autowired
    private BplidPeriodMapper bplidPeriodMapper;
    @Autowired
    private DocEntryMapper docEntryMapper;
    @Autowired
    private MtcSdOBJDMapper mtcSdOBJDMapper;
    @Autowired
    private MtcSdBJD1Mapper mtcSdBJD1Mapper;
    @Autowired
    private MtcSdBJD2Mapper mtcSdBJD2Mapper;
    @Autowired
    private PaymentDao paymentDao;
    @Autowired
    private IncomingPaymentsDao incomingPaymentsDao;
    @Autowired
    private SysConfigDao sysConfigDao;

    /**
     * 修改获取明细
     *
     * @param docentry
     * @return
     * @throws RRException
     */
    public PriceOrderDto info(Integer docentry) throws RRException {
        PriceOrderDto priceOrderDto = new PriceOrderDto();

        MtcSdOBJDExample mtcSdOBJDExample = new MtcSdOBJDExample();
        mtcSdOBJDExample.createCriteria().andDocentryEqualTo(docentry);
        List<MtcSdOBJD> mtcSdOBJDList = mtcSdOBJDMapper.selectByExample(mtcSdOBJDExample);
        if (ListUtils.isEmpty(mtcSdOBJDList)) {
            throw new RRException("未查询到有效的单据");
        }
        MtcSdOBJD mtcSdOBJD = mtcSdOBJDList.get(0);
        priceOrderDto.setMtcSdOBJD(mtcSdOBJD);

        //子表数据
        MtcSdBJD1Example mtcSdBJD1Example = new MtcSdBJD1Example();
        mtcSdBJD1Example.createCriteria().andDocentryEqualTo(docentry);
        mtcSdBJD1Example.setOrderByClause("\"LineNum\"");
        List<MtcSdBJD1> mtcSdBJD1List = mtcSdBJD1Mapper.selectByExample(mtcSdBJD1Example);
        priceOrderDto.setMtcSdBJD1List(mtcSdBJD1List);

        //收款单信息数据
        MtcSdBJD2Example mtcSdBJD2Example = new MtcSdBJD2Example();
        mtcSdBJD2Example.createCriteria().andDocentryEqualTo(docentry);
        List<MtcSdBJD2> mtcSdBJD2List = mtcSdBJD2Mapper.selectByExample(mtcSdBJD2Example);
        priceOrderDto.setMtcSdBJD2List(mtcSdBJD2List);

        //根据最新的收款方式
        List<PaymentEntity> paymentEntityList = paymentDao.searchInSO(mtcSdOBJD.getCardcode(),
                ShiroUtils.getUserEntity().getUsername(), mtcSdOBJD.getBplid(),
                String.valueOf(mtcSdOBJD.getDocentry()), "MTC_SD_OBJD");
        List<PaymentEntity> needAddPayList = new ArrayList<>();
        for (PaymentEntity paymentEntity : paymentEntityList) {
            List<MtcSdBJD2> tmpList = mtcSdBJD2List.stream().filter((MtcSdBJD2 a)
                    -> a.getPaycode().equals(paymentEntity.getPayCode())).collect(Collectors.toList());
            if (ListUtils.isEmpty(tmpList)) {
                needAddPayList.add(paymentEntity);
            }
        }

        for (PaymentEntity paymentEntity : needAddPayList) {
            MtcSdBJD2 mtcSdBJD2 = new MtcSdBJD2();
            mtcSdBJD2.setDocentry(docentry);
            mtcSdBJD2.setPaycode(paymentEntity.getPayCode());
            mtcSdBJD2.setPayment(paymentEntity.getPayName());
            mtcSdBJD2.setPayamt(BigDecimal.ZERO);
            mtcSdBJD2.setPaynum("");
            mtcSdBJD2List.add(mtcSdBJD2);
        }
        return priceOrderDto;
    }

    /**
     * 保价单保存接口
     *
     * @param priceOrderDto
     * @param isEdit
     * @return
     * @throws RRException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public PriceOrderDto save(PriceOrderDto priceOrderDto, boolean isEdit) throws RRException {
        //保存前检查数据
        checkData(priceOrderDto, isEdit);
        if (!isEdit) {
            Integer docEntry = docEntryMapper.getPriceOrderDocEntry();
            MtcSdOBJD mtcSdOBJD = priceOrderDto.getMtcSdOBJD();
            mtcSdOBJD.setDocentry(docEntry);
            mtcSdOBJD.setDocnum(docEntry);
            mtcSdOBJD.setCreator(ShiroUtils.getUserEntity().getUsername());
            mtcSdOBJD.setCreatetime(DateUtils.getDateString());
            mtcSdOBJD.setDocstatus(Constant.PriceOrderDocStatusEnum.未清.getCode());

            List<MtcSdBJD1> mtcSdBJD1List = priceOrderDto.getMtcSdBJD1List();
            for (MtcSdBJD1 mtcSdBJD1 : mtcSdBJD1List) {
                mtcSdBJD1.setDocentry(docEntry);
            }

            List<MtcSdBJD2> mtcSdBJD2List = priceOrderDto.getMtcSdBJD2List();
            for (MtcSdBJD2 mtcSdBJD2 : mtcSdBJD2List) {
                mtcSdBJD2.setDocentry(docEntry);
            }

            //插入数据
            mtcSdOBJDMapper.insert(mtcSdOBJD);
            for (MtcSdBJD1 mtcSdBJD1 : mtcSdBJD1List) {
                mtcSdBJD1Mapper.insert(mtcSdBJD1);
            }
            for (MtcSdBJD2 mtcSdBJD2 : mtcSdBJD2List) {
                mtcSdBJD2Mapper.insert(mtcSdBJD2);
            }
        } else {
            MtcSdOBJD mtcSdOBJD = priceOrderDto.getMtcSdOBJD();
            mtcSdOBJD.setUpdator(ShiroUtils.getUserEntity().getUsername());
            mtcSdOBJD.setUpdatetime(DateUtils.getDateString());

            //更新主表
            MtcSdOBJDExample mtcSdOBJDExample = new MtcSdOBJDExample();
            mtcSdOBJDExample.createCriteria().andDocentryEqualTo(mtcSdOBJD.getDocentry());
            //检查主表是否存在
            List<MtcSdOBJD> mtcSdOBJDList = mtcSdOBJDMapper.selectByExample(mtcSdOBJDExample);
            if (ListUtils.isEmpty(mtcSdOBJDList)) {
                throw new RRException("单据数据不存在");
            } else {
                mtcSdOBJD.setCreator(mtcSdOBJDList.get(0).getCreator());
                mtcSdOBJD.setCreatetime(mtcSdOBJDList.get(0).getCreatetime());
            }
            mtcSdOBJDMapper.updateByExample(mtcSdOBJD, mtcSdOBJDExample);

            //更新明细表
            MtcSdBJD1Example mtcSdBJD1Example = new MtcSdBJD1Example();
            mtcSdBJD1Example.createCriteria().andDocentryEqualTo(mtcSdOBJD.getDocentry());
            mtcSdBJD1Mapper.deleteByExample(mtcSdBJD1Example);
            for (MtcSdBJD1 mtcSdBJD1 : priceOrderDto.getMtcSdBJD1List()) {
                mtcSdBJD1Mapper.insert(mtcSdBJD1);
            }

            //更新收款单
            MtcSdBJD2Example mtcSdBJD2Example = new MtcSdBJD2Example();
            mtcSdBJD2Example.createCriteria().andDocentryEqualTo(mtcSdOBJD.getDocentry());
            mtcSdBJD2Mapper.deleteByExample(mtcSdBJD2Example);
            for (MtcSdBJD2 mtcSdBJD2 : priceOrderDto.getMtcSdBJD2List()) {
                mtcSdBJD2Mapper.insert(mtcSdBJD2);
            }
        }
        return priceOrderDto;
    }

    /**
     * 订料单取消接口
     *
     * @param docentry
     * @throws RRException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancel(Integer docentry) throws RRException {
        MtcSdOBJDExample mtcSdOBJDExample = new MtcSdOBJDExample();
        mtcSdOBJDExample.createCriteria().andDocentryEqualTo(docentry);
        List<MtcSdOBJD> mtcSdOBJDList = mtcSdOBJDMapper.selectByExample(mtcSdOBJDExample);
        if (ListUtils.isEmpty(mtcSdOBJDList)) {
            throw new RRException("未查询到有效的单据");
        }
        MtcSdOBJD mtcSdOBJD = mtcSdOBJDList.get(0);
        if (mtcSdOBJD.getUsedamt() != null && mtcSdOBJD.getUsedamt().compareTo(BigDecimal.ZERO) > 0) {
            throw new RRException("已存在收款，不能更新");
        }
        if (Constant.PriceOrderDocStatusEnum.取消.getCode().equals(mtcSdOBJD.getDocstatus())
                || Constant.PriceOrderDocStatusEnum.已结算.getCode().equals(mtcSdOBJD.getDocstatus())) {
            throw new RRException("单据状态为【" + Constant.PriceOrderDocStatusEnum.value(mtcSdOBJD.getDocstatus()).name() + "】，不能取消");
        }
        MtcSdBJD2Example mtcSdBJD2Example = new MtcSdBJD2Example();
        mtcSdBJD2Example.createCriteria().andDocentryEqualTo(docentry);
        List<MtcSdBJD2> mtcSdBJD2List = mtcSdBJD2Mapper.selectByExample(mtcSdBJD2Example);
        for (MtcSdBJD2 mtcSdBJD2 : mtcSdBJD2List) {
            if (StringUtils.isNotEmpty(mtcSdBJD2.getPaynum())) {
                throw new RRException("已存在收款，不能更新");
            }
        }
        //检查分支期间锁定状态不能取消，只能关闭
        BplidPeriodExample bplidPeriodExample = new BplidPeriodExample();
        bplidPeriodExample.createCriteria().andCodeEqualTo(mtcSdOBJD.getBplid())
                .andUStdateLessThanOrEqualTo(DateUtils.stringToDate(mtcSdOBJD.getDocdate(), DateUtils.DATE_PATTERN))
                .andUEnddateGreaterThanOrEqualTo(DateUtils.stringToDate(mtcSdOBJD.getDocdate(), DateUtils.DATE_PATTERN))
                .andUFstatusEqualTo(Constant.PeriodStatusUnLock);
        List<BplidPeriod> bplidPeriodList = bplidPeriodMapper.selectByExample(bplidPeriodExample);
        if (ListUtils.isEmpty(bplidPeriodList)) {
            throw new RRException("分支期间未解锁");
        }
        mtcSdOBJD.setDocstatus(Constant.PriceOrderDocStatusEnum.取消.getCode());
        mtcSdOBJD.setUpdator(ShiroUtils.getUserEntity().getUsername());
        mtcSdOBJD.setUpdatetime(DateUtils.getDateString());
        mtcSdOBJDMapper.updateByExample(mtcSdOBJD, mtcSdOBJDExample);
    }

    /**
     * 订料单关闭接口
     *
     * @param docentry
     * @throws RRException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void close(Integer docentry) throws RRException {
        MtcSdOBJDExample mtcSdOBJDExample = new MtcSdOBJDExample();
        mtcSdOBJDExample.createCriteria().andDocentryEqualTo(docentry);
        List<MtcSdOBJD> mtcSdOBJDList = mtcSdOBJDMapper.selectByExample(mtcSdOBJDExample);
        if (ListUtils.isEmpty(mtcSdOBJDList)) {
            throw new RRException("未查询到有效的单据");
        }
        MtcSdOBJD mtcSdOBJD = mtcSdOBJDList.get(0);
        if (Constant.PriceOrderDocStatusEnum.取消.getCode().equals(mtcSdOBJD.getDocstatus())
                || Constant.PriceOrderDocStatusEnum.已结算.getCode().equals(mtcSdOBJD.getDocstatus())) {
            throw new RRException("单据状态为【" + Constant.PriceOrderDocStatusEnum.value(mtcSdOBJD.getDocstatus()).name() + "】，不能关闭");
        }

        mtcSdOBJD.setDocstatus(Constant.PriceOrderDocStatusEnum.已结算.getCode());
        mtcSdOBJD.setUpdator(ShiroUtils.getUserEntity().getUsername());
        mtcSdOBJD.setUpdatetime(DateUtils.getDateString());
        mtcSdOBJDMapper.updateByExample(mtcSdOBJD, mtcSdOBJDExample);
    }

    /**
     * 订料单生成收款单接口
     *
     * @param docentry
     * @throws RRException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void receipt(Integer docentry) throws RRException {
        //检查单据状态
        MtcSdOBJDExample mtcSdOBJDExample = new MtcSdOBJDExample();
        mtcSdOBJDExample.createCriteria().andDocentryEqualTo(docentry);
        List<MtcSdOBJD> mtcSdOBJDList = mtcSdOBJDMapper.selectByExample(mtcSdOBJDExample);
        if (ListUtils.isEmpty(mtcSdOBJDList)) {
            throw new RRException("未查询到有效的单据");
        }
        MtcSdOBJD mtcSdOBJD = mtcSdOBJDList.get(0);
        if (Constant.PriceOrderDocStatusEnum.取消.getCode().equals(mtcSdOBJD.getDocstatus())
                || Constant.PriceOrderDocStatusEnum.已结算.getCode().equals(mtcSdOBJD.getDocstatus())) {
            throw new RRException("单据状态为【" + Constant.PriceOrderDocStatusEnum.value(mtcSdOBJD.getDocstatus()).name() + "】，不能生成收款单");
        }


        MtcSdBJD2Example mtcSdBJD2Example = new MtcSdBJD2Example();
        mtcSdBJD2Example.createCriteria().andDocentryEqualTo(docentry);
        List<MtcSdBJD2> mtcSdBJD2List = mtcSdBJD2Mapper.selectByExample(mtcSdBJD2Example);
        if (ListUtils.isEmpty(mtcSdBJD2List)) {
            throw new RRException("没有有效的收款信息，无法生成收款单");
        }
        boolean needCreate = false;
        for (MtcSdBJD2 mtcSdBJD2 : mtcSdBJD2List) {
            if (StringUtils.isEmpty(mtcSdBJD2.getPaynum())) {
                needCreate = true;
                break;
            }
        }
        if (!needCreate) {
            throw new RRException("收款单已生成");
        }

        SysConfigEntity sysConfigEntity = sysConfigDao.queryByKey("Default_Payment_AcctCode");
        if (sysConfigEntity == null || StringUtils.isEmpty(sysConfigEntity.getParamValue())) {
            throw new RRException("请先配置生成收款单时的控制科目：数据字典【Default_Payment_AcctCode】");
        }

        List<IncomingPayments> incomingPaymentsList = paymentDao.searchPriceOrderReceipt(String.valueOf(docentry));

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
            incomingPayments.setU_BankID(incomingPayments.getBankID());
            incomingPayments.setU_PayCode(incomingPayments.getPayCode());
            incomingPayments.setU_PayName(incomingPayments.getPayName());
            incomingPayments.setU_BankName(incomingPayments.getBankName());
            incomingPayments.setU_BaseEntry(incomingPayments.getBaseEntry());
            incomingPayments.setControlAccount(sysConfigEntity.getParamValue());
            incomingPayments.setU_BaseType(incomingPayments.getBaseType());
            //设定单据唯一标识，防止重复添加单据
            incomingPayments.setU_GUID(UUID.randomUUID().toString());

            Integer series = paymentDao.getSeries(incomingPayments.getBPLID().toString());
            incomingPayments.setSeries(series);

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
                paymentDao.updateOBJDReceipt(String.valueOf(docentry));
            }
        }
    }

    /**
     * 取消生成的收款单
     *
     * @param docentry
     * @throws RRException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelrpt(Integer docentry) throws RRException {
        //检查单据状态
        MtcSdOBJDExample mtcSdOBJDExample = new MtcSdOBJDExample();
        mtcSdOBJDExample.createCriteria().andDocentryEqualTo(docentry);
        List<MtcSdOBJD> mtcSdOBJDList = mtcSdOBJDMapper.selectByExample(mtcSdOBJDExample);
        if (ListUtils.isEmpty(mtcSdOBJDList)) {
            throw new RRException("未查询到有效的单据");
        }
        MtcSdOBJD mtcSdOBJD = mtcSdOBJDList.get(0);
        if (Constant.PriceOrderDocStatusEnum.取消.getCode().equals(mtcSdOBJD.getDocstatus())
                || Constant.PriceOrderDocStatusEnum.已结算.getCode().equals(mtcSdOBJD.getDocstatus())) {
            throw new RRException("单据状态为【" + Constant.DesOrderDocStatusEnum.value(mtcSdOBJD.getDocstatus()).name() + "】，不能取消收款单");
        }
        //收款金额被开票单使用时，不允许取消收款
        if (mtcSdOBJD.getUsedamt() != null && BigDecimal.ZERO.compareTo(mtcSdOBJD.getUsedamt()) < 0) {
            throw new RRException("已使用收款金额，不能取消收款");
        }

        MtcSdBJD2Example mtcSdBJD2Example = new MtcSdBJD2Example();
        mtcSdBJD2Example.createCriteria().andDocentryEqualTo(docentry);
        List<MtcSdBJD2> mtcSdBJD2List = mtcSdBJD2Mapper.selectByExample(mtcSdBJD2Example);
        if (ListUtils.isEmpty(mtcSdBJD2List)) {
            throw new RRException("没有有效的收款信息，无法取消收款单");
        }
        List<String> reciptNumList = new ArrayList<>();
        for (MtcSdBJD2 mtcSdBJD2 : mtcSdBJD2List) {
            if (StringUtils.isNotEmpty(mtcSdBJD2.getPaynum())) {
                reciptNumList.add(mtcSdBJD2.getPaynum());
            }
        }
        if (ListUtils.isEmpty(reciptNumList)) {
            throw new RRException("没有可取消的收款单");
        }

        //依次循环取消对应的收款单
        for (String docNum : reciptNumList) {
            try {
                List<String> docEntryList = paymentDao.selectORCTEntry(docNum);
                if (ListUtils.isEmpty(docEntryList)) continue;
                incomingPaymentsDao.cancel(Integer.valueOf(docEntryList.get(0)));

                //收款后更新单据为未清
                mtcSdOBJD.setDocstatus(Constant.PriceOrderDocStatusEnum.未清.getCode());
                mtcSdOBJD.setUpdator(ShiroUtils.getUserEntity().getUsername());
                mtcSdOBJD.setUpdatetime(DateUtils.getDateString());
                mtcSdOBJDMapper.updateByExample(mtcSdOBJD, mtcSdOBJDExample);
            } catch (Exception e) {
                throw new RRException(e.getMessage());
            } finally {
                //更新收款单号
                paymentDao.updateOBJDReceipt(String.valueOf(docentry));
            }
        }
    }

    /**
     * 检查数据
     *
     * @param priceOrderDto
     * @throws RRException
     */
    private void checkData(PriceOrderDto priceOrderDto, boolean isEdit) throws RRException {
        //检查主表逻辑
        MtcSdOBJD mtcSdOBJD = priceOrderDto.getMtcSdOBJD();
        if (StringUtils.isEmpty(mtcSdOBJD.getBplid())) {
            throw new RRException("请选择分支");
        }
        if (StringUtils.isEmpty(mtcSdOBJD.getCardcode())) {
            throw new RRException("请选择客户");
        }
        if (mtcSdOBJD.getDocdate() == null) {
            throw new RRException("请输入单据日期");
        }
        //检查分支期间
        BplidPeriodExample bplidPeriodExample = new BplidPeriodExample();
        bplidPeriodExample.createCriteria().andCodeEqualTo(mtcSdOBJD.getBplid())
                .andUStdateLessThanOrEqualTo(DateUtils.stringToDate(mtcSdOBJD.getDocdate(), DateUtils.DATE_PATTERN))
                .andUEnddateGreaterThanOrEqualTo(DateUtils.stringToDate(mtcSdOBJD.getDocdate(), DateUtils.DATE_PATTERN))
                .andUFstatusEqualTo(Constant.PeriodStatusUnLock);
        List<BplidPeriod> bplidPeriodList = bplidPeriodMapper.selectByExample(bplidPeriodExample);
        if (ListUtils.isEmpty(bplidPeriodList)) {
            throw new RRException("分支期间未解锁");
        }

        //修改状态下检查数据
        if (isEdit) {
            MtcSdOBJDExample mtcSdOBJDExample = new MtcSdOBJDExample();
            mtcSdOBJDExample.createCriteria().andDocentryEqualTo(mtcSdOBJD.getDocentry());
            List<MtcSdOBJD> mtcSdOBJDList = mtcSdOBJDMapper.selectByExample(mtcSdOBJDExample);
            if (ListUtils.isEmpty(mtcSdOBJDList)) {
                throw new RRException("未查询到有效的单据");
            }
            if (!mtcSdOBJDList.get(0).getDocstatus().equals(mtcSdOBJD.getDocstatus())) {
                throw new RRException("状态已变更，请刷新");
            }
            if (mtcSdOBJD.getUsedamt() != null && mtcSdOBJD.getUsedamt().compareTo(BigDecimal.ZERO) > 0) {
                throw new RRException("已存在收款，不能更新");
            }
        }

        List<MtcSdBJD1> mtcSdBJD1List = priceOrderDto.getMtcSdBJD1List();
        if (ListUtils.isEmpty(mtcSdBJD1List)) {
            throw new RRException("订料单明细不能为空");
        }

        //检查明细表信息
        int rowIndex = 0;
        for (MtcSdBJD1 mtcSdBJD1 : mtcSdBJD1List) {
            rowIndex++;
            mtcSdBJD1.setLinenum(rowIndex);
            if (StringUtils.isEmpty(mtcSdBJD1.getItemcode())) {
                throw new RRException("第" + rowIndex + "行物料代码不能为空");
            }

            List<MtcSdBJD1> tmpMtcSdBJD1List = mtcSdBJD1List.stream().filter((MtcSdBJD1 a) ->
                    a.getItemcode().equals(mtcSdBJD1.getItemcode())).collect(Collectors.toList());
            if (ListUtils.isNotEmpty(tmpMtcSdBJD1List) && tmpMtcSdBJD1List.size() > 1) {
                throw new RRException("物料【" + mtcSdBJD1.getItemcode() + " - " + mtcSdBJD1.getItemname() + "】存在多条记录");
            }
        }

        MtcSdOBJDExample mtcSdOBJDExample = new MtcSdOBJDExample();
        //检查收款单号信息
        if (isEdit) {
            MtcSdBJD2Example mtcSdBJD2Example = new MtcSdBJD2Example();
            mtcSdBJD2Example.createCriteria().andDocentryEqualTo(mtcSdOBJD.getDocentry());
            List<MtcSdBJD2> mtcSdBJD2List = mtcSdBJD2Mapper.selectByExample(mtcSdBJD2Example);
            for (MtcSdBJD2 mtcSdBJD2 : mtcSdBJD2List) {
                if (StringUtils.isNotEmpty(mtcSdBJD2.getPaynum())) {
                    throw new RRException("已存在收款，不能更新");
                }
            }

            //检查同分支、同客户是否存在未清的订料单（排除当前单据）
            mtcSdOBJDExample.createCriteria().andBplidEqualTo(mtcSdOBJD.getBplid()).andCardcodeEqualTo(mtcSdOBJD.getCardcode())
                    .andDocstatusEqualTo(Constant.PriceOrderDocStatusEnum.未清.getCode())
                    .andDocentryNotEqualTo(mtcSdOBJD.getDocentry());
            List<MtcSdOBJD> mtcSdOBJDList = mtcSdOBJDMapper.selectByExample(mtcSdOBJDExample);
            if (ListUtils.isNotEmpty(mtcSdOBJDList)) {
                throw new RRException("分支【" + mtcSdOBJD.getBplid() + "】业务伙伴【" + mtcSdOBJD.getCardcode() + "】已存在未清单据，不能更新");
            }
        } else {
            //检查同分支、同客户是否存在未清的订料单
            mtcSdOBJDExample.createCriteria().andBplidEqualTo(mtcSdOBJD.getBplid()).andCardcodeEqualTo(mtcSdOBJD.getCardcode())
                    .andDocstatusEqualTo(Constant.DesOrderDocStatusEnum.未清.getCode());
            List<MtcSdOBJD> mtcSdOBJDList = mtcSdOBJDMapper.selectByExample(mtcSdOBJDExample);
            if (ListUtils.isNotEmpty(mtcSdOBJDList)) {
                throw new RRException("分支【" + mtcSdOBJD.getBplid() + "】业务伙伴【" + mtcSdOBJD.getCardcode() + "】已存在未清单据，不能添加");
            }
        }

    }
}
