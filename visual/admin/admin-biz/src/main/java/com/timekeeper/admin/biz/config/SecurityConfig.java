package com.timekeeper.admin.biz.config;

import com.timekeeper.common.core.constant.SecurityConstants;
import com.timekeeper.common.security.configure.CustomAccessDeniedHandler;
import com.timekeeper.common.security.configure.CustomAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .accessDeniedHandler(new CustomAccessDeniedHandler())
                        .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                        .jwt()
                        .jwtAuthenticationConverter(jwtAuthenticationConverter()))
                .build();
    }

    private JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        JwtGrantedAuthoritiesConverter authoritiesConverter = new JwtGrantedAuthoritiesConverter();
        // 去掉 SCOPE_ 的前缀
        authoritiesConverter.setAuthorityPrefix("");
        // 从jwt claim 中那个字段获取权限，模式从 scope 字段中获取
        authoritiesConverter.setAuthoritiesClaimName(SecurityConstants.SCOPE);
        converter.setJwtGrantedAuthoritiesConverter(authoritiesConverter);
        return converter;
    }
}
