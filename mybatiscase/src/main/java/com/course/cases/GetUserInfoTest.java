package com.course.cases;

import java.io.IOException;

import org.apache.ibatis.session.SqlSession;
import org.testng.annotations.Test;

import com.course.config.TestConfig;
import com.course.model.GetUserInfoCase;
import com.course.utils.DatabaseUtil;

@Test(dependsOnGroups="loginTrue",description="获取用户id=1的用户信息的接口测试")
public class GetUserInfoTest {
	public void getUserInfo() throws IOException {
		SqlSession sqlsession = DatabaseUtil.getSqlSession();
		GetUserInfoCase getuserinfo = sqlsession.selectOne("getuserinfoCase", 1);
		System.out.println( getuserinfo.toString() );
		System.out.println( TestConfig.getUserInfoUrl);
	}
}
