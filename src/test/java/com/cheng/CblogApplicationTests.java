package com.cheng;

import cn.hutool.crypto.digest.DigestUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

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

}
