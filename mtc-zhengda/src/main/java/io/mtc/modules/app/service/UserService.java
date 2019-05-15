package io.mtc.modules.app.service;

import io.mtc.modules.app.entity.UserEntity;
import io.mtc.modules.app.form.LoginForm;

/**
 * 用户
 */
public interface UserService {

    /**
     * 查找账号
     *
     * @param UserName
     * @return
     */
    UserEntity queryByUserName(String UserName);

    /**
     * 用户登录
     *
     * @param form 登录表单
     * @return 返回用户ID
     */
    long login(LoginForm form);

    /**
     * 更新密码
     *
     * @param userEntity
     * @return
     */
    UserEntity resetPassword(UserEntity userEntity);

    /**
     * 绑定微信号信息
     *
     * @param userEntity
     * @return
     */
    UserEntity bindmicromsg(UserEntity userEntity);
}
