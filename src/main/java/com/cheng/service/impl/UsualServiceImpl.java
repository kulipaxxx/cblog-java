package com.cheng.service.impl;

import com.cheng.common.dto.ForumDTO;
import com.cheng.common.lang.DataResponse;
import com.cheng.common.lang.ResponseEnum;
import com.cheng.service.UsualService;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.LinkedList;
import java.util.List;

@Service
public class UsualServiceImpl implements UsualService {

    private static final String DEFAULT_VALUE = "0:0:0:0:0:0";
    public static final Byte BYTE_ZERO = 0;
    public static final Byte BYTE_ONE = 1;
    public static final Byte BYTE_TWO = 2;
    public static final Byte BYTE_THREE = 3;
    public static final Byte BYTE_FOUR = 4;
    public static final Byte BYTE_FIVE = 5;
    public static final Byte BYTE_SIX = 6;

    @Autowired
    RedisTemplate redisTemplate;


    @Override
    public DataResponse keepNum(ForumDTO forumDTO) {
        //将帖子id 设置为 key
        String key = forumDTO.getPostId().toString();
        //get 用户id
        String userId = forumDTO.getUserId();
        String count, newCount;
        //绑定数据集key
        BoundHashOperations<String, Object, Object> post = redisTemplate.boundHashOps("post:");
        //获取hKey
        // count: 0论坛-点赞量  1评论量 2收藏量 3浏览 4评论-点赞量
        if (null == post.get(key)) {
            //无则set
            post.put(key, DEFAULT_VALUE);
            //再取出来赋值给 count
            count = post.get(key).toString();
        } else {
            //有直接赋值 count
            count = post.get(key).toString();
        }
        // operationType 1 浏览 2 帖子点赞 3  收藏  4评论-点赞
        String prefix;
        switch (forumDTO.getOperationType()) {
            case 1:
                //记录浏览次数 OPERATIONTYPE 1 : 记录浏览次数
                newCount = resetValue(count, BYTE_THREE, true);
                post.put(key, newCount);
                break;
            case 2:
                //记录帖子-点赞
                prefix = "thumbs:post";
                switch (forumDTO.getClickType()) {
                    case 0:
                        /**
                         *  OPERATIONTYPE 2: + CLICKTYPE 0 = 给帖子点赞
                         * 0点赞
                         * 从redis中获取数量  帖子d 例如:177488r88t78r78r7
                         *  count: 0论坛-点赞量  1评论量 2收藏量 3浏览 4评论-点赞量
                         * 避免每种数量都去查询redis 直接通过 redis value 记录所有的数量
                         * 获取加 +1 后的值
                         */
                        if (redisTemplate.opsForSet().isMember(prefix + ":" + key, prefix + ":" + userId)) {
                            return DataResponse.fail("不能重复点赞哦");
                        } else {
                            redisTemplate.opsForSet().add(prefix + ":" + key, prefix + ":" + userId);
                        }
                        newCount = resetValue(count, BYTE_ZERO, true);
                        //set to redis
                        post.put(key, newCount);
                        break;
                    case 1:
                        //OPERATIONTYPE 2: + CLICKTYPE 1 = 取消帖子点赞
                        //1取消帖子点赞
                        if (!redisTemplate.opsForSet().isMember(prefix + ":" + key, prefix + ":" + userId)) {
                            //重复处理
                            return DataResponse.fail("不能重复取消哦");
                        } else {
                            //删除
                            redisTemplate.opsForSet().remove(prefix + ":" + key, prefix + ":" + userId);
                        }
                        newCount = resetValue(count, BYTE_ZERO, false);
                        post.put(key, newCount);
                        break;
                }
                break;
            case 3:
                prefix = "collection:post";
                List<Message> sendList = new LinkedList<>();
//                Message mqMessage = new Message();
                switch (forumDTO.getClickType()) {
                    //OPERATIONTYPE 3 + CLICKTYPE 0 = 记录收藏
                    case 0:
                        //数量+1
                        //根据用户id + 帖子id 查询redis 数据
                        if (redisTemplate.opsForSet().isMember(prefix + ":" + key, prefix + ":" + userId)) {
                            //重复处理
                            return DataResponse.fail("不能重复收藏哦");
                        }
                        //add
                        redisTemplate.opsForSet().add(prefix + ":" + key, prefix + ":" + userId);
                        //set to redis
//                        newCount = resetValue(count, BYTE_TWO, true);
//                        post.put(key, newCount);
//                        mqMessage.setType(new Byte("9"));
//                        mqMessage.setSenderId(userId);
//                        mqMessage.setPostId(forumDTO.getPostId());
//                        sendList.add(mqMessage);
//                        this.sendMq.send(sendList);
                        break;
                    //OPERATIONTYPE 3 + CLICKTYPE 1 = 取消收藏
                    case 1:
                        //取消收藏
                        //尝试从redis取出当前用户是否已经收藏
                        if (!redisTemplate.opsForSet().isMember(prefix + ":" + key, prefix + ":" + userId)) {
                            //重复处理
                            return DataResponse.fail("不能重复取消哦");
                        }
                        //删除
                        redisTemplate.opsForSet().remove(prefix + ":" + key, prefix + ":" + userId);
//                        newCount = resetValue(count, BYTE_TWO, false);
//                        post.put(key, newCount);
//                        mqMessage.setType(new Byte("10"));
//                        mqMessage.setSenderId(userId);
//                        mqMessage.setPostId(forumDTO.getPostId());
//                        sendList.add(mqMessage);
//                        this.sendMq.send(sendList);
                        break;
                }
                break;
            case 4:
                //记录评论-点赞
                // OPERATIONTYPE 4: + CLICKTYPE 0 = 给评论点赞
                if (null == forumDTO.getCommentId()) {
                    return DataResponse.fail("评论id不能为空");
                }
                String commentNum, ckey = forumDTO.getCommentId().toString();
                BoundHashOperations<String, Object, Object> comment = redisTemplate.boundHashOps("post:comment");
                if (null == comment.get(ckey)) {
                    //无则set
                    comment.put(ckey, "0");
                    //再取出来赋值给 count
                    commentNum = comment.get(ckey).toString();
                } else {
                    //有直接赋值 count
                    commentNum = comment.get(ckey).toString();
                }
                //赞评论
                prefix = "thumbs:comment";
                switch (forumDTO.getClickType()) {
                    case 0:
                        /**
                         * 0点赞
                         * 从redis中获取数量  帖子d 例如:177488r88t78r78r7
                         *  count: 0论坛-点赞量  1评论量 2收藏量 3浏览 4评论-点赞量
                         * 避免每种数量都去查询redis 直接通过 redis value 记录所有的数量
                         * 获取加 + 后的值
                         */
                        if (redisTemplate.opsForSet().isMember(prefix + ":" + ckey, prefix + ":" + userId)) {
                            return DataResponse.fail("不能重复点赞哦");
                        } else {
                            redisTemplate.opsForSet().add(prefix + ":" + ckey, prefix + ":" + userId);
                        }
                        //set to redis
                        comment.put(ckey, cResetValue(commentNum, true));
                        break;
                    case 1:
                        //1取消评论点赞
                        if (!redisTemplate.opsForSet().isMember(prefix + ":" + ckey, prefix + ":" + userId)) {
                            //重复处理
                            return DataResponse.fail("不能重复取消哦");
                        } else {
                            //删除
                            redisTemplate.opsForSet().remove(prefix + ":" + ckey, prefix + ":" + userId);
                        }
                        newCount = cResetValue(commentNum, false);
                        comment.put(ckey, newCount);
                        break;
                }
                break;
            default:
                DataResponse.fail(ResponseEnum.FAILED);
        }
        return DataResponse.success(ResponseEnum.SUCCESS);
    }

