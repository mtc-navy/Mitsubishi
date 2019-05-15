package io.mtc.modules.sap.dao;

import io.mtc.modules.sap.entity.DocEntryEntity;
import io.mtc.modules.sap.entity.PaymentEntity;
import io.mtc.servicelayer.model.IncomingPayments;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentDao {

    List<PaymentEntity> search(@Param("CardCode") String cardCode,
                               @Param("UserName") String userName,
                               @Param("BPLId") String BPLId,
                               @Param("FilterValue") String filterValue);


    List<PaymentEntity> searchInSO(@Param("CardCode") String cardCode,
                                   @Param("UserName") String userName,
                                   @Param("BPLId") String BPLId,
                                   @Param("DocEntry") String docEntry,
                                   @Param("FilterValue") String filterValue);


    List<PaymentEntity> searchInvInSO(@Param("BPLId") String BPLId,
                                      @Param("DocEntry") String docEntry,
                                      @Param("FromOrder") String fromOrder);

    List<DocEntryEntity> searchPayments(@Param("DocEntry") String DocEntry);

    List<IncomingPayments> searchDesOrderReceipt(@Param("DocEntry") String DocEntry);

    List<IncomingPayments> searchPriceOrderReceipt(@Param("DocEntry") String DocEntry);

    Integer getSeries(@Param("BPLId") String bplid);

    Integer getDocumentSeries(@Param("BPLId") String bplid, @Param("ObjType") String objtype);

    void updatePays(@Param("DocEntry") String docEntry);

    void updateODLDReceipt(@Param("DocEntry") String docEntry);

    void updateOBJDReceipt(@Param("DocEntry") String docEntry);

    List<String> selectORCTEntry(@Param("DocNum") String docNum);
}
