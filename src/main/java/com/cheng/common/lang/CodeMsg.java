package com.cheng.common.lang;

public class CodeMsg {
    private int code;
    private String message;

    public CodeMsg(int code,String message){
        this.code = code;
        this.message = message;
    }

    public static CodeMsg SUCCESS = new CodeMsg(0,"SUCCESS");
    public static CodeMsg SERVER_ERROR = new CodeMsg(500100,"服务端异常");

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
