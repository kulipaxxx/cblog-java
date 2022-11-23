package com.cheng.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cheng.entity.UserLike;

import java.util.List;

public interface LikedService {

    /**
     * 保存点赞记录
     * @param userLike
     * @return
     */
    void save(UserLike userLike);

    /**
     * 批量保存或修改
     * @param list
     */
    void saveAll(List<UserLike> list);


    /**
     * 根据被点赞人的id查询点赞列表（即查询都谁给这个人点赞过）
     * @param likedBlogId 被点赞人的id
     * @param currentPage
     * @return
     */
    IPage getLikedListByLikedUserId(String likedBlogId, Integer currentPage);

    /**
     * 根据点赞人的id查询点赞列表（即查询这个人都给谁点赞过）
     * @param giveLikedId
     * @param currentPage
     * @return
     */
    IPage getLikedListByGiveLikedId(String giveLikedId, Integer currentPage);

    /**
     * 通过被点赞人和点赞人id查询是否存在点赞记录
     * @param likedBlogId
     * @param giveLikedId
     * @return
     */
    UserLike getByLikedUserIdAndGiveLikedId(String likedBlogId, String giveLikedId);

    /**
     * 将Redis里的点赞数据存入数据库中
     */
    void transLikedFromRedis2DB();

    /**
     * 将Redis中的点赞数量数据存入数据库
     */
    void transLikedCountFromRedis2DB();

}

