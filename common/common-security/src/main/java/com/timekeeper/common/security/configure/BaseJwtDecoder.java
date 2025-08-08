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

/**
 * 自定义 JWT 解码器
 * <p>
 * 该类实现了 Spring Security 提供的 {@link JwtDecoder} 接口，
 * 用于在接收到客户端传递的 JWT 时进行验证、解析，并将其中的用户信息加载到 Spring Security 上下文中。
 * <p>
 * 核心功能：
 * <ul>
 *   <li>验证 JWT 签名，确保未被篡改</li>
 *   <li>检查 Token 是否过期</li>
 *   <li>根据 JWT 中的用户名加载数据库中的用户信息</li>
 *   <li>将用户权限、信息等封装到 {@link Jwt} 对象返回</li>
 * </ul>
 */
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
        BaseUserDetails user = this.loadUserByJwt(jwt);

        return Jwt.withTokenValue(token)
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
    public BaseUserDetails loadUserByJwt(@NonNull JWT jwt) throws UnauthorizedException {
        // jwt是否包含用户
        String uid = getUidByJwt(jwt);
        if (StrUtil.isEmpty(uid)) {
            throw new UnauthorizedException("token is invalid!");
        }

        // jwt包含的用户是否在系统中存在
        BaseUserDetails user = userDetailsService.loadUserByUid(uid);
        if (ObjectUtil.isNull(user) || StrUtil.isEmpty(user.getId())) {
            throw new UnauthorizedException("token is invalid! (without user)");
        }

        return user;
    }

    /**
     * @param jwt JWT对象
     * @return jwt中的uid
     */
    protected String getUidByJwt(JWT jwt) {
        if (jwt == null) {
            return null;
        }
        Object uid = jwt.getPayload(JWTPayloadConstants.USER_ID);
        if (uid == null) {
            return null;
        }
        return uid.toString();
    }
}