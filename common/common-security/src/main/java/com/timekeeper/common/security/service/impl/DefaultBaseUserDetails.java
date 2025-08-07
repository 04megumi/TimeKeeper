package com.timekeeper.common.security.service.impl;

import com.timekeeper.common.security.service.BaseUserDetails;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 默认的 BaseUserDetails 实现类，用于提供一个占位或兜底用户详情结构。
 * 实际项目中应提供自定义实现（从数据库加载用户、角色、权限等）。
 *
 * 该类配合 @ConditionalOnMissingBean 通常作为默认 Bean 存在。
 *
 * @author 魏子越
 */
@Data
public class DefaultBaseUserDetails implements BaseUserDetails {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private String userName;
    private String userNameEn;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public String getUserNameEn() {
        return userNameEn;
    }

    @Override
    public List<String> getPermissionIds() {
        return new ArrayList<>();
    }

    @Override
    public List<String> getRoleIds() {
        return new ArrayList<>();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
