package com.cheng.utils;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.cheng.shrio.AccountProfile;
import org.apache.shiro.SecurityUtils;

public class ShiroUtil {

    public static AccountProfile getProfile(){

        JSONObject jsonObject= JSONUtil.parseObj(SecurityUtils.getSubject().getPrincipal());

        return JSONUtil.toBean(jsonObject,AccountProfile.class) ;
    }

}
