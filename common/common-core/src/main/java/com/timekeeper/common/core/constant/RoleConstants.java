package com.timekeeper.common.core.constant;

/**
 * 系统内角色常量定义
 * <p>
 * 用于统一管理和引用系统中的角色标识，避免在代码中出现硬编码字符串。
 * 通常配合权限控制（如 Spring Security / Sa-Token）使用。
 * </p>
 *
 * <ul>
 *   <li>{@link #ADMIN}   - 管理员角色，拥有最高权限</li>
 *   <li>{@link #STUDENT} - 学生角色，主要用于普通学员功能</li>
 *   <li>{@link #TEACHER} - 教师角色，负责教学和课程管理</li>
 * </ul>
 *
 * <p>使用建议：
 *   <pre>
 *   if (RoleConstants.ADMIN.equals(user.getRole())) {
 *       // 执行管理员逻辑
 *   }
 *   </pre>
 * </p>
 *
 * @author 魏子越
 */
public interface RoleConstants {

    /** 管理员角色标识（系统最高权限） */
    String ADMIN = "admin";

    /** 学生角色标识（普通用户） */
    String STUDENT = "student";

    /** 教师角色标识（教学人员） */
    String TEACHER = "teacher";
}