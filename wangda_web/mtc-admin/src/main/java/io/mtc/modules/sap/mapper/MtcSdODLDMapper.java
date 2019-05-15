package io.mtc.modules.sap.mapper;

import io.mtc.modules.sap.entity.MtcSdODLD;
import io.mtc.modules.sap.entity.MtcSdODLDExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MtcSdODLDMapper {
    /**
     * 根据条件计数
     *
     * @param example
     */
    long countByExample(MtcSdODLDExample example);

    /**
     *
     * @param example
     */
    int deleteByExample(MtcSdODLDExample example);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insert(MtcSdODLD record);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insertSelective(MtcSdODLD record);

    /**
     * 根据条件查询列表
     *
     * @param example
     */
    List<MtcSdODLD> selectByExample(MtcSdODLDExample example);

    /**
     * 选择性更新数据库记录
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") MtcSdODLD record, @Param("example") MtcSdODLDExample example);

    /**
     * 选择性更新数据库记录
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") MtcSdODLD record, @Param("example") MtcSdODLDExample example);
}