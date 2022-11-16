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
//    public ResultVO clickPraise(HttpServletRequest request, @RequestBody String param) {
//        if (JSON.parseObject(param).containsKey("newsId")) {
//            if (JSON.parseObject(param).containsKey("status")) {
//                RegAlumniuser user = sysUserUtil.getUser(request);
//                String newsId = JSON.parseObject(param).getString("newsId");//新闻主键id
//                String status = JSON.parseObject(param).getString("status");//点赞状态
//                if (status.equals("1")) {//点赞状态为1 ，点赞
//                    boolean b = redisUtil.hasKey("like_news" + newsId);
//                    if (b == false) {//不存在，需新建
//                        redisUtil.sSet("like_news" + newsId, newsId + "::" + user.getRegAlumniuserid());
//                    }
//                    redisUtil.sSetAndTime("like_news" + newsId, 3000, newsId + "::" + user.getRegAlumniuserid());//存在则向缓存中添加
//                } else {//取消点赞
//                    redisUtil.setRemove("like_news" + newsId, newsId + "::" + user.getRegAlumniuserid());
//                }
//                boolean b = redisUtil.sHasKey("like_news" + newsId, newsId + "::" + user.getRegAlumniuserid());//判断value是否还在redis中
//                if (b == true) {
//                    return new ResultVO(ResultCode.SUCCESS, 1);//点赞成功
//                }
//                return new ResultVO(ResultCode.SUCCESS, 0);//无点赞
//            }
//            return new ResultVO(ResultCode.FAILED, "请传入参数");
//        }
//        return new ResultVO(ResultCode.FAILED, "请传入参数");
//    }

}
