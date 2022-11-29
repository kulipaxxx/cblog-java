package com.cheng.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cheng.common.dto.CommentDto;
import com.cheng.entity.Comment;

/**
 * <p>
 * 用户评论表 服务类
 * </p>
 *
 * @author author: cheng
 * @since 2022-11-29
 */
public interface CommentService extends IService<Comment> {
    boolean removeComment(CommentDto commentDto);
}
