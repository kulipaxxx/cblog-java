package com.cheng.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cheng.common.dto.LikedCountDTO;
import com.cheng.utils.LikedStatusEnum;
import com.cheng.entity.Blog;
import com.cheng.entity.UserLike;
import com.cheng.service.BlogService;
import com.cheng.service.LikedService;
import com.cheng.service.RedisService;
import com.cheng.service.UserLikeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class LikedServiceImpl implements LikedService {

    @Autowired
    UserLikeService userLikeService;

    @Autowired
    RedisService redisService;

    @Autowired
    BlogService blogService;

    @Override
    @Transactional
    public void save(UserLike userLike) {
        userLikeService.saveOrUpdate(userLike);
    }

    @Override
    @Transactional
    public void saveAll(List<UserLike> list) {
        userLikeService.saveBatch(list);
    }

    /**
     * 获取该用户被点赞信息
     * @param likedBlogId 被点赞人的id
     * @param currentPage
     * @return
     */
    @Override
    public IPage getLikedListByLikedUserId(String likedBlogId, Integer currentPage) {
        return userLikeService.findByLikedUserIdAndStatus(likedBlogId, LikedStatusEnum.LIKE.getCode(), currentPage);
    }

    /**
     * 根据点赞人的id查询点赞列表（即查询这个人都给谁点赞过）
     * @param giveLikedId
     * @param currentPage
     * @return
     */
    @Override
    public IPage getLikedListByGiveLikedId(String giveLikedId, Integer currentPage) {
        return userLikeService.findByGiveLikedIdAndStatus(giveLikedId, LikedStatusEnum.LIKE.getCode(), currentPage);
    }

    /**
     *通过被点赞人和点赞人id查询是否存在点赞记录
     * @param likedBlogId
     * @param giveLikedId
     * @return
     */
    @Override
    public UserLike getByLikedUserIdAndGiveLikedId(String likedBlogId, String giveLikedId) {
        return userLikeService.findByLikedUserIdAndLikedPostId(likedBlogId, giveLikedId);
    }

    /**
     * 持久化存入数据库
     */
    @Override
    @Transactional //事务管理
    public void transLikedFromRedis2DB() {
        List<UserLike> list = redisService.getLikedDataFromRedis();
        for (UserLike like : list) {
            UserLike ul = getByLikedUserIdAndGiveLikedId(like.getLikedBlogId(), like.getGiveLikedId());
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

    /**
     * 持久化存入数据库
     */
    @Override
    @Transactional
    public void transLikedCountFromRedis2DB() {
        List<LikedCountDTO> list = redisService.getLikedCountFromRedis();
        for (LikedCountDTO dto : list) {
            UserLike user = userLikeService.getById(dto.getInfoId());
            String userId = user.getLikedBlogId();

            Blog blog = blogService.getById(userId);
            //点赞数量属于无关紧要的操作，出错无需抛异常
            if (blog != null) {
                Integer likeNum = dto.getValue();
                blog.setLikeCount(likeNum);
                //更新点赞数量
                blogService.updateById(blog);
            }
        }
    }
}

