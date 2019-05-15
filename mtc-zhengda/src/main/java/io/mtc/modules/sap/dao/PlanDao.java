package io.mtc.modules.sap.dao;

import io.mtc.modules.sap.entity.MtcWUQTEntity;
import io.mtc.modules.sap.form.EarliestDateForm;
import io.mtc.modules.sap.form.MaterialForm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * 计划单列表
 */
@Mapper
public interface PlanDao {

    List<MaterialForm> premeteriallist(@Param("CardCode") String cardCode);

    List<EarliestDateForm> calearliestdate(@Param("UserId") Long userId,
                                           @Param("WhsCode") String whsCode,
                                           @Param("ItemCode") String itemCode);

    List<MtcWUQTEntity> list(@Param("UserId") Long userID,
                             @Param("PageIndex") Integer pageIndex,
                             @Param("PageSize") Integer pageSize,
                             @Param("StartDate") String startDate,
                             @Param("EndDate") String endDate,
                             @Param("CardCode") String cardCode,
                             @Param("CardName") String cardName,
                             @Param("DocStatus") String docStatus);

    String avgcheck(@Param("UserId") Long userID,
                    @Param("CardCode") String cardCode,
                    @Param("ItemCode") String itemCode,
                    @Param("DocDate") String docDate,
                    @Param("Quantity") BigDecimal quantity);

    String closeitem(@Param("UserId") Long userID,
                     @Param("DocEntry") Long docEntry,
                     @Param("LineNum") Integer lineNum);

    String update(@Param("UserId") Long userID,
                  @Param("DocEntry") Long docEntry,
                  @Param("LineNum") Integer lineNum,
                  @Param("Quantity") BigDecimal quantity);
}