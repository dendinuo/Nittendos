package com.course.cases;

import java.io.IOException;

import org.apache.ibatis.session.SqlSession;
import org.testng.annotations.Test;

import com.course.config.TestConfig;
import com.course.model.AddUserCase;
import com.course.utils.DatabaseUtil;

public class AddUserTest {
	@Test (dependsOnGroups="loginTrue",description="添加用户接口测试")
	public void addUser() throws IOException {
		SqlSession sqlsession = DatabaseUtil.getSqlSession();
		AddUserCase adduser = sqlsession.selectOne("adduserCase", 1);
		System.out.println( adduser.toString() );
		System.out.println( TestConfig.addUserUrl);
	}
}
