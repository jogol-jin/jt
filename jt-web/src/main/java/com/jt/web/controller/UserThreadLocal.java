package com.jt.web.controller;

import com.jt.web.pojo.User;

public class UserThreadLocal {
	//创建一个user的threadlocal对象
	private static final ThreadLocal<User> USER = new ThreadLocal<User>();

	public static User get(){
		return USER.get();
	}
	
	public static void set(User user){
		USER.set(user);
	}
	//直接获取userId
	public static Long getuserId(){
		if(USER.get() != null){
			return USER.get().getId();
		}else{
			return null;
		}
	}
}
