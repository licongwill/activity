package com.me.activity.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.me.activity.service.MeActivityService;
import org.activiti.engine.*;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.repository.DeploymentBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: activity
 * @description: MeActivityService实现
 * @author: lic
 * @create: 2020-01-08 10:26
 **/
@Service
public class MeActivityServiceImpl implements MeActivityService {

    private static final Logger logger = LoggerFactory.getLogger(MeActivityServiceImpl.class);

    private final RepositoryService repositoryService;

    private final RuntimeService runtimeService;

    private final TaskService taskService;

    private final HistoryService historyService;

    private final ManagementService managementService;

    @Autowired
    public MeActivityServiceImpl(RepositoryService repositoryService, RuntimeService runtimeService,
         TaskService taskService, HistoryService historyService, ManagementService managementService) {
        this.repositoryService = repositoryService;
        this.runtimeService = runtimeService;
        this.taskService = taskService;
        this.historyService = historyService;
        this.managementService = managementService;
    }

    /**
    *@Description: createDeployment
    *@Param: []
    *@return: org.activiti.engine.repository.DeploymentBuilder
    *@Author: lic
    *@date: 2020/1/8
    */
    @Override
    public DeploymentBuilder createDeployment() {
        logger.info("begin createDeployment");
        DeploymentBuilder deployment = repositoryService.createDeployment();
        logger.info("deployment is {}", JSONObject.toJSONString(deployment));
        return deployment;
    }

    /**
    *@Description: deleteDeployment
    *@Param: [deploymentId]
    *@return: void
    *@Author: lic
    *@date: 2020/1/8
    */
    @Override
    public void deleteDeployment(String deploymentId) {
        logger.info("deploymentId is {}",deploymentId);
        repositoryService.deleteDeployment(deploymentId);
        logger.info("end deploymentId");
    }
    
    /**
    *@Description: addEventListener
    *@Param: [listenerToAdd]
    *@return: void
    *@Author: lic
    *@date: 2020/1/8
    */
    @Override
    public void addEventListener(ActivitiEventListener listenerToAdd) {
        runtimeService.addEventListener(listenerToAdd);
    }
    
    /**
    *@Description: addEventListener
    *@Param: [listenerToAdd, types]
    *@return: void
    *@Author: lic
    *@date: 2020/1/8
    */
    @Override
    public void addEventListener(ActivitiEventListener listenerToAdd, ActivitiEventType... types) {
        runtimeService.addEventListener(listenerToAdd,types);
    }
    
    /**
    *@Description: removeEventListener
    *@Param: [listenerToRemove]
    *@return: void
    *@Author: lic
    *@date: 2020/1/8
    */
    @Override
    public void removeEventListener(ActivitiEventListener listenerToRemove) {
        runtimeService.removeEventListener(listenerToRemove);
    }


}
