package com.cheng.controller.admin.user;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import com.cheng.common.lang.Result;
import com.cheng.entity.User;
import com.cheng.service.UserService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
    @PostMapping("/edit")
    public Result editRoles(@Validated @RequestBody User user) {
        User temp = null;
        if (user.getId() != null) {//存在用户，进行编辑更新操作
            temp = userService.getById(user.getId());
        } else {//不存在，进行新增操作
            temp = new User();
            temp.setCreated(LocalDateTime.now());
            temp.setStatus(0);
        }
        BeanUtil.copyProperties(user, temp, "id", "created", "status");
        userService.saveOrUpdate(temp);
        return Result.success(null);
    }

    /**
     * 删除角色
     *
     * @param id id
     * @return {@link Result}
     */
    @DeleteMapping("{id}")
    public Result deleteRoles(@PathVariable long id){
        User user = userService.getById(id);
        Assert.notNull(user,"用户不存在");
        userService.removeById(id);
        return Result.success("删除成功");
    }
    
}
