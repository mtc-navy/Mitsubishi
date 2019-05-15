package io.mtc.modules.sap.service;

import com.baomidou.mybatisplus.service.IService;
import io.mtc.common.utils.Constant;
import io.mtc.common.utils.PageUtils;
import io.mtc.modules.sap.dto.SaleInvoiceEditDto;
import io.mtc.modules.sap.entity.SaleInvoiceEntity;

import java.util.List;
import java.util.Map;

public interface SaleInvoiceService extends IService<SaleInvoiceEntity> {

    /**
     * 查询销售订单列表
     *
     * @param params
     * @return
     */
    PageUtils queryPage(Map<String, Object> params) throws Exception;

    /**
     * 查询销售订单详情
     *
     * @param docEntry
     * @param docStatus
     * @return
     */
    SaleInvoiceEditDto info(Long docEntry, String docStatus) throws Exception;

    /**
     * 获取交货单信息
     *
     * @param docEntry
     * @return
     */
    SaleInvoiceEditDto getDelivery(Long docEntry) throws Exception;

    /**
     * 取消订单
     *
     * @param docEntry
     * @param docStatus
     * @return
     */
    void cancel(Long docEntry, String docStatus) throws Exception;

    /**
     * 添加销售订单/草稿
     *
     * @param saleInvoiceEditDto
     */
    Integer save(SaleInvoiceEditDto saleInvoiceEditDto, boolean isDraft, boolean isEdit) throws Exception;

    /**
     * 添加销售订单/草稿
     *
     * @param saleInvoiceEditDto
     */
    Integer save(SaleInvoiceEditDto saleInvoiceEditDto, boolean isDraft, Constant.DraftType type, boolean isEdit) throws Exception;

    /**
     * 添加草稿为正式单据
     *
     * @throws Exception
     */
    Integer draftToDocuments(SaleInvoiceEditDto saleInvoiceEditDto) throws Exception;

    /**
     * 保存收款单
     *
     * @param docEntry
     * @throws Exception
     */
    void incomingPayments(String docEntry) throws Exception;


    /**
     * 批量生成收款单
     *
     * @param docEntryList
     * @throws Exception
     */
    String incomingPaymentsBatch(List<String> docEntryList) throws Exception;

    /**
     * 取消收款单
     *
     * @param docEntry
     * @throws Exception
     */
    void incomingPayCancel(String docEntry) throws Exception;

    /**
     * 批量取消收款单
     *
     * @param docEntryList
     * @throws Exception
     */
    String incomingPayBatchCancel(List<String> docEntryList) throws Exception;


    /**
     * 批量生成交货单
     *
     * @param docEntryList
     * @throws Exception
     */
    String batchDelivery(List<String> docEntryList) throws Exception;


    /**
     * 批量取消交货单
     *
     * @param docEntryList
     * @throws Exception
     */
    String batchCancelDelivery(List<String> docEntryList) throws Exception;

    /**
     * 批量更新销售订单
     *
     * @param docEntryList
     * @return
     * @throws Exception
     */
    String batchupdatewhs(List<Long> docEntryList) throws Exception;

    /**
     * 打印
     *
     * @param docEntry
     * @return
     */
    Constant.PDFType print(Long docEntry);

    /**
     * 打印日志
     *
     * @param docEntry
     */
    void printLog(Long docEntry, String docType, Constant.PDFType pdfType) throws Exception;

    /**
     * 更新提货单信息
     *
     * @param docEntryList
     * @param isCreate
     * @return
     */
    String updateTakeGoods(List<Long> docEntryList, String isCreate);
}
