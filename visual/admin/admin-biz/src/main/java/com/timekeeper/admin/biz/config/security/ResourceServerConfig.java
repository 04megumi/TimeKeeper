package com.timekeeper.admin.biz.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;

/**
 * 资源服务器配置
 *
 * @author 魏子越
 */
@Configuration
public class ResourceServerConfig {

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().antMatchers("/login/**");
    }

}
