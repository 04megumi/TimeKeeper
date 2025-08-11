package com.timekeeper.auth.api.exception;

import com.timekeeper.common.core.constant.BusinessCodes;
import com.timekeeper.common.core.exception.BusinessException;
import lombok.Getter;

/**
 * 认证相关业务异常类
 *
 * 继承自 {@link BusinessException}，用于封装认证模块中的特定业务异常。
 * 该异常携带特定错误码（100901），方便前端或调用方识别异常类型并做出相应处理。
 *
 * 示例场景：
 * - 用户名不存在
 * - 密码错误
 * - 账户被锁定等认证失败情况
 *
 * @author 魏子越
 */
@Getter
public class AuthException extends BusinessException {

    /**
     * 认证异常错误
     */
    private final int code;

    /**
     * 构造方法，传入异常信息
     * @param message 异常描述信息
     */
    public AuthException(String message) {
        super(message);
        this.code = BusinessCodes.AUTH_ERROR;
    }

}