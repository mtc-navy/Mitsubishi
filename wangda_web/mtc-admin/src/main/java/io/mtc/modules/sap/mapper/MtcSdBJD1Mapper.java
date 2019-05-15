package io.mtc.modules.sap.mapper;

import io.mtc.modules.sap.entity.MtcSdBJD1;
import io.mtc.modules.sap.entity.MtcSdBJD1Example;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MtcSdBJD1Mapper {
    /**
     * 根据条件计数
     *
     * @param example
     */
    long countByExample(MtcSdBJD1Example example);

    /**
     *
     * @param example
     */
    int deleteByExample(MtcSdBJD1Example example);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insert(MtcSdBJD1 record);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insertSelective(MtcSdBJD1 record);

    /**
     * 根据条件查询列表
     *
     * @param example
     */
    List<MtcSdBJD1> selectByExample(MtcSdBJD1Example example);

    /**
     * 选择性更新数据库记录
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") MtcSdBJD1 record, @Param("example") MtcSdBJD1Example example);

    /**
     * 选择性更新数据库记录
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") MtcSdBJD1 record, @Param("example") MtcSdBJD1Example example);
}