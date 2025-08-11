package com.timekeeper.admin.biz.service.impl;

import com.timekeeper.admin.biz.service.AdminCurrentService;
import com.timekeeper.common.security.util.SecurityUtils;
import org.springframework.stereotype.Service;

@Service
public class CurrentServiceImpl implements AdminCurrentService {

    @Override
    public String getUid() { return SecurityUtils.getUserId(); }
}
