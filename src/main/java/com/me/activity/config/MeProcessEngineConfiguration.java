package com.me.activity.config;

import lombok.Getter;
import lombok.Setter;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: activity
 * @description: 流程引擎配置
 * @author: lic
 * @create: 2020-01-07 14:56
 **/
@Configuration
@ConfigurationProperties(prefix = "me.process.engine")
@Setter
@Getter
public class MeProcessEngineConfiguration {
    /*数据库连接地址*/
    private String jdbcUrl;
    /*驱动类*/
    private String jdbcDriver;
    /*数据库用户名*/
    private String jdbcUsername;
    /*数据库类型*/
    private String databaseType;
    /*密码*/
    private String jdbcPassword;
    /*数据库更新模式*/
    private String databaseSchemaUpdate;
    /*是否异步执行*/
    private boolean asyncExecutorActivate;
    /*邮箱地址*/
    private String mailServerHost;
    /*邮箱用户名*/
    private String mailServerUsername;
    /*邮箱密码*/
    private String mailServerPassword;
    /*邮箱服务器*/
    private int mailServerPort;

    @Bean(name = "processEngineConfiguration")
    public ProcessEngineConfiguration processEngineConfiguration(){
        StandaloneProcessEngineConfiguration configuration = new StandaloneProcessEngineConfiguration();
        configuration.setJdbcUrl(this.getJdbcUrl());
        configuration.setJdbcDriver(this.getJdbcDriver());
        configuration.setJdbcUsername(this.getJdbcUsername());
        configuration.setDatabaseType(this.getDatabaseType());
        configuration.setJdbcPassword(this.getJdbcPassword());
        configuration.setDatabaseSchema(this.getDatabaseSchemaUpdate());
        configuration.setAsyncExecutorActivate(false);
        return configuration;
    }
}
