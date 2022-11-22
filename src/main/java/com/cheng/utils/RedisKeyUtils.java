package com.cheng.utils;

import com.cheng.mapper.BlogMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class RedisKeyUtils {

    @Autowired
    BlogMapper blogMapper;

    //保存用户点赞数据的key
    public static final String MAP_USER_LIKED = "MAP_USER_LIKED";
    //保存用户被点赞数量的key
    public static final String MAP_USER_LIKED_COUNT = "MAP_USER_LIKED_COUNT";
    //保存网站访问次数
    public static final String MAP_USER_COUNT = "MAP_USER_COUNT";
    /**
     * 拼接被点赞的用户id和点赞的人的id作为key。格式 001::1006
     * @param likedUserId 被点赞的人id
     * @param giveLikeId 点赞的人的id
     * @return
     */
    public static String getLikedKey(String likedUserId, String giveLikeId){
        StringBuilder builder = new StringBuilder();
        builder.append(likedUserId);
        builder.append("::");
        builder.append(giveLikeId);
        return builder.toString();
    }
}
