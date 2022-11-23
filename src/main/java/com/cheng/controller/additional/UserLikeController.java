package com.cheng.controller.additional;


import com.cheng.common.dto.LikeDto;
import com.cheng.common.lang.Result;
import com.cheng.service.LikedService;
import com.cheng.service.RedisService;
import com.cheng.service.UserLikeService;
import io.swagger.annotations.Api;
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
@RestController
@RequestMapping("/api")
@Api(description = "点赞模块")
public class UserLikeController {

    @Autowired
    RedisService redisService;

    @Autowired
    LikedService likedService;

    @Autowired
    UserLikeService userLikeService;

    /**
     * 获取当前博客点赞量
     * @param blog_id
     * @return
     */
    @GetMapping("/like/getClickL")
    public Result getClickL(String blog_id){
        /**
         * 1.先查缓存
         * 2.缓存没有查数据库
         */
        System.out.println(blog_id);
        Integer count = redisService.getLikedCount(blog_id);
        System.out.println("博客：" + blog_id + "点赞总数" + count);

        return Result.succ(count);
    }

    /**
     * 点赞业务
     * @param likeDto
     * @return
     */
    @PostMapping("/like/clickL")
    public Result clickLike(@Validated @RequestBody LikeDto likeDto){
        String likedUserId = likeDto.getLikedUserId();
        String giveLikedId = likeDto.getGiveLikedId();
        System.out.println(likeDto.toString());
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

        return Result.succ(null);
    }




}
