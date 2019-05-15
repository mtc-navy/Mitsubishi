package io.mtc.modules.sap.mapper;

import io.mtc.modules.sap.entity.MtcSdDLD2;
import io.mtc.modules.sap.entity.MtcSdDLD2Example;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MtcSdDLD2Mapper {
    /**
     * 根据条件计数
     *
     * @param example
     */
    long countByExample(MtcSdDLD2Example example);

    /**
     *
     * @param example
     */
    int deleteByExample(MtcSdDLD2Example example);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insert(MtcSdDLD2 record);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insertSelective(MtcSdDLD2 record);

    /**
     * 根据条件查询列表
     *
     * @param example
     */
    List<MtcSdDLD2> selectByExample(MtcSdDLD2Example example);

    /**
     * 选择性更新数据库记录
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") MtcSdDLD2 record, @Param("example") MtcSdDLD2Example example);

    /**
     * 选择性更新数据库记录
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") MtcSdDLD2 record, @Param("example") MtcSdDLD2Example example);
}