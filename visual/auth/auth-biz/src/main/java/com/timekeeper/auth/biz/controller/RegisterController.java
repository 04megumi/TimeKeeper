package com.timekeeper.auth.biz.controller;

import com.timekeeper.auth.api.dto.LoginRequest;
import com.timekeeper.auth.api.dto.impl.AccountLoginDTO;
import com.timekeeper.auth.api.dto.impl.PhoneLoginDTO;
import com.timekeeper.auth.api.dto.impl.WechatMpAppLoginDTO;
import com.timekeeper.auth.biz.constant.Platform;
import com.timekeeper.auth.biz.user.service.AuthUserDetailsService;
import com.timekeeper.common.core.util.R;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
public class RegisterController {

    @NonNull
    private final AuthUserDetailsService userServiceImpl;

    public RegisterController(AuthUserDetailsService userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    /**
     * 微信小程序注册接口
     * <p>
     * 接口 URL: POST /register/wx
     * 请求体参数见 {@link com.timekeeper.auth.api.dto.impl.WechatMpAppLoginDTO}
     * <p>
     * 请求体示例:
     * {
     *   "openid": "微信小程序登录凭证 code，前端 wx.login() 获取"
     * }
     * <p>
     * 返回:
     * 成功：
     * {
     *   "code": 100000,
     *   "data": null,
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
    public R<Object> registerWX(@NonNull @RequestBody WechatMpAppLoginDTO loginRequest) {
        userServiceImpl.register(Platform.WX, loginRequest);
        return R.ok();
    }

    /**
     * 手机号注册接口
     * <p>
     * 接口 URL: POST /register/phone
     * 请求体参数见 {@link com.timekeeper.auth.api.dto.impl.PhoneLoginDTO}
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
     *   "data": null,
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
    public R<Object> registerPhone(@NonNull @RequestBody PhoneLoginDTO loginRequest) {
        userServiceImpl.register(Platform.PHONE, loginRequest);
        return R.ok();
    }

    /**
     * 账号密码注册接口
     * <p>
     * 接口 URL: POST /register/account
     * 请求体参数见 {@link com.timekeeper.auth.api.dto.impl.AccountLoginDTO}
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
     *   "data": null,
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
    public R<Object> registerAccount(@NonNull @RequestBody AccountLoginDTO loginRequest) {
        userServiceImpl.register(Platform.NAME, loginRequest);
        return R.ok();
    }
}
