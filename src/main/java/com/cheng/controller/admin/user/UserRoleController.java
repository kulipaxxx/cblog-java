package com.cheng.controller.admin.user;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cheng.common.dto.RoleDto;
import com.cheng.common.lang.Result;
import com.cheng.entity.User;
import com.cheng.service.UserService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户角色控制器
 * 用户控制台控制器
 *
 * @Description: UserConsoleController
 * @Author cheng
 * @Date: 2022/11/25 21:55
 * @Version 1.0
 * @date 2022/11/25
 */
@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/admin/user/role")
@Api("admin: 用户管理模块")
public class UserRoleController {

    @Autowired
    UserService userService;
    /**
     *  角色管理 判断有无权限访问shiro
     *  1.获取所有角色
     *  2.更改编辑角色
     *  3.新加角色
     *  4.删除角色
     */


    /**
     * 得到所有用户
     *
     * @return {@link Result}
     */
    @RequiresRoles("admin")
    @GetMapping("/getRoles")
    public Result getAllUsers() {
        List<User> users = userService.list();
        Assert.notNull(users, "没有用户存在");
        return Result.success(users);
    }

    /**
     * 管理员才有权限更新shiro验证
     * 编辑角色
     * 编辑，新添角色
     *
     * @param user 用户
     * @return {@link Result}
     */
    @RequiresRoles("admin")
    @PostMapping("/edit")
    public Result editRoles(@Validated @RequestBody RoleDto user) {
        Assert.notNull(user, "不能传空表");
        log.info("Role修改信息:{}", user.toString());
        User temp = userService.getById(user.getId());
        Assert.notNull(temp, "用户不存在");

        userService.update(temp,new QueryWrapper<User>().eq("id", user.getId()));
        return Result.success("修改成功");
    }

    /**
     * 删除角色
     *
     * @param id id
     * @return {@link Result}
     */
    @RequiresRoles("admin")
    @DeleteMapping("{id}")
    public Result deleteRoles(@PathVariable long id){
        User user = userService.getById(id);
        Assert.notNull(user,"用户不存在");
        userService.removeById(id);
        return Result.success("删除成功");
    }

}
