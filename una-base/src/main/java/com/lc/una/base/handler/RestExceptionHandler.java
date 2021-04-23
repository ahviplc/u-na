package com.lc.una.base.handler;

import cn.hutool.log.StaticLog;
import com.lc.una.base.global.ErrorCode;
import com.lc.una.base.vo.Result;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author : LC
 * @description : RestApi参数校验异常处理
 * @date : 2019年12月4日22:48:18
 */
@ControllerAdvice
public class RestExceptionHandler {

    /**
     * 400错误
     *
     * @param ex
     * @return
     */
    @ExceptionHandler({HttpMessageNotReadableException.class})
    @ResponseBody
    public Result requestNotReadable(HttpMessageNotReadableException ex) {
        StaticLog.error("异常类 HttpMessageNotReadableException {},", ex.getMessage());
        return Result.createWithErrorMessage("参数异常", ErrorCode.PARAM_INCORRECT);
    }

    /**
     * 400错误
     *
     * @param ex
     * @return
     */
    @ExceptionHandler({TypeMismatchException.class})
    @ResponseBody
    public Result requestTypeMismatch(TypeMismatchException ex) {
        StaticLog.error("异常类 TypeMismatchException {},", ex.getMessage());
        return Result.createWithErrorMessage("参数异常", ErrorCode.PARAM_INCORRECT);
    }

    /**
     * 400错误
     *
     * @param ex
     * @return
     */
    @ExceptionHandler({MissingServletRequestParameterException.class})
    @ResponseBody
    public Result requestMissingServletRequest(MissingServletRequestParameterException ex) {
        StaticLog.error("异常类 MissingServletRequestParameterException {},", ex.getMessage());
        return Result.createWithErrorMessage("参数异常", ErrorCode.PARAM_INCORRECT);
    }

    /**
     * 405错误
     *
     * @return
     */
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    @ResponseBody
    public Result request405() {
        StaticLog.error("异常类 HttpRequestMethodNotSupportedException ");
        return Result.createWithErrorMessage("参数异常", ErrorCode.PARAM_INCORRECT);
    }

    /**
     * 415错误
     *
     * @return
     */
    @ExceptionHandler({HttpMediaTypeNotSupportedException.class})
    @ResponseBody
    public Result request415(HttpMediaTypeNotSupportedException ex) {
        StaticLog.error("异常类 HttpMediaTypeNotSupportedException {}", ex.getMessage());
        return Result.createWithErrorMessage("参数异常", ErrorCode.PARAM_INCORRECT);
    }
}
