package io.mtc.modules.sap.dao;

import io.mtc.modules.sap.entity.SendItemEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SendItemDao {
    List<SendItemEntity> search(@Param("CardCode") String cardCode, @Param("BPLId") String bplid);

}
