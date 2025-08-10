package com.timekeeper.auth.api.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 当前登录用户的基本信息 VO（View Object）
 * <p>
 * 用于向前端返回用户的基本展示信息。
 * 仅包含必要的非敏感字段，例如姓名和英文名。
 * </p>
 *
 * @author 魏子越
 * @since 2025-08-09
 */
@Data
@AllArgsConstructor
public class MeVO {

    /**
     * 用户中文名（昵称或真实姓名）
     */
    private String name;

    /**
     * 用户英文名（系统唯一标识用）
     */
    private String nameEn;
}