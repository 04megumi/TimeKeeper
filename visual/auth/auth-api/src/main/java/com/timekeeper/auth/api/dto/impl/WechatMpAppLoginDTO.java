package com.timekeeper.auth.api.dto.impl;

import com.timekeeper.auth.api.dto.LoginRequest;
import lombok.Data;
import lombok.NonNull;

/**
 * 微信小程序登录数据传输对象（DTO）
 *
 * 实现了 {@link LoginRequest} 接口，用于封装微信小程序登录时传递的参数。
 * 该类包含微信小程序登录凭证 openid，前端通过调用 wx.login() 获取。
 *
 * 字段说明：
 * - openid：微信小程序的唯一用户标识，不能为空。
 *
 * 使用 Lombok 的 @Data 注解自动生成 getter/setter、toString、equals 和 hashCode 方法。
 *
 * 注意：
 * - openid 通常需通过微信服务端接口换取，结合后端进行用户身份验证。
 *
 * @author 魏子越
 */
@Data
public class WechatMpAppLoginDTO implements LoginRequest {

    /**
     * 微信小程序登录凭证 code（前端调用 wx.login() 获取）
     */
    @NonNull
    private String openid;
}
