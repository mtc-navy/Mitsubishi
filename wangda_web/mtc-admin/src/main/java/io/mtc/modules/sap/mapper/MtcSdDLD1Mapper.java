package io.mtc.modules.sap.mapper;

import io.mtc.modules.sap.entity.MtcSdDLD1;
import io.mtc.modules.sap.entity.MtcSdDLD1Example;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MtcSdDLD1Mapper {
    /**
     * 根据条件计数
     *
     * @param example
     */
    long countByExample(MtcSdDLD1Example example);

    /**
     * @param example
     */
    int deleteByExample(MtcSdDLD1Example example);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insert(MtcSdDLD1 record);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insertSelective(MtcSdDLD1 record);

    /**
     * 根据条件查询列表
     *
     * @param example
     */
    List<MtcSdDLD1> selectByExample(MtcSdDLD1Example example);

    /**
     * 选择性更新数据库记录
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") MtcSdDLD1 record, @Param("example") MtcSdDLD1Example example);

    /**
     * 选择性更新数据库记录
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") MtcSdDLD1 record, @Param("example") MtcSdDLD1Example example);
}