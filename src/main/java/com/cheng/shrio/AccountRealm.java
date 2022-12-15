package com.cheng.shrio;

import cn.hutool.core.bean.BeanUtil;
import com.cheng.entity.User;
import com.cheng.entity.role.Record;
import com.cheng.service.UserService;
import com.cheng.utils.JwtUtils;
import com.cheng.utils.ShiroUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;

@Slf4j
@Component
public class AccountRealm extends AuthorizingRealm {
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    UserService userService;

    /**
     * 支持
     *
     * @param token 令牌
     * @return boolean
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 赋予权限信息
     *
     * @param principals 校长
     * @return {@link AuthorizationInfo}
     *///授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //获取用户的用户名
        Long id = ShiroUtil.getProfile().getId();

        List<Record> rolesRecord = userService.getRoles(id);
        HashSet<String> roles = new HashSet<>();
        rolesRecord.forEach(sn -> {
            roles.add(sn.getString("sn"));
        });
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setRoles(roles);
        //info.setStringPermissions(ps);

        return info;
    }

    /**
     * 做得到身份验证信息
     *
     * @param token 令牌
     * @return {@link AuthenticationInfo}
     * @throws AuthenticationException 身份验证异常
     *///认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        JwtToken jwt = (JwtToken) token;
        log.info("jwt----------------->{}", jwt);
        String userId = jwtUtils.getClaimByToken((String) jwt.getPrincipal()).getSubject();
        User user = userService.getById(Long.parseLong(userId));

        if(user == null) {
            throw new UnknownAccountException("账户不存在！");
        }
        if(user.getStatus() == -1) {
            throw new LockedAccountException("账户已被锁定！");
        }
        AccountProfile profile = new AccountProfile();
        BeanUtil.copyProperties(user, profile);
        log.info("profile----------------->{}", profile.toString());
        return new SimpleAuthenticationInfo(profile, jwt.getCredentials(), getName());
    }
}
