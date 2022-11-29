package com.cheng.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 评论信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("包装获取的评论数据")
public class CommentDto implements Serializable {

    private Long id;       // 评论ID
    private String content;       // 评论内容
    private Long userId;          // 评论作者ID
    private String userName;      // 评论作者姓名
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;      // 创建时间
    private Integer isDelete;     // 是否删除（0：未删除；1：已删除）

    private Long blogId;      // 博客ID
    private Long parentId;    // 父评论ID（被回复的评论）
    private Long rootParentId;      // 根评论ID（最顶级的评论）

    private List<CommentDto> child;    // 本评论下的子评论
}

