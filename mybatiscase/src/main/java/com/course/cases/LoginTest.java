package com.course.cases;

import java.io.IOException;

import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.ibatis.session.SqlSession;
import org.testng.annotations.Test;

import com.course.config.TestConfig;
import com.course.model.InterfaceName;
import com.course.model.LoginCase;
import com.course.utils.ConfigFile;
import com.course.utils.DatabaseUtil;

public class LoginTest {
	
	public void beforeTest() {
		TestConfig.getUserInfoUrl = ConfigFile.getUrl(InterfaceName.GETUSERINFO);
		TestConfig.getUserlistUrl = ConfigFile.getUrl(InterfaceName.GETUSERLIST);
		TestConfig.addUserUrl = ConfigFile.getUrl(InterfaceName.ADDUSER);
		TestConfig.loginUrl = ConfigFile.getUrl(InterfaceName.LOGIN);
		TestConfig.updateUserInfoUrl = ConfigFile.getUrl(InterfaceName.UPDATEUSERINFO);
		
		TestConfig.defaultHttpClient = new DefaultHttpClient();
	}
	
	@Test(groups = "loginTrue",description = "登录成功接口测试")
	public void loginTrue() throws IOException {
		SqlSession session = DatabaseUtil.getSqlSession();
		LoginCase logincase = session.selectOne("loginCase",1);
		System.out.println( logincase.toString() );
		System.out.println( TestConfig.loginUrl );
	}
	
	@Test(groups="loginFalse",description="登录失败接口测试")
	public void loginFalse() {
		
	}
	
	
	

}
