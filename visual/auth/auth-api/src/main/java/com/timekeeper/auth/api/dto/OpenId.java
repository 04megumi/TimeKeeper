package com.timekeeper.auth.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * OpenId DTO（数据传输对象），用于封装用户的 OpenID 信息。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OpenId {

    /**
     * 用户的 OpenID，通常用于身份标识。
     */
    private String openId;
}