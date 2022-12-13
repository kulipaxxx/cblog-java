package com.cheng.controller.admin.blog;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import com.cheng.common.lang.Result;
import com.cheng.entity.Blog;
import com.cheng.service.BlogService;
import com.cheng.utils.ShiroUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * @Description: BlogConsoleCtroller
 * @Author cheng
 * @Date: 2022/12/9 22:48
 * @Version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/admin/blog")
@Api("admin: 博客管理模块")
public class BlogConsoleController {
    @Autowired
    BlogService blogService;
    /**
     * 编辑
     *
     * @param blog 博客
     * @return {@link Result}
     *///编辑
    @RequiresAuthentication
    @ApiOperation("发表、编辑博客api")
    @PostMapping("/edit")
    public Result edit(@Validated @RequestBody Blog blog) {
        Blog temp = null;
        if(blog.getId() != null) {//判别权限
            temp = blogService.getById(blog.getId());
            Assert.isTrue(temp.getUserId().equals(ShiroUtil.getProfile().getId()), "没有权限编辑");
        } else {
            temp = new Blog();
            temp.setUserId(ShiroUtil.getProfile().getId());
            temp.setCreated(LocalDateTime.now());
            temp.setStatus(0);
        }
        BeanUtil.copyProperties(blog, temp, "id", "userId", "created", "status");
        blogService.saveOrUpdate(temp);
        return Result.success(null);
    }

    /**
     * 删除
     *
     * @param id id
     * @return {@link Result}
     *///删除
    @RequiresAuthentication
    @ApiOperation("删除博客api")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id){
        System.out.println("删除博客id :" + id);
        Blog blog = blogService.getById(id);
        Assert.notNull(blog,"删除失败，博客不存在");

        blogService.removeById(id);
        return Result.success("删除成功");
    }
}
