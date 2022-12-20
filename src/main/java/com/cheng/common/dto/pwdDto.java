package com.cheng.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Description: pwdDto
 * @Author cheng
 * @Date: 2022/11/26 23:28
 * @Version 1.0
 */
@Data
@ApiModel("找回密码封装信息")
public class pwdDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户名")
    @NotBlank(message = "昵称不能为空")
    private String username;

    @ApiModelProperty("新密码")
    private String password;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("验证码")
    @NotBlank(message = "验证码不能为空")
    private String code;

    @ApiModelProperty("验证码key(用于从redis中获取验证码进行比对)")
    private String uuid;
}
