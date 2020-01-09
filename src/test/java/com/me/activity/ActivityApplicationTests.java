package com.me.activity;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.db.DbSchemaCreate;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

@SpringBootTest(classes = {ActivityApplication.class})
@RunWith(SpringRunner.class)
@Slf4j
@AutoConfigureMockMvc
class ActivityApplicationTests {
	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private TaskService taskService;

	//下面是测试接口
	@Autowired
	private MockMvc mockMvc;


	private static final Logger logger = LoggerFactory.getLogger(ActivityApplicationTests.class);
	@Test
	void contextLoads() {
	}


	/**
	*@Description: 创建activity数据库
	*@Param: []
	*@return: void
	*@Author: lic
	*@date: 2020/1/8
	*/
	@Test
	public void loadActivityDataBase(){
		String[] args = new String[2];
		DbSchemaCreate.main(args);
	}

	public void programDeploy() throws FileNotFoundException {
		String barFileName = "path/to/process-one.bar";
		ZipInputStream inputStream = new ZipInputStream(new FileInputStream(barFileName));

		repositoryService.createDeployment()
				.name("process-one.bar")
				.addZipInputStream(inputStream)
				.deploy();
	}

	/**
	*@Description: 部署工作流
	*@Param: []
	*@return: void
	*@Author: lic
	*@date: 2020/1/8
	*/
	@Test
	public void createDeployment(){
		DeploymentBuilder deployment = repositoryService.createDeployment();
		Deployment deploy = deployment.name("部署请假流程").category("部署请假流程").key("vacationRequest").enableDuplicateFiltering()
				.deploymentProperty("test", "test").addClasspathResource("process/VacationRequest.bpmn20.xml").deploy();
		logger.info("deploy is {}",deploy);
	}


	/**
	*@Description: 启动流程
	*@Param: []
	*@return: void
	*@Author: lic
	*@date: 2020/1/9
	*/
	@Test
	public void startProcessInstance(){
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("employeeName", "Kermit");
		variables.put("numberOfDays", new Integer(4));
		variables.put("vacationMotivation", "I'm really tired!");
		ProcessInstance processInstance  = runtimeService.startProcessInstanceByKey("vacationRequest",variables);
		logger.info("processInstance is {}", processInstance);
	}

	/**
	*@Description: 完成任务
	*@Param: []
	*@return: void
	*@Author: lic
	*@date: 2020/1/9
	*/
	@Test
	public void completeTask(){
		List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("management").list();
		tasks.stream().forEach(n->{
			logger.info("task is {}",n);
		});

		Task task = tasks.get(0);
		Map<String, Object> taskVariables = new HashMap<String, Object>();
		taskVariables.put("vacationApproved", "false");
		taskVariables.put("managerMotivation", "We have a tight deadline!");
		taskService.complete("17505", taskVariables);
	}

	/**
	*@Description: mvcTestDeployment
	*@Param: []
	*@return: void
	*@Author: lic
	*@date: 2020/1/8
	*/
	@Test
	public void mvcTestDeployment() throws Exception{
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/activity/createDeployment",null))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		int status = mvcResult.getResponse().getStatus();
		logger.info("status is {}",status);
	}


}
