package io.mtc.modules.sap.dao;

import io.mtc.modules.sap.form.MaterialForm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 物料列表
 */
@Mapper
public interface MaterialDao {

    List<MaterialForm> list(@Param("CardCode") String cardCode);

}