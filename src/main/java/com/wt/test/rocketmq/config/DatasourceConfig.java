package com.wt.test.rocketmq.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * @author Xljnc
 * @date 2018/11/26 21:31
 * @description 自定义Datasource配置
 */
@Configuration
public class DatasourceConfig {

    /**
     * account数据库配置类
     */
    @Bean("accountDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.account")
    public DataSource accountDataSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * order数据库配置类
     */
    @Bean("orderDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.order")
    public DataSource orderDataSource() {
        return DataSourceBuilder.create().build();
    }
}
