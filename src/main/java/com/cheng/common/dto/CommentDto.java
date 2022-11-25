package com.cheng.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("包装获取的评论数据")
public class CommentDto implements Serializable {

    private static final long serialVersionUID = 1L;


    private Long id;
    @ApiModelProperty("blogId")
    private Long blogId;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("评论内容")
    private String content;

    @ApiModelProperty("用户头像")
    private String avatar;
}
