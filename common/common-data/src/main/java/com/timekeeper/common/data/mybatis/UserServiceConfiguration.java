package com.timekeeper.common.data.mybatis;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MybatisPlus 默认用户服务配置
 * 获取用户名，服务审计字段，默认为空
 * 条件注入，只需在其他服务引入新Bean注册即可
 *
 * @author 魏子越
 */
@Configuration
public class UserServiceConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public CurrentUserService getCurrentUserService() {
        return new CurrentUserServiceImpl();
    }
}
