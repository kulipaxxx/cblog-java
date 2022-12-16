package com.cheng.controller.additional;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cheng.common.lang.Result;
import com.cheng.entity.Notice;
import com.cheng.service.NoticeService;
import com.cheng.utils.ShiroUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * 注意到控制器
 * <p>
 * 公告 前端控制器
 * </p>
 *
 * @author author: cheng
 * @date 2022/12/16
 * @since 2022-12-16
 */
@RestController
@Slf4j
@RequestMapping("/api/notice")
public class NoticeController {

    @Autowired
    NoticeService noticeService;

    /**
     * 获取当前用户所有公告
     *
     * @param id          id
     * @param currentPage 当前页面
     * @return {@link Result}
     */
    @GetMapping("/getNotice/{id}/{currentPage}")
    public Result getNotice(@PathVariable Long id,@PathVariable Integer currentPage){
        log.info("用户id{},{}",id,currentPage);
        if (currentPage == null || currentPage == 1) currentPage=1;
        Notice notice = noticeService.getById(id);
        if (notice == null){
            return Result.success("没有公告");
        }
        Page page = new Page(currentPage, 7);
        IPage notices = noticeService.page(page, new QueryWrapper<Notice>().eq("userId", id).orderByDesc("created"));

        return Result.success(notices);
    }

    @GetMapping("/{id}")
    public Result getDetail(@PathVariable Long id){
        Notice notice = noticeService.getById(id);
        Assert.notNull(notice,"公告不存在");
        return Result.success(notice);
    }
    /**
     * 编辑或新建公告
     *
     * @param notice 请注意
     * @return {@link Result}
     */
    @PostMapping("/editNotice")
    public Result editNotice(@Validated @RequestBody Notice notice){
        log.info("公告信息：{}", notice.toString());
        Notice temp = null;
        if (notice.getId() != null){
            temp = noticeService.getById(notice.getId());
            temp.setUpdate_time(LocalDateTime.now());
            Assert.isTrue(temp.getUserId().equals(ShiroUtil.getProfile().getId()), "没有权限编辑");
        }else {
            temp = new Notice();
            temp.setUserId(ShiroUtil.getProfile().getId());
            temp.setCreated(LocalDateTime.now());
            temp.setStatus(0);
        }
        BeanUtil.copyProperties(notice, temp, "id", "userId", "created", "status");
        //noticeService.saveOrUpdate(temp);
        return Result.success("编辑成功");
    }

    @RequiresAuthentication
    @DeleteMapping("/{id}")
    public Result deleteNotice(@PathVariable Long id){
        System.out.println("删除公告id :" + id);
        Notice notice = noticeService.getById(id);
        Assert.notNull(notice,"删除失败，博客不存在");

        noticeService.removeById(id);
        return Result.success("删除成功");
    }
}
