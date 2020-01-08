package com.me.activity;

import org.activiti.engine.impl.db.DbSchemaCreate;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ActivityApplicationTests {

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

}
