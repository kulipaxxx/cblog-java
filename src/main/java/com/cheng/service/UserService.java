package com.cheng.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cheng.entity.User;
import com.cheng.entity.role.Record;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author: cheng
 * @since 2022-10-31
 */
public interface UserService extends IService<User> {
    List<Record> getRoles(Long id);
}
