package com.timekeeper.auth.api.dto;

/**
 * 登录请求接口
 *
 * 该接口用于标记所有登录请求的数据传输对象（DTO）。
 * 不同登录平台（如Web、移动端、第三方等）可实现此接口，
 * 以统一登录方法的参数类型，便于多平台登录功能的扩展与维护。
 *
 * 具体登录请求参数应在实现类中定义。
 *
 * 示例：
 * - WebLoginRequest 实现此接口，包含用户名密码字段
 * - MobileLoginRequest 实现此接口，包含手机号验证码字段
 *
 * @author 魏子越
 */
public interface LoginRequest {
}
