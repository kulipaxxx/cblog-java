package com.cheng.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户点赞表
 * </p>
 *
 * @author author: cheng
 * @since 2022-11-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel("博客点赞实体类")
public class UserLike implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    /**
     * 被点赞的用户id
     */
    private String likedUserId;

    /**
     * 点赞的用户id
     */
    private String giveLikedId;

    /**
     * 点赞状态，0取消，1点赞
     */
    private Integer status;

    /**
     * 创建   时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    public UserLike(String likedUserId,String giveLikedId,Integer value){
        this.likedUserId = likedUserId;
        this.giveLikedId = giveLikedId;
        this.status = value;
    }


}
