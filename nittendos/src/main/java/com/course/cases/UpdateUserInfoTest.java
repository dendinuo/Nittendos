package com.course.cases;

import java.io.IOException;

import org.apache.ibatis.session.SqlSession;
import org.testng.annotations.Test;

import com.course.config.TestConfig;
import com.course.model.UpdateUserInfoCase;
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
	public void deleteuser() throws IOException {
		SqlSession sqlsession = DatabaseUtil.getSqlSession();
		UpdateUserInfoCase updateuser = sqlsession.selectOne("updateuserinfoCase", 2);
		System.out.println( updateuser.toString() );
		System.out.println( TestConfig.updateUserInfoUrl);
	}
}
