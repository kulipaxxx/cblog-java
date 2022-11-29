package com.cheng.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cheng.common.dto.CommentDto;
import com.cheng.entity.Comment;
import com.cheng.mapper.CommentMapper;
import com.cheng.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

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
    @Autowired
    CommentMapper commentMapper;

    /**
     * 删除评论
     *
     * @param commentDto 评论dto
     * @return boolean
     * 为了能完整地删除这棵子树，我们需要遍历这棵子树的每一个结点，比较简单的方式就是层序遍历。
     * 这里我采用了非递归的方法，即借助队列实现。
     */
    @Override
    public boolean removeComment(CommentDto commentDto) {
        Queue<CommentDto> queue = new LinkedList<>();
        queue.offer(commentDto);
        while(!queue.isEmpty()) {
            CommentDto cur = queue.poll();
            int resultNum = commentMapper.deleteById(cur.getId());
            if(resultNum <= 0) return false;
            if(cur.getChild() != null) {
                List<CommentDto> child = cur.getChild();
                for(CommentDto tmp: child)
                    queue.offer(tmp);
            }
        }
        return true;
    }

}
