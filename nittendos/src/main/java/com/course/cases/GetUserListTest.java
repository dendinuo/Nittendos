package com.course.cases;

import java.io.IOException;
import java.util.List;

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
import com.course.model.GetUserListCase;
import com.course.model.User;
import com.course.utils.DatabaseUtil;

public class GetUserListTest {
	@Test(dependsOnGroups="loginTrue",description="获取用户性别为男性的接口测试")
	public void getuserlist() throws IOException, InterruptedException, JSONException {
		SqlSession sqlsession = DatabaseUtil.getSqlSession();
		GetUserListCase getUserListCase = sqlsession.selectOne("getuserlistCase",1); 
		System.out.println( getUserListCase.toString() );
		System.out.println( TestConfig.getUserlistUrl);
		
		
		JSONArray resultJson = getJsonResult(getUserListCase);
      
        Thread.sleep(2000);
        List<User> userList = sqlsession.selectList(getUserListCase.getExpected(),getUserListCase);
        for(User u : userList){
            System.out.println("list获取的user:"+u.toString());
        }
        JSONArray userListJson = new JSONArray(userList);

        Assert.assertEquals(userListJson.length(),resultJson.length());
        for(int i = 0;i<resultJson.length();i++){
            JSONObject expect = (JSONObject) resultJson.get(i);
            JSONObject actual = (JSONObject) userListJson.get(i);
            Assert.assertEquals(expect.toString(), actual.toString());
        }
	}
	private JSONArray getJsonResult(GetUserListCase getUserListCase) throws IOException, JSONException {
        HttpPost post = new HttpPost(TestConfig.getUserlistUrl);
        JSONObject param = new JSONObject();
        param.put("userName",getUserListCase.getUserName());
        param.put("sex",getUserListCase.getSex());
        param.put("age",getUserListCase.getAge());
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
        org.json.JSONArray jsonArray = new org.json.JSONArray(result);

        System.out.println("调用接口list result:"+result);
        return jsonArray;
    }
		
}
