package com.cheng.shrio;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * jwt令牌
 *
 * @author Administrator
 * @date 2022/11/25
 */
public class JwtToken implements AuthenticationToken {
    private String token;
    public JwtToken(String token) {
        this.token = token;
    }

    /**
     * Principal 身份信息：
     *
     * @return {@link Object}
     */
    @Override
    public Object getPrincipal() {
        return token;
    }

    /**
     * Credential 凭证信息
     *
     * @return {@link Object}
     */
    @Override
    public Object getCredentials() {
        return token;
    }
}
