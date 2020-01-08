package com.me.activity.service;

import org.activiti.engine.repository.DeploymentBuilder;

/**
 * @program: activity
 * @description: 工作流流程引擎服务
 * @author: lic
 * @create: 2020-01-08 10:25
 **/
public interface MeActivityService {
    DeploymentBuilder createDeployment();

    void deleteDeployment(String deploymentId);
}
