package com.me.activity;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.db.DbSchemaCreate;
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

@SpringBootTest(classes = {ActivityApplication.class})
@RunWith(SpringRunner.class)
@Slf4j
@AutoConfigureMockMvc
class ActivityApplicationTests {
	@Autowired
	private RepositoryService repositoryService;

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

	/**
	*@Description: 部署工作流
	*@Param: []
	*@return: void
	*@Author: lic
	*@date: 2020/1/8
	*/
	@Test
	public void createDeployment(){
		repositoryService.createDeployment().addClasspathResource("process/VacationRequest.bpmn20.xml").deploy();
		long count = repositoryService.createProcessDefinitionQuery().count();
		logger.info("count is {}",count);
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
