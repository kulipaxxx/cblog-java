package com.cheng.controller.additional;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cheng.common.dto.LikeDto;
import com.cheng.common.lang.Result;
import com.cheng.entity.Blog;
import com.cheng.entity.UserLike;
import com.cheng.service.BlogService;
import com.cheng.service.LikedService;
import com.cheng.service.RedisService;
import com.cheng.service.UserLikeService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户点赞表 前端控制器
 * </p>
 *
 * @author author: cheng
 * @since 2022-11-19
 */

@Slf4j
@RestController
@RequestMapping("/api/like")
@Api(description = "点赞模块")
public class UserLikeController {

    @Resource
    RedisService redisService;

    @Autowired
    LikedService likedService;

    @Autowired
    UserLikeService userLikeService;

    @Autowired
    BlogService blogService;
    /**
     * 得到clickl
     * 获取当前博客点赞量
     *
     * @param id id
     * @return {@link Result}
     */
    @GetMapping("/getClickL/{id}")
    public Result getClickL(@PathVariable String id){
        /**
         * 1.先查缓存
         * 2.缓存没有查数据库
         */
        Integer count = redisService.getLikedCount(id);
        log.info("博客："+ id + "点赞总数" + count);
        if (count == null){ //如果缓存没有查数据库
            Blog blog = blogService.getOne(new QueryWrapper<Blog>().eq("id", id));
            log.info("查询出来的blogLikeCount:" + blog.getLikeCount());
            count = blog.getLikeCount();
        }
        if (count == null)
            count = 0;
        else if (count < 0)
            count = 0;
        return Result.success(MapUtil.builder()
                .put("count",count)
                .map());
    }

    /**
     * 点击喜欢
     * 点赞业务
     *
     * @param likeDto 像dto
     * @return {@link Result}
     */
    @PostMapping("/clickL")
    public Result clickLike(@Validated @RequestBody LikeDto likeDto){
        String likeBlogId = likeDto.getLikedBlogId();
        String giveLikedId = likeDto.getGiveLikedId();
        log.info("点赞信息" + likeDto.toString());
        if (likeDto.getStatus() == 1){//是否点赞
            redisService.saveLiked2Redis(likeBlogId, giveLikedId);
            //点赞总数加一
            redisService.incrementLikedCount(likeBlogId);
        }else {
            //缓存 取消点赞
            redisService.unlikeFromRedis(likeBlogId, giveLikedId);
            //缓存删除点赞记录
            redisService.deleteLikedFromRedis(likeBlogId, giveLikedId);
            //缓存点赞总数减一
            redisService.decrementLikedCount(likeBlogId);
            //数据库更新
            UserLike userLike = new UserLike();
            BeanUtil.copyProperties(likeDto,userLike);
            userLike.setUpdateTime(LocalDateTime.now());
            System.out.println("更新点赞状态：" + userLike.toString());
            userLikeService.update(userLike,new QueryWrapper<UserLike>().eq("liked_blog_id",likeBlogId).eq("give_liked_id",giveLikedId));
        }
        return Result.success();
    }


    /**
     * 喜欢关系
     *
     * @param blogId 博客id
     * @param userId 用户id
     * @return {@link Result}
     */
    @GetMapping("/likeRelationships/{blogId}/{userId}")
    public Result likeRelationships(@PathVariable String blogId, @PathVariable String userId){
        boolean status = false;
        /*
        1.查询是否存在点赞关系
        2.若存在则设状态为true
         */
        UserLike userLike = likedService.getByLikedUserIdAndGiveLikedId(blogId, userId);
        if (userLike != null)
            status = true;
        log.info("点赞状态:" + status);
        return Result.success(status);
    }

}
