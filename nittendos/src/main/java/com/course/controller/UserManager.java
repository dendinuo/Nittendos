package com.course.controller;

import java.awt.List;
import java.util.Objects;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.course.model.User;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;


@Log4j2
@RestController
@RequestMapping("v1")
@Api(value="v1",description="用户管理系统",tags="用户d管理系统")
public class UserManager {
	@Autowired
	private SqlSessionTemplate template;
	
	/**验证cookies**/
	private boolean verifyCookies( HttpServletRequest request ) {
		Cookie[] cookies = request.getCookies();
		if(Objects.isNull ( cookies )) {
			log.info("cookies为空");
			return false;
		}
		for(Cookie cookie:cookies) {
			if(cookie.getName().equals("login")&&cookie.getValue().equals("true")) {
				log.info("cookies验证通过！");
				return true;
			}
		}
		return false;
	}
	
	@ApiOperation(value="登录接口",httpMethod="POST")
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public Boolean login( HttpServletResponse response, @RequestBody  User user) {
		int i = template.selectOne("login",user);
		Cookie cookie = new Cookie("login","true");
		
		log.info("查询到的结果 i="+i);
		if(i==1) {
			response.addCookie(cookie);
			log.info("登陆的用户是："+user.getUserName());
			return true;
		}
		return false;
	}
	
	@ApiOperation(value="添加用户接口",httpMethod="POST")
	@RequestMapping(value="/adduser",method=RequestMethod.POST)
	public boolean addUser( HttpServletRequest request ,@RequestBody User user ) {
		Boolean x = verifyCookies(request);
		int result = 0;
		if ( x!=null ) {
			result = template.insert("addUser",user);
		}
		if ( result>0 ) {
			log.info("添加的用户数量是："+result);
			return true;
		}
		return false;
	}
	
	@ApiOperation(value="获取用户列表信息接口",httpMethod="POST")
	@RequestMapping(value="/getUserInfo",method=RequestMethod.POST)
	public java.util.List<User> getUserInfo(HttpServletRequest request,@RequestBody User user){
		boolean x = verifyCookies(request);
		if(x==true) {
			java.util.List<User> users=template.selectList("getUserInfo");
			log.info("获取到用户的数量是："+users.size());
			return users;
		}else {
			return null;
		}
	}
	
	@ApiOperation(value = "更新/删除用户接口",httpMethod = "POST")
    @RequestMapping(value = "/updateUserInfo",method = RequestMethod.POST)
    public int updateUser(HttpServletRequest request,@RequestBody User user){
        Boolean x = verifyCookies(request);
        int i = 0;
        if(x==true) {
            i = template.update("updateUserInfo", user);
        }
        log.info("更新数据的条目数为:" + i);
        return i;

    }
	
}
