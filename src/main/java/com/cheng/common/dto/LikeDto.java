package com.cheng.common.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description: LikeDto
 * @Author cheng
 * @Date: 2022/11/22 11:22
 * @Version 1.0
 */
@Data
@ApiModel("封装点赞模块")
public class LikeDto implements Serializable {

    private static final long serialVersionUID = 1L;

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

}
