package com.timekeeper.auth.api.dto.impl;

import com.timekeeper.auth.api.dto.LoginRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * 手机号登录数据传输对象（DTO）
 *
 * 实现了 {@link LoginRequest} 接口，用于封装基于手机号和密码的登录请求参数。
 * 该类适用于用户使用手机号进行账号密码登录的场景。
 *
 * 字段说明：
 * - phoneNumber：登录手机号，不能为空，建议格式校验确保合法手机号
 * - password：用户密码，不能为空，建议传输过程加密保护
 *
 * 使用 Lombok 的 @Data 注解自动生成 getter/setter、toString、equals 和 hashCode 方法。
 *
 * @author 魏子越
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhoneLoginDTO implements LoginRequest {

    /**
     * 登录手机号
     */
    @NonNull
    private String phoneNum;

    /**
     * 用户密码
     */
    @NonNull
    private String password;
}
