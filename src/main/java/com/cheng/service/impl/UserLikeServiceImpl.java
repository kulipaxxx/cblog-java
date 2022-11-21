package com.cheng.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cheng.entity.UserLike;
import com.cheng.mapper.UserLikeMapper;
import com.cheng.service.UserLikeService;
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


    /**
     * 根据被点赞人的id查询点赞列表（即查询都谁给这个人点赞过）
     * @param likedUserId
     * @param code
     * @param currentPage
     * @return
     */
    @Override
    public IPage findByLikedUserIdAndStatus(String likedUserId, Integer code, Integer currentPage) {
        if(currentPage == null || currentPage < 1) currentPage = 1;
        Page page = new Page(currentPage, 5);

        return null;
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
     * @param likedUserId
     * @param giveLikedId
     * @return
     */
    @Override
    public UserLike findByLikedUserIdAndLikedPostId(String likedUserId, String giveLikedId) {
        return null;
    }
}
