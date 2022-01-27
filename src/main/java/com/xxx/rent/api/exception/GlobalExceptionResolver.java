package com.xxx.rent.api.exception;

import com.xxx.rent.api.util.Result;
import com.xxx.rent.api.util.ThrowableUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionResolver {
    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionResolver.class);

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseBody
    public Object handleMissingServletRequestParameterException(HttpMediaTypeNotSupportedException e) {
        return Result.responseError(101, "不支持的数据类型[" + e.getContentType() + "]");
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    public Object handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        return Result.responseError(101, "缺少必要参数[" + e.getParameterName() + "]");
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public Object handleTokenException(HttpMessageNotReadableException e) {
        return new Result(false, 500, "请输入合法参数", null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public Object handleNotValidException(MethodArgumentNotValidException e) {
        return new Result(false, 500, e.getBindingResult().getFieldError().getDefaultMessage());
    }

    @ExceptionHandler(BizException.class)
    @ResponseBody
    public Object handleBizException(BizException e) {
        LOG.error("{}", ThrowableUtil.getStackTrace(e));
        return Result.responseError(e.getErrCode(), e.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public Object handleHttpRequestMethodNotSupportedException(Exception exception) {
        return Result.responseError(500, "请求方式不支持");
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object handleException(Exception e) {
        LOG.error(e.getMessage(), e);
        return Result.responseError(500, "服务器无法响应！");
    }
}
