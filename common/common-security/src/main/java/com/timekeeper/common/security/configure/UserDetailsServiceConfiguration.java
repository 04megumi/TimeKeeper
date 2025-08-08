package com.timekeeper.common.security.configure;

import com.timekeeper.common.security.service.BaseUserDetailsService;
import com.timekeeper.common.security.service.impl.DefaultUserDetailsService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 条件注入，只需在其他服务引入新Bean注册即可
 *
 * @author 魏子越
 */
@Configuration
public class UserDetailsServiceConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public BaseUserDetailsService userDetailsService() {
        return new DefaultUserDetailsService();
    }
}
