package com.cheng.controller.admin.user;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/admin/user/Role")
@Api("admin: 用户管理模块")
public class UserRoleController {

    /**
     *  角色管理 判断有无权限访问shrio
     *  1.获取所有角色
     *  2.更改编辑角色
     *  3.新加角色
     *  4.删除角色
     */


}
