package com.timekeeper.common.security.configure;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.jwt.JWTHeader;
import cn.hutool.jwt.JWTUtil;
import com.timekeeper.common.core.constant.JWTPayloadConstants;
import com.timekeeper.common.core.constant.SecurityConstants;
import com.timekeeper.common.security.exception.UnauthorizedException;
import com.timekeeper.common.security.service.BaseUserDetails;
import com.timekeeper.common.security.service.BaseUserDetailsService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import cn.hutool.jwt.JWT;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import java.util.stream.Collectors;


@Configuration
@RequiredArgsConstructor
@ComponentScan("com.timekeeper.common.security")
public class BaseJwtDecoder implements JwtDecoder {

    @Value("${jwt.secret-key}")
    private String jwtSecretKey;

    @NonNull
    protected BaseUserDetailsService userDetailsService;

    @Override
    public Jwt decode(String token) throws JwtException {
        JWT jwt = JWTUtil.parseToken(token);

        // token是否被串改
        if (!JWTUtil.verify(token, getJwtSecretKey(jwt).getBytes())) {
            throw new UnauthorizedException("token verify failed!");
        }

        // jwt是否已过期
        DateTime expiresAt = new DateTime(Long.parseLong(jwt.getPayload(JWTPayloadConstants.EXPIRES_AT).toString()) * 1000);
        if (expiresAt.isBefore(new DateTime())) {
            throw new UnauthorizedException("token expired!");
        }

        // 获取真实用户
        BaseUserDetails user = this.loadUserByUsername(jwt);

        return Jwt.withTokenValue(token)
                .claim("scope", user.getRoleIds().stream().map(x -> "ROLE_" + x).collect(Collectors.toList()))
                .header(JWTHeader.ALGORITHM, jwt.getHeader(JWTHeader.ALGORITHM))
                .expiresAt(expiresAt.toInstant())
                .claim(SecurityConstants.DETAILS_USER, user)
                .subject(user.getUserNameEn())
                .build();
    }

    /**
     * 获取 JwtSecretKey
     *
     * @param jwt JWT
     * @return String
     */
    public String getJwtSecretKey(@NonNull JWT jwt) {
        return jwtSecretKey;
    }

    /**
     * 加载用户
     *
     * @param jwt JWT
     * @return BaseUserDetails
     */
    public BaseUserDetails loadUserByUsername(@NonNull JWT jwt) throws UnauthorizedException {
        // jwt是否包含用户
        String username = getUsernameByJwt(jwt);
        if (StrUtil.isEmpty(username)) {
            throw new UnauthorizedException("token is invalid!");
        }

        // jwt包含的用户是否在系统中存在
        BaseUserDetails user = userDetailsService.loadUserByUsername(username);
        if (ObjectUtil.isNull(user) || StrUtil.isEmpty(user.getUsername())) {
            throw new UnauthorizedException("token is invalid! (without user)");
        }

        return user;
    }

    /**
     * @param jwt JWT对象
     * @return jwt中的用户名
     */
    protected String getUsernameByJwt(JWT jwt) {
        if (jwt == null) {
            return null;
        }
        Object username = jwt.getPayload(JWTPayloadConstants.USERNAME_EN);
        if (username == null) {
            return null;
        }
        return username.toString();
    }
}