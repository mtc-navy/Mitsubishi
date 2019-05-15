package io.mtc.modules.sys.service.impl;

import io.mtc.common.utils.Constant;
import io.mtc.modules.sys.dao.SysMenuDao;
import io.mtc.modules.sys.dao.SysUserDao;
import io.mtc.modules.sys.dao.SysUserTokenDao;
import io.mtc.modules.sys.entity.SysMenuEntity;
import io.mtc.modules.sys.entity.SysUserEntity;
import io.mtc.modules.sys.entity.SysUserTokenEntity;
import io.mtc.modules.sys.redis.SysUserTokenRedis;
import io.mtc.modules.sys.service.ShiroService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ShiroServiceImpl implements ShiroService {
    @Autowired
    private SysMenuDao sysMenuDao;
    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private SysUserTokenDao sysUserTokenDao;

    @Autowired
    private SysUserTokenRedis sysUserTokenRedis;

    @Override
    public Set<String> getUserPermissions(long userId) {
        List<String> permsList;

        //系统管理员，拥有最高权限
        if (userId == Constant.SUPER_ADMIN) {
            List<SysMenuEntity> menuList = sysMenuDao.selectList(null);
            permsList = new ArrayList<>(menuList.size());
            for (SysMenuEntity menu : menuList) {
                permsList.add(menu.getPerms());
            }
        } else {
            permsList = sysUserDao.queryAllPerms(userId);
        }
        //用户权限列表
        Set<String> permsSet = new HashSet<>();
        for (String perms : permsList) {
            if (StringUtils.isBlank(perms)) {
                continue;
            }
            permsSet.addAll(Arrays.asList(perms.trim().split(",")));
        }
        return permsSet;
    }

    @Override
    public SysUserTokenEntity queryByToken(String token) {
        SysUserTokenEntity tokenEntity = sysUserTokenRedis.get(token);
        if (tokenEntity == null) {
            tokenEntity = sysUserTokenDao.queryByToken(token);
            sysUserTokenRedis.saveOrUpdate(tokenEntity);
        }
        return tokenEntity;
    }

    @Override
    public SysUserEntity queryUser(Long userId) {
        return sysUserDao.selectById(userId);
    }
}
