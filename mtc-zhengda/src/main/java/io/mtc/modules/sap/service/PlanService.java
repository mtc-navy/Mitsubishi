
package io.mtc.modules.sap.service;

import io.mtc.modules.sap.entity.MtcWUQTEntity;
import io.mtc.modules.sap.form.EarliestDateForm;
import io.mtc.modules.sap.form.MaterialForm;
import io.mtc.modules.sap.form.PlanForm;
import io.mtc.modules.sap.param.*;

import java.util.List;

/**
 * 计划单接口
 */
public interface PlanService {

    /**
     * 获取上一次物料清单
     *
     * @param cardCode
     * @return
     */
    List<MaterialForm> premeteriallist(String cardCode);

    /**
     * 计算提货日期
     *
     * @param calEarliestDateParam
     * @return
     */
    List<EarliestDateForm> calearliestdate(CalEarliestDateParam calEarliestDateParam);

    /**
     * 根据输入校验后生成计划单
     * @param planForm
     * @return
     */
    PlanForm insert(PlanForm planForm);

    /**
     * 当前用户下指定条件的计划单列表
     *
     * @param searchParam
     * @return
     */
    List<MtcWUQTEntity> list(SearchParam searchParam);

    /**
     * 根据主键获取计划单明细
     *
     * @param keyParam
     * @return
     */
    PlanForm info(KeyParam keyParam);

    /**
     * 关闭指定计划单中的物料
     *
     * @param lineKeyParam
     * @return
     */
    String closeitem(LineKeyParam lineKeyParam);

    /**
     * 修改计划单时数据校验更新
     *
     * @param planUpdateParam
     * @return
     */
    String update(PlanUpdateParam planUpdateParam);

    /**
     * 关闭计划单
     *
     * @param keyParam
     */
    void close(KeyParam keyParam);


    /**
     * 取消计划单
     *
     * @param keyParam
     */
    void cancel(KeyParam keyParam);

    /**
     * 销售量校验
     *
     * @param avgCheckParam
     * @return
     */
    String avgcheck(AvgCheckParam avgCheckParam);
}
