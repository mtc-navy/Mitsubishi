
package io.mtc.modules.sap.service;

import io.mtc.modules.sap.entity.MtcWRDREntity;
import io.mtc.modules.sap.form.WorkOrderForm;
import io.mtc.modules.sap.param.KeyParam;
import io.mtc.modules.sap.param.LineKeyParam;
import io.mtc.modules.sap.param.SearchParam;
import io.mtc.modules.sap.param.WorkOrderUpdateParam;

import java.util.List;

/**
 * 提货单接口
 */
public interface WorkOrderService {

    /**
     * 根据主键获取提货单明细
     *
     * @param keyParam
     * @return
     */
    WorkOrderForm info(KeyParam keyParam);

    /**
     * 根据计划单生成提货单
     *
     * @param workOrderForm
     * @return
     */
    WorkOrderForm insert(WorkOrderForm workOrderForm);

    /**
     * 根据条件列出当前用户下所有提货单列表
     *
     * @param searchParam
     * @return
     */
    List<MtcWRDREntity> list(SearchParam searchParam);

    /**
     * 修改订货单时数据校验更新s
     *
     * @param workOrderUpdateParam
     * @return
     */
    String update(WorkOrderUpdateParam workOrderUpdateParam);

    /**
     * 关闭指定提货单中的物料
     *
     * @param lineKeyParam
     * @return
     */
    String closeitem(LineKeyParam lineKeyParam);


    /**
     * 关闭提货单
     *
     * @param keyParam
     */
    void close(KeyParam keyParam);


    /**
     * 取消提货单
     *
     * @param keyParam
     */
    void cancel(KeyParam keyParam);
}
