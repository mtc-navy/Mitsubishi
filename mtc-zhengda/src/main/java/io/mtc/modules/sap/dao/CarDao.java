package io.mtc.modules.sap.dao;

import io.mtc.modules.sap.form.CarForm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 车牌信息列表
 */
@Mapper
public interface CarDao {

    List<CarForm> list(@Param("UserId") Long userID);

}