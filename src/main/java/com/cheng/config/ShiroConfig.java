package com.cheng.config;

import com.cheng.shrio.AccountRealm;

import com.cheng.shrio.JwtFilter;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Autowired
    JwtFilter jwtFilter;

    @Bean
    public SessionManager sessionManager(RedisSessionDAO redisSessionDAO) {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();

        // inject redisSessionDAO
        sessionManager.setSessionDAO(redisSessionDAO);
        return sessionManager;
    }

    //3.shiro的心脏，来管理shiro
    @Bean
    public DefaultWebSecurityManager securityManager(AccountRealm accountRealm,
                                                     SessionManager sessionManager,
                                                     RedisCacheManager redisCacheManager) {

        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager(accountRealm);

        //inject sessionManager
        securityManager.setSessionManager(sessionManager);

        // inject redisCacheManager
        securityManager.setCacheManager(redisCacheManager);
        return securityManager;
    }

    //2.管理权限
    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();

        Map<String, String> filterMap = new LinkedHashMap<>();


        //配置shiro的内置过滤器
        /*
            anon：匿名拦截器，不需要登录即可访问的资源，匿名用户或游客，一般用于过滤静态资源。
            authc：需要认证登录才能访问
            user：用户拦截器，表示必须存在用户
            perms：权限授权拦截器，验证用户是否拥有权限
                参数可写多个，表示需要某些权限才能通过，多个参数时写 perms[“user, admin”]，当有多个参数时必须每个参数都通过才算可以
            roles：角色授权拦截器，验证用户是或否拥有角色。
                   参数可写多个，表示某些角色才能通过，多个参数时写 roles[“admin,user”]，当有多个参数时必须每个参数都通过才算通过

         */
        filterMap.put("/admin/user/*", "roles[admin]");
        filterMap.put("/admin/blog/*", "roles[admin,user]");
        filterMap.put("/**", "jwt");// 主要通过注解方式校验权限

        chainDefinition.addPathDefinitions(filterMap);

        return chainDefinition;
    }

    //1.类似于AOP，执行相关操作前，先进行功能过滤，拦截所有请求，进入到shiro中进行认证与授权
    @Bean("shiroFilterFactoryBean")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager,
                                                         ShiroFilterChainDefinition shiroFilterChainDefinition) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        //设置安全管理器
        shiroFilter.setSecurityManager(securityManager);

        Map<String, Filter> filters = new HashMap<>();
        filters.put("jwt", jwtFilter);
        shiroFilter.setFilters(filters);

        Map<String, String> filterMap = shiroFilterChainDefinition.getFilterChainMap();

        shiroFilter.setFilterChainDefinitionMap(filterMap);

        shiroFilter.setUnauthorizedUrl("/unauth");

        return shiroFilter;
    }

}
