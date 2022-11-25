package com.cheng.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
    @ApiModelProperty("被点赞blogid")
    @NotBlank(message = "被点赞id不能为空")
    private String likedBlogId;

    /**
     * 点赞的用户id
     */
    @ApiModelProperty("点赞的用户id")
    @NotBlank(message = "点赞人不能为空")
    private String giveLikedId;

    /**
     * 点赞状态，0取消，1点赞
     */
    @ApiModelProperty("点赞状态")
    @NotNull(message = "状态不能为空")
    private Integer status;

}
