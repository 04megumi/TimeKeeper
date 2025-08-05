package com.timekeeper.common.core.exception;

/**
 * 业务异常类
 *
 * @author 魏子越
 */
public class BusinessException extends BaseException {

    private static final long serialVersionUID = 1L;

    public BusinessException(int code) { this.code = code; }
    public BusinessException(String message) { this.message = message; }
    public BusinessException(int code, String message) { this.code = code; this.message = message; }
}
