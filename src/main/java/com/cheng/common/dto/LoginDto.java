package com.cheng.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@ApiModel("服务端Login信息")
public class LoginDto implements Serializable {

    @ApiModelProperty("用户名")
    @NotBlank(message = "昵称不能为空")
    private String username;

    @ApiModelProperty("密码")
    @NotBlank(message = "密码不能为空")
    private String password;

    @ApiModelProperty("验证码")
    @NotBlank(message = "验证码不能为空")
    private String code;

    @ApiModelProperty("验证码key(用于从redis中获取验证码进行比对)")
    private String uuid;
}
