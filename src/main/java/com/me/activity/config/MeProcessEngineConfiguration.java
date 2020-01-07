package com.me.activity.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * @program: activity
 * @description: 流程引擎配置
 * @author: lic
 * @create: 2020-01-07 14:56
 **/
@Configuration
public class MeProcessEngineConfiguration {

    @Bean(name = "activityProcessEngineConfiguration")
    @DependsOn(value = {"dataSource","coreTransactionManager"})
    @ConditionalOnMissingBean(ProcessEngineConfigurationImpl.class)
    public ProcessEngineConfigurationImpl processEngineConfiguration(@Qualifier("dataSource") DataSource dataSource,
            PlatformTransactionManager coreTransactionManager){
        SpringProcessEngineConfiguration configuration = new SpringProcessEngineConfiguration();
        configuration.setDataSource(dataSource);
        configuration.setTransactionManager(coreTransactionManager);
        configuration.setAsyncExecutorActivate(false);
        configuration.setDatabaseSchemaUpdate("true");
        return configuration;
    }

    @Bean("activityProcessEngine")
    @DependsOn(value = {"activityProcessEngineConfiguration"})
    @ConditionalOnMissingBean(ProcessEngineFactoryBean.class)
    public ProcessEngine processEngineFactoryBean(ProcessEngineConfigurationImpl activityProcessEngineConfiguration) throws Exception {
        ProcessEngineFactoryBean factoryBean = new ProcessEngineFactoryBean();
        factoryBean.setProcessEngineConfiguration(activityProcessEngineConfiguration);
        ProcessEngine processEngine = factoryBean.getObject();

        return processEngine;
    }

    @Bean("activityRepositoryService")
    @DependsOn(value = {"activityProcessEngineFactoryBean"})
    public RepositoryService repositoryService(ProcessEngine processEngine){
        RepositoryService repositoryService = processEngine.getRepositoryService();
        return repositoryService;
    }
}
