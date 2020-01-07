package com.me.activity.config;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * @program: activity
 * @description: 数据源配置
 * @author: lic
 * @create: 2020-01-07 16:59
 **/
@Configuration
@MapperScan(basePackages = CoreDataSourceConfiguration.PACKAGE,sqlSessionFactoryRef = "coreSqlSessionFactory")
@ConfigurationProperties(prefix = "spring.datasource")
@EnableTransactionManagement(proxyTargetClass = true)
@Getter
@Setter
@Slf4j
public class CoreDataSourceConfiguration {
    static final String PACKAGE = "com.me.activity.dao";
    static final String MAPPER_LOCATION = "classpath:mapper/*.xml";
    private String url;
    private String username;
    private String password;
    private String driverClassName;
    private Integer maxActive;
    private Integer initialSize;
    private Integer maxWait;
    private Integer minIdle;
    private Integer timeBetweenEvictionRunsMillis;
    private Integer minEvictableIdleTimeMillis;
    private String validationQuery;
    private boolean testWhileIdle;
    private boolean testOnBorrow;
    private boolean testOnReturn;

    @Bean(name = "dataSource")
    public DataSource coreDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setName("businessDataSource");
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setMaxActive(maxActive);
        dataSource.setInitialSize(initialSize);
        dataSource.setMaxWait(maxWait);
        dataSource.setMinIdle(minIdle);
        dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        dataSource.setValidationQuery(validationQuery);
        dataSource.setTestWhileIdle(testWhileIdle);
        dataSource.setTestOnBorrow(testOnBorrow);
        dataSource.setTestOnReturn(testOnReturn);
        return dataSource;
    }

    @Bean(name = "coreTransactionManager")
    public DataSourceTransactionManager coreTransactionManager() {
        return new DataSourceTransactionManager(coreDataSource());
    }

    @Bean(name = "coreSqlSessionFactory")
    public SqlSessionFactory coreSqlSessionFactory(@Qualifier("dataSource") DataSource xtDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(xtDataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(CoreDataSourceConfiguration.MAPPER_LOCATION));
        return sessionFactory.getObject();
    }
}
