package com.xxx.rent.api.util;

import java.io.Serializable;
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 处理状态
     */
    private boolean flag;
    /**
     * 响应码
     */
    private Integer code;
    /**
     * 响应消息
     */
    private String message;
    /**
     * 数据
     */
    private T data;

    public Result(boolean flag, Integer code, String message, T data) {
        this.flag = flag;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Result(boolean flag, Integer code, String message) {
        this.flag = flag;
        this.code = code;
        this.message = message;
        this.data = null;
    }

    public Result() {
        this.flag = true;
        this.code = StatusCode.OK;
        this.message = "操作成功!";
    }

    public Result(ResultCode resultCode) {
        this.message = resultCode.getDesc();
        this.code = resultCode.getCode();
        this.flag = this.code == 200;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static Result<String> responseError(ResultCode code) {

        return new Result<String>(code);
    }

    public static Result<String> responseError(int code, String message) {
        return new Result(false, code, message);
    }

    public static <T> Result<T> responseSuccess(String msg) {
        return new Result<T>(true, ResultCode.OK.getCode(), msg);
    }

    public static <T> Result<T> responseSuccess(T data) {
        return new Result<T>(true, ResultCode.OK.getCode(), "ok", data);
    }

    public static Result<Object> redirect(Integer code, String url, String msg) {
        return new Result<>(false, code, msg, url);
    }

    public static <T> Result<T> responseSuccess(String msg, T data) {
        return new Result<T>(true, ResultCode.OK.getCode(), msg, data);
    }

    public static <T> Result<T> responseSuccess(Integer code, String msg, T data) {
        return new Result<T>(true, code, msg, data);
    }

    public static <T> Result<T> responseSuccess() {
        return new Result<T>(true, ResultCode.OK.getCode(), "请求成功");
    }
}
