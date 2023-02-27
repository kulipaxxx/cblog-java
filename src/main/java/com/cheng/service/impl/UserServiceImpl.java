package com.cheng.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cheng.entity.User;
import com.cheng.entity.role.Record;
import com.cheng.mapper.UserMapper;
import com.cheng.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author author: cheng
 * @since 2022-10-31
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    UserMapper userMapper;

    @Override
    public List<Record> getRoles(Long id) {
        return userMapper.getRoles(id);
    }
}
