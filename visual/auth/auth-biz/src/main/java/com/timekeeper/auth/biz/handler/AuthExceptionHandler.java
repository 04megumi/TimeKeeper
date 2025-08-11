package com.timekeeper.auth.biz.handler;

import com.timekeeper.auth.api.exception.AuthException;
import com.timekeeper.common.core.constant.BusinessCodes;
import com.timekeeper.common.core.util.R;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 认证模块全局异常处理器
 *
 * 统一处理认证相关的业务异常 {@link AuthException}，
 * 并封装成统一的接口返回格式 {@link R}。
 *
 *
 * @author 魏子越
 */
@RestControllerAdvice
public class AuthExceptionHandler {

    /**
     * 处理认证业务异常
     *
     * @param e 认证异常对象
     * @return 统一的错误响应结果
     */
    @ExceptionHandler(AuthException.class)
    public R<?> handleAuthException(AuthException e) {
        return R.failed(e.getCode(), e.getMessage());
    }
}
