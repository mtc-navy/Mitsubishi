package io.mtc.modules.app.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 登录表单
 */
@Data
@ApiModel(value = "登录表单")
public class LoginForm implements Serializable {
    @ApiModelProperty(value = "账号")
    private String UserName;

    @ApiModelProperty(value = "密码")
    private String Password;

    @ApiModelProperty(value = "微信号")
    private String OpenId;

    public String getUserName() {
        return UserName;
    }
}
