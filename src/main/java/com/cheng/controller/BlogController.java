package com.cheng.controller;


import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cheng.common.lang.Result;
import com.cheng.entity.Blog;
import com.cheng.service.BlogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
