package com.cheng.common.lang;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result1<T> implements Serializable {

    private int code; // 200是正常，非200表示异常
    private String msg;
    private T data;

    //构造方法私有化，不允许外部new
    private Result1(T data){
        this.code = 0;
        this.msg = "SUCCESS";
        this.data = data;
    }

    private Result1(CodeMsg codeMsg){
        if (codeMsg==null) {
            return;
        }
        this.code = codeMsg.getCode();
        this.msg = codeMsg.getMessage();
    }

    //成功时调用
    public static <T> Result1<T> succ(T data) {
        return new Result1<T>(data);
    }

    //失败时调用
    public static <T> Result1<T> error(CodeMsg codeMsg) {
        return new Result1<T>(codeMsg);
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
