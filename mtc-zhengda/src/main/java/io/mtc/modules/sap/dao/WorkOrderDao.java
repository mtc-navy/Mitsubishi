package io.mtc.modules.sap.dao;

import io.mtc.modules.sap.entity.MtcWRDREntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * 提货单接口
 */
@Mapper
public interface WorkOrderDao {

    List<MtcWRDREntity> list(@Param("UserId") Long userID,
                             @Param("PageIndex") Integer pageIndex,
                             @Param("PageSize") Integer pageSize,
                             @Param("StartDate") String startDate,
                             @Param("EndDate") String endDate,
                             @Param("CardCode") String cardCode,
                             @Param("CardName") String cardName,
                             @Param("DocStatus") String docStatus);

    String update(@Param("UserId") Long userID,
                  @Param("DocEntry") Long docEntry,
                  @Param("LineNum") Integer lineNum,
                  @Param("Quantity") BigDecimal quantity,
                  @Param("CarNum") String carNum);

    String closeitem(@Param("UserId") Long userID,
                     @Param("DocEntry") Long docEntry,
                     @Param("LineNum") Integer lineNum);
}