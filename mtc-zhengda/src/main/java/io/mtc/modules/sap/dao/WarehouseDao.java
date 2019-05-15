package io.mtc.modules.sap.dao;

import io.mtc.modules.sap.form.WarehouseForm;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 仓库列表
 */
@Mapper
public interface WarehouseDao {

    List<WarehouseForm> list();

}