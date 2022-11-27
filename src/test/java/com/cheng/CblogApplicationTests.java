package com.cheng;

import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cheng.mapper.UserMapper;
import com.cheng.service.UserLikeService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class CblogApplicationTests {

    @Test
    void contextLoads() {
        String str_pwd="123456";
//e10adc3949ba59abbe56e057f20f883e
        String md5Hex1 = DigestUtil.md5Hex(str_pwd);
        String str_login_pwd="123456";
//e10adc3949ba59abbe56e057f20f883e
        String md5Hex2 = DigestUtil.md5Hex(str_login_pwd);
//true
        System.out.println(md5Hex2);
        System.out.println(md5Hex1.equals(md5Hex2));
    }

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserLikeService userLikeService;

    @Test
    void test1(){
        IPage iPage = userLikeService.findByLikedUserIdAndStatus("1", 0, 0);
        Object[] objects = iPage.getRecords().toArray();
        for (Object object : objects) {
            System.out.println(object.toString());
        }
    }


}
