package io.mtc.modules.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.mtc.common.utils.R;
import io.mtc.common.utils.ShiroUtils;
import io.mtc.modules.sys.dao.SysUserTokenDao;
import io.mtc.modules.sys.entity.SysUserTokenEntity;
import io.mtc.modules.sys.oauth2.TokenGenerator;
import io.mtc.modules.sys.redis.SysUserTokenRedis;
import io.mtc.modules.sys.service.SysUserTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service("sysUserTokenService")
public class SysUserTokenServiceImpl extends ServiceImpl<SysUserTokenDao, SysUserTokenEntity> implements SysUserTokenService {
    //12小时后过期
    private final static int EXPIRE = 3600 * 12;

    @Autowired
    private SysUserTokenRedis sysUserTokenRedis;

    @Override
    public R createToken(long userId) {
        //生成一个token
        String token = ShiroUtils.getSession().getId() + "";

        //当前时间
        Date now = new Date();
        //过期时间
        Date expireTime = new Date(now.getTime() + EXPIRE * 1000);

        //判断是否生成过token
        SysUserTokenEntity tokenEntity = this.getById(userId);
        if (tokenEntity == null) {
            tokenEntity = new SysUserTokenEntity();
            tokenEntity.setUserId(userId);
            tokenEntity.setToken(token);
            tokenEntity.setUpdateTime(now);
            tokenEntity.setExpireTime(expireTime);

            //保存token
            this.save(tokenEntity);
        } else {
            tokenEntity.setToken(token);
            tokenEntity.setUpdateTime(now);
            tokenEntity.setExpireTime(expireTime);

            //更新token
            this.updateById(tokenEntity);
        }


        R r = R.ok().put("token", token).put("expire", EXPIRE);
        sysUserTokenRedis.saveOrUpdate(tokenEntity);
        return r;
    }

    @Override
    public void logout(long userId) {
        //生成一个token
        String token = TokenGenerator.generateValue();

        //修改token
        SysUserTokenEntity tokenEntity = this.getById(userId);
        if (tokenEntity != null) {
            sysUserTokenRedis.delete(tokenEntity.getToken());

            tokenEntity.setUserId(userId);
            tokenEntity.setToken(token);
            this.updateById(tokenEntity);
        }
    }
}
