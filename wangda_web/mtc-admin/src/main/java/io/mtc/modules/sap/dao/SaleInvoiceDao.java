package io.mtc.modules.sap.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import io.mtc.modules.sap.dto.SaleInvoiceEditDto;
import io.mtc.modules.sap.dto.SaleTakeNoDto;
import io.mtc.modules.sap.dto.SalesUpdateDto;
import io.mtc.modules.sap.entity.SaleInvoiceEntity;
import io.mtc.servicelayer.model.IncomingPayments;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface SaleInvoiceDao extends BaseMapper<SaleInvoiceEntity> {

    List<SaleInvoiceEntity> queryList(Pagination page, @Param("CardCode") String cardCode, @Param("DocStatus") String docStatus,
                                      @Param("StartDate") Date startDate, @Param("EndDate") Date endDate, @Param("UserName") String userName,
                                      @Param("type") String type, @Param("DocNum") String docNum, @Param("TakeNo") String takeNo);

    SaleInvoiceEntity searchByKey(@Param("DocEntry") String docEntry);

    SaleInvoiceEntity searchInlineByKey(@Param("DocNum") String docNum);

    SaleInvoiceEditDto searchMater(@Param("DocEntry") String docEntry, @Param("IsDraft") String isDraft);

    List<SaleInvoiceEditDto.ItemInfo> searchDetail(@Param("DocEntry") String docEntry, @Param("IsDraft") String isDraft);

    SaleInvoiceEditDto searchInvMater(@Param("DocEntry") String docEntry, @Param("FromOrder") String fromOrder);

    SaleInvoiceEditDto searchSOMater(@Param("DocEntry") String docEntry, @Param("FromOrder") String fromOrder);

    List<SaleInvoiceEditDto.ItemInfo> searchInvDetail(@Param("DocEntry") String docEntry, @Param("FromOrder") String fromOrder);

    List<SaleInvoiceEditDto.ItemInfo> searchForInvDetail(@Param("DocEntry") String docEntry, @Param("FromOrder") String fromOrder);

    List<IncomingPayments> structPays(@Param("DocEntry") String docEntry);

    String searchInLineNo(@Param("DocEntry") String docEntry,
                          @Param("TableName") String tableName);

    String searchDocEntry(@Param("DocNum") String docNum);

    String searchInLineNoWithNum(@Param("DocNum") String docNum,
                                 @Param("TableName") String tableName);

    String searchInLine(@Param("DocNum") String docNum,
                        @Param("TableName") String tableName);

    void updateSOInlineNo(@Param("DocEntry") String docEntry,
                          @Param("TableName") String tableName,
                          @Param("InLineNo") String inLineNo);

    void updateSOInlineNoWithNum(@Param("DocNum") String docNum,
                                 @Param("TableName") String tableName,
                                 @Param("InLineNo") String inLineNo);

    void updateInlineNoWithFid(@Param("DocNum") String docNum,
                               @Param("TableName") String tableName);

    void updateSOSrcNum(@Param("DocNum") String docNum,
                        @Param("InlineNo") String inlineNo,
                        @Param("TableName") String tableName,
                        @Param("SrcNum") String srcNum);

    String selectSrcNum(@Param("DocNum") String docNum);

    String getSODocStatus(@Param("DocNum") String docNum, @Param("TableName") String tableName);

    String searchInlineDocEntry(@Param("DocEntry") String docEntry);

    void insertErrorLog(@Param("Sql") String sql);

    SaleInvoiceEditDto searchDeliveryMater(@Param("DocEntry") Long DocEntry);

    List<SaleInvoiceEditDto.ItemInfo> searchDeliveryDetail(@Param("DocEntry") Long DocEntry);

    String selectDeliveryLineNum(@Param("DocEntry") Integer docEntry, @Param("ItemCode") String itemCode);

    String getOrderBPLId(@Param("DocEntry") Integer DocEntry);

    List<SalesUpdateDto> qryBatchUpdateWhs(@Param("DocEntry") Long docEntry);

    SaleTakeNoDto qryInvDriverInfo(@Param("DocEntry") Long docEntry);

    String createTakeNo(@Param("AliasName") String AliasName);

    void updateSOTakeNo(@Param("DocEntry") Long DocEntry,
                        @Param("TakeNo") String takeNo,
                        @Param("IsTake") String IsTake);

    String getWarehouse(@Param("ItemCode") String itemCode,
                        @Param("TaxIdNum") String taxIdNum,
                        @Param("UserName") String userName);
}
