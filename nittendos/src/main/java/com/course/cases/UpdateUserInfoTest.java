package com.course.cases;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.course.config.TestConfig;
import com.course.model.UpdateUserInfoCase;
import com.course.model.User;
import com.course.utils.DatabaseUtil;


public class UpdateUserInfoTest {
	
	@Test(dependsOnGroups="loginTrue",description="更新用户信息的接口测试")
	public void updateuserinfo() throws IOException {
		SqlSession sqlsession = DatabaseUtil.getSqlSession();
		UpdateUserInfoCase updateuser = sqlsession.selectOne("updateuserinfoCase", 1);
		System.out.println( updateuser.toString() );
		System.out.println( TestConfig.updateUserInfoUrl);
	}
	
	@Test(dependsOnGroups="loginTrue",description="删除用户的接口测试")
	public void deleteuser() throws IOException, JSONException, InterruptedException {
		SqlSession sqlsession = DatabaseUtil.getSqlSession();
		UpdateUserInfoCase updateUserInfoCase = sqlsession.selectOne("updateuserinfoCase", 2);
		System.out.println( updateUserInfoCase.toString() );
		System.out.println( TestConfig.updateUserInfoUrl);
		
		int result = getResult(updateUserInfoCase);

        /**
         * 验证
         */
        Thread.sleep(2000);
        User user = sqlsession.selectOne(updateUserInfoCase.getExpected(),updateUserInfoCase);
        System.out.println(user.toString());


        Assert.assertNotNull(user);
        Assert.assertNotNull(result);
	}
	
	private int getResult(UpdateUserInfoCase updateUserInfoCase) throws IOException, JSONException {
        HttpPost post = new HttpPost(TestConfig.updateUserInfoUrl);
        JSONObject param = new JSONObject();
        param.put("id",updateUserInfoCase.getId());
        param.put("userName",updateUserInfoCase.getUserName());
        param.put("sex",updateUserInfoCase.getSex());
        param.put("age",updateUserInfoCase.getAge());
        param.put("permission",updateUserInfoCase.getPermission());
        param.put("isDelete",updateUserInfoCase.getIsDelete());
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
        System.out.println(result);
        return Integer.parseInt(result);

    }
}
