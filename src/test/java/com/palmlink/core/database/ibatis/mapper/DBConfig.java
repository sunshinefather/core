/**
 * 
 */
package com.palmlink.core.database.ibatis.mapper;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.managed.ManagedTransactionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import com.palmlink.core.platform.DefaultAppConfig;

/**
 * @author Shihai.Fu
 * 
 */
public class DBConfig extends DefaultAppConfig {

    @Inject
    Environment env;

    @Bean(destroyMethod = "close")
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(env.getRequiredProperty("jdbc.driver.name"));
        dataSource.setUrl(env.getRequiredProperty("jdbc.writedb.proxy.url"));
        dataSource.setUsername(env.getRequiredProperty("jdbc.username"));
        dataSource.setPassword(env.getRequiredProperty("jdbc.password"));
        dataSource.setTestOnBorrow(true);
        dataSource.setValidationQuery("select 1 from dual");
        return dataSource;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() {
        TransactionFactory transactionFactory = new ManagedTransactionFactory();
        org.apache.ibatis.mapping.Environment environment = new org.apache.ibatis.mapping.Environment("development", transactionFactory, dataSource());
        Configuration configuration = new Configuration(environment);
        configuration.addMappers(SqlMapper.class.getPackage().getName());
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
        return sqlSessionFactory;
    }
}
