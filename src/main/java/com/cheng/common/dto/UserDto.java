package com.cheng.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description: UserDto
 * @Author cheng
 * @Date: 2022/12/17 16:58
 * @Version 1.0
 */
@Data
@ApiModel("用户更改信息DTO")
public class UserDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户id")
    private Long id;

    @ApiModelProperty("用户昵称")
    private String username;

    @ApiModelProperty("用户头像")
    private String avatar;

    @ApiModelProperty("用户邮箱")
    private String email;

    @ApiModelProperty("用户性别")
    private String gender;

    @ApiModelProperty("用户年龄")
    private int age;

}
