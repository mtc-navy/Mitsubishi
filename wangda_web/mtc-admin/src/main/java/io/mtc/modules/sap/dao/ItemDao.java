package io.mtc.modules.sap.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import io.mtc.modules.sap.entity.ItemEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemDao extends BaseMapper<ItemEntity> {

    List<ItemEntity> search(@Param("CardCode") String cardCode,
                            @Param("UserName") String userName,
                            @Param("BPLId") String bplid,
                            @Param("docDate") String docDate,
                            @Param("desOrder") String desOrder,
                            @Param("priceOrder") String priceOrder);

    List<ItemEntity> searchByWhs(@Param("WhsCode") String whsCode,
                                @Param("UserName") String userName);

    List<ItemEntity> searchByWhsUsr(@Param("WhsCode") String whsCode,
                                    @Param("UserName") String userName);

    ItemEntity info(@Param("BPLId") String bplid,
                    @Param("WhsCode") String whsCode,
                    @Param("ItemCode") String itemCode);
}
