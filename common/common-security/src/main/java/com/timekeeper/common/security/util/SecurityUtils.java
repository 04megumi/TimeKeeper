package com.timekeeper.common.security.util;

import com.timekeeper.common.core.constant.SecurityConstants;
import com.timekeeper.common.security.service.BaseUserDetails;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

@UtilityClass
public class SecurityUtils {

    /**
     * 获取Authentication
     */
    public Authentication getAuthentication() { return SecurityContextHolder.getContext().getAuthentication(); }

    /**
     * 获取用户
     *
     * @return Jwt
     */
    private Jwt getUser(Authentication authentication) {
        if (authentication == null) {
            return null;
        }
        return (Jwt) authentication.getPrincipal();
    }

    /**
     * 获取用户
     *
     * @return BaseUserDetails
     */
    public BaseUserDetails getUser() {
        Jwt jwt = getUser(getAuthentication());
        if (jwt == null) {
            return null;
        }
        return jwt.getClaim(SecurityConstants.DETAILS_USER);
    }

    /**
     * 获取用户ID
     *
     * @return Integer
     */
    public Integer getUserId() {
        BaseUserDetails user = getUser();
        return user != null ? user.getId() : null;
    }

    /**
     * 获取用户rtx
     *
     * @return String
     */
    public String getUserNameEn() {
        BaseUserDetails user = getUser();
        return user != null ? user.getUserNameEn() : null;
    }
}
