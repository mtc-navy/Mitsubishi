package io.mtc.modules.sap.dao;

import io.mtc.modules.sap.entity.GiftInfoEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GiftInfoDao {

    void deleteByDocEntry(@Param("DocEntry") String docEntry);

    void insert(GiftInfoEntity giftInfoEntity);

    List<GiftInfoEntity> selectByDocEntry(@Param("DocEntry") String docEntry);

    List<GiftInfoEntity> selectInfoForReturns(@Param("DocEntry") String docEntry);
}
