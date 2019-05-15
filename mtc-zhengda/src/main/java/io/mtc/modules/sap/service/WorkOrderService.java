
package io.mtc.modules.sap.service;

import io.mtc.modules.sap.form.WorkOrderForm;
import io.mtc.modules.sap.param.KeyParam;

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
}
