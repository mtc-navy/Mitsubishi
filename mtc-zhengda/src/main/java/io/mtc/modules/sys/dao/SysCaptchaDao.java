package io.mtc.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.mtc.modules.sys.entity.SysCaptchaEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 验证码
 */
@Mapper
public interface SysCaptchaDao extends BaseMapper<SysCaptchaEntity> {

}
