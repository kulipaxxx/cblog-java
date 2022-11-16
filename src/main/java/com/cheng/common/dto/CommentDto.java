package com.cheng.common.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("包装获取的评论数据")
public class CommentDto {

    private Long blogId;

    private Long userId;

    private String userName;

    private String content;
}
