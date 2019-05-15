package io.mtc.modules.sap.dao;

import io.mtc.modules.sap.entity.*;
import org.apache.ibatis.annotations.Param;
import org.apache.logging.log4j.util.PerformanceSensitive;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Repository
public interface DiscountDao {

    List<DiscountEntity> search(@Param("CardCode") String cardCode,
                                @Param("UserName") String userName,
                                @Param("BPLId") String bplid,
                                @Param("FilterValue") String filterValue);

    List<DiscountEntity> searchInSO(@Param("CardCode") String cardCode,
                                    @Param("UserName") String userName,
                                    @Param("BPLId") String bplid,
                                    @Param("DocEntry") String docEntry,
                                    @Param("FilterValue") String filterValue);

    List<DiscountEntity> searchInvInSO(@Param("CardCode") String cardCode,
                                       @Param("BPLId") String bplid,
                                       @Param("DocEntry") String docEntry,
                                       @Param("FromOrder") String fromOrder);

    List<GiftAmtEntity> getGiftAmt(@Param("ObjType") String object,
                                   @Param("BPLId") String bplid,
                                   @Param("DocEntry") String DocEntry,
                                   @Param("CardCode") String cardCode,
                                   @Param("DocDate") String docDate,
                                   @Param("ListItems") String listItems);

    List<DiscTypeInfoEntity> getSODiscType(@Param("CardCode") String cardCode,
                                           @Param("BPLId") String bplid,
                                           @Param("DocDate") String docDate);

    List<ItemFacEntity> getItemGrpFac(@Param("ItemCode") String itemCode,
                                      @Param("BPLId") String bplid);

    BigDecimal getMaxAmt(@Param("DocEntry") String docEntry);

    List<DiscMaxAmtEntity> checkMaxGiftAmt(@Param("DocEntry") String docEntry,
                                           @Param("DiscEntry") String discEntry,
                                           @Param("ItemCode") String itemCode);

    List<GiftPackageEntity> getGiftPackage(@Param("DocEntry") String docEntry,
                                           @Param("BPLId") String BPLId,
                                           @Param("ZKDocEntry") String zkDocEntry,
                                           @Param("ZKItemCode") String zkItemCode,
                                           @Param("PKG") BigDecimal PKG,
                                           @Param("KG") BigDecimal KG,
                                           @Param("ListItems") String listItems,
                                           @Param("DiscItems") String discItems);

    BigDecimal getSalFactor1(@Param("ItemCode") String itemCode);

    BigDecimal checkMaxGift(@Param("DocEntry") String docEntry,
                            @Param("ZKDocEntry") String zkDocEntry,
                            @Param("ListItems") String listItems);

    String getItemName(@Param("ItemCode") String itemCode);

}
