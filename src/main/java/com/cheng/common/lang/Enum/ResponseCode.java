package com.cheng.common.lang.Enum;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;

@ApiModel("响应集")
public enum ResponseCode implements Serializable {


    // 成功返回
    SUCCESS(200, "SUCCESS"),
    // 执行错误
    ERROR(400, "ERROR"),
    // 参数错误
    PERMISSIONS_ARGUMENT(403, "PERMISSIONS_ARGUMENT");
    private static final long serialVersionUID = 1L;
    private final int code;
    private final String desc;

    ResponseCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
