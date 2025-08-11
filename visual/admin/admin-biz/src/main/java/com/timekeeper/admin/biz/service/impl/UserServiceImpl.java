package com.timekeeper.admin.biz.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.timekeeper.admin.api.exception.AdminException;
import com.timekeeper.admin.biz.mapper.UserMapper;
import com.timekeeper.admin.biz.service.AdminUserService;
import com.timekeeper.auth.api.entity.User;
import com.timekeeper.common.security.service.BaseUserDetails;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements AdminUserService {

    @NonNull
    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public BaseUserDetails loadUserByUid(String uid) {
        try {
            if(ObjectUtil.isNull(uid)) throw new AdminException("uid为空");
            User user = userMapper.selectById(uid);
            if(ObjectUtil.isNull(user)) {
                throw new AdminException("用户不存在, uid: " + uid);
            }
            return user;
        } catch (Exception e) {
            throw new AdminException("LOAD_USER_BY_UID" + e.getMessage());
        }
    }
}
