package com.timekeeper.admin.biz.service;

import com.timekeeper.common.security.service.BaseUserDetailsService;

/**
 * 管理端用户服务接口
 *
 * <p>该接口继承自 {@link BaseUserDetailsService}，用于实现 jwt 身份校验
 *
 * <p>主要职责：</p>
 * <ul>
 *   <li>根据 uid 加载用户信息</li>
 * </ul>
 *
 * @author 魏子越
 * @see com.timekeeper.common.security.service.BaseUserDetailsService
 */
public interface AdminUserService extends BaseUserDetailsService {

}
