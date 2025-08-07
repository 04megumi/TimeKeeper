package com.timekeeper.common.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 未授权异常
 *
 * <p>继承自 Spring Security 的 AuthenticationException，用于表示用户认证失败或无权限访问的异常情况。</p>
 * <p>此异常通常在认证流程中抛出，触发相应的异常处理机制。</p>
 *
 * @author 魏子越
 */
public class UnauthorizedException extends AuthenticationException {

    private static final long serialVersionUID = 1L;

    public UnauthorizedException(String msg) { super(msg); }
}
