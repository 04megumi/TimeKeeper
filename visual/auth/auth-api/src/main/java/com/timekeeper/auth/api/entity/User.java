package com.timekeeper.auth.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;
import com.timekeeper.common.data.mybatis.model.BaseModel;
import com.timekeeper.common.security.service.BaseUserDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 用户表实体
 * 包含登录相关字段和审计字段，继承BaseModel实现通用字段管理
 *
 * @author 魏子越
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("user")
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseModel<User> implements BaseUserDetails {

    private static final long serialVersionUID = 1L;

    /**
     * 用户唯一ID（主键，UUID或雪花ID，建议String类型）
     */
    @TableId(type = IdType.ASSIGN_UUID)
    @TableField(value = "uid")
    private String uid;

    /**
     * 用户名（登录账号或昵称，可能中文）
     */
    @TableField(value = "name")
    private String name;

    /**
     * 用户名英文版（唯一，用于系统内部标识）
     */
    @TableField(value = "name_en")
    private String nameEn;

    /**
     * 手机号（唯一）
     */
    @TableField(value = "phone_num")
    private String phoneNumber;

    /**
     * 密码（存储加密后的hash值）
     */
    @TableField(value = "password")
    private String password;

    /**
     * 微信小程序OpenId（唯一，用于小程序登录）
     */
    @TableField(value = "openid")
    private String openId;

    @Override
    public String getId() { return uid; }

    @Override
    public String getUserNameEn() { return this.nameEn; }

    @Override
    public String getUserPhoneNumber() { return this.phoneNumber; }

    @Override
    public List<String> getPermissionIds() { return new ArrayList<>(); }

    @Override
    public List<String> getRoleIds() { return new ArrayList<>(); }

    @Override
    public boolean isRoot() { return false; }

    @Override
    public boolean hasRole(String... roleIds) { return false; }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() { return this.name; }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.isDeleted != 1;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.isDeleted != 1;
    }
}