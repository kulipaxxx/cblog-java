package com.cheng.common.lang;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("统一结果返回")
public class Result implements Serializable {

    @ApiModelProperty("访问状态")
    private int code; // 200是正常，非200表示异常
    @ApiModelProperty("返回信息")
    private String msg;
    @ApiModelProperty("返回数据")
    private Object data;

    public static Result succ(Object data) {
        return succ(ResultCode.SUCCESS, "操作成功", data);
    }

    public static Result succ(String msg){
        return succ(ResultCode.SUCCESS,msg,null);
    }

    public static Result succ(int code, String msg, Object data) {
        Result r = new Result();
        r.setCode(code);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }

    public static Result fail(String msg) {
        return fail(ResultCode.FAILED, msg, null);
    }

    public static Result fail(String msg, Object data) {
        return fail(ResultCode.FAILED, msg, data);
    }

    public static Result fail(int code, String msg, Object data) {
        Result r = new Result();
        r.setCode(code);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }

}
