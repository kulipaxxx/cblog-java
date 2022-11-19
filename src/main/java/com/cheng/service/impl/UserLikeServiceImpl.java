package com.cheng.service.impl;

import com.cheng.entity.UserLike;
import com.cheng.mapper.UserLikeMapper;
import com.cheng.service.UserLikeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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

}
