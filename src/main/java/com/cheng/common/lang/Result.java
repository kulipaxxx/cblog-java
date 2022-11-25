package com.cheng.common.lang;

import com.cheng.common.lang.Enum.ResponseCode;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("统一结果封装")
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;
    // 响应代码
    private int code;
    // 响应信息
    private String msg;
    // 响应数据
    private T data;

    /**
     * 构造方法 私有不允许外部访问
     */
    private Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
    private Result(int code, T data) {
        this.code = code;
        this.data = data;
    }
    private Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    private Result(int code) {
        this.code = code;
    }

    /**
     * 请求成功
     * @param msg  返回信息
     * @param data 泛型数据
     * @param <T>  返回数据，可以不填
     * @return 1.状态码（默认） 2.返回信息 3.泛型数据
     */
    public static <T> Result<T> success(String msg, T data) {
        return new Result<T>(ResponseCode.SUCCESS.getCode(), msg, data);
    }
    public static <T> Result<T> success(T data) {
        return new Result<T>(ResponseCode.SUCCESS.getCode(), data);
    }
    public static <T> Result<T> success(String msg) {
        return new Result<T>(ResponseCode.SUCCESS.getCode(), msg);
    }
    public static <T> Result<T> success() {
        return new Result<>(ResponseCode.SUCCESS.getCode());
    }

    /**
     * 请求失败
     * @param code
     * @param msg
     * @return 1.状态码（自定义） 2.返回信息（自定义）
     */
    public static <T> Result<T> error(int code, String msg) {
        return new Result<>(code, msg);
    }

    public static <T> Result<T> error() {
        return new Result<T>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getDesc());
    }

    public static <T> Result<T> error(String msg) {
        return new Result<T>(ResponseCode.ERROR.getCode(), msg);
    }

}
