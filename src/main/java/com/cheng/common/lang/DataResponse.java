package com.cheng.common.lang;


import lombok.Data;

@Data
public final class DataResponse<T> {

    private boolean success;

    private T data;


    public DataResponse<T> setData(T data) {
        this.data = data;
        return this;
    }

    private Integer code;

    private String message;

    public DataResponse<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public DataResponse() {
    }

    public DataResponse(boolean success, T data, Integer code, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
        this.success = success;
    }


    public static <T> DataResponse<T> success(String message) {
        return new DataResponse<T>(true, null, ResponseEnum.SUCCESS.getStatus(), message);
    }

    public static <T> DataResponse<T> success() {
        return new DataResponse<>(true, null, ResponseEnum.SUCCESS.getStatus(), ResponseEnum.SUCCESS.getMessage());
    }

    public static <T> DataResponse<T> success(ResponseEnum responseEnum) {
        return new DataResponse<>(true, null, responseEnum.getStatus(), responseEnum.getMessage());
    }

    public static <T> DataResponse<T> success(T data) {
        return new DataResponse<>(true, data, ResponseEnum.SUCCESS.getStatus(), ResponseEnum.SUCCESS.getMessage());
    }

    public static <T> DataResponse<T> success(ResponseEnum responseEnum, T data) {
        return new DataResponse<>(true, data, responseEnum.getStatus(), responseEnum.getMessage());
    }

    public static <T> DataResponse<T> success(ResponseEnum responseEnum, T data, Object... args) {
        String s = String.format(responseEnum.getMessage(), args);
        return new DataResponse<>(true, data, responseEnum.getStatus(), s);
    }

    public static <T> DataResponse<T> fail() {
        return new DataResponse<>(false, null, ResponseEnum.FAILED.getStatus(), ResponseEnum.FAILED.getMessage());
    }

    public static <T> DataResponse<T> fail(ResponseEnum responseEnum) {
        return new DataResponse<>(false, null, responseEnum.getStatus(), responseEnum.getMessage());
    }

    public static <T> DataResponse<T> fail(T data) {
        return new DataResponse<>(false, data, ResponseEnum.FAILED.getStatus(), ResponseEnum.FAILED.getMessage());
    }

    public static <T> DataResponse<T> fail(String message) {
        return new DataResponse<>(false, null, ResponseEnum.FAILED.getStatus(), message);
    }

    public static <T> DataResponse<T> fail(Integer code, String message) {
        return new DataResponse<>(false, null, code, message);
    }

    public static <T> DataResponse<T> fail(ResponseEnum responseEnum, T data) {
        return new DataResponse<>(false, data, responseEnum.getStatus(), responseEnum.getMessage());
    }

    public static <T> DataResponse<T> fail(ResponseEnum responseEnum, T data, Object... args) {
        String s = String.format(responseEnum.getMessage(), args);
        return new DataResponse<>(false, data, responseEnum.getStatus(), s);
    }

}
