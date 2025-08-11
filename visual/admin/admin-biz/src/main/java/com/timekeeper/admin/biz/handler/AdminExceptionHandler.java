package com.timekeeper.admin.biz.handler;

import com.timekeeper.admin.api.exception.AdminException;
import com.timekeeper.common.core.constant.BusinessCodes;
import com.timekeeper.common.core.util.R;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 认证模块全局异常处理器
 *
 * 统一处理认证相关的业务异常 {@link AdminException}，
 * 并封装成统一的接口返回格式 {@link R}。
 *
 *
 * @author 魏子越
 */
@RestControllerAdvice
public class AdminExceptionHandler {

    /**
     * 处理认证业务异常
     *
     * @param e 认证异常对象
     * @return 统一的错误响应结果
     */
    @ExceptionHandler(value = AdminException.class)
    R<?> handleAdminException(AdminException e) { return R.failed(BusinessCodes.ADMIN_ERROR, e.getMessage()); }
}
