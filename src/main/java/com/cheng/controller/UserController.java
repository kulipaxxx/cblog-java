package com.cheng.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cheng.common.dto.UserDto;
import com.cheng.common.lang.Result;
import com.cheng.entity.User;
import com.cheng.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@RequestMapping("/api/user")
@Api(description = "系统：用户模块")
public class UserController {

    @Autowired
    UserService userService;

    /**
     * 指数
     *
     * @return {@link Result}
     */
    @RequiresAuthentication
    @GetMapping("/{id}")
    public Result index(@PathVariable Long id) {
        User user = userService.getById(id);
        return Result.success(user);
    }

    /**
     * 保存
     *
     * @param userDto 用户dto
     * @return {@link Result}
     */ //编辑用户信息
    @ApiOperation("保存用户信息api")
    @RequiresAuthentication //要求权限
    @PostMapping("/save")
    public Result save(@Validated @RequestBody UserDto userDto) {
        log.info("用户修改之后的信息:{}", userDto.toString());
        User user = userService.getById(userDto.getId());
        BeanUtil.copyProperties(userDto,user);
        //log.info(user.toString());
        userService.update(user,new QueryWrapper<User>().eq("id",user.getId()));
        return Result.success(user);
    }
}
