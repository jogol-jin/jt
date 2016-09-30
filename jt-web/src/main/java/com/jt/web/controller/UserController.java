package com.jt.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.util.CookieUtils;
import com.jt.common.vo.SysResult;
import com.jt.web.pojo.User;
import com.jt.web.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	public static final String UserCookieName = "JT_TICKET";
	
	@RequestMapping("/user/register")
	public String toRegister(){
		return "register";
	}
	//注册提交
	@RequestMapping("/user/doRegister")
	@ResponseBody
	public SysResult doRegister(User user) throws Exception{
		System.out.println("=============="+user);
		return userService.doRegister(user);
	}
	
	//转向登录页面
	@RequestMapping("/user/login")
	public String toLogin(){
		return "login";
	}
	//登录
	@RequestMapping("/user/doLogin")
	@ResponseBody
	public SysResult doLogin(String username, String password,HttpServletRequest request, HttpServletResponse response) throws Exception{
		SysResult result = userService.doLogin(username, password);
		String ticket = (String) result.getData();
		//写cookie
		CookieUtils.setCookie(request, response, UserCookieName, ticket);
		return result;
	}
}
