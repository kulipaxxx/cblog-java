package com.cheng.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
@TableName("m_blog")
@ApiModel("文章实体类")
public class Blog implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("文章id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @ApiModelProperty("文章所属用户")
    private Long userId;

    @ApiModelProperty("文章标题")
    @NotBlank(message = "标题不能为空")
    private String title;

    @ApiModelProperty("文章摘要")
    @NotBlank(message = "摘要不能为空")
    private String description;

    @ApiModelProperty("文章内容")
    @NotBlank(message = "内容不能为空")
    private String content;

    @ApiModelProperty("文章创建时间")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDateTime created;

    @ApiModelProperty("文章状态")
    private Integer status;


}
