package com.cheng.common.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel("返回评论")
public class CommentVo {

    private Long id;

    private Long user_id;

    private String username;

    private String content;

    private LocalDateTime created;
}
