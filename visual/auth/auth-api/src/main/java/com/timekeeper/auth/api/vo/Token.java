package com.timekeeper.auth.api.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Token {

    /**
     * 返回前端 jwt
     */
    String token;
}
