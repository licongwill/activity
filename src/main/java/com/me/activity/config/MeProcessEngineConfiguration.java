package com.me.activity.config;

import org.activiti.engine.*;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
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
    @DependsOn(value = {"activityProcessEngine"})
    @ConditionalOnMissingBean(RepositoryService.class)
    public RepositoryService repositoryService(ProcessEngine activityProcessEngine){
        RepositoryService repositoryService = activityProcessEngine.getRepositoryService();
        return repositoryService;
    }

    @Bean("activityRuntimeService")
    @DependsOn(value = {"activityProcessEngine"})
    @ConditionalOnMissingBean(RuntimeService.class)
    public RuntimeService runtimeService(ProcessEngine activityProcessEngine){
        RuntimeService runtimeService = activityProcessEngine.getRuntimeService();
        return runtimeService;
    }

    @Bean("taskService")
    @DependsOn(value = {"activityProcessEngine"})
    @ConditionalOnMissingBean(TaskService.class)
    public TaskService taskService(ProcessEngine activityProcessEngine){
        TaskService taskService = activityProcessEngine.getTaskService();
        return taskService;
    }

    @Bean("historyService")
    @DependsOn(value = {"activityProcessEngine"})
    @ConditionalOnMissingBean(HistoryService.class)
    public HistoryService historyService(ProcessEngine activityProcessEngine){
        HistoryService historyService = activityProcessEngine.getHistoryService();
        return historyService;
    }

    @Bean("managementService")
    @DependsOn(value = {"activityProcessEngine"})
    @ConditionalOnMissingBean(ManagementService.class)
    public ManagementService managementService(ProcessEngine activityProcessEngine){
        ManagementService managementService = activityProcessEngine.getManagementService();
        return managementService;
    }
}
