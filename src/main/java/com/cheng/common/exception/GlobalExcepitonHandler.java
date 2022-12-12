package com.cheng.common.exception;

import com.cheng.common.lang.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

/**
 * 全局异常处理
 */
@Slf4j
@RestControllerAdvice
public class GlobalExcepitonHandler {

    /**
     * handle403
     *
     * @param e e
     * @return {@link Result}
     */
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(value = AuthorizationException.class)
    public Result handle403(AuthorizationException e) {
        log.error("权限异常：----------------{}", e.getCause());
        return Result.error(403, e.getMessage());
    }

    /**
     * handle401
     *
     * @param e e
     * @return {@link Result}
     */// 捕捉shiro的异常
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = ShiroException.class)
    public Result handle401(ShiroException e) {
        log.error("运行时异常：----------------{}", e.getCause());
        return Result.error(401, e.getMessage());
    }

    /**
     * 处理程序
     * 处理Assert的异常
     *
     * @param e e
     * @return {@link Result}
     * @throws IOException ioexception
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = IllegalArgumentException.class)
    public Result handler(IllegalArgumentException e) throws IOException {
        log.error("Assert异常:-------------->{}",e.getMessage());
        return Result.error(e.getMessage());
    }

    /**
     * 处理程序
     *
     * @param e e
     * @return {@link Result}
     * @throws IOException ioexception
     * @Validated 校验错误异常处理
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result handler(MethodArgumentNotValidException e) throws IOException {
        log.error("运行时异常:-------------->",e);
        BindingResult bindingResult = e.getBindingResult();
        ObjectError objectError = bindingResult.getAllErrors().stream().findFirst().get();
        return Result.error(objectError.getDefaultMessage());
    }

    /**
     * 处理程序
     *
     * @param e e
     * @return {@link Result}
     * @throws IOException ioexception
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = RuntimeException.class)
    public Result handler(RuntimeException e) throws IOException {
        log.error("运行时异常:-------------->",e);
        return Result.error(e.getMessage());
    }
}
