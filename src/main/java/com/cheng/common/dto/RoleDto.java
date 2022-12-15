package com.cheng.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description: RoleDto
 * @Author cheng
 * @Date: 2022/12/15 22:44
 * @Version 1.0
 */
@Data
@ApiModel("Role信息")
public class RoleDto implements Serializable{
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户id")
    private Long id;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("年龄")
    private String age;

    @ApiModelProperty("性别")
    private String gender;
}
