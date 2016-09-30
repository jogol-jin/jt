package com.jt.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.SysResult;
import com.jt.sso.mapper.UserMapper;
import com.jt.sso.pojo.User;
import com.jt.sso.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	//http://sso.jt.com/user/check/{param}/{type}
	@RequestMapping("/user/check/{param}/{type}")
	@ResponseBody
	public SysResult check(@PathVariable String param, @PathVariable Integer type){
		return userService.check(param, type);
	}
	@RequestMapping("/user/register")
	@ResponseBody
	public SysResult register(User user){
		System.out.println("==================================" + user);
		return userService.register(user);
	}
	
	//用户登录
	@RequestMapping("/user/login")
	@ResponseBody
	public SysResult login(String u, String p) throws Exception{
		return userService.login(u, p);
	}
	//根据ticket查询用户
	@RequestMapping("/user/query/{ticket}")
	@ResponseBody
	public SysResult queryByTicket(@PathVariable String ticket){
		return userService.queryByTicket(ticket);
	}
}
