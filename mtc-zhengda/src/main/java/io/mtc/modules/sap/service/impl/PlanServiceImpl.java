package io.mtc.modules.sap.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.mtc.common.exception.RRException;
import io.mtc.common.utils.DateUtils;
import io.mtc.common.utils.ListUtils;
import io.mtc.common.utils.ShiroUtils;
import io.mtc.common.validator.Assert;
import io.mtc.modules.sap.constant.Constant;
import io.mtc.modules.sap.dao.PlanDao;
import io.mtc.modules.sap.entity.MtcWUQT1Entity;
import io.mtc.modules.sap.entity.MtcWUQTEntity;
import io.mtc.modules.sap.form.EarliestDateForm;
import io.mtc.modules.sap.form.MaterialForm;
import io.mtc.modules.sap.form.PlanForm;
import io.mtc.modules.sap.param.*;
import io.mtc.modules.sap.service.MtcWUQT1Service;
import io.mtc.modules.sap.service.MtcWUQTService;
import io.mtc.modules.sap.service.PlanService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * 计划单接口
 */
@Service("planService")
public class PlanServiceImpl implements PlanService {

    @Autowired
    private PlanDao planDao;
    @Autowired
    private MtcWUQTService mtcWUQTService;
    @Autowired
    private MtcWUQT1Service mtcWUQT1Service;

    /**
     * 获取物料清单
     *
     * @param cardCode
     * @return
     */
    @Override
    public List<MaterialForm> premeteriallist(String cardCode) {
        return planDao.premeteriallist(cardCode);
    }

    /**
     * 计算提货日期
     *
     * @param calEarliestDateParam
     * @return
     */
    @Override
    public List<EarliestDateForm> calearliestdate(CalEarliestDateParam calEarliestDateParam) {
        return planDao.calearliestdate(ShiroUtils.getUserId(), calEarliestDateParam.getWhsCode(), calEarliestDateParam.getItemCode());
    }

    /**
     * 根据输入校验后生成计划单
     *
     * @param planForm
     * @return
     */
    @Override
    public PlanForm insert(PlanForm planForm) {
        Assert.isNull(planForm.getMaster(), "计划单主表不能为空");
        if (ListUtils.isEmpty(planForm.getDetail())) {
            throw new RRException("计划单子表不能为空");
        }
        for (int i = 0; i < planForm.getDetail().size(); i++) {
            MtcWUQT1Entity mtcWUQT1Entity = planForm.getDetail().get(i);
            if (mtcWUQT1Entity.getQuantity() == null ||
                    BigDecimal.ZERO.compareTo(mtcWUQT1Entity.getQuantity()) > 0) {
                throw new RRException("第" + i + "行数量不能为空");
            }
            mtcWUQT1Entity.setLinenum(i + 1);
        }

        MtcWUQTEntity mtcWUQTEntity = planForm.getMaster();
        mtcWUQTEntity.setCreator(ShiroUtils.getUserId().toString());
        mtcWUQTEntity.setCreatedate(DateUtils.getDate());
        List<MtcWUQT1Entity> mtcWUQT1EntityList = planForm.getDetail();

        //保存主表数据
        mtcWUQTService.save(mtcWUQTEntity);
        for (MtcWUQT1Entity mtcWUQT1Entity : mtcWUQT1EntityList) {
            mtcWUQT1Entity.setDocentry(mtcWUQTEntity.getDocentry());
        }
        mtcWUQT1Service.saveBatch(mtcWUQT1EntityList);
        return planForm;
    }

    /**
     * 当前用户下指定条件的计划单列表
     *
     * @param searchParam
     * @return
     */
    @Override
    public List<MtcWUQTEntity> list(SearchParam searchParam) {
        return planDao.list(ShiroUtils.getUserId(),
                searchParam.getPageIndex(),
                searchParam.getPageSize(),
                searchParam.getStartDate(),
                searchParam.getEndDate(),
                searchParam.getCardCode(),
                searchParam.getCardName(),
                searchParam.getDocStatus());
    }

    /**
     * 根据主键获取计划单明细
     *
     * @param keyParam
     * @return
     */
    @Override
    public PlanForm info(KeyParam keyParam) {
        //获取计划单主表
        MtcWUQTEntity mtcWUQTEntity = mtcWUQTService.getById(keyParam.getDocEntry());
        Assert.isNull(mtcWUQTEntity, "计划单不存在:DocEntry[" + keyParam.getDocEntry().toString() + "]");

        //获取计划单子表
        List<MtcWUQT1Entity> mtcWUQT1EntityList = mtcWUQT1Service.list(new QueryWrapper<MtcWUQT1Entity>()
                .eq("\"DocEntry\"", keyParam.getDocEntry().toString()));

        //构建返回结果
        PlanForm planForm = new PlanForm();
        planForm.setMaster(mtcWUQTEntity);
        planForm.setDetail(mtcWUQT1EntityList);
        return planForm;
    }

