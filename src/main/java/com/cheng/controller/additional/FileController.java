package com.cheng.controller.additional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cheng.common.lang.Result;
import com.cheng.controller.vo.FileVo;
import com.cheng.entity.Blog;
import com.cheng.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 关于页面
 *
 * @Description: AboutController
 * @Author cheng
 * @Date: 2022/11/25 22:36
 * @Version 1.0
 * @date 2022/11/25
 */
@RestController
@RequestMapping("/api/about")
public class FileController {

    @Autowired
    BlogService blogService;

    /**
     * 获取归档信息
     */
    @GetMapping("/{id}")
    public Result getFileInfo(@PathVariable("id") long userId) {
        List<Blog> blogs = blogService.list(new QueryWrapper<Blog>().eq("user_id", userId));
        ArrayList<FileVo> fileList = new ArrayList<>();

        for (Blog blog : blogs) {
            FileVo vo = new FileVo();
            vo.setId(blog.getId());
            vo.setTitle(blog.getTitle());
            vo.setCreated(blog.getCreated());
            fileList.add(vo);
        }
        return Result.success(fileList);
    }
}
