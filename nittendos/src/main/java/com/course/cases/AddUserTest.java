package com.course.cases;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.Asserts;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.course.config.TestConfig;
import com.course.model.AddUserCase;
import com.course.model.User;
import com.course.utils.DatabaseUtil;

import lombok.extern.log4j.Log4j2;
@Log4j2
public class AddUserTest {
	@Test (dependsOnGroups="loginTrue",description="添加用户接口测试")
	public void addUser() throws IOException, JSONException, InterruptedException {
		SqlSession sqlsession = DatabaseUtil.getSqlSession();
		AddUserCase adduser = sqlsession.selectOne("adduserCase", 1);
		System.out.println( adduser.toString() );
		System.out.println( TestConfig.addUserUrl);
		
		//发请求，获取结果
		String result = getResult(adduser);
		//验证返回结果
		//查询用户看是否添加成功
        Thread.sleep(2000);
		User user = sqlsession.selectOne("addUser", adduser);
		System.out.println(user.toString());
		//处理结果，就是判断返回结果是否符合预期
		Assert.assertEquals(adduser.getExpected(), result);
	}
	
	//返回响应结果的方法
	private String getResult(AddUserCase addUserCase) throws ClientProtocolException, IOException, JSONException {
		HttpPost post = new HttpPost( TestConfig.addUserUrl );
		JSONObject param=new JSONObject();
		param.put("userName", addUserCase.getUserName());
		param.put("password", addUserCase.getPassword());
		param.put("sex", addUserCase.getSex());
		param.put("age", addUserCase.getAge());
		param.put("permission", addUserCase.getPermission());
		param.put("isDelete", addUserCase.getIsDelete());
		log.info(param);
		//设置header
		post.setHeader("content-type","application/json");
		//添加参数到请求
		StringEntity entity=new StringEntity(param.toString(),"utf-8");
		post.setEntity(entity);
		//设置cookies
		TestConfig.defaultHttpClient.setCookieStore(TestConfig.store);
		//存放返回结果的
		String result;
		//执行请求
		log.info(post);
		HttpResponse response = TestConfig.defaultHttpClient.execute(post);
		result = EntityUtils.toString(response.getEntity(),"utf-8");
		log.info("添加用户的result:"+result);
		return result;
	}
}


