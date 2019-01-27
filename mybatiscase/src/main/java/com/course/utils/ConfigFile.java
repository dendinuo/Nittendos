package com.course.utils;

import java.util.Locale;
import java.util.ResourceBundle;

import com.course.model.InterfaceName;

public class ConfigFile {
	private static ResourceBundle bundle = ResourceBundle.getBundle("application",Locale.CHINA);
	public static String getUrl(InterfaceName name) {
		String address = bundle.getString("getUserList.uri");
		String uri="";
		String testUrl = null;
		if(name==InterfaceName.GETUSERLIST) {
			uri=bundle.getString("getUserList.uri");
		}
		
		if(name == InterfaceName.LOGIN) {
			uri = bundle.getString("login.uri");
		}
		
		if(name == InterfaceName.UPDATEUSERINFO) {
			uri = bundle.getString("updateUserInfo.uri");
		}
		
		if(name == InterfaceName.GETUSERINFO) {
			uri = bundle.getString("getUserInfo.uri");
		}
		
		if(name == InterfaceName.ADDUSER) {
			uri = bundle.getString("addUser.uri");
		}
		
		return testUrl;
		
	}
}
