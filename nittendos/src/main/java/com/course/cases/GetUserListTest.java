package com.course.cases;

import java.io.IOException;

import org.apache.ibatis.session.SqlSession;
import org.testng.annotations.Test;

import com.course.config.TestConfig;
import com.course.model.GetUserListCase;
import com.course.utils.DatabaseUtil;

public class GetUserListTest {
	@Test(dependsOnGroups="loginTrue",description="获取用户性别为男性的接口测试")
	public void getuserlist() throws IOException {
		SqlSession sqlsession = DatabaseUtil.getSqlSession();
		GetUserListCase getuserlist = sqlsession.selectOne("getuserlistCase",1); 
		System.out.println( getuserlist.toString() );
		System.out.println( TestConfig.getUserlistUrl);
	}
}
