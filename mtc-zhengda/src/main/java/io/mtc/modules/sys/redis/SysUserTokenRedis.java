package io.mtc.modules.sys.redis;

import io.mtc.common.utils.RedisUtils;
import io.mtc.modules.sys.entity.SysUserTokenEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 用户token Redis
 */
@Component
public class SysUserTokenRedis {

    @Autowired
    private RedisUtils redisUtils;

    public void saveOrUpdate(SysUserTokenEntity token) {
        if(token == null){
            return;
        }
        String key = getKey(token.getClass(),token.getToken());
        redisUtils.set(key, token,(token.getExpireTime().getTime()-System.currentTimeMillis())/1000);
    }

    public void delete(String token) {
        String key = getKey(SysUserTokenEntity.class,token);
        redisUtils.delete(key);
    }

    public SysUserTokenEntity get(String token){
        String key = getKey(SysUserTokenEntity.class,token);
        return redisUtils.get(key, SysUserTokenEntity.class);
    }

    private String getKey(Class clazz,String key){
        return clazz.getName().replaceAll("\\.",":")+":"+key;
    }


}
