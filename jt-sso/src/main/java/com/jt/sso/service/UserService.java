package com.jt.sso.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.RedisService;
import com.jt.common.vo.SysResult;
import com.jt.sso.mapper.UserMapper;
import com.jt.sso.pojo.User;

@Service
public class UserService extends BaseService<User>{
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private RedisService redisService;
	private static final ObjectMapper MAPPER = new ObjectMapper();
	
	public SysResult check(String param, Integer type){
		Map<String, Object> map = new HashMap<String, Object>();
		String condition = "";
		if(1 == type){
			condition = "username = '" + param + "'";
		}else if(2 == type){
			condition = "phone = '" + param + "'";
		}else if(3 == type){
			condition = "email = '" + param + "'";
		}
		map.put("condition", condition);
		Integer count = userMapper.check(map);
		if(count > 0 ){
			return SysResult.ok(true);
		}else{
			return SysResult.ok(false);
		}
	}
	// 注册
	public SysResult register(User user){
		user.setEmail(user.getPhone());
		user.setCreated(new Date());
		user.setUpdated(user.getCreated());
		user.setPassword(DigestUtils.md5Hex(user.getPassword()));
		try {
			userMapper.insertSelective(user);
			return SysResult.ok(user.getUsername());
		} catch (Exception e) {
			return SysResult.ok(user.getUsername());
		}
	}
	//登录
	public SysResult login(String username, String password) throws Exception{
		User user = new User();
		user.setUsername(username);
		List<User> userList = userMapper.select(user);
		if(userList != null && userList.size() > 0){
			User curUser = userList.get(0);
			password = DigestUtils.md5Hex(password);
			String newPassword = curUser.getPassword();
			if(password.equals(newPassword)){
				//形成ticket
				String ticket = "JT_TICKET_" + username + System.currentTimeMillis();
				//用MD5进行加密
				ticket = DigestUtils.md5Hex(ticket);
				//将ticket存放到redis中
				redisService.set(ticket, MAPPER.writeValueAsString(curUser));
				return SysResult.ok(ticket);
			}else{
				return SysResult.build(201, "密码不正确");
			}
		}else{
			return SysResult.build(201, "用户名不存在");
		}
	}
	//根据ticket查询用户信息、
	public SysResult queryByTicket(String ticket){
		 String jsonDate = redisService.get(ticket);
		 return SysResult.ok(jsonDate);
	}
}
