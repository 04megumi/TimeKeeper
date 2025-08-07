package com.timekeeper.common.security.configure;

import cn.hutool.jwt.JWT;
import com.timekeeper.common.core.constant.JWTPayloadConstants;
import com.timekeeper.common.security.service.BaseUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;


/**
 * JWT 编码器：用于根据用户信息生成 JWT Token
 *
 * @author 魏子越
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class JwtEncoder {

    @Value("${jwt.secret-key}")
    private String jwtSecretKey;

    @Value("${jwt.expire-seconds:3600}") // 默认1小时
    private long expireSeconds;

    /**
     * 根据用户信息签发 Token
     *
     * @param userDetails 用户信息
     * @return JWT token 字符串
     */
    public String encode(BaseUserDetails userDetails) {
        long now = System.currentTimeMillis();
        long expiresAt = now + expireSeconds * 1000;

        Map<String, Object> payload = new HashMap<>();
        payload.put(JWTPayloadConstants.USERNAME_EN, userDetails.getUserNameEn());
        payload.put(JWTPayloadConstants.ISSUED_AT, now / 1000);
        payload.put(JWTPayloadConstants.EXPIRES_AT, expiresAt / 1000);

        return JWT.create()
                .addPayloads(payload)
                .setKey(jwtSecretKey.getBytes(StandardCharsets.UTF_8))
                .sign();
    }
}