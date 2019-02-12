package com.course.utils;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;



public class DatabaseUtil {
	public static SqlSession getSqlSession() throws IOException {
		//获取配置的资源文件
		Reader reader = Resources.getResourceAsReader("databaseConfig.xml");
		
		SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader);
		//sqlsession用于执行配置文件中的sql语句
		SqlSession sqlsession = factory.openSession();
		
		return sqlsession;
	}
}
