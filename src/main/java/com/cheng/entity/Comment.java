package com.cheng.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户评论表
 * </p>
 *
 * @author author: cheng
 * @since 2022-11-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("m_comment")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 评论Id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论人id
     */
    private Long userId;

    /**
     * 评论人姓名
     */
    private String userName;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 是否已删除0：未删除；1：已删除
     */
    private Integer isDelete;

    /**
     * 所属博客id
     */
    private Long blogId;

    /**
     * 父评论id
     */
    private Long parentId;

    /**
     * 根评论id
     */
    private Long rootParentId;


}
