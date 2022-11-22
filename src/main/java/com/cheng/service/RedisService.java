package com.cheng.service;

import com.cheng.common.dto.LikedCountDTO;
import com.cheng.entity.UserLike;

import java.util.List;

/**
 * @Description: RedisService
 * @Author cheng
 * @Date: 2022/11/19 23:23
 * @Version 1.0
 */
public interface RedisService {
        /**
         * 点赞。状态为1
         * @param likedUserId
         * @param giveLikeId
         */
        void saveLiked2Redis(String likedUserId, String giveLikeId);
        /**
         * 取消点赞。将状态改变为0
         * @param likedUserId
         * @param giveLikeId
         */
        void unlikeFromRedis(String likedUserId, String giveLikeId);
        /**
         * 从Redis中删除一条点赞数据
         * @param likedUserId
         * @param giveLikeId
         */
        void deleteLikedFromRedis(String likedUserId, String giveLikeId);
        /**
         * 该用户的点赞数加1
         * @param likedUserId
         */
        void incrementLikedCount(String likedUserId);

        /**
         * 获取点赞数量
         * @param likedUserId
         * @return
         */
        Integer getLikedCount(String likedUserId);
        /**
         * 该用户的点赞数减1
         * @param likedUserId
         */
        void decrementLikedCount(String likedUserId);
        /**
         * 获取Redis中存储的所有点赞数据
         * @return
         */
        List<UserLike> getLikedDataFromRedis();
        /**
         * 获取Redis中存储的所有点赞数量
         * @return
         */
        List<LikedCountDTO> getLikedCountFromRedis();

}
