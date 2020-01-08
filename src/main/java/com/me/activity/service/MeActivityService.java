package com.me.activity.service;

import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.repository.DeploymentBuilder;

/**
 * @program: activity
 * @description: 工作流流程引擎服务
 * @author: lic
 * @create: 2020-01-08 10:25
 **/
public interface MeActivityService {
    /************************************************** repository service begin ***************************************************************/

    /**
    *@Description: createDeployment
    *@Param: []
    *@return: org.activiti.engine.repository.DeploymentBuilder
    *@Author: lic
    *@date: 2020/1/8
    */
    DeploymentBuilder createDeployment();

    /**
    *@Description: deleteDeployment
    *@Param: [deploymentId]
    *@return: void
    *@Author: lic
    *@date: 2020/1/8
    */
    void deleteDeployment(String deploymentId);

    /************************************************** repository service end ***************************************************************/


    /************************************************** runtime service begin ***************************************************************/

    /**
    *@Description: addEventListener
    *@Param: [listenerToAdd]
    *@return: void
    *@Author: lic
    *@date: 2020/1/8
    */
    void addEventListener(ActivitiEventListener listenerToAdd);

    void addEventListener(ActivitiEventListener listenerToAdd, ActivitiEventType... types);

    void removeEventListener(ActivitiEventListener listenerToRemove);

    /************************************************** runtime service end ***************************************************************/
}
