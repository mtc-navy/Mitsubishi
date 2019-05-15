package io.mtc.modules.sap.mapper;

import io.mtc.modules.sap.entity.BplidPeriod;
import io.mtc.modules.sap.entity.BplidPeriodExample;
import io.mtc.modules.sap.entity.BplidPeriodKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BplidPeriodMapper {
    /**
     * 根据条件计数
     *
     * @param example
     */
    long countByExample(BplidPeriodExample example);

    /**
     *
     * @param example
     */
    int deleteByExample(BplidPeriodExample example);

    /**
     * 根据主键删除数据库的记录
     *
     * @param key
     */
    int deleteByPrimaryKey(BplidPeriodKey key);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insert(BplidPeriod record);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insertSelective(BplidPeriod record);

    /**
     * 根据条件查询列表
     *
     * @param example
     */
    List<BplidPeriod> selectByExample(BplidPeriodExample example);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param key
     */
    BplidPeriod selectByPrimaryKey(BplidPeriodKey key);

    /**
     * 选择性更新数据库记录
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") BplidPeriod record, @Param("example") BplidPeriodExample example);

    /**
     * 选择性更新数据库记录
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") BplidPeriod record, @Param("example") BplidPeriodExample example);

    /**
     * 根据主键来更新部分数据库记录
     *
     * @param record
     */
    int updateByPrimaryKeySelective(BplidPeriod record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(BplidPeriod record);
}