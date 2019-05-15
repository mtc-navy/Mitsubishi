package io.mtc.modules.app.controller;

import io.mtc.common.annotation.SysLog;
import io.mtc.common.exception.RRException;
import io.mtc.common.utils.R;
import io.mtc.common.utils.ShiroUtils;
import io.mtc.modules.app.entity.UserEntity;
import io.mtc.modules.app.form.LoginForm;
import io.mtc.modules.app.service.UserService;
import io.mtc.modules.sys.service.SysUserTokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录授权
 */
@RestController
@RequestMapping("/sap/user")
@Api("登录接口")
public class AppLoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private SysUserTokenService sysUserTokenService;

    /**
     * 登录:独立身份验证机制，非SAP B1校验
     *
     * @param form
     * @return
     */
    @PostMapping("login")
    @ApiOperation("I0001-身份验证：独立身份验证机制，非SAP B1校验")
    //@SysLog("身份验证-I0001")
    public R login(@RequestBody LoginForm form) {
        //表单校验
        if (StringUtils.isBlank(form.getOpenId()) && StringUtils.isBlank(form.getUserName())) {
            throw new RRException("账号不能为空");
        }
        if (StringUtils.isBlank(form.getOpenId()) && StringUtils.isBlank(form.getPassword())) {
            throw new RRException("密码不能为空");
        }

        //用户登录
        long userId = userService.login(form);

        //生成token，并保存到数据库
        R r = sysUserTokenService.createToken(userId);

        return R.ok(r);
    }

    /**
     * 用于修改当前用户密码
     *
     * @param form
     * @return
     */
    @PostMapping("password")
    @ApiOperation("I0002-密码修改：可用于修改当前用户密码")
    @SysLog("I0002-密码修改")
    public R password(@RequestBody LoginForm form) {
        if (StringUtils.isBlank(form.getPassword())) {
            throw new RRException("新密码不能为空");
        }

        UserEntity userEntity = ShiroUtils.getUserEntity();
        //TODO 设定最新的密码
        userEntity.setUPassword(DigestUtils.sha256Hex(form.getPassword()));
        userEntity.setUPassword(form.getPassword());

        userService.resetPassword(userEntity);
        return R.ok();
    }

    /**
     * 设置微信与当前用户身份绑定，实际自动登录
     *
     * @param form
     * @return
     */
    @PostMapping("bindmicromsg")
    @ApiOperation("I0003-微信绑定：设置微信与当前用户身份绑定，实际自动登录")
    //@SysLog("微信绑定-I0003")
    public R bindmicromsg(@RequestBody LoginForm form) {
        if (StringUtils.isBlank(form.getOpenId())) {
            throw new RRException("微信号OpenId不能为空");
        }

        UserEntity userEntity = ShiroUtils.getUserEntity();
        //更新微信号OpenId
        userEntity.setUWechat(form.getOpenId());
        userService.bindmicromsg(userEntity);
        return R.ok();
    }

    /**
     * 设置微信与当前用户身份解绑
     *
     * @return
     */
    @PostMapping("unbindmicromsg")
    @ApiOperation("I0004-微信解绑：设置微信与当前用户身份解绑")
    @SysLog("I0004-微信解绑")
    public R unbindmicromsg() {
        UserEntity userEntity = ShiroUtils.getUserEntity();
        //更新微信号OpenId为空
        userEntity.setUWechat(null);
        userService.bindmicromsg(userEntity);
        return R.ok();
    }
}
