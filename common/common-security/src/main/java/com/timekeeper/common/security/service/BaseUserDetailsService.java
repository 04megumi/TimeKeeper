package com.timekeeper.common.security.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * BaseUserDetailsService 接口
 *
 * <p>
 * 该接口用于自定义用户认证服务，是 Spring Security 中 {@link org.springframework.security.core.userdetails.UserDetailsService}
 * 的拓展形式，支持返回项目中定义的 {@link BaseUserDetails} 类型，携带更多业务相关信息（如用户ID、角色ID、权限ID等）。
 * </p>
 *
 * <p>
 * 实现该接口的类，通常用于在认证时（如账号密码登录、JWT 登录）根据用户名加载用户信息，并作为用户上下文写入 Spring Security。
 * </p>
 *
 * <p>
 * 建议在你的认证模块（如 admin、auth）中实现该接口，并注入 Spring 容器用于安全配置类中。
 * </p>
 *
 * 示例用途：
 * - 数据库认证：根据用户名从数据库查询用户及其权限信息
 * - 第三方认证：根据用户名从其他系统获取用户数据
 * </p>
 *
 * @author 魏子越
 */
public interface BaseUserDetailsService {

    /**
     * 根据用户名获取用户信息
     *
     * @param username the username identifying the user whose data is required.
     * @return BaseUserDetails
     */
    BaseUserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
