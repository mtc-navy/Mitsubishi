package io.mtc.modules.sap.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PrintDao {

    String canPrintOrNot(@Param("userCode") String userCode, @Param("bplId") String bplId, @Param("docType") String docType, @Param("docEntry") Long docEntry, @Param("printType") String printType);

    void updatePrintInfo(@Param("userCode") String userCode, @Param("printTime") String printTime, @Param("docEntry") Long docEntry);

}
