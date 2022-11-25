package com.cheng.shrio;

import org.apache.shiro.authc.AuthenticationToken;

public class JwtToken implements AuthenticationToken {
    private String token;
    public JwtToken(String token) {
        this.token = token;
    }

    /**
     * 得到主
     *
     * @return {@link Object}
     */
    @Override
    public Object getPrincipal() {
        return token;
    }

    /**
     * 获得证书
     *
     * @return {@link Object}
     */
    @Override
    public Object getCredentials() {
        return token;
    }
}
