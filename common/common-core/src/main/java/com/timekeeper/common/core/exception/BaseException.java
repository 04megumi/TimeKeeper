package com.timekeeper.common.core.exception;

import lombok.Getter;

import java.io.Serializable;

/**
 * 异常基类
 *
 * @author 魏子越
 */
public class BaseException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 1L;

    @Getter
    protected int code;

    protected String message;

    public BaseException() {}

    public BaseException(String message) {
        super(message);
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getMessage() {
        return message;
    }
}
