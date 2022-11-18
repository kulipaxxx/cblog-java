package com.cheng.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 点赞记录表
 * </p>
 *
 * @author author: cheng
 * @since 2022-11-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserLikes implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 点赞信息ID
     */
    private String id;

    /**
     * 点赞对象id
     */
    private String infoId;

    /**
     * 时间
     */
    private LocalDateTime createTime;

    /**
     * 点赞人ID
     */
    private String likeUserId;

    private LocalDateTime updateTime;

    /**
     * 0 取消 1 点赞
     */
    private Integer status;


}
