package com.mama.common.aop;

import com.mama.common.exception.SystemException;
import com.mama.common.result.basicResult.JsonView;
import com.mama.common.result.basicResult.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @Auther: cjw
 * @Date: 2018/7/30 17:23
 * @Description:统一处理异常
 */
@RestControllerAdvice
@Slf4j
public class ExceptionHandler {


    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @org.springframework.web.bind.annotation.ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseResult handleHttpMessageNotReadableException(HttpMessageNotReadableException e, HttpServletRequest request) {
        log.error(request.getRequestURI() + ":参数解析失败",e);
        return JsonView.fail(e);
    }

    /**
     * 405 - Method Not Allowed
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @org.springframework.web.bind.annotation.ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseResult handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e, HttpServletRequest request) {
        log.error(request.getRequestURI() + ":不支持当前请求方法",e);
        return JsonView.fail(e);
    }

    /**
     * 415 - Unsupported Media Type
     */
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @org.springframework.web.bind.annotation.ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseResult handleHttpMediaTypeNotSupportedException(Exception e, HttpServletRequest request) {
        log.error(request.getRequestURI() + ":不支持当前媒体类型",e);
        return JsonView.fail(e);
    }

    /**
     * 500 - Internal Server Error
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResponseResult handleException(Exception e, HttpServletRequest request) {
        log.error(request.getRequestURI() + "运行时异常");
        return JsonView.fail(e);

    }


    /**
     * 500 - Internal Server Error
     */
    @ResponseStatus(HttpStatus.OK)
    @org.springframework.web.bind.annotation.ExceptionHandler(SystemException.class)
    public ResponseResult handleException(SystemException e, HttpServletRequest request) {
        log.error(request.getRequestURI() + "自定义运行时异常");
        return JsonView.fail(e);

    }
}
