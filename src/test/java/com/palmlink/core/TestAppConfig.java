package com.palmlink.core;

import java.sql.Driver;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.palmlink.core.database.JDBCAccess;
import com.palmlink.core.database.ibatis.mapper.SqlMapper;
import com.palmlink.core.platform.DefaultAnnotationScopeResolver;
import com.palmlink.core.platform.DefaultAppConfig;
import com.palmlink.core.platform.concurrent.TaskExecutor;
import com.palmlink.core.platform.intercept.AnnotatedMethodAdvisor;
import com.palmlink.core.platform.intercept.InterceptorTestAnnotation;
import com.palmlink.core.platform.intercept.TestInterceptor;
import com.palmlink.core.util.ClasspathResource;

/**
 * @author Shihai.Fu
 */
@Configuration
@ComponentScan(basePackageClasses = TestAppConfig.class, scopeResolver = DefaultAnnotationScopeResolver.class)
@EnableCaching
@EnableAspectJAutoProxy
@EnableTransactionManagement
@PropertySource("classpath:site-jdbc.properties")
@MapperScan("com.palmlink.core.database.ibatis.mapper")
public class TestAppConfig extends DefaultAppConfig {
    
    @Inject
    Environment env;
    
    /**
     * jdbc.driver.name=com.mysql.jdbc.Driver
jdbc.readdb.proxy.url=jdbc:mysql://writedb:3306/security
jdbc.writedb.proxy.url=jdbc:mysql://writedb:3306/security
jdbc.username=root
jdbc.password=78ujmki*
     * @return
     */
    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(Driver.class.getName());
        dataSource.setUrl("jdbc:mysql://writedb:3306/security");
        dataSource.setUsername("root");
        dataSource.setPassword("78ujmki*");
        dataSource.setTestOnBorrow(true);
        dataSource.setValidationQuery("select 1 from dual");
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSource());
        return transactionManager;
    }

    @Bean
    public JDBCAccess jdbcAccess() {
        JDBCAccess jdbcAccess = new JDBCAccess();
        jdbcAccess.setDataSource(dataSource());
        return jdbcAccess;
    }

    @Bean
    public CacheManager cacheManager() {
        EhCacheCacheManager manager = new EhCacheCacheManager();
        manager.setCacheManager(new net.sf.ehcache.CacheManager(new ClasspathResource("ehcache.xml").getInputStream()));
        return manager;
    }

    @Bean
    public TaskExecutor taskExecutor() {
        return new TaskExecutor(1);
    }

    @Bean
    public AnnotatedMethodAdvisor testInterceptor() {
        return new AnnotatedMethodAdvisor(InterceptorTestAnnotation.class, TestInterceptor.class);
    }

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer config = new MapperScannerConfigurer();
        config.setBasePackage(SqlMapper.class.getPackage().getName());
        return config;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource());
        return sqlSessionFactory.getObject();
    }
    
}
