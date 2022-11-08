package com.cheng.service.impl;

import com.cheng.entity.User;
import com.cheng.mapper.UserMapper;
import com.cheng.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author author: cheng
 * @since 2022-10-31
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
