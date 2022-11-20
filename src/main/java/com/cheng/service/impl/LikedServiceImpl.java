package com.cheng.service.impl;

import cn.hutool.system.UserInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cheng.common.dto.LikedCountDTO;
import com.cheng.common.lang.LikedStatusEnum;
import com.cheng.entity.UserLike;
import com.cheng.service.LikedService;
import com.cheng.service.RedisService;
import com.cheng.service.UserLikeService;
import com.cheng.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.util.List;

@Service
@Slf4j
public class LikedServiceImpl implements LikedService {

    @Autowired
    UserLikeService userLikeService;

    @Autowired
    RedisService redisService;

    @Override
    @Transactional
    public void save(UserLike userLike) {
        userLikeService.save(userLike);
    }

    @Override
    @Transactional
    public void saveAll(List<UserLike> list) {
        userLikeService.saveBatch(list);
    }

    @Override
    public Page<UserLike> getLikedListByLikedUserId(String likedUserId, Pageable pageable) {
        return userLikeService.findByLikedUserIdAndStatus(likedUserId, LikedStatusEnum.LIKE.getCode(), pageable);
    }

    @Override
    public Page<UserLike> getLikedListByGiveLikedId(String giveLikedId, Pageable pageable) {
        return userLikeService.findByGiveLikedIdAndStatus(giveLikedId, LikedStatusEnum.LIKE.getCode(), pageable);
    }

    @Override
    public UserLike getByLikedUserIdAndGiveLikedId(String likedUserId, String giveLikedId) {
        return userLikeService.findByLikedUserIdAndLikedPostId(likedUserId, giveLikedId);
    }

    @Override
    @Transactional
    public void transLikedFromRedis2DB() {
        List<UserLike> list = redisService.getLikedDataFromRedis();
        for (UserLike like : list) {
            UserLike ul = getByLikedUserIdAndGiveLikedId(like.getLikedUserId(), like.getGiveLikedId());
            if (ul == null) {
                //没有记录，直接存入
                save(like);
            } else {
                //有记录，需要更新
                ul.setStatus(like.getStatus());
                save(ul);
            }
        }
    }

    @Override
    @Transactional
    public void transLikedCountFromRedis2DB() {
        List<LikedCountDTO> list = redisService.getLikedCountFromRedis();
        for (LikedCountDTO dto : list) {
            UserInfo user = userLikeService.getById(dto.getInfoId());
            //点赞数量属于无关紧要的操作，出错无需抛异常
            if (user != null) {
                Integer likeNum = user.getLikeNum() + dto.getCount();
                user.setLikeNum(likeNum);
                //更新点赞数量
                userLikeService.update(user);
            }
        }
    }
}

