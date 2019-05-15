package io.mtc.modules.app.service.impl;

import io.mtc.common.exception.RRException;
import io.mtc.common.validator.Assert;
import io.mtc.modules.app.dao.UserDao;
import io.mtc.modules.app.entity.UserEntity;
import io.mtc.modules.app.entity.UserEntityExample;
import io.mtc.modules.app.form.LoginForm;
import io.mtc.modules.app.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserEntity queryByUserName(String UserName) {
        return userDao.selectByPrimaryKey(UserName);
    }

    @Override
    public long login(LoginForm form) {
        //通过账户和密码登录
        UserEntity user;
        if (!StringUtils.isBlank(form.getUserName())) {
            user = queryByUserName(form.getUserName());
            Assert.isNull(user, "账号或密码错误");

            //TODO 验证加密后密码是否一致
            if (!user.getUPassword().equals(DigestUtils.sha256Hex(form.getPassword()))
                    && !user.getUPassword().equals(form.getPassword())) {
                throw new RRException("账号或密码错误");
            }
        } else {
            //通过微信号登录
            UserEntityExample userEntityExample = new UserEntityExample();
            userEntityExample.createCriteria().andUWechatEqualTo(form.getOpenId());
            List<UserEntity> userEntities = userDao.selectByExample(userEntityExample);
            if (userEntities == null || userEntities.isEmpty()) {
                throw new RRException("未绑定该微信号");
            }
            user = userEntities.get(0);
        }

        return user.getDocentry();
    }

    @Override
    public UserEntity resetPassword(UserEntity userEntity) {
        try {
            userDao.resetPassword(userEntity);
        } catch (Exception e) {
            throw new RRException("更新密码失败：" + e.getMessage());
        }
        return userEntity;
    }

    @Override
    public UserEntity bindmicromsg(UserEntity userEntity) {
        try {
            userDao.resetOpenId(userEntity);
        } catch (Exception e) {
            throw new RRException("更新微信号信息失败：" + e.getMessage());
        }
        return userEntity;
    }
}
