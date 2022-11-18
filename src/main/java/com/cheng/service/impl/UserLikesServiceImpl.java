package com.cheng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cheng.common.dto.UserLikCountDTO;
import com.cheng.common.dto.UserLikesDto;
import com.cheng.entity.UserLikes;
import com.cheng.mapper.UserLikesMapper;
import com.cheng.service.UserLikesService;
import com.cheng.service.Utils.LikedStatusEum;
import com.cheng.service.Utils.RedisKeyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 点赞记录表 服务实现类
 * </p>
 *
 * @author author: cheng
 * @since 2022-11-18
 */
@Service
public class UserLikesServiceImpl extends ServiceImpl<UserLikesMapper, UserLikes> implements UserLikesService {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    UserLikesService userLikesService;

    public Object likeStatus(String infoId, String likeUserId) {
        if (redisTemplate.opsForHash().hasKey(RedisKeyUtils.MAP_KEY_USER_LIKED, RedisKeyUtils.getLikedKey(infoId, likeUserId))) {
            String o = redisTemplate.opsForHash().get(RedisKeyUtils.MAP_KEY_USER_LIKED, RedisKeyUtils.getLikedKey(infoId, likeUserId)).toString();
            if ("1".equals(o)) {
                unLikes(infoId, likeUserId);
                return LikedStatusEum.UNLIKE;
            }
            if ("0".equals(o)) {
                likes(infoId, likeUserId);
                return LikedStatusEum.LIKE;
            }
        }
        UserLikes userLikes = userLikesService.getOne(new QueryWrapper<UserLikes>().eq("info_id", infoId).eq("like_user_id", likeUserId));
        if (userLikes == null) {
            UserLikes userLikes1 = new UserLikes();
            userLikes1.setInfoId(infoId);
            userLikes1.setLikeUserId(likeUserId);
            userLikesService.save(userLikes1);
            likes(infoId, likeUserId);
            return LikedStatusEum.LIKE;
        }
        if (userLikes.getStatus() == 1) {
            unLikes(infoId, likeUserId);
            return LikedStatusEum.UNLIKE;
        }

        if (userLikes.getStatus() == 0) {
            likes(infoId, likeUserId);
            return LikedStatusEum.LIKE;
        }
        return "";
    }

    public void likes(String infoId, String likeUserId) {
        String likedKey = RedisKeyUtils.getLikedKey(infoId, likeUserId);
        redisTemplate.opsForHash().increment(RedisKeyUtils.MAP_KEY_USER_LIKED_COUNT, infoId, 1);
        redisTemplate.opsForHash().put(RedisKeyUtils.MAP_KEY_USER_LIKED, likedKey, LikedStatusEum.LIKE);
    }

    public void unLikes(String infoId, String likeUserId) {
        String likedKey = RedisKeyUtils.getLikedKey(infoId, likeUserId);
        redisTemplate.opsForHash().increment(RedisKeyUtils.MAP_KEY_USER_LIKED_COUNT, infoId, -1);
        redisTemplate.opsForHash().delete(RedisKeyUtils.MAP_KEY_USER_LIKED, likedKey);
    }

    public List<UserLikesDto> getLikedDataFromRedis() {
        Cursor<Map.Entry<Object, Object>> scan = redisTemplate.opsForHash().scan(RedisKeyUtils.MAP_KEY_USER_LIKED, ScanOptions.NONE);
        List<UserLikesDto> list = new ArrayList<>();
        while (scan.hasNext()) {
            Map.Entry<Object, Object> entry = scan.next();
            String key = (String) entry.getKey();
            String[] split = key.split("::");
            String infoId = split[0];
            String likeUserId = split[1];
            Integer value = (Integer) entry.getValue();
            //组装成 UserLike 对象
            UserLikesDto userLikeDetail = new UserLikesDto(infoId, likeUserId, value);
            list.add(userLikeDetail);
            //存到 list 后从 Redis 中删除
            redisTemplate.opsForHash().delete(RedisKeyUtils.MAP_KEY_USER_LIKED, key);
        }
        return list;
    }

    public List<UserLikCountDTO> getLikedCountFromRedis() {
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(RedisKeyUtils.MAP_KEY_USER_LIKED_COUNT, ScanOptions.NONE);
        List<UserLikCountDTO> list = new ArrayList<>();
        while (cursor.hasNext()) {
            Map.Entry<Object, Object> map = cursor.next();
            String key = (String) map.getKey();
            Integer value = (Integer) map.getValue();
            UserLikCountDTO userLikCountDTO = new UserLikCountDTO(key, value);
            list.add(userLikCountDTO);
            redisTemplate.opsForHash().delete(RedisKeyUtils.MAP_KEY_USER_LIKED_COUNT, key);
        }
        return list;
    }



}
