package com.cheng.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 注册dto
 *
 * @Description: RegisterDto
 * @Author cheng
 * @Date: 2022/11/27 19:37
 * @Version 1.0
 * @date 2022/11/27
 */
@Data
@ApiModel("注册封装信息")
public class RegisterDto {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户名")
    @NotBlank(message = "昵称不能为空")
    private String username;

    @ApiModelProperty("密码")
    @NotBlank(message = "密码不能为空")
    private String password;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("验证码")
    @NotBlank(message = "验证码不能为空")
    private String code;

    @ApiModelProperty("验证码key(用于从redis中获取验证码进行比对)")
    private String uuid;
}