    /**
     * 关闭指定计划单中的物料
     *
     * @param lineKeyParam
     * @return
     */
    @Override
    @Transactional
    public String closeitem(LineKeyParam lineKeyParam) {
        MtcWUQTEntity mtcWUQTEntity = mtcWUQTService.getById(lineKeyParam.getDocEntry());
        Assert.isNull(mtcWUQTEntity, "计划单不存在:DocEntry[" + lineKeyParam.getDocEntry().toString() + "]");

        if (ListUtils.isEmpty(lineKeyParam.getDetail())) {
            throw new RRException("选择关闭的明细行数不能为空");
        }

        int success = 0;
        int failure = 0;
        int select = lineKeyParam.getDetail().size();
        StringBuilder errorLog = new StringBuilder("");

        for (int i = 0; i < select; i++) {
            try {
                String msg = planDao.closeitem(ShiroUtils.getUserId(), lineKeyParam.getDocEntry(), lineKeyParam.getDetail().get(i));
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
     * 修改计划单时数据校验更新
     *
     * @param planUpdateParam
     * @return
     */
    @Override
    @Transactional
    public String update(PlanUpdateParam planUpdateParam) {
        MtcWUQTEntity mtcWUQTEntity = mtcWUQTService.getById(planUpdateParam.getDocEntry());
        Assert.isNull(mtcWUQTEntity, "计划单不存在:DocEntry[" + planUpdateParam.getDocEntry().toString() + "]");

        if (ListUtils.isEmpty(planUpdateParam.getDetail())) {
            throw new RRException("选择关闭的明细行数不能为空");
        }

        int success = 0;
        int failure = 0;
        int select = planUpdateParam.getDetail().size();
        StringBuilder errorLog = new StringBuilder("");

        for (int i = 0; i < select; i++) {
            try {
                String msg = planDao.update(ShiroUtils.getUserId(), planUpdateParam.getDocEntry(),
                        planUpdateParam.getDetail().get(i).getLineNum(),
                        planUpdateParam.getDetail().get(i).getQuantity());
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
     * 关闭计划单
     *
     * @param keyParam
     */
    @Override
    public void close(KeyParam keyParam) {
        //获取计划单主表
        MtcWUQTEntity mtcWUQTEntity = mtcWUQTService.getById(keyParam.getDocEntry());
        Assert.isNull(mtcWUQTEntity, "计划单不存在:DocEntry[" + keyParam.getDocEntry().toString() + "]");

        mtcWUQTEntity.setDocstatus(Constant.PlanDocStatus.Canceled.getValue());
        mtcWUQTService.updateById(mtcWUQTEntity);
    }

    /**
     * 取消计划单
     *
     * @param keyParam
     */
    @Override
    public void cancel(KeyParam keyParam) {
        //获取计划单主表
        MtcWUQTEntity mtcWUQTEntity = mtcWUQTService.getById(keyParam.getDocEntry());
        Assert.isNull(mtcWUQTEntity, "计划单不存在:DocEntry[" + keyParam.getDocEntry().toString() + "]");

        if (!Constant.PlanDocStatus.CanEdit.getValue().equals(mtcWUQTEntity.getDocstatus())) {
            throw new RRException("计划单" + Constant.PlanDocStatus.value(mtcWUQTEntity.getDocstatus()).getName());
        }
        mtcWUQTEntity.setDocstatus(Constant.PlanDocStatus.Canceled.getValue());
        mtcWUQTService.updateById(mtcWUQTEntity);
    }

    /**
     * 销售量校验
     *
     * @param avgCheckParam
     * @return
     */
    @Override
    public String avgcheck(AvgCheckParam avgCheckParam) {
        String msg = planDao.avgcheck(ShiroUtils.getUserId(),
                avgCheckParam.getCardCode(),
                avgCheckParam.getItemCode(),
                avgCheckParam.getDocDate(),
                avgCheckParam.getQuantity());
        if (StringUtils.isNotEmpty(msg)) {
            throw new RRException(msg);
        }
        return msg;
    }
}
