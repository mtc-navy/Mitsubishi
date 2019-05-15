package io.mtc.modules.sap.service;

import com.baomidou.mybatisplus.service.IService;
import io.mtc.common.utils.PageUtils;
import io.mtc.modules.sap.dto.SaleInvoiceEditDto;
import io.mtc.modules.sap.entity.SaleReturnEntity;

import java.util.Map;

public interface SaleReturnService extends IService<SaleReturnEntity> {

    /**
     * 查询订单列表
     *
     * @param params
     * @return
     * @throws Exception
     */
    PageUtils queryPage(Map<String, Object> params) throws Exception;

    /**
     * 取消订单
     *
     * @param docEntry
     * @throws Exception
     */
    void cancel(Long docEntry) throws Exception;

    /**
     * 保存退货草稿
     *
     * @param saleInvoiceEditDto
     * @throws Exception
     */
    Integer save(SaleInvoiceEditDto saleInvoiceEditDto) throws Exception;

    /**
     * 更新退货草稿
     *
     * @param
     * @return
     */
    void update(SaleInvoiceEditDto saleInvoiceEditDto) throws Exception;

    /**
     * 修改时获取销售订单信息
     *
     * @param docEntry
     * @return
     * @throws Exception
     */
    SaleInvoiceEditDto info(Long docEntry) throws Exception;

}
