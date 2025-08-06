package com.timekeeper.common.data.mybatis.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.timekeeper.common.data.mybatis.service.CurrentUserService;
import com.timekeeper.common.data.mybatis.handler.MybatisPlusMetaObjectHandler;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
     * 配置 Mybatis-Plus 的核心拦截器 {@link MybatisPlusInterceptor}。
     *
     * 该拦截器统一注册分页插件，并根据配置决定是否启用乐观锁插件。
     *
     * 使用说明：
     * - 分页插件始终启用，支持分页查询。
     * - 若在配置文件中设置：mybatis-plus.interceptor.optimistic-lock=true，
     *   则启用乐观锁插件，支持基于 version 字段的乐观锁机制。
     *
     * 示例配置（application.yml）：
     * mybatis-plus:
     *   interceptor:
     *     optimistic-lock: true
     *
     * @param enableOptimisticLock 是否启用乐观锁插件，默认 false
     * @return MybatisPlusInterceptor 拦截器实例
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor (@Value("${mybatis-plus.interceptor.optimistic-lock:false}") boolean enableOptimisticLock) {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 注册分页插件
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
        paginationInnerInterceptor.setMaxLimit(1000L);
        interceptor.addInnerInterceptor(paginationInnerInterceptor);
        // 根据配置选择是否启用乐观锁
        if (enableOptimisticLock) {
            interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        }
        return interceptor;
    }

    /**
     * 审计字段自动填充
     *
     * @return {@link MybatisPlusMetaObjectHandler}
     */
    @Bean
    public MybatisPlusMetaObjectHandler mybatisPlusMetaObjectHandler() {
        return new MybatisPlusMetaObjectHandler(currentUserService);
    }
}
