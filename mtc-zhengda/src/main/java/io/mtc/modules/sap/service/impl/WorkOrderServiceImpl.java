package io.mtc.modules.sap.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.mtc.common.validator.Assert;
import io.mtc.modules.sap.entity.MtcWRDR1Entity;
import io.mtc.modules.sap.entity.MtcWRDREntity;
import io.mtc.modules.sap.form.WorkOrderForm;
import io.mtc.modules.sap.param.KeyParam;
import io.mtc.modules.sap.service.MtcWRDR1Service;
import io.mtc.modules.sap.service.MtcWRDRService;
import io.mtc.modules.sap.service.WorkOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
