package com.cheng.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
 * <p>
 *  前端控制器
 * </p>
 *
 * @author author: cheng
 * @since 2022-10-31
 */
@RestController
@Slf4j
@RequestMapping("/api/blog")
@Api(description = "系统：博客模块")
public class BlogController {
    @Autowired
    BlogService blogService;

    /**
     * 博客
     *
     * @param currentPage 当前页面
     * @return {@link Result}
     *///博客列表
    @ApiOperation("查询所有博客api")
    @GetMapping("/index")
    public Result blogs(Integer currentPage) {
        log.info("currentPage{}", currentPage);
        //分页
        if(currentPage == null || currentPage < 1) currentPage = 1;
        Page page = new Page(currentPage, 5);
        IPage pageData = blogService.page(page, new QueryWrapper<Blog>().orderByDesc("created"));

        return Result.success(pageData);
    }

    /**
     * 细节
     *
     * @param id id
     * @return {@link Result}
     */
    @ApiOperation("博客详情api")
    @GetMapping("/detail/{id}")
    public Result detail(@PathVariable(name = "id") Long id) {
        Blog blog = blogService.getById(id);
        Assert.notNull(blog, "该博客已删除！");
        return Result.success(blog);
    }

    /**
     * 博客用户
     *
     * @param id          id
     * @param currentPage 当前页面
     * @return {@link Result}
     */
    @ApiOperation("查询主页api")
    @GetMapping("/index/{id}")
    public Result blogsOfUser(@PathVariable(name = "id") Long id, Integer currentPage){
        if(currentPage == null || currentPage < 1) currentPage = 1;
        Page page = new Page(currentPage, 5);
        IPage pageData = blogService.page(page, new QueryWrapper<Blog>().eq("user_id", id).orderByDesc("created"));

        return Result.success(pageData);
    }

    /**
     * 编辑
     *
     * @param blog 博客
     * @return {@link Result}
     *///编辑
    @ApiOperation("发表、编辑博客api")
    @RequiresAuthentication
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
