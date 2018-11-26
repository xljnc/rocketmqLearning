package com.wt.test.rocketmq.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author Xljnc
 * @date 2018/11/26 22:27
 * @description Order SqlSessionFactory配置类
 */
@Configuration
@MapperScan(basePackages = {"com.wt.test.rocketmq.dao.order"}, sqlSessionFactoryRef = "orderSqlSessionFactory", sqlSessionTemplateRef = "orderSqlSessionTemplate")
public class OrderSqlSessionFactoryConfig {

    @Autowired
    @Qualifier("orderDataSource")
    private DataSource orderDataSource;

    @Bean
    public SqlSessionFactory orderSqlSessionFactory() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(orderDataSource);
        return factoryBean.getObject();
    }

    @Bean
    public SqlSessionTemplate orderSqlSessionTemplate() throws Exception {
        return new SqlSessionTemplate(orderSqlSessionFactory());
    }

}
