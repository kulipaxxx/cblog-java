package com.cheng.controller.additional;


import cn.hutool.core.map.MapUtil;
import com.cheng.common.dto.LikeDto;
import com.cheng.common.lang.Result;
import com.cheng.entity.UserLike;
import com.cheng.service.LikedService;
import com.cheng.service.RedisService;
import com.cheng.service.UserLikeService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    RedisService redisService;

    @Autowired
    LikedService likedService;

    @Autowired
    UserLikeService userLikeService;

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
        if (count == null){

        }
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
        String likedUserId = likeDto.getLikedBlogId();
        String giveLikedId = likeDto.getGiveLikedId();
        log.info("点赞信息" + likeDto.toString());
        if (likeDto.getStatus() == 1){//是否点赞
            redisService.saveLiked2Redis(likedUserId, giveLikedId);
            //点赞总数加一
            redisService.incrementLikedCount(likedUserId);
        }else {//取消点赞
            redisService.unlikeFromRedis(likedUserId, giveLikedId);
            redisService.deleteLikedFromRedis(likedUserId, giveLikedId);//删除点赞记录
            //点赞总数减一
            redisService.decrementLikedCount(likedUserId);
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
        System.out.println("信息" + blogId + "  " + userId);
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
