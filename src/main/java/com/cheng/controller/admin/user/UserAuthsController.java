package com.cheng.controller.admin.user;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户身份验证控制器
 *
 * @Description: UserAuthsController
 * @Author cheng
 * @Date: 2022/12/8 22:21
 * @Version 1.0
 * @date 2022/12/08
 */
@Slf4j
@RestController
@RequestMapping("/admin/user/Auths")
@Api("admin: 用户管理模块")
public class UserAuthsController {
    /**
     * 权限管理 判断有无权限访问 通过shrio实现
     *  1.判断有无权限
     *  1.赋予权限
     *  2.
     */
}
