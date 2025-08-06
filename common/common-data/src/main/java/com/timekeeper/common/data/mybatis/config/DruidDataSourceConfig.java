package com.timekeeper.common.data.mybatis.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.timekeeper.common.data.mybatis.filter.DruidSqlLogFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;
import java.util.Collections;

/**
 * Druid 数据源配置
 *
 * 自动绑定 spring.datasource.* 配置，并注册 SQL 格式化插件
 *
 * @author 魏子越
 */
@Configuration
public class DruidDataSourceConfig {

    /**
     * SQL 日志格式化
     *
     * @return DruidSqlLogFilter
     */
    @Bean
    public DruidSqlLogFilter druidSqlLogFilter (@Value("${mybatis-plus.show-sql:true}") boolean showSql) {
        return new DruidSqlLogFilter(showSql);
    }

    @Bean
    @ConfigurationProperties("spring.datasource")
    public DataSource dataSource(DruidSqlLogFilter druidSqlLogFilter) {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setProxyFilters(Collections.singletonList(druidSqlLogFilter));
        return dataSource;
    }
}
