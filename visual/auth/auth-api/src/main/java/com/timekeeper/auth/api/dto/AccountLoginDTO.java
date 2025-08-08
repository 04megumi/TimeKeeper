package com.timekeeper.auth.api.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class AccountLoginDTO {

    /**
     * 登录用户名
     */
    @NonNull
    private String userNameEn;

    /**
     * 用户密码
     */
    @NonNull
    private String password;
}