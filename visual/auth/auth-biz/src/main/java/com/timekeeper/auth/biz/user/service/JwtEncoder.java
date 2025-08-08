package com.timekeeper.auth.biz.user.service;

import cn.hutool.jwt.JWT;
import cn.hutool.jwt.signers.JWTSignerUtil;
import org.springframework.beans.factory.annotation.Value;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class JwtEncoder {

    //@Value("${jwt.secret-key}")
    private static String jwtSecretKey = "xxx";

    /**
     * 根据用户ID生成 JWT Token，payload 中包含 "userId" 字段
     *
     * @param userId 用户ID
     * @return JWT 字符串
     */
    public static String generateToken(String userId) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("userId", userId);
        payload.put("iat", System.currentTimeMillis() / 1000);
        payload.put("exp", (System.currentTimeMillis() / 1000) + 3600);

        JWT jwt = JWT.create().setSigner(JWTSignerUtil.hs256(jwtSecretKey.getBytes(StandardCharsets.UTF_8)));

        for (Map.Entry<String, Object> entry : payload.entrySet()) {
            jwt.setPayload(entry.getKey(), entry.getValue());
        }

        return jwt.sign();
    }
}
