package com.cheng.controller;


import com.cheng.common.lang.Result;
import com.cheng.entity.User;
import com.cheng.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author author: cheng
 * @since 2022-10-31
 */
@RestController
@RequestMapping("/user")
@Api(description = "用户模块")
public class UserController {

    @Autowired
    UserService userService;

    @RequiresAuthentication
    @GetMapping("/index")
    public Result index() {
        User user = userService.getById(1L);
        return Result.succ(user);
    }

    //编辑用户信息
    @ApiOperation("保存用户信息api")
    @RequiresAuthentication //要求权限
    @PostMapping("/save")
    public Result save(@Validated @RequestBody User user) {

        return Result.succ(user);
    }
}
