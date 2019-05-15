package io.mtc.modules.sap.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import io.mtc.modules.sap.dto.StockTransferDto;
import io.mtc.modules.sap.entity.StockTransferEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockTransferDao extends BaseMapper<StockTransferEntity> {

    List<StockTransferEntity> queryList(Pagination page,
                                        @Param("BPLId") String bplId,
                                        @Param("DocStatus") String docStatus,
                                        @Param("StartDate") String startDate,
                                        @Param("EndDate") String endDate,
                                        @Param("UserName") String userName,
                                        @Param("FromWhsCode") String fromWhsCode,
                                        @Param("ToWhsCode") String toWhsCode);

    List<StockTransferEntity> queryList(@Param("BPLId") String bplId,
                                        @Param("DocStatus") String docStatus,
                                        @Param("StartDate") String startDate,
                                        @Param("EndDate") String endDate,
                                        @Param("UserName") String userName,
                                        @Param("FromWhsCode") String fromWhsCode,
                                        @Param("ToWhsCode") String toWhsCode);

    StockTransferDto searchMaster(@Param("DocEntry") Long docEntry);

    List<StockTransferDto.LineInfo> searchDetail(@Param("DocEntry") Long docEntry,
                                                 @Param("WhsCode") String whsCode);

    StockTransferDto searchReqMaster(@Param("DocEntry") Long docEntry);

    List<StockTransferDto.LineInfo> searchReqDetail(@Param("DocEntry") Long docEntry,
                                                    @Param("WhsCode") String whsCode);

}
