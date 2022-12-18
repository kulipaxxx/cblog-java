package com.cheng.controller.admin.blog;


import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cheng.common.lang.Result;
import com.cheng.entity.About;
import com.cheng.service.AboutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author author: cheng
 * @since 2022-12-18
 */
@RestController
@RequestMapping("/admin/about")
public class AboutController {

    @Autowired
    AboutService aboutService;

    /**
     * 获取页面
     *
     * @param id id
     * @return {@link Result}
     */
    @GetMapping("/{id}")
    public Result getPage(@PathVariable Long id){
        About pageInfo = aboutService.getOne(new QueryWrapper<About>().eq("user_id", id));
        Assert.notNull(pageInfo,"还没有关于页面,快发布吧");
        return Result.success(pageInfo);
    }

    /**
     * 保存页面
     *
     * @return {@link Result}
     */
    @PostMapping("/save")
    public Result savePage(@Validated @RequestBody About about){
        aboutService.saveOrUpdate(about);

        return Result.success("更新成功");
    }
}
