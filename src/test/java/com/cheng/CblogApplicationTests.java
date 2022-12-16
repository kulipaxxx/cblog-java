package com.cheng;

import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cheng.entity.Notice;
import com.cheng.entity.role.Record;
import com.cheng.service.NoticeService;
import com.cheng.service.UserLikeService;
import com.cheng.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class CblogApplicationTests {

    @Test
    void contextLoads() {
        String str_pwd = "123456";
//e10adc3949ba59abbe56e057f20f883e
        String md5Hex1 = DigestUtil.md5Hex(str_pwd);
        String str_login_pwd = "123456";
//e10adc3949ba59abbe56e057f20f883e
        String md5Hex2 = DigestUtil.md5Hex(str_login_pwd);
//true
        System.out.println(md5Hex2);
        System.out.println(md5Hex1.equals(md5Hex2));
    }

    @Autowired
    UserService userService;

    @Autowired
    UserLikeService userLikeService;

    @Test
    void test1() {
        IPage iPage = userLikeService.findByLikedUserIdAndStatus("1", 0, 0);
        Object[] objects = iPage.getRecords().toArray();
        for (Object object : objects) {
            System.out.println(object.toString());
        }
    }

    @Test
    void testJoin() {
        List<Record> test = userService.getRoles(1L);

        //方法引用运算符
        test.forEach(System.out::println);

        for (Record record : test) {
            System.out.println(record.getString("sn"));
        }
    }

    @Autowired
    NoticeService noticeService;


    @Test
    void test2() {
        Notice id = noticeService.getById(2L);
        noticeService.removeById(1L);
        //System.out.println(byId.toString());
    }

}
