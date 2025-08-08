package com.timekeeper.auth.api.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class WechatMpAppLoginDTO {

    /**
     * 微信小程序登录凭证 code（前端调用 wx.login() 获取）
     */
    @NonNull
    private String code;
}
