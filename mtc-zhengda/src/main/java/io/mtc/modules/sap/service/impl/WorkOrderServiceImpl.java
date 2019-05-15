package io.mtc.modules.sap.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.mtc.common.exception.RRException;
import io.mtc.common.utils.DateUtils;
import io.mtc.common.utils.ListUtils;
import io.mtc.common.utils.ShiroUtils;
import io.mtc.common.validator.Assert;
import io.mtc.modules.sap.constant.Constant;
import io.mtc.modules.sap.dao.WorkOrderDao;
import io.mtc.modules.sap.entity.MtcWRDR1Entity;
import io.mtc.modules.sap.entity.MtcWRDREntity;
import io.mtc.modules.sap.entity.MtcWUQTEntity;
import io.mtc.modules.sap.form.WorkOrderForm;
import io.mtc.modules.sap.param.KeyParam;
import io.mtc.modules.sap.param.LineKeyParam;
import io.mtc.modules.sap.param.SearchParam;
import io.mtc.modules.sap.param.WorkOrderUpdateParam;
import io.mtc.modules.sap.service.MtcWRDR1Service;
import io.mtc.modules.sap.service.MtcWRDRService;
import io.mtc.modules.sap.service.WorkOrderService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * 提货单接口
 */
@Service("workOrderService")
public class WorkOrderServiceImpl implements WorkOrderService {

    @Autowired
    private MtcWRDRService mtcWRDRService;
    @Autowired
    private MtcWRDR1Service mtcWRDR1Service;
    @Autowired
    private WorkOrderDao workOrderDao;

    /**
     * 根据主键获取提货单明细
     *
     * @param keyParam
     * @return
     */
    @Override
    public WorkOrderForm info(KeyParam keyParam) {
        //获取提货单主表
        MtcWRDREntity mtcWUQTEntity = mtcWRDRService.getById(keyParam.getDocEntry());
        Assert.isNull(mtcWUQTEntity, "提货单不存在:DocEntry[" + keyParam.getDocEntry().toString() + "]");

        //获取提货单子表
        List<MtcWRDR1Entity> mtcWUQT1EntityList = mtcWRDR1Service.list(new QueryWrapper<MtcWRDR1Entity>()
                .eq("\"DocEntry\"", keyParam.getDocEntry().toString()));

        //构建返回结果
        WorkOrderForm workOrderForm = new WorkOrderForm();
        workOrderForm.setMaster(mtcWUQTEntity);
        workOrderForm.setDetail(mtcWUQT1EntityList);
        return workOrderForm;
    }

    /**
     * 根据计划单生成提货单
     *
     * @param workOrderForm
     * @return
     */
    @Override
    @Transactional
    public WorkOrderForm insert(WorkOrderForm workOrderForm) {
        Assert.isNull(workOrderForm.getMaster(), "提货单主表不能为空");
        if (ListUtils.isEmpty(workOrderForm.getDetail())) {
            throw new RRException("提货单子表不能为空");
        }
        for (int i = 0; i < workOrderForm.getDetail().size(); i++) {
            MtcWRDR1Entity mtcWRDR1Entity = workOrderForm.getDetail().get(i);
            if (mtcWRDR1Entity.getQuantity() == null ||
                    BigDecimal.ZERO.compareTo(mtcWRDR1Entity.getQuantity()) > 0) {
                throw new RRException("第" + i + "行数量不能为空");
            }
            mtcWRDR1Entity.setLinenum(i + 1);
        }

        MtcWRDREntity mtcWRDREntity = workOrderForm.getMaster();
        mtcWRDREntity.setCreator(ShiroUtils.getUserId().toString());
        mtcWRDREntity.setCreatedate(DateUtils.getDate());
        List<MtcWRDR1Entity> mtcWRDR1EntityList = workOrderForm.getDetail();

        //保存主表数据
        mtcWRDRService.save(mtcWRDREntity);
        for (MtcWRDR1Entity mtcWRDR1Entity : mtcWRDR1EntityList) {
            mtcWRDR1Entity.setDocentry(mtcWRDREntity.getDocentry());
        }
        mtcWRDR1Service.saveBatch(mtcWRDR1EntityList);
        return workOrderForm;
    }

    /**
     * 根据条件列出当前用户下所有提货单列表
     *
     * @param searchParam
     * @return
     */
    @Override
    public List<MtcWRDREntity> list(SearchParam searchParam) {
        return workOrderDao.list(ShiroUtils.getUserId(),
                searchParam.getPageIndex(),
                searchParam.getPageSize(),
                searchParam.getStartDate(),
                searchParam.getEndDate(),
                searchParam.getCardCode(),
                searchParam.getCardName(),
                searchParam.getDocStatus());
    }


