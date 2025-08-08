package com.timekeeper.auth.api.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class PhoneLoginDTO {

    /**
     * 登录手机号
     */
    @NonNull
    private String phoneNumber;

    /**
     * 用户密码
     */
    @NonNull
    private String password;
}
