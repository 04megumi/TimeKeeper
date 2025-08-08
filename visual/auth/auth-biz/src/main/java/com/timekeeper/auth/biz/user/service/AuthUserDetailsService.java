package com.timekeeper.auth.biz.user.service;

import com.timekeeper.auth.api.dto.LoginRequest;
import com.timekeeper.auth.api.exception.AuthException;
import com.timekeeper.auth.biz.constant.Platform;
import com.timekeeper.common.security.service.BaseUserDetails;
import com.timekeeper.common.security.service.BaseUserDetailsService;

/**
 * 用户认证服务接口
 *
 * 继承自 BaseUserDetailsService，扩展了用户登录和注册相关的功能。
 * 主要负责根据不同登录平台（如微信、手机号、账号密码）处理登录和注册操作，
 * 并返回相应的用户详情或登录凭证。
 */
public interface AuthUserDetailsService extends BaseUserDetailsService {

    /**
     * 根据指定登录平台和登录请求信息进行用户登录操作
     *
     * @param platform     登录平台枚举（如微信、手机号、用户名等）
     * @param loginRequest 登录请求参数，包含必要的认证信息
     * @return 登录成功后返回的令牌字符串（如 JWT Token）
     * @throws AuthException 当登录失败（例如认证信息错误）时抛出异常
     */
    String login(Platform platform, LoginRequest loginRequest);

    /**
     * 根据指定登录平台和注册请求信息进行用户注册操作
     *
     * @param platform     注册的平台枚举（如微信、手机号、用户名等）
     * @param loginRequest 注册请求参数，包含注册所需信息
     * @return 注册成功后返回的用户详情对象
     * @throws AuthException 当注册失败（例如用户已存在）时抛出异常
     */
    void register(Platform platform, LoginRequest loginRequest);
}