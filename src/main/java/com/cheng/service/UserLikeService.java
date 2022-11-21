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
     *
     * @param likedUserId
     * @param code
     * @param currentPage
     * @return
     */
    IPage findByLikedUserIdAndStatus(String likedUserId, Integer code, Integer currentPage);

    /**
     *
     * @param giveLikedId
     * @param code
     * @param currentPage
     * @return
     */
    IPage findByGiveLikedIdAndStatus(String giveLikedId, Integer code,Integer currentPage);

    /**
     *
     * @param likedUserId
     * @param giveLikedId
     * @return
     */
    UserLike findByLikedUserIdAndLikedPostId(String likedUserId, String giveLikedId);
}
