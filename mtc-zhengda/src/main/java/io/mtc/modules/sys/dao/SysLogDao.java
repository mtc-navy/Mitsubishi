package io.mtc.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.mtc.modules.sys.entity.SysLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统日志
 *
 */
@Mapper
public interface SysLogDao extends BaseMapper<SysLogEntity> {
	
}
