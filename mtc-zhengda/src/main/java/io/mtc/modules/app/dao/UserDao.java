package io.mtc.modules.app.dao;

import io.mtc.modules.app.entity.UserEntity;
import io.mtc.modules.app.entity.UserEntityExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户
 */
@Mapper
@Repository
public interface UserDao {

    /**
     * 根据条件计数
     *
     * @param example
     */
    long countByExample(UserEntityExample example);

    /**
     * @param example
     */
    int deleteByExample(UserEntityExample example);

    /**
     * 根据主键删除数据库的记录
     *
     * @param code
     */
    int deleteByPrimaryKey(String code);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insert(UserEntity record);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insertSelective(UserEntity record);

    /**
     * 根据条件查询列表
     *
     * @param example
     */
    List<UserEntity> selectByExample(UserEntityExample example);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param code
     */
    UserEntity selectByPrimaryKey(String code);


    /**
     * 根据主键获取一条数据库记录
     *
     * @param docentry
     */
    UserEntity selectByDocEntry(@Param("docentry") Long docentry);

    /**
     * 选择性更新数据库记录
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") UserEntity record, @Param("example") UserEntityExample example);

    /**
     * 选择性更新数据库记录
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") UserEntity record, @Param("example") UserEntityExample example);

    /**
     * 根据主键来更新部分数据库记录
     *
     * @param record
     */
    int updateByPrimaryKeySelective(UserEntity record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(UserEntity record);


    /**
     * 更新密码
     *
     * @param record
     */
    int resetPassword(UserEntity record);

    /**
     * 更新微信号
     *
     * @param record
     * @return
     */
    int resetOpenId(UserEntity record);
}
