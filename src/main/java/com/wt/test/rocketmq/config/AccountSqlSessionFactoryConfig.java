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
 * @date 2018/11/26 22:01
 * @description Account SqlSessionFactory配置类
 */
@Configuration
@MapperScan(basePackages = {"com.wt.test.rocketmq.dao.account"}, sqlSessionFactoryRef = "accountSqlSessionFactory", sqlSessionTemplateRef = "accountSqlSessionTemplate")
public class AccountSqlSessionFactoryConfig {

    @Autowired
    @Qualifier("accountDataSource")
    private DataSource accountDataSource;

    @Bean
    public SqlSessionFactory accountSqlSessionFactory() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(accountDataSource);
        return factoryBean.getObject();
    }

    @Bean
    public SqlSessionTemplate accountSqlSessionTemplate() throws Exception {
        return new SqlSessionTemplate(accountSqlSessionFactory());
    }


}
