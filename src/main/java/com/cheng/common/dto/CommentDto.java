package com.cheng.common.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
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
    @NotBlank(message = "内容不能为空")
    private String content;       // 评论内容
    @NotBlank(message = "评论作者ID不能为空")
    private Long userId;          // 评论作者ID
    @NotBlank(message = "评论作者姓名不能为空")
    private String userName;      // 评论作者姓名

    private Date createTime;      // 创建时间
    private Integer isDelete;     // 是否删除（0：未删除；1：已删除）
    @NotBlank(message = "博客ID为空")
    private Long blogId;      // 博客ID
    private Long parentId;    // 父评论ID（被回复的评论）
    private Long rootParentId;      // 根评论ID（最顶级的评论）

    private List<CommentDto> child;    // 本评论下的子评论
}

