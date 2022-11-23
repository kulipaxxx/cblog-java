package com.cheng.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cheng.entity.UserLike;

/**
 * <p>
 * 用户点赞表 服务类
 * </p>
 *
 * @author author: cheng
 * @since 2022-11-19
 */
public interface UserLikeService extends IService<UserLike> {

    /**
     *根据被点赞人的id查询点赞列表（即查询都谁给这个人点赞过）
     * @param likedBlogId
     * @param code
     * @param currentPage
     * @return
     */
    IPage findByLikedUserIdAndStatus(String likedBlogId, Integer code, Integer currentPage);

    /**
     *根据点赞人的id查询点赞列表（即查询这个人都给谁点赞过）
     * @param giveLikedId
     * @param code
     * @param currentPage
     * @return
     */
    IPage findByGiveLikedIdAndStatus(String giveLikedId, Integer code,Integer currentPage);

    /**
     *通过被点赞人和点赞人id查询是否存在点赞记录
     * @param likedBlogId
     * @param giveLikedId
     * @return
     */
    UserLike findByLikedUserIdAndLikedPostId(String likedBlogId, String giveLikedId);
}