    /**
     * 修改订货单时数据校验更新s
     *
     * @param workOrderUpdateParam
     * @return
     */
    @Override
    @Transactional
    public String update(WorkOrderUpdateParam workOrderUpdateParam) {
        MtcWRDREntity mtcWRDREntity = mtcWRDRService.getById(workOrderUpdateParam.getDocEntry());
        Assert.isNull(mtcWRDREntity, "计划单不存在:DocEntry[" + workOrderUpdateParam.getDocEntry().toString() + "]");

        if (ListUtils.isEmpty(workOrderUpdateParam.getDetail())) {
            throw new RRException("选择关闭的明细行数不能为空");
        }

        int success = 0;
        int failure = 0;
        int select = workOrderUpdateParam.getDetail().size();
        StringBuilder errorLog = new StringBuilder("");

        for (int i = 0; i < select; i++) {
            try {
                String msg = workOrderDao.update(ShiroUtils.getUserId(), workOrderUpdateParam.getDocEntry(),
                        workOrderUpdateParam.getDetail().get(i).getLineNum(),
                        workOrderUpdateParam.getDetail().get(i).getQuantity(),
                        workOrderUpdateParam.getDetail().get(i).getCarNum());
                if (StringUtils.isNotEmpty(msg)) {
                    failure++;
                    errorLog.append("<br>第" + (i + 1) + "条行更新失败：" + msg);
                } else {
                    success++;
                }
            } catch (Exception e) {
                failure++;
                errorLog.append("<br>第" + (i + 1) + "条行更新失败：" + e.getMessage());
            }
        }

        StringBuilder builder = new StringBuilder("");
        builder.append("更新单据行数：" + select + " ");
        builder.append("成功：" + success + " ");
        builder.append("失败：" + failure + " ");
        builder.append(errorLog.toString());
        if (failure == 0) {
            return "更新成功";
        }
        return builder.toString();
    }

    /**
     * 关闭指定提货单中的物料
     *
     * @param lineKeyParam
     * @return
     */
    @Override
    public String closeitem(LineKeyParam lineKeyParam) {
        MtcWRDREntity mtcWRDREntity = mtcWRDRService.getById(lineKeyParam.getDocEntry());
        Assert.isNull(mtcWRDREntity, "提货单不存在:DocEntry[" + lineKeyParam.getDocEntry().toString() + "]");

        if (ListUtils.isEmpty(lineKeyParam.getDetail())) {
            throw new RRException("选择关闭的明细行数不能为空");
        }

        int success = 0;
        int failure = 0;
        int select = lineKeyParam.getDetail().size();
        StringBuilder errorLog = new StringBuilder("");

        for (int i = 0; i < select; i++) {
            try {
                String msg = workOrderDao.closeitem(ShiroUtils.getUserId(), lineKeyParam.getDocEntry(), lineKeyParam.getDetail().get(i));
                if (StringUtils.isNotEmpty(msg)) {
                    failure++;
                    errorLog.append("<br>第" + (i + 1) + "条行关闭失败：" + msg);
                } else {
                    success++;
                }
            } catch (Exception e) {
                failure++;
                errorLog.append("<br>第" + (i + 1) + "条行关闭失败：" + e.getMessage());
            }
        }

        StringBuilder builder = new StringBuilder("");
        builder.append("关闭单据行数：" + select + " ");
        builder.append("成功：" + success + " ");
        builder.append("失败：" + failure + " ");
        builder.append(errorLog.toString());
        return builder.toString();
    }


    /**
     * 关闭提货单
     *
     * @param keyParam
     */
    @Override
    public void close(KeyParam keyParam) {
        //获取计划单主表
        MtcWRDREntity mtcWRDREntity = mtcWRDRService.getById(keyParam.getDocEntry());
        Assert.isNull(mtcWRDREntity, "提货单不存在:DocEntry[" + keyParam.getDocEntry().toString() + "]");

        mtcWRDREntity.setDocstatus(Constant.WorkOrderDocStatus.Canceled.getValue());
        mtcWRDRService.updateById(mtcWRDREntity);
    }


    /**
     * 取消提货单
     *
     * @param keyParam
     */
    @Override
    public void cancel(KeyParam keyParam) {
        //获取计划单主表
        MtcWRDREntity mtcWRDREntity = mtcWRDRService.getById(keyParam.getDocEntry());
        Assert.isNull(mtcWRDREntity, "提货单不存在:DocEntry[" + keyParam.getDocEntry().toString() + "]");

        if (!Constant.WorkOrderDocStatus.Draft.getValue().equals(mtcWRDREntity.getDocstatus())
                && !Constant.WorkOrderDocStatus.UnPass.getValue().equals(mtcWRDREntity.getDocstatus())) {
            throw new RRException("提货单" + Constant.WorkOrderDocStatus.value(mtcWRDREntity.getDocstatus()).getName());
        }
        mtcWRDREntity.setDocstatus(Constant.WorkOrderDocStatus.Canceled.getValue());
        mtcWRDRService.updateById(mtcWRDREntity);
    }
}
