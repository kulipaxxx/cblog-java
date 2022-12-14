package com.cheng.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cheng.common.dto.LoginDto;
import com.cheng.common.dto.RegisterDto;
import com.cheng.common.dto.pwdDto;
import com.cheng.common.lang.Result;
import com.cheng.entity.Notice;
import com.cheng.entity.User;
import com.cheng.service.NoticeService;
import com.cheng.service.UserService;
import com.cheng.service.additionalService.MailService;
import com.cheng.utils.JwtUtils;
import com.cheng.utils.RedisUtil;
import com.wf.captcha.ArithmeticCaptcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiresRoles("roles[admin]")
@Api(description = "系统：授权接口")
public class AccountController {
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    UserService userService;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    MailService mailService;
    @Autowired
    NoticeService noticeService;

    /**
     * 登录
     * 默认账号密码：cblog / 111111
     *
     * @param loginDto 登录dto
     * @param response 响应
     * @return {@link Result}
     */
    @ApiOperation("登录api")
    @CrossOrigin //运行跨越资源请求
    @PostMapping("/login")
    public Result login(@Validated @RequestBody LoginDto loginDto, HttpServletResponse response) {
        User user = userService.getOne(new QueryWrapper<User>().eq("username", loginDto.getUsername()));
        Assert.notNull(user, "用户不存在");
        if (!user.getPassword().equals(SecureUtil.md5(loginDto.getPassword()))) {
            return Result.error("密码或账号错误！");
        }
        String code = (String) redisUtil.get(loginDto.getUuid());
        Assert.notNull(code, "验证码已失效，请刷新验证码");
        Assert.isTrue(code.equals(loginDto.getCode()), "验证码错误");

        //更新登录时间
        user.setLastLogin(LocalDateTime.now());
        userService.update(user, new QueryWrapper<User>().eq("username", loginDto.getUsername()));
        //获取有效公告
        Notice notice = noticeService.getOne(new QueryWrapper<Notice>().eq("user_id", user.getId()).eq("status", 1));

        //获取当前用户id作为shrio主id
        String jwt = jwtUtils.generateToken(user.getId());
        //new UsernamePasswordToken()
        log.info("jwt{}", jwt);
        response.setHeader("Authorization", jwt);
        response.setHeader("Access-Control-Expose-Headers", "Authorization");

        if (notice != null)
            // 用户可以另一个接口
            return Result.success(MapUtil.builder()
                    .put("id", user.getId())
                    .put("username", user.getUsername())
                    .put("avatar", user.getAvatar())
                    .put("email", user.getEmail())
                    .put("gender", user.getGender())
                    .put("age", user.getAge())
                    .put("password", user.getPassword())
                    .put("title", notice.getTitle())
                    .put("notice", notice.getContent())
                    .map()
            );
        else
            return Result.success(MapUtil.builder()
                    .put("id", user.getId())
                    .put("username", user.getUsername())
                    .put("avatar", user.getAvatar())
                    .put("email", user.getEmail())
                    .put("gender", user.getGender())
                    .put("age", user.getAge())
                    .put("password", user.getPassword())
                    .map()
            );
    }

    /**
     * 注销
     *
     * @return {@link Result}
     */// 退出
    //@RequiresAuthentication
    @ApiOperation("退出api")
    @PostMapping("/logout")
    public Result logout() {
        SecurityUtils.getSubject().logout();

        return Result.success(null);
    }

    /**
     * 注册
     *
     * @param registerDto 注册dto
     * @return {@link Result}
     * @throws Exception 异常
     */
    @ApiOperation("注册api")
    @PostMapping("/register")
    public Result register(@Validated @RequestBody RegisterDto registerDto) throws Exception {
        log.info("注册模块：用户信息：{}", registerDto.toString());

        User temp = null;
        if (registerDto.getUsername() != null) {//判别是否重名
            temp = userService.getOne(new QueryWrapper<User>().eq("username", registerDto.getUsername()));
            Assert.isNull(temp, "用户已存在");
            //User email = userService.getOne(new QueryWrapper<User>().eq("email", registerDto.getEmail()));
            //Assert.isNull(email,"该邮箱已被注册");
        }
        String code = (String) redisUtil.get(registerDto.getUuid());
        Assert.notNull(code, "验证码已失效，请刷新验证码");
        Assert.isTrue(redisUtil.get(registerDto.getUuid()).equals(registerDto.getCode()), "验证码错误");

        temp = new User();
        temp.setCreated(LocalDateTime.now());
        temp.setStatus(0);
        temp.setPassword(DigestUtil.md5Hex(registerDto.getPassword()));
        temp.setUsername(registerDto.getUsername());
        temp.setEmail(registerDto.getEmail());
        //设置普通用户权限
        temp.setRoleId(1L);
        userService.save(temp);
        //异步发送注册成功邮件
        mailService.sendHtmlMail(registerDto.getEmail(), "注册成功", registerDto);

        return Result.success("注册成功");
    }

    /**
     * 验证码
     *
     * @return {@link Result}
     *///获取验证码的请求路径
    @ApiOperation("获取验证码api")
    @GetMapping("/code")
    public Result captcha() {

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

        log.info("===============获取运算结果为=========:{}", result);

        String key = UUID.randomUUID().toString();
        //存入redis缓存,过期时间两分钟
        log.info("===============获取运算结果为=========:{}", key);
        redisUtil.set(key, result, 2);
        Map<String, Object> map = new HashMap<>();
        map.put("key", key);
        map.put("img", captcha.toBase64());

        return Result.success(map);
    }

    /**
     * 得到密码
     * 找回密码
     *
     * @param pwdDto pwd dto
     * @return {@link Result}
     */
    @PostMapping("/getPassword")
    public Result getPassword(@Validated @RequestBody pwdDto pwdDto) throws Exception {
        String code = (String) redisUtil.get(pwdDto.getUuid());
        Assert.notNull(code, "验证码已失效，请刷新验证码");
        Assert.isTrue(redisUtil.get(pwdDto.getUuid()).equals(pwdDto.getCode()), "验证码错误");
        User email = userService.getOne(new QueryWrapper<User>().eq("username", pwdDto.getUsername()));
        Assert.notNull(email, "用户不存在");
        Assert.isTrue(pwdDto.getEmail().equals(email.getEmail()),"邮箱地址与该用户不匹配");
        BeanUtil.copyProperties(pwdDto,email,"id","avatar","status","created"
                ,"lastLogin","gender","age","roleId");
        //密码加密
        email.setPassword(SecureUtil.md5(pwdDto.getPassword()));
        userService.update(email,new QueryWrapper<User>().eq("username", pwdDto.getUsername()));
        System.out.println("user: " +  email.toString());
        mailService.getPassword(pwdDto);
        return Result.success("邮件已发送");
    }

}
