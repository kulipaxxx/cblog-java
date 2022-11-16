package com.cheng.controller;

import com.cheng.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UsualController {

    @Autowired
    RedisUtil redisUtil;

//    @RequestMapping(value = "/like",method = RequestMethod.POST)
//    @ResponseBody
//    public String like(int entityType,int entityId){
//        User user = hostHolder.getUser();
//        // 实现点赞
//        likeService.like(user.getId(),entityType,entityId);
//        // 获取点赞的数量
//        long likeCount = likeService.findEntityLikeCount(entityType,entityId);
//        // 获取点赞的状态
//        int likeStatus = likeService.findEntityLikeStatus(user.getId(),entityType,entityId);
//        // 返回的结果
//        Map<String ,Object> map = new HashMap<>();
//        map.put("likeCount",likeCount);
//        map.put("likeStatus",likeStatus);
//        return CommunityUtil.getJSONString(0,null,map);
//    }

}
