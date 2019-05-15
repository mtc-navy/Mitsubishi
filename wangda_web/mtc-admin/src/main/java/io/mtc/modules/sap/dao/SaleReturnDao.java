package io.mtc.modules.sap.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import io.mtc.modules.sap.dto.SaleInvoiceEditDto;
import io.mtc.modules.sap.entity.DiscountEntity;
import io.mtc.modules.sap.entity.GiftInfoEntity;
import io.mtc.modules.sap.entity.PaymentEntity;
import io.mtc.modules.sap.entity.SaleReturnEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface SaleReturnDao extends BaseMapper<SaleReturnEntity> {

    List<SaleReturnEntity> queryList(Pagination page, @Param("CardCode") String cardCode, @Param("DocStatus") String docStatus,
                                     @Param("StartDate") Date startDate, @Param("EndDate") Date endDate,
                                     @Param("UserName") String userName, @Param("DeliveryNum") String deliveryNum);

    SaleInvoiceEditDto searchMater(@Param("DocEntry") String docEntry);

    List<SaleInvoiceEditDto.ItemInfo> searchDetail(@Param("DocEntry") String docEntry);

    List<PaymentEntity> searchPay(@Param("CardCode") String cardCode,
                                  @Param("UserName") String userName,
                                  @Param("BPLId") String bplid,
                                  @Param("DocEntry") String docEntry);

    List<DiscountEntity> searchDisc(@Param("CardCode") String cardCode,
                                    @Param("UserName") String userName,
                                    @Param("BPLId") String bplid,
                                    @Param("DocEntry") String docEntry);

    List<GiftInfoEntity> searchGift(@Param("DocEntry") String docEntry);
}
