package com.timekeeper.auth.biz.controller;

import com.timekeeper.auth.api.dto.impl.AccountLoginDTO;
import com.timekeeper.auth.api.dto.impl.PhoneLoginDTO;
import com.timekeeper.auth.api.dto.impl.WechatMpAppLoginDTO;
import com.timekeeper.auth.api.vo.Token;
import com.timekeeper.auth.biz.constant.Platform;
import com.timekeeper.auth.biz.converter.TokenConverter;
import com.timekeeper.auth.biz.user.service.AuthUserDetailsService;
import com.timekeeper.common.core.util.R;
import lombok.NonNull;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录控制器，提供多种登录方式的接口：
 * 1. 微信小程序登录（使用微信授权的openid）
 * 2. 账号密码登录（用户名 + 密码）
 * 3. 手机号登录（手机号 + 密码）
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    @NonNull
    private final AuthUserDetailsService userServiceImpl;

    public LoginController(AuthUserDetailsService userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    /**
     /**
     * 微信小程序登录接口
     * <p>
     * 接口 URL: POST /login/wx
     * 请求体参数见 {@link WechatMpAppLoginDTO}
     * <p>
     * 请求体示例:
     * {
     *   "openid": "微信登录凭证 code，前端 wx.login() 获取"
     * }
     * <p>
     * 返回:
     * 成功：
     * {
     *   "code": 100000,
     *   "data": {
     *     "token": "JWT字符串"
     *   },
     *   "msg": "success"
     * }
     * <p>
     * 失败：
     * {
     *   "code": 100901,
     *   "data": null,
     *   "msg": "错误原因描述"
     * }
     * </p>
     */
    @PostMapping(value = "/wx")
    public R<Token> loginWX(@NonNull @RequestBody WechatMpAppLoginDTO loginRequest) {
        return R.ok(TokenConverter.INSTANCE.toToken(userServiceImpl.login(Platform.WX, loginRequest)));
    }

    /**
     * 账号密码登录接口
     * <p>
     * 接口 URL: POST /login/account
     * 请求体参数见 {@link AccountLoginDTO}
     * <p>
     * 请求体示例:
     * {
     *   "userNameEn": "用户名",
     *   "password": "密码"
     * }
     * <p>
     * 返回:
     * 成功：
     * {
     *   "code": 100000,
     *   "data": {
     *     "token": "JWT字符串"
     *   },
     *   "msg": "success"
     * }
     * <p>
     * 失败：
     * {
     *   "code": 100901,
     *   "data": null,
     *   "msg": "错误原因描述"
     * }
     * </p>
     */
    @PostMapping(value = "/account")
    public R<Token> loginAccount(@NonNull @RequestBody AccountLoginDTO loginRequest) {
        return R.ok(TokenConverter.INSTANCE.toToken(userServiceImpl.login(Platform.NAME, loginRequest)));
    }

    /**
     * 手机号登录接口
     * <p>
     * 接口 URL: POST /login/phone
     * 请求体参数见 {@link PhoneLoginDTO}
     * <p>
     * 请求体示例:
     * {
     *   "phoneNum": "手机号",
     *   "password": "密码"
     * }
     * <p>
     * 返回:
     * 成功：
     * {
     *   "code": 100000,
     *   "data": {
     *     "token": "JWT字符串"
     *   },
     *   "msg": "success"
     * }
     * <p>
     * 失败：
     * {
     *   "code": 100901,
     *   "data": null,
     *   "msg": "错误原因描述"
     * }
     * </p>
     */
    @PostMapping(value = "/phone")
    public R<Token> loginPhone(@NonNull @RequestBody PhoneLoginDTO loginRequest) {
        return R.ok(TokenConverter.INSTANCE.toToken(userServiceImpl.login(Platform.PHONE, loginRequest)));
    }
}
