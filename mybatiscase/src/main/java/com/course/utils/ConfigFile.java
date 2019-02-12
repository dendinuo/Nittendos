package com.course.utils;

import java.util.Locale;
import java.util.ResourceBundle;

import com.course.model.InterfaceName;

public class ConfigFile {
	private static ResourceBundle bundle = ResourceBundle.getBundle("application",Locale.CHINA);
	public static String getUrl(InterfaceName name) {
		String address = bundle.getString("test.url");
		String uri="";
		
		//最终测试地址
		String testUrl ;
		if(name==InterfaceName.GETUSERLIST) {
			uri=bundle.getString("getuserlist.uri");
		}
		
		if(name == InterfaceName.LOGIN) {
			uri = bundle.getString("login.uri");
		}
		
		if(name == InterfaceName.UPDATEUSERINFO) {
			uri = bundle.getString("updateuserinfo.uri");
		}
		
		if(name == InterfaceName.GETUSERINFO) {
			uri = bundle.getString("getuserinfo.uri");
		}
		
		if(name == InterfaceName.ADDUSER) {
			uri = bundle.getString("adduser.uri");
		}
		testUrl = address + uri ;
		return testUrl;
		
	}
}
