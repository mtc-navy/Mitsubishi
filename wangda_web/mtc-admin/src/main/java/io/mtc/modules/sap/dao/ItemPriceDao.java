package io.mtc.modules.sap.dao;

import io.mtc.modules.sap.entity.DistNumberEntity;
import io.mtc.modules.sap.entity.ItemPriceEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemPriceDao {

    /**
     * 查询物料单价
     *
     * @param cardCode
     * @param itemCode
     * @param docDate
     * @param bplid
     * @return
     */
    List<ItemPriceEntity> search(@Param("CardCode") String cardCode,
                                 @Param("ItemCode") String itemCode,
                                 @Param("DocDate") String docDate,
                                 @Param("BPLId") String bplid);

    /**
     * 查询默认仓库
     *
     * @param bplid
     * @param itemcode
     * @return
     */
    String searchDftWarehouse(@Param("BPLId") String bplid,
                              @Param("ItemCode") String itemcode);

    /**
     * 获取片区经理名称
     *
     * @param cardCode
     * @param itemCode
     * @param bplid
     * @return
     */
    String getSupRegName(@Param("cardCode") String cardCode, @Param("itemCode") String itemCode, @Param("bplid") String bplid);

    /**
     * 查询物料批次（先进先出排序）
     *
     * @param itemcode
     * @param whscode
     * @return
     */
    List<DistNumberEntity> searchDistNumber(@Param("ItemCode") String itemcode, @Param("WhsCode") String whscode);


    /**
     * 查询物料批次（先出先进排序）
     *
     * @param cardCode
     * @param bplid
     * @param itemcode
     * @param whscode
     * @return
     */
    List<DistNumberEntity> searchLastDistNumber(@Param("CardCode") String cardCode, @Param("BPLId") String bplid,
                                                @Param("ItemCode") String itemcode, @Param("WhsCode") String whscode);

    /**
     * 查询交货单对应的批次信息
     *
     * @param docEntry
     * @param itemcode
     * @param lineNum
     * @return
     */
    List<DistNumberEntity> searchDeliveryDistNumber(@Param("DocEntry") String docEntry, @Param("ItemCode") String itemcode,
                                                    @Param("LineNum") String lineNum);
}
