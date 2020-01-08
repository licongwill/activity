package com.me.activity.controller;

import org.activiti.engine.RepositoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: activity
 * @description: 工作流的controller
 * @author: lic
 * @create: 2020-01-08 16:00
 **/
@RestController
@RequestMapping(value = "/activity")
public class MeActivityController {
    private static final Logger logger = LoggerFactory.getLogger(MeActivityController.class);

    @Autowired
    private RepositoryService repositoryService;

    @GetMapping(value = "/createDeployment")
    public Object createDeployment(){
        repositoryService.createDeployment().addClasspathResource("process/VacationRequest.bpmn20.xml").deploy();
        long count = repositoryService.createProcessDefinitionQuery().count();
        return count;
    }
}
