package com.course.cases;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.course.config.TestConfig;
import com.course.model.GetUserInfoCase;
import com.course.model.User;
import com.course.utils.DatabaseUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Test(dependsOnGroups="loginTrue",description="获取用户id=1的用户信息的接口测试")
public class GetUserInfoTest {
	public void getUserInfo() throws IOException, InterruptedException, JSONException {
		SqlSession sqlsession = DatabaseUtil.getSqlSession();
		GetUserInfoCase getUserInfoCase = sqlsession.selectOne("getuserinfoCase", 1);
		System.out.println( getUserInfoCase.toString() );
		System.out.println( TestConfig.getUserInfoUrl);
		
		//验证结果
        JSONArray resultJson = getJsonResult(getUserInfoCase);
        Thread.sleep(2000);
        User user = sqlsession.selectOne(getUserInfoCase.getExpected(),getUserInfoCase);
        System.out.println("自己查库获取用户信息:"+user.toString());

        List userList = new ArrayList();
        userList.add(user);
        JSONArray jsonArray = new JSONArray(userList);
        System.out.println("获取用户信息:"+jsonArray.toString());
        System.out.println("调用接口获取用户信息:"+resultJson.toString());
        Assert.assertEquals(jsonArray,resultJson);
	}
	
	private JSONArray getJsonResult(GetUserInfoCase getUserInfoCase) throws IOException, JSONException {
        HttpPost post = new HttpPost(TestConfig.getUserInfoUrl);
        JSONObject param = new JSONObject();
        param.put("id",getUserInfoCase.getUserId());
        //设置请求头信息 设置header
        post.setHeader("content-type","application/json");
        //将参数信息添加到方法中
        StringEntity entity = new StringEntity(param.toString(),"utf-8");
        post.setEntity(entity);
        //设置cookies
        TestConfig.defaultHttpClient.setCookieStore(TestConfig.store);
        //声明一个对象来进行响应结果的存储
        String result;
        //执行post方法
        HttpResponse response = TestConfig.defaultHttpClient.execute(post);
        //获取响应结果
        result = EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println("调用接口result:"+result);
        List resultList = Arrays.asList(result);
        JSONArray array = new JSONArray(resultList);
        System.out.println(array.toString());
        return array;

    }
}
