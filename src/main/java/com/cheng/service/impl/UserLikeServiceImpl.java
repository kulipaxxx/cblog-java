package com.cheng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cheng.entity.UserLike;
import com.cheng.mapper.UserLikeMapper;
import com.cheng.service.UserLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户点赞表 服务实现类
 * </p>
 *
 * @author author: cheng
 * @since 2022-11-19
 */
@Service
public class UserLikeServiceImpl extends ServiceImpl<UserLikeMapper, UserLike> implements UserLikeService {

    @Autowired
    UserLikeMapper userLikeMapper;

    /**
     * 根据被点赞人的id查询点赞列表（即查询都谁给这个人点赞过）
     * @param likedBlogId
     * @param code
     * @param currentPage
     * @return
     */
    @Override
    public IPage findByLikedUserIdAndStatus(String likedBlogId, Integer code, Integer currentPage) {
        if(currentPage == null || currentPage < 1) currentPage = 1;
        Page<UserLike> page1 = new Page<>(currentPage,5);

        return userLikeMapper.selectPage( page1, new QueryWrapper<UserLike>().eq("liked_blog_id",likedBlogId).eq("status",code));
    }

    /**
     * 根据点赞人的id查询点赞列表（即查询这个人都给谁点赞过）
     * @param giveLikedId
     * @param code
     * @param currentPage
     * @return
     */
    @Override
    public Page<UserLike> findByGiveLikedIdAndStatus(String giveLikedId, Integer code, Integer currentPage) {
        return null;
    }

    /**
     * 通过被点赞人和点赞人id查询是否存在点赞记录
     * @param likedBlogId
     * @param giveLikedId
     * @return
     */
    @Override
    public UserLike findByLikedUserIdAndLikedPostId(String likedBlogId, String giveLikedId) {
       return userLikeMapper.selectOne(new QueryWrapper<UserLike>().eq("liked_blog_id",likedBlogId).eq("give_liked_id",giveLikedId));
    }
}