    /**
     * 功能描述: <br>
     * 〈点赞数、收藏数等数量重置〉
     * @param val    数组
     * @param j   0帖子点赞量  1评论量 2收藏量 3浏览 4评论点赞量
     * @param isPlus 是否增加数量 true +   false -
     * @Return: java.lang.String
     * @Author:王震
     * @Date: 2020/8/5 10:27
     * StringUtils包：import org.apache.commons.lang3.StringUtils;
     * 可以使用jdk的包替代split方法；但jdk的包需要验证正则，效率较低。
     */
    private String resetValue(String val, int j, boolean isPlus) {
        String[] value = StringUtils.split(val, ":");
        Long temp = Long.valueOf(value[j]);
        StringBuffer sb = new StringBuffer(16);
        if (isPlus) {
            temp += 1;
        } else {
            temp -= 1;
        }
        value[j] = temp.toString();
        for (int i = 0, len = value.length; i < len; i++) {
            if (i != len - 1) {
                sb.append(value[i]).append(":");
            }else {
                sb.append(value[i]);
            }
        }
        return sb.toString();
    }

    private String cResetValue(String count, boolean b) {
        Long c = Long.parseLong(count);
        if (b) {
            c += 1;
        } else {
            c -= 1;
        }
        return c.toString();
    }


}
