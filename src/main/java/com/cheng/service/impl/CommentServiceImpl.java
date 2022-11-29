package com.cheng.service.impl;

import com.cheng.entity.Comment;
import com.cheng.mapper.CommentMapper;
import com.cheng.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户评论表 服务实现类
 * </p>
 *
 * @author author: cheng
 * @since 2022-11-29
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

}
