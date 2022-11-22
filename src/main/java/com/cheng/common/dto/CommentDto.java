package com.cheng.common.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("包装获取的评论数据")
public class CommentDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long blogId;

    private Long userId;

    private String userName;

    private String content;
}
