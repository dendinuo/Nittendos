package com.course.cases;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.jsoup.helper.DataUtil;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.alibaba.fastjson.JSONObject;
import com.course.config.TestConfig;
import com.course.model.InterfaceName;
import com.course.model.LoginCase;
import com.course.utils.ConfigFile;
import com.course.utils.DatabaseUtil;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class LoginTest {
	
	@BeforeTest(groups="BeforeTest",description="测试前准备")
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
		System.out.println( logincase );
		System.out.println( TestConfig.loginUrl );
		//发送请求
		String result = getResult(logincase);
		//验证结果
		Assert.assertEquals(logincase.getExpected(), result);
	}

	
	@Test(groups="loginFalse",description="登录失败接口测试")
	public void loginFalse() throws IOException {
		SqlSession session = DatabaseUtil.getSqlSession();
		LoginCase logincase = session.selectOne("loginCase",2);
		System.out.println( logincase );
		System.out.println( TestConfig.loginUrl );
		//发送请求
		String result = getResult(logincase);
		//验证结果
		Assert.assertEquals(logincase.getExpected(), result);
	}
	
	//返回响应结果的方法
	private String getResult( LoginCase loginCase ) throws ClientProtocolException, IOException {
		HttpPost post = new HttpPost(TestConfig.loginUrl);
		JSONObject param = new JSONObject();
		param.put("userName", loginCase.getUserName());
		param.put("password", loginCase.getPassword());
		//设置header
		post.setHeader("content-type","application/json");
		//添加参数到请求
		StringEntity entity=new StringEntity(param.toString(),"utf-8");
		post.setEntity(entity);
		String result;
		//执行请求
		HttpResponse response = TestConfig.defaultHttpClient.execute(post);
		result = EntityUtils.toString(response.getEntity(),"utf-8");
		//添加cookies
		TestConfig.store = TestConfig.defaultHttpClient.getCookieStore();
		
		log.info("用例(登录)参数请求后的返回结果："+result);
		return result;
	}
	
	
	

}
