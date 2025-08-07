package com.timekeeper.common.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.PatternMatchUtils;
import java.util.List;

/**
 * 扩展自 Spring Security 的 UserDetails 接口，用于增加项目自定义的用户信息字段和行为判断逻辑。
 * <p>
 * 该接口作为业务系统中用户身份的统一描述方式，在认证成功后被存入 SecurityContext 中。
 * 可以用于权限校验、角色判断等安全控制逻辑。
 * </p>
 *
 * 实现类可以配合自定义 UserDetailsService 使用，如 JWT 或数据库登录场景。
 *
 * @author 魏子越
 */
public interface BaseUserDetails extends UserDetails {

    /**
     * 获取用户ID
     *
     * @return Integer
     */
    Integer getId();

    /**
     * 获取用户rtx
     *
     * @return String
     */
    String getUserNameEn();

    /**
     * 获取用户的权限ID列表
     *
     * @return List<String>
     */
    List<String> getPermissionIds();

    /**
     * 获取用户的角色ID列表
     *
     * @return List<String>
     */
    List<String> getRoleIds();

    /**
     * 是否超级管理员
     *
     * @return boolean
     */
    default boolean isRoot() {
        return getRoleIds().stream().anyMatch(x -> PatternMatchUtils.simpleMatch("ROOT", x));
    }

    /**
     * 是否含有这些角色
     *
     * @param roleIds String[]
     * @return boolean
     */
    default boolean hasRole(String... roleIds) {
        return getRoleIds().stream().anyMatch(x -> PatternMatchUtils.simpleMatch(roleIds, x));
    }
}
