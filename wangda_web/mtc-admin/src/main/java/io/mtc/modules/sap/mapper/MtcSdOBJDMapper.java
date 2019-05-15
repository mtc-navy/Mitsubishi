package io.mtc.modules.sap.mapper;

import io.mtc.modules.sap.entity.MtcSdOBJD;
import io.mtc.modules.sap.entity.MtcSdOBJDExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MtcSdOBJDMapper {
    /**
     * 根据条件计数
     *
     * @param example
     */
    long countByExample(MtcSdOBJDExample example);

    /**
     * @param example
     */
    int deleteByExample(MtcSdOBJDExample example);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insert(MtcSdOBJD record);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insertSelective(MtcSdOBJD record);

    /**
     * 根据条件查询列表
     *
     * @param example
     */
    List<MtcSdOBJD> selectByExample(MtcSdOBJDExample example);

    /**
     * 选择性更新数据库记录
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") MtcSdOBJD record, @Param("example") MtcSdOBJDExample example);

    /**
     * 选择性更新数据库记录
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") MtcSdOBJD record, @Param("example") MtcSdOBJDExample example);
}