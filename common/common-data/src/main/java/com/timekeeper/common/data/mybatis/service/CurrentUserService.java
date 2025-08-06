package com.timekeeper.common.data.mybatis.service;

/**
 * 获取当前用户服务
 *
 * @author 魏子越
 */
public interface CurrentUserService {

    /**
     * 获取当前登陆用户ID
     *
     * @return Integer
     */
    Integer getUid();
}
