package com.cheng.service.impl;

import com.cheng.entity.Blog;
import com.cheng.mapper.BlogMapper;
import com.cheng.service.BlogService;
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
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

}
