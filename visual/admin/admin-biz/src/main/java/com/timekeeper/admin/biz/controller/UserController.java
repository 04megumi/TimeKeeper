package com.timekeeper.admin.biz.controller;

import com.timekeeper.admin.api.exception.AdminException;
import com.timekeeper.common.core.util.R;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/test")
    @PreAuthorize("hasRole(T(com.timekeeper.common.core.constant.RoleConstants).TEACHER)")
    public R<Object> test() {
        throw new AdminException("111");
    }
}
