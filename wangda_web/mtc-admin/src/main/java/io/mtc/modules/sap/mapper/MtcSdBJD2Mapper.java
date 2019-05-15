package io.mtc.modules.sap.mapper;

import io.mtc.modules.sap.entity.MtcSdBJD2;
import io.mtc.modules.sap.entity.MtcSdBJD2Example;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MtcSdBJD2Mapper {
    /**
     * 根据条件计数
     *
     * @param example
     */
    long countByExample(MtcSdBJD2Example example);

    /**
     *
     * @param example
     */
    int deleteByExample(MtcSdBJD2Example example);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insert(MtcSdBJD2 record);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insertSelective(MtcSdBJD2 record);

    /**
     * 根据条件查询列表
     *
     * @param example
     */
    List<MtcSdBJD2> selectByExample(MtcSdBJD2Example example);

    /**
     * 选择性更新数据库记录
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") MtcSdBJD2 record, @Param("example") MtcSdBJD2Example example);

    /**
     * 选择性更新数据库记录
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") MtcSdBJD2 record, @Param("example") MtcSdBJD2Example example);
}