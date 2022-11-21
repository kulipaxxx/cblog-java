package com.cheng.controller.additional;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cheng.common.dto.CommentDto;
import com.cheng.common.lang.Result;
import com.cheng.entity.Blog;
import com.cheng.entity.Comment;
import com.cheng.service.BlogService;
import com.cheng.service.CommentService;
import com.cheng.service.UserService;
import com.cheng.utils.RedisUtil;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author author: cheng
 * @since 2022-11-15
 */
@RestController
@RequestMapping("/comment")
@Api(description = "评论模块")
public class CommentController {

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    CommentService commentService;

    @Autowired
    UserService userService;

    @Autowired
    BlogService blogService;

    //获取博客评论
    @GetMapping("/{id}")
    public Result getBlogComment(@PathVariable("id")Long id,Integer currentPage){
        //分页查询
        if (currentPage==null || currentPage < 1)currentPage = 1;
        List<Object> list_user_id = redisUtil.lGet(String.valueOf(id), 0, redisUtil.lGetListSize(String.valueOf(id)));

        Page page = new Page(currentPage,5);
        IPage iPage = commentService.page(page, new QueryWrapper<Comment>().eq("blog_id", id));

        return Result.succ(iPage);
    }

    //发表评论
    @PostMapping("/sub")
    @RequiresAuthentication //要求权限
    public Result subComment(@Validated @RequestBody CommentDto commentDto){
        Blog id = blogService.getById(commentDto.getBlogId());
        if (id == null){
            return Result.fail("博客不存在");
        }
        //设置评论数据
        Comment comment = new Comment();
        comment.setBlogId(commentDto.getBlogId());
        comment.setContent(commentDto.getContent());
        comment.setCreated(LocalDateTime.now());
        comment.setUserId(commentDto.getUserId());
        comment.setUsername(commentDto.getUserName());
        comment.setAvatar(userService.getById(commentDto.getUserId()).getAvatar());
        /*
        //获取到用户评论，对评论人id存redis,具体数据交给rabbitMQ异步存储在mysql
        Utils.lSet(String.valueOf(commentDto.getBlogId()), commentDto.getUserId());

        //rabbitMQ异步存数据到mysql
        */
        //保存到mysql
        commentService.save(comment);

        return Result.succ("发表成功");
    }
}