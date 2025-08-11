package com.timekeeper.admin.biz.controller;

import com.timekeeper.admin.api.exception.AdminException;
import com.timekeeper.common.core.util.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/test")
    public R<Object> test() {
        throw new AdminException("111");
    }
}
