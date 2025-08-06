package com.timekeeper.common.data.mybatis.config;

import com.timekeeper.common.data.mybatis.service.CurrentUserService;
import com.timekeeper.common.data.mybatis.handler.MybatisPlusMetaObjectHandler;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * MybatisPlus 配置注册类
 * 确保只有在容器中有 DataSource 时才加载这个配置类。避免因数据源未配置而初始化失败。
 * 保证在 Spring 的 DataSource 自动配置完成之后再执行当前配置。
 * 如需自定义用户信息来源，请实现并注册一个 CurrentUserService Bean。
 *
 * @author 魏子越
 */
@Configuration
@AllArgsConstructor
@ConditionalOnBean(DataSource.class)
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
public class MybatisPlusConfiguration {

    private final CurrentUserService currentUserService;

    /**
     * 审计字段自动填充
     *
     * @return MetaObjectHandler
     */
    @Bean
    public MybatisPlusMetaObjectHandler mybatisPlusMetaObjectHandler() {
        return new MybatisPlusMetaObjectHandler(currentUserService);
    }
}
