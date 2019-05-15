package io.mtc.modules.sap.service.impl;

import io.mtc.common.exception.RRException;
import io.mtc.common.utils.DateUtils;
import io.mtc.common.utils.ListUtils;
import io.mtc.modules.sap.constant.Constant;
import io.mtc.modules.sap.dao.PaymentDao;
import io.mtc.modules.sap.dto.DesOrderDto;
import io.mtc.modules.sap.entity.*;
import io.mtc.modules.sap.mapper.*;
import io.mtc.modules.sap.service.DesOrderService;
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
@Service("desOrderService")
public class DesOrderServiceImpl implements DesOrderService {

    @Autowired
    private BplidPeriodMapper bplidPeriodMapper;
    @Autowired
    private DocEntryMapper docEntryMapper;
    @Autowired
    private MtcSdODLDMapper mtcSdODLDMapper;
    @Autowired
    private MtcSdDLD1Mapper mtcSdDLD1Mapper;
    @Autowired
    private MtcSdDLD2Mapper mtcSdDLD2Mapper;
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
    public DesOrderDto info(Integer docentry) throws RRException {
        DesOrderDto desOrderDto = new DesOrderDto();

        MtcSdODLDExample mtcSdODLDExample = new MtcSdODLDExample();
        mtcSdODLDExample.createCriteria().andDocentryEqualTo(docentry);
        List<MtcSdODLD> mtcSdODLDList = mtcSdODLDMapper.selectByExample(mtcSdODLDExample);
        if (ListUtils.isEmpty(mtcSdODLDList)) {
            throw new RRException("未查询到有效的单据");
        }
        MtcSdODLD mtcSdODLD = mtcSdODLDList.get(0);
        desOrderDto.setMtcSdODLD(mtcSdODLD);

        //子表数据
        MtcSdDLD1Example mtcSdDLD1Example = new MtcSdDLD1Example();
        mtcSdDLD1Example.createCriteria().andDocentryEqualTo(docentry);
        mtcSdDLD1Example.setOrderByClause("\"LineNum\"");
        List<MtcSdDLD1> mtcSdDLD1List = mtcSdDLD1Mapper.selectByExample(mtcSdDLD1Example);
        desOrderDto.setMtcSdDLD1List(mtcSdDLD1List);

        //收款单信息数据
        MtcSdDLD2Example mtcSdDLD2Example = new MtcSdDLD2Example();
        mtcSdDLD2Example.createCriteria().andDocentryEqualTo(docentry);
        List<MtcSdDLD2> mtcSdDLD2List = mtcSdDLD2Mapper.selectByExample(mtcSdDLD2Example);
        desOrderDto.setMtcSdDLD2List(mtcSdDLD2List);

        //根据最新的收款方式
        List<PaymentEntity> paymentEntityList = paymentDao.searchInSO(mtcSdODLD.getCardcode(),
                ShiroUtils.getUserEntity().getUsername(), mtcSdODLD.getBplid(),
                String.valueOf(mtcSdODLD.getDocentry()), "MTC_SD_ODLD");
        List<PaymentEntity> needAddPayList = new ArrayList<>();
        for (PaymentEntity paymentEntity : paymentEntityList) {
            List<MtcSdDLD2> tmpList = mtcSdDLD2List.stream().filter((MtcSdDLD2 a)
                    -> a.getPaycode().equals(paymentEntity.getPayCode())).collect(Collectors.toList());
            if (ListUtils.isEmpty(tmpList)) {
                needAddPayList.add(paymentEntity);
            }
        }

        for (PaymentEntity paymentEntity : needAddPayList) {
            MtcSdDLD2 mtcSdDLD2 = new MtcSdDLD2();
            mtcSdDLD2.setDocentry(docentry);
            mtcSdDLD2.setPaycode(paymentEntity.getPayCode());
            mtcSdDLD2.setPayment(paymentEntity.getPayName());
            mtcSdDLD2.setPayamt(BigDecimal.ZERO);
            mtcSdDLD2.setPaynum("");
            mtcSdDLD2List.add(mtcSdDLD2);
        }
        return desOrderDto;
    }

