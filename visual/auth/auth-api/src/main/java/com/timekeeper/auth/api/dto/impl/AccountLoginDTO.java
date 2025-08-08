package com.timekeeper.auth.api.dto.impl;

import com.timekeeper.auth.api.dto.LoginRequest;
import lombok.Data;
import lombok.NonNull;

/**
 * 账户登录数据传输对象（DTO）
 *
 * 实现了 {@link LoginRequest} 接口，用于封装基于用户名和密码的登录请求参数。
 * 该类用于 Web 或其他平台的账号密码登录场景。
 *
 * 字段说明：
 * - userNameEn：登录用户名，不能为空
 * - password：用户密码，不能为空，建议在传输时进行加密或通过 HTTPS 保护安全
 *
 * 通过 Lombok 的 @Data 自动生成 getter/setter、toString、equals 和 hashCode 方法。
 * @author 魏子越
 */
@Data
public class AccountLoginDTO implements LoginRequest {

    /**
     * 登录用户名
     */
    @NonNull
    private String userNameEn;

    /**
     * 用户密码
     */
    @NonNull
    private String password;
}