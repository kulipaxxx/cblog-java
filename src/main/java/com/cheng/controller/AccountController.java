package com.cheng.controller;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cheng.common.dto.LoginDto;
import com.cheng.common.lang.Result;
import com.cheng.entity.User;
import com.cheng.service.UserService;
import com.cheng.utils.JwtUtils;
import com.cheng.utils.RedisUtil;
import com.wf.captcha.ArithmeticCaptcha;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class AccountController {
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    UserService userService;
    @Autowired
    RedisUtil redisUtil;

    /**
     * 默认账号密码：cblog / 111111
     */
    @CrossOrigin
    @PostMapping("/login")
    public Result login(@Validated @RequestBody LoginDto loginDto, HttpServletResponse response
            , HttpServletRequest request) {
        User user = userService.getOne(new QueryWrapper<User>().eq("username", loginDto.getUsername()));
        Assert.notNull(user, "用户不存在");
        if (!user.getPassword().equals(SecureUtil.md5(loginDto.getPassword()))) {
            return Result.fail("密码错误！");
        }
//        else if (!request.getSession().getAttribute("vrifyCode").equals(loginDto.getVrifyCode())) {
//            String msg = "验证码错误";
//            return Result.fail(msg);
//        }
        String jwt = jwtUtils.generateToken(user.getId());
        response.setHeader("Authorization", jwt);
        response.setHeader("Access-Control-Expose-Headers", "Authorization");
        // 用户可以另一个接口
        return Result.succ(MapUtil.builder()
                .put("id", user.getId())
                .put("username", user.getUsername())
                .put("avatar", user.getAvatar())
                .put("email", user.getEmail())
                .map()
        );
    }

    // 退出
    @PostMapping("/logout")
    @RequiresAuthentication
    public Result logout() {
        SecurityUtils.getSubject().logout();

        return Result.succ(null);
    }

    @PostMapping("/register")
    public Result register(@Validated @RequestBody LoginDto loginDto) {
        System.out.println(loginDto.toString());
        User temp = null;
        if (loginDto.getUsername() != null) {//判别是否重名
            temp = userService.getOne(new QueryWrapper<User>().eq("username", loginDto.getUsername()));
            Assert.isNull(temp, "用户已存在");
        }
        temp = new User();
        temp.setCreated(LocalDateTime.now());
        temp.setStatus(0);
        temp.setPassword(DigestUtil.md5Hex(loginDto.getPassword()));
        temp.setUsername(loginDto.getUsername());
        userService.save(temp);

        return Result.succ("注册成功");
    }

    //获取验证码的请求路径
    @GetMapping("/code")
    public Result captcha(){

        //算术类型
        ArithmeticCaptcha captcha = new ArithmeticCaptcha();

        //中文类型验证吗
        //ChineseCaptcha captcha = new ChineseCaptcha();

        // 英文与数字验证码
        // SpecCaptcha captcha = new SpecCaptcha();

        //英文与数字动态验证码
        //GifCaptcha captcha = new GifCaptcha();

        //中文动态验证码
        //ChineseGifCaptcha captcha = new ChineseGifCaptcha();
        //几位数运算   默认是两位
        captcha.setLen(2);

        //获取运算结果
        String result = captcha.text();

        log.info("===============获取运算结果为=========:{}",result);

        String key = UUID.randomUUID().toString();
        redisUtil.set(key,result,2);
        Map<String,Object> map = new HashMap<>();
        map.put("key", key);
        map.put("img", captcha.toBase64());

        return Result.succ(map);
    }
}
