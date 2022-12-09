package com.cheng.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author author: cheng
 * @since 2022-10-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("m_user")
@ApiModel("用户实体类")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("用户昵称")
    @NotBlank(message = "昵称不能为空")
    private String username;

    @ApiModelProperty("用户头像")
    private String avatar;

    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    @ApiModelProperty("用户邮箱")
    private String email;

    @ApiModelProperty("用户密码")
    @NotBlank(message = "密码不能为空")
    private String password;

    @ApiModelProperty("用户状态")
    private Integer status;

    @ApiModelProperty("用户注册时间")
    private LocalDateTime created;

    @ApiModelProperty("用户上次登录时间")
    private LocalDateTime lastLogin;

    @ApiModelProperty("用户性别")
    private String gender;

    @ApiModelProperty("用户年龄")
    private int age;

}
