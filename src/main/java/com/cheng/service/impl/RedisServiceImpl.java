package com.cheng.service.impl;

import com.cheng.common.dto.LikedCountDTO;
import com.cheng.entity.UserLike;
import com.cheng.service.RedisService;
import com.cheng.utils.LikedStatusEnum;
import com.cheng.utils.RedisKeyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class RedisServiceImpl implements RedisService {
    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public void saveLiked2Redis(String likedBlogId, String giveLikeId) {
        String key = RedisKeyUtils.getLikedKey(likedBlogId, giveLikeId);
        redisTemplate.opsForHash().put(RedisKeyUtils.MAP_USER_LIKED,
                key, LikedStatusEnum.LIKE.getCode());
    }

    @Override
    public void unlikeFromRedis(String likedBlogId, String giveLikeId) {
        String key = RedisKeyUtils.getLikedKey(likedBlogId, giveLikeId);
        redisTemplate.opsForHash().put(RedisKeyUtils.MAP_USER_LIKED,
                key, LikedStatusEnum.UNLIKE.getCode());
    }

    @Override
    public void deleteLikedFromRedis(String likedBlogId, String giveLikeId) {
        String key = RedisKeyUtils.getLikedKey(likedBlogId, giveLikeId);
        redisTemplate.opsForHash().delete(RedisKeyUtils.MAP_USER_LIKED, key);
    }

    //原子操作
    @Override
    public void incrementLikedCount(String likedBlogId) {
        redisTemplate.opsForHash().increment(RedisKeyUtils.MAP_USER_LIKED_COUNT, likedBlogId, 1);
    }

    @Override
    public Integer getLikedCount(String likedBlogId) {
        return (Integer) redisTemplate.opsForHash().get(RedisKeyUtils.MAP_USER_LIKED_COUNT, likedBlogId);
    }

    @Override
    public void decrementLikedCount(String likedBlogId) {
        redisTemplate.opsForHash().increment(RedisKeyUtils.MAP_USER_LIKED_COUNT, likedBlogId, -1);
    }

    /**
     * 好喜欢关系
     *
     * @param likeBlogId  像博客id
     * @param giveLikedId 给喜欢id
     * @return {@link Boolean}
     */
    @Override
    public Boolean findLikeRelation(String likeBlogId, String giveLikedId) {
        List<UserLike> userLikes = getLikedDataFromRedis(false);
        boolean flag = false;
        for (UserLike userLike : userLikes) {
            if (userLike.getLikedBlogId().equals(likeBlogId)&&
                userLike.getGiveLikedId().equals(giveLikedId)){//查找是否有缓存记录
                flag = true;
                break;
            }
        }
        return flag;
    }


    //获取like数据 flag是否删除 true删除缓存
    @Override
    public List<UserLike> getLikedDataFromRedis(Boolean flag) {
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(RedisKeyUtils.MAP_USER_LIKED, ScanOptions.NONE);
        List<UserLike> list = new ArrayList<>();
        if (flag) {
            while (cursor.hasNext()) {
                Map.Entry<Object, Object> entry = cursor.next();
                String key = (String) entry.getKey();
                //分离出 likedUserId，giveLikeId
                String[] split = key.split("::");
                String likedUserId = split[0];
                String giveLikeId = split[1];
                Integer value = (Integer) entry.getValue();
                //组装成 UserLike 对象
                UserLike userLike = new UserLike(likedUserId, giveLikeId, value);
                list.add(userLike);
                //存到 list 后从 Redis 中删除缓存
                redisTemplate.opsForHash().delete(RedisKeyUtils.MAP_USER_LIKED, key);
            }
        } else {
            while (cursor.hasNext()) {
                Map.Entry<Object, Object> entry = cursor.next();
                String key = (String) entry.getKey();
                //分离出 likedUserId，giveLikeId
                String[] split = key.split("::");
                String likedUserId = split[0];
                String giveLikeId = split[1];
                Integer value = (Integer) entry.getValue();
                //组装成 UserLike 对象
                UserLike userLike = new UserLike(likedUserId, giveLikeId, value);
                list.add(userLike);
            }
        }
        return list;
    }

    @Override
    public List<LikedCountDTO> getLikedCountFromRedis(Boolean flag) {
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(RedisKeyUtils.MAP_USER_LIKED_COUNT, ScanOptions.NONE);
        List<LikedCountDTO> list = new ArrayList<>();
        if (flag){
            while (cursor.hasNext()) {
                Map.Entry<Object, Object> map = cursor.next();
                //将点赞数量存储在 LikedCountDT
                String key = (String) map.getKey();
                LikedCountDTO dto = new LikedCountDTO(key, (Integer) map.getValue());
                list.add(dto);
                //从Redis中删除这条记录
                redisTemplate.opsForHash().delete(RedisKeyUtils.MAP_USER_LIKED_COUNT, key);
            }
        }else {
            while (cursor.hasNext()) {
                Map.Entry<Object, Object> map = cursor.next();
                //将点赞数量存储在 LikedCountDT
                String key = (String) map.getKey();
                LikedCountDTO dto = new LikedCountDTO(key, (Integer) map.getValue());
                list.add(dto);
            }
        }

        return list;
    }

}