    /**
     * 订料单保存接口
     *
     * @param desOrderDto
     * @param isEdit
     * @return
     * @throws RRException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public DesOrderDto save(DesOrderDto desOrderDto, boolean isEdit) throws RRException {
        //保存前检查数据
        checkData(desOrderDto, isEdit);
        if (!isEdit) {
            Integer docEntry = docEntryMapper.getDesOrderDocEntry();
            MtcSdODLD mtcSdODLD = desOrderDto.getMtcSdODLD();
            mtcSdODLD.setDocentry(docEntry);
            mtcSdODLD.setDocnum(String.valueOf(docEntry));
            mtcSdODLD.setCreator(ShiroUtils.getUserEntity().getUsername());
            mtcSdODLD.setCreatetime(DateUtils.getDateString());
            mtcSdODLD.setDocstatus(Constant.DesOrderDocStatusEnum.未清.getCode());

            List<MtcSdDLD1> mtcSdDLD1List = desOrderDto.getMtcSdDLD1List();
            for (MtcSdDLD1 mtcSdDLD1 : mtcSdDLD1List) {
                mtcSdDLD1.setLastqty(mtcSdDLD1.getQuantity());
                mtcSdDLD1.setDocentry(docEntry);
            }

            List<MtcSdDLD2> mtcSdDLD2List = desOrderDto.getMtcSdDLD2List();
            for (MtcSdDLD2 mtcSdDLD2 : mtcSdDLD2List) {
                mtcSdDLD2.setDocentry(docEntry);
            }

            //插入数据
            mtcSdODLDMapper.insert(mtcSdODLD);
            for (MtcSdDLD1 mtcSdDLD1 : mtcSdDLD1List) {
                mtcSdDLD1.setLastqty(mtcSdDLD1.getQuantity());
                mtcSdDLD1Mapper.insert(mtcSdDLD1);
            }
            for (MtcSdDLD2 mtcSdDLD2 : mtcSdDLD2List) {
                mtcSdDLD2Mapper.insert(mtcSdDLD2);
            }
        } else {
            MtcSdODLD mtcSdODLD = desOrderDto.getMtcSdODLD();
            mtcSdODLD.setUpdator(ShiroUtils.getUserEntity().getUsername());
            mtcSdODLD.setUpdatetime(DateUtils.getDateString());

            //更新主表
            MtcSdODLDExample mtcSdODLDExample = new MtcSdODLDExample();
            mtcSdODLDExample.createCriteria().andDocentryEqualTo(mtcSdODLD.getDocentry());
            //检查主表是否存在
            List<MtcSdODLD> mtcSdODLDList = mtcSdODLDMapper.selectByExample(mtcSdODLDExample);
            if (ListUtils.isEmpty(mtcSdODLDList)) {
                throw new RRException("单据数据不存在");
            } else {
                mtcSdODLD.setCreator(mtcSdODLDList.get(0).getCreator());
                mtcSdODLD.setCreatetime(mtcSdODLDList.get(0).getCreatetime());
            }

            mtcSdODLDMapper.updateByExample(mtcSdODLD, mtcSdODLDExample);

            //更新明细表
            MtcSdDLD1Example mtcSdDLD1Example = new MtcSdDLD1Example();
            mtcSdDLD1Example.createCriteria().andDocentryEqualTo(mtcSdODLD.getDocentry());
            mtcSdDLD1Mapper.deleteByExample(mtcSdDLD1Example);
            for (MtcSdDLD1 mtcSdDLD1 : desOrderDto.getMtcSdDLD1List()) {
                mtcSdDLD1.setLastqty(mtcSdDLD1.getQuantity());
                mtcSdDLD1Mapper.insert(mtcSdDLD1);
            }

            //更新收款单
            MtcSdDLD2Example mtcSdDLD2Example = new MtcSdDLD2Example();
            mtcSdDLD2Example.createCriteria().andDocentryEqualTo(mtcSdODLD.getDocentry());
            mtcSdDLD2Mapper.deleteByExample(mtcSdDLD2Example);
            for (MtcSdDLD2 mtcSdDLD2 : desOrderDto.getMtcSdDLD2List()) {
                mtcSdDLD2Mapper.insert(mtcSdDLD2);
            }
        }
        return desOrderDto;
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
        MtcSdODLDExample mtcSdODLDExample = new MtcSdODLDExample();
        mtcSdODLDExample.createCriteria().andDocentryEqualTo(docentry);
        List<MtcSdODLD> mtcSdODLDList = mtcSdODLDMapper.selectByExample(mtcSdODLDExample);
        if (ListUtils.isEmpty(mtcSdODLDList)) {
            throw new RRException("未查询到有效的单据");
        }
        MtcSdODLD mtcSdODLD = mtcSdODLDList.get(0);
        if (mtcSdODLD.getUsedamt() != null && mtcSdODLD.getUsedamt().compareTo(BigDecimal.ZERO) > 0) {
            throw new RRException("已存在收款，不能更新");
        }
        if (Constant.DesOrderDocStatusEnum.取消.getCode().equals(mtcSdODLD.getDocstatus())
                || Constant.DesOrderDocStatusEnum.已结算.getCode().equals(mtcSdODLD.getDocstatus())) {
            throw new RRException("单据状态为【" + Constant.DesOrderDocStatusEnum.value(mtcSdODLD.getDocstatus()).name() + "】，不能取消");
        }
        MtcSdDLD2Example mtcSdDLD2Example = new MtcSdDLD2Example();
        mtcSdDLD2Example.createCriteria().andDocentryEqualTo(docentry);
        List<MtcSdDLD2> mtcSdDLD2List = mtcSdDLD2Mapper.selectByExample(mtcSdDLD2Example);
        for (MtcSdDLD2 mtcSdDLD2 : mtcSdDLD2List) {
            if (StringUtils.isNotEmpty(mtcSdDLD2.getPaynum())) {
                throw new RRException("已存在收款，不能更新");
            }
        }
        //检查分支期间锁定状态不能取消，只能关闭
        BplidPeriodExample bplidPeriodExample = new BplidPeriodExample();
        bplidPeriodExample.createCriteria().andCodeEqualTo(mtcSdODLD.getBplid())
                .andUStdateLessThanOrEqualTo(DateUtils.stringToDate(mtcSdODLD.getDocdate(), DateUtils.DATE_PATTERN))
                .andUEnddateGreaterThanOrEqualTo(DateUtils.stringToDate(mtcSdODLD.getDocdate(), DateUtils.DATE_PATTERN))
                .andUFstatusEqualTo(Constant.PeriodStatusUnLock);
        List<BplidPeriod> bplidPeriodList = bplidPeriodMapper.selectByExample(bplidPeriodExample);
        if (ListUtils.isEmpty(bplidPeriodList)) {
            throw new RRException("分支期间未解锁");
        }
        mtcSdODLD.setDocstatus(Constant.DesOrderDocStatusEnum.取消.getCode());
        mtcSdODLD.setUpdator(ShiroUtils.getUserEntity().getUsername());
        mtcSdODLD.setUpdatetime(DateUtils.getDateString());
        mtcSdODLDMapper.updateByExample(mtcSdODLD, mtcSdODLDExample);
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
        MtcSdODLDExample mtcSdODLDExample = new MtcSdODLDExample();
        mtcSdODLDExample.createCriteria().andDocentryEqualTo(docentry);
        List<MtcSdODLD> mtcSdODLDList = mtcSdODLDMapper.selectByExample(mtcSdODLDExample);
        if (ListUtils.isEmpty(mtcSdODLDList)) {
            throw new RRException("未查询到有效的单据");
        }
        MtcSdODLD mtcSdODLD = mtcSdODLDList.get(0);
        if (Constant.DesOrderDocStatusEnum.取消.getCode().equals(mtcSdODLD.getDocstatus())
                || Constant.DesOrderDocStatusEnum.已结算.getCode().equals(mtcSdODLD.getDocstatus())) {
            throw new RRException("单据状态为【" + Constant.DesOrderDocStatusEnum.value(mtcSdODLD.getDocstatus()).name() + "】，不能关闭");
        }

        mtcSdODLD.setDocstatus(Constant.DesOrderDocStatusEnum.已结算.getCode());
        mtcSdODLD.setUpdator(ShiroUtils.getUserEntity().getUsername());
        mtcSdODLD.setUpdatetime(DateUtils.getDateString());
        mtcSdODLDMapper.updateByExample(mtcSdODLD, mtcSdODLDExample);
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
        MtcSdODLDExample mtcSdODLDExample = new MtcSdODLDExample();
        mtcSdODLDExample.createCriteria().andDocentryEqualTo(docentry);
        List<MtcSdODLD> mtcSdODLDList = mtcSdODLDMapper.selectByExample(mtcSdODLDExample);
        if (ListUtils.isEmpty(mtcSdODLDList)) {
            throw new RRException("未查询到有效的单据");
        }
        MtcSdODLD mtcSdODLD = mtcSdODLDList.get(0);
        if (Constant.DesOrderDocStatusEnum.取消.getCode().equals(mtcSdODLD.getDocstatus())
                || Constant.DesOrderDocStatusEnum.已结算.getCode().equals(mtcSdODLD.getDocstatus())) {
            throw new RRException("单据状态为【" + Constant.DesOrderDocStatusEnum.value(mtcSdODLD.getDocstatus()).name() + "】，不能生成收款单");
        }


        MtcSdDLD2Example mtcSdDLD2Example = new MtcSdDLD2Example();
        mtcSdDLD2Example.createCriteria().andDocentryEqualTo(docentry);
        List<MtcSdDLD2> mtcSdDLD2List = mtcSdDLD2Mapper.selectByExample(mtcSdDLD2Example);
        if (ListUtils.isEmpty(mtcSdDLD2List)) {
            throw new RRException("没有有效的收款信息，无法生成收款单");
        }
        boolean needCreate = false;
        for (MtcSdDLD2 mtcSdDLD2 : mtcSdDLD2List) {
            if (StringUtils.isEmpty(mtcSdDLD2.getPaynum())) {
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

        List<IncomingPayments> incomingPaymentsList = paymentDao.searchDesOrderReceipt(String.valueOf(docentry));
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
            //设定单据唯一标识，防止重复添加单据
            incomingPayments.setU_GUID(UUID.randomUUID().toString());

            try {
                incomingPaymentsDao.save(incomingPayments);
            } catch (Exception e) {
                throw new RRException(e.getMessage());
            } finally {
                //更新收款单号
                paymentDao.updateODLDReceipt(String.valueOf(docentry));
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
        MtcSdODLDExample mtcSdODLDExample = new MtcSdODLDExample();
        mtcSdODLDExample.createCriteria().andDocentryEqualTo(docentry);
        List<MtcSdODLD> mtcSdODLDList = mtcSdODLDMapper.selectByExample(mtcSdODLDExample);
        if (ListUtils.isEmpty(mtcSdODLDList)) {
            throw new RRException("未查询到有效的单据");
        }
        MtcSdODLD mtcSdODLD = mtcSdODLDList.get(0);
        if (Constant.DesOrderDocStatusEnum.取消.getCode().equals(mtcSdODLD.getDocstatus())
                || Constant.DesOrderDocStatusEnum.已结算.getCode().equals(mtcSdODLD.getDocstatus())) {
            throw new RRException("单据状态为【" + Constant.DesOrderDocStatusEnum.value(mtcSdODLD.getDocstatus()).name() + "】，不能取消收款单");
        }
        //收款金额被开票单使用时，不允许取消收款
        if (mtcSdODLD.getUsedamt() != null && BigDecimal.ZERO.compareTo(mtcSdODLD.getUsedamt()) < 0) {
            throw new RRException("已使用收款金额，不能取消收款");
        }

        MtcSdDLD2Example mtcSdDLD2Example = new MtcSdDLD2Example();
        mtcSdDLD2Example.createCriteria().andDocentryEqualTo(docentry);
        List<MtcSdDLD2> mtcSdDLD2List = mtcSdDLD2Mapper.selectByExample(mtcSdDLD2Example);
        if (ListUtils.isEmpty(mtcSdDLD2List)) {
            throw new RRException("没有有效的收款信息，无法取消收款单");
        }
        List<String> reciptNumList = new ArrayList<>();
        for (MtcSdDLD2 mtcSdDLD2 : mtcSdDLD2List) {
            if (StringUtils.isNotEmpty(mtcSdDLD2.getPaynum())) {
                reciptNumList.add(mtcSdDLD2.getPaynum());
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

                //取消收款后更新单据状态为未清
                mtcSdODLD.setDocstatus(Constant.DesOrderDocStatusEnum.未清.getCode());
                mtcSdODLD.setUpdator(ShiroUtils.getUserEntity().getUsername());
                mtcSdODLD.setUpdatetime(DateUtils.getDateString());
                mtcSdODLDMapper.updateByExample(mtcSdODLD, mtcSdODLDExample);
            } catch (Exception e) {
                throw new RRException(e.getMessage());
            } finally {
                //更新收款单号
                paymentDao.updateODLDReceipt(String.valueOf(docentry));
            }
        }
    }

    /**
     * 检查数据
     *
     * @param desOrderDto
     * @throws RRException
     */
    private void checkData(DesOrderDto desOrderDto, boolean isEdit) throws RRException {
        //检查主表逻辑
        MtcSdODLD mtcSdODLD = desOrderDto.getMtcSdODLD();
        if (StringUtils.isEmpty(mtcSdODLD.getBplid())) {
            throw new RRException("请选择分支");
        }
        if (StringUtils.isEmpty(mtcSdODLD.getCardcode())) {
            throw new RRException("请选择客户");
        }
        if (mtcSdODLD.getDocdate() == null) {
            throw new RRException("请输入单据日期");
        }
        //检查分支期间
        BplidPeriodExample bplidPeriodExample = new BplidPeriodExample();
        bplidPeriodExample.createCriteria().andCodeEqualTo(mtcSdODLD.getBplid())
                .andUStdateLessThanOrEqualTo(DateUtils.stringToDate(mtcSdODLD.getDocdate(), DateUtils.DATE_PATTERN))
                .andUEnddateGreaterThanOrEqualTo(DateUtils.stringToDate(mtcSdODLD.getDocdate(), DateUtils.DATE_PATTERN))
                .andUFstatusEqualTo(Constant.PeriodStatusUnLock);
        List<BplidPeriod> bplidPeriodList = bplidPeriodMapper.selectByExample(bplidPeriodExample);
        if (ListUtils.isEmpty(bplidPeriodList)) {
            throw new RRException("分支期间未解锁");
        }

        //修改状态下检查数据
        if (isEdit) {
            MtcSdODLDExample mtcSdODLDExample = new MtcSdODLDExample();
            mtcSdODLDExample.createCriteria().andDocentryEqualTo(mtcSdODLD.getDocentry());
            List<MtcSdODLD> mtcSdODLDList = mtcSdODLDMapper.selectByExample(mtcSdODLDExample);
            if (ListUtils.isEmpty(mtcSdODLDList)) {
                throw new RRException("未查询到有效的单据");
            }
            if (!mtcSdODLDList.get(0).getDocstatus().equals(mtcSdODLD.getDocstatus())) {
                throw new RRException("状态已变更，请刷新");
            }
            if (mtcSdODLD.getUsedamt() != null && mtcSdODLD.getUsedamt().compareTo(BigDecimal.ZERO) > 0) {
                throw new RRException("已存在收款，不能更新");
            }
        }

        List<MtcSdDLD1> mtcSdDLD1List = desOrderDto.getMtcSdDLD1List();
        if (ListUtils.isEmpty(mtcSdDLD1List)) {
            throw new RRException("订料单明细不能为空");
        }

        //检查明细表信息
        int rowIndex = 0;
        for (MtcSdDLD1 mtcSdDLD1 : mtcSdDLD1List) {
            rowIndex++;
            mtcSdDLD1.setLinenum(rowIndex);
            if (StringUtils.isEmpty(mtcSdDLD1.getItemcode())) {
                throw new RRException("第" + rowIndex + "行物料代码不能为空");
            }
            if (BigDecimal.ZERO.compareTo(mtcSdDLD1.getQuantity()) > 0) {
                throw new RRException("第" + rowIndex + "行数量不能小于0");
            }
            if (isEdit) {
                if (mtcSdDLD1.getUesdqty() != null && mtcSdDLD1.getUesdqty().compareTo(BigDecimal.ZERO) > 0) {
                    throw new RRException("第" + rowIndex + "行已存在使用数量，不能更新");
                }
            }
            List<MtcSdDLD1> tmpMtcSdDLD1List = mtcSdDLD1List.stream().filter((MtcSdDLD1 a) ->
                    a.getItemcode().equals(mtcSdDLD1.getItemcode())).collect(Collectors.toList());
            if (ListUtils.isNotEmpty(tmpMtcSdDLD1List) && tmpMtcSdDLD1List.size() > 1) {
                throw new RRException("物料【" + mtcSdDLD1.getItemcode() + " - " + mtcSdDLD1.getItemname() + "】存在多条记录");
            }
        }

        MtcSdODLDExample mtcSdODLDExample = new MtcSdODLDExample();
        //检查收款单号信息
        if (isEdit) {
            MtcSdDLD2Example mtcSdDLD2Example = new MtcSdDLD2Example();
            mtcSdDLD2Example.createCriteria().andDocentryEqualTo(mtcSdODLD.getDocentry());
            List<MtcSdDLD2> mtcSdDLD2List = mtcSdDLD2Mapper.selectByExample(mtcSdDLD2Example);
            for (MtcSdDLD2 mtcSdDLD2 : mtcSdDLD2List) {
                if (StringUtils.isNotEmpty(mtcSdDLD2.getPaynum())) {
                    throw new RRException("已存在收款，不能更新");
                }
            }

            //检查同分支、同客户是否存在未清的订料单（排除当前单据）
            mtcSdODLDExample.createCriteria().andBplidEqualTo(mtcSdODLD.getBplid()).andCardcodeEqualTo(mtcSdODLD.getCardcode())
                    .andDocstatusEqualTo(Constant.DesOrderDocStatusEnum.未清.getCode())
                    .andDocentryNotEqualTo(mtcSdODLD.getDocentry());
            List<MtcSdODLD> mtcSdODLDList = mtcSdODLDMapper.selectByExample(mtcSdODLDExample);
            if (ListUtils.isNotEmpty(mtcSdODLDList)) {
                throw new RRException("分支【" + mtcSdODLD.getBplid() + "】业务伙伴【" + mtcSdODLD.getCardcode() + "】已存在未清单据，不能更新");
            }
        } else {
            //检查同分支、同客户是否存在未清的订料单
            mtcSdODLDExample.createCriteria().andBplidEqualTo(mtcSdODLD.getBplid()).andCardcodeEqualTo(mtcSdODLD.getCardcode())
                    .andDocstatusEqualTo(Constant.DesOrderDocStatusEnum.未清.getCode());
            List<MtcSdODLD> mtcSdODLDList = mtcSdODLDMapper.selectByExample(mtcSdODLDExample);
            if (ListUtils.isNotEmpty(mtcSdODLDList)) {
                throw new RRException("分支【" + mtcSdODLD.getBplid() + "】业务伙伴【" + mtcSdODLD.getCardcode() + "】已存在未清单据，不能添加");
            }
        }
    }
}
