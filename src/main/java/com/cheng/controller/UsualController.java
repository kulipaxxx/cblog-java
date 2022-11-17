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

//     @PostMapping("/clickPraise")
//    public Result clickPraise(HttpServletRequest request, @RequestBody String param) {
//        if (param.contains("newsId")) {
//            if (param.contains("status")) {
//                User user = new User();
//                String newsId = "newsId";//新闻主键id
//                Integer status = user.getStatus();//点赞状态
//                if (status.equals("1")) {//点赞状态为1 ，点赞
//                    boolean b = redisUtil.hasKey("like_news" + newsId);
//                    if (!b) {//不存在，需新建
//                        redisUtil.sSet("like_news" + newsId, newsId + "::" + user.getRegAlumniuserid());
//                    }
//                    redisUtil.sSetAndTime("like_news" + newsId, 3000, newsId + "::" + user.getRegAlumniuserid());//存在则向缓存中添加
//                } else {//取消点赞
//                    redisUtil.setRemove("like_news" + newsId, newsId + "::" + user.getRegAlumniuserid());
//                }
//                boolean b = redisUtil.sHasKey("like_news" + newsId, newsId + "::" + user.getRegAlumniuserid());//判断value是否还在redis中
//                if (b == true) {
//                    return Result.succ(1);//点赞成功
//                }
//                return Result.succ(0);//无点赞
//            }
//            return Result.fail("请传入参数");
//        }
//        return Result.fail( "请传入参数");
//    }

}

