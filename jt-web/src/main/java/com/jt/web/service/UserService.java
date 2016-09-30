package com.jt.web.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.HttpClientService;
import com.jt.common.vo.SysResult;
import com.jt.web.pojo.User;

@Service
public class UserService{
	@Autowired
	private HttpClientService httpClientService;
	private static final ObjectMapper MAPPER = new ObjectMapper();
	//利用httpClient请求用户注册
	public SysResult doRegister(User user) throws Exception{
		String url = "http://sso.jt.com/user/register";
		Map<String,String> params = new HashMap<String,String>();
		
		params.put("username", user.getUsername());
		params.put("password", user.getPassword());
		params.put("phone", user.getPhone());
		params.put("email", user.getEmail());
		
		String jsonData = httpClientService.doPost(url, params, "utf-8");
		//因为sysresult中有特殊的方法，mapper不能直接转
		//直接读取json串中的status属性,等于200
		JsonNode statusNode = MAPPER.readTree(jsonData);
		Integer status = statusNode.get("status").asInt();
		if(status ==200){
			return SysResult.ok();
		}else{
			JsonNode dataNode = MAPPER.readTree(jsonData);
			String username = dataNode.get("data").asText();
			return SysResult.build(201, "用户注册失败", username);
		}
	}
	public SysResult doLogin(String username, String password) throws Exception {
		//访问单点登录系统http://sso.jt.com/user/login
		String url = "http://sso.jt.com/user/login";
		Map<String, String> params = new HashMap<String, String>();
		params.put("u", username);
		params.put("p", password);
		String jsonData = httpClientService.doPost(url, params, "utf-8");
		JsonNode jsonNode = MAPPER.readTree(jsonData);
		Integer status = jsonNode.get("status").asInt();
		if(status == 200){
			String ticket = jsonNode.get("data").asText();
			return SysResult.ok(ticket);
		}else{
			return SysResult.build(201, "用户名或密码出错");
		}
	}
}
