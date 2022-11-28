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
         * @param likedBlogId
         * @param giveLikeId
         */
        void saveLiked2Redis(String likedBlogId, String giveLikeId);
        /**
         * 取消点赞。将状态改变为0
         * @param likedBlogId
         * @param giveLikeId
         */
        void unlikeFromRedis(String likedBlogId, String giveLikeId);
        /**
         * 从Redis中删除一条点赞数据
         * @param likedBlogId
         * @param giveLikeId
         */
        void deleteLikedFromRedis(String likedBlogId, String giveLikeId);
        /**
         * 该博客的点赞数加1
         * @param likedBlogId
         */
        void incrementLikedCount(String likedBlogId);

        /**
         * 获取点赞数量
         * @param likedBlogId
         * @return
         */
        Integer getLikedCount(String likedBlogId);
        /**
         * 该博客的点赞数减1
         * @param likedBlogId
         */
        void decrementLikedCount(String likedBlogId);

        /**
         * 找到喜欢关系
         * 喜欢关系
         *
         * @param likeBlogId     像博客id
         * @param giveLikeUserId 给像用户id
         * @return {@link Boolean}
         */
        Boolean findLikeRelation(String likeBlogId,String giveLikeUserId);

        /**
         * 得到喜欢数据
         * 获取Redis中存储的所有点赞数据并删除
         *
         * @param flag 国旗
         * @return {@link List}<{@link UserLike}>
         */
        List<UserLike> getLikedDataFromRedis(Boolean flag);

        /**
         * 获取Redis中存储的所有点赞数量并删除
         * @return
         */
        List<LikedCountDTO> getLikedCountFromRedis(Boolean flag);

}
