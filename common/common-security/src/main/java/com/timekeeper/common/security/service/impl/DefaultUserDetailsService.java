package com.timekeeper.common.security.service.impl;

import com.timekeeper.common.security.service.BaseUserDetails;
import com.timekeeper.common.security.service.BaseUserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


/**
 * 默认的 UserDetailsService 实现，用于提供用户认证信息。
 *
 * ⚠️ 该类为占位/模板实现，实际使用中请根据具体业务逻辑进行替换。
 * 你需要实现从数据库或其他用户服务中根据用户名查询用户信息，
 * 并封装成 {@link BaseUserDetails} 返回，用于 Spring Security 的认证流程。
 *
 * 示例用途：
 * - 在 admin 模块或 auth 模块中重写该接口，加载真实用户信息（如账号、密码、角色、权限等）。
 *
 * @author 魏子越
 */
public class DefaultUserDetailsService implements BaseUserDetailsService {

    @Override
    public BaseUserDetails loadUserByUid(String uid) throws UsernameNotFoundException {
        return new DefaultBaseUserDetails();
    }
}
