package com.jt.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.HttpClientService;
import com.jt.common.util.CookieUtils;
import com.jt.web.pojo.User;

@Controller
public class CartInterceptor implements HandlerInterceptor{
	
	private static ThreadLocal<String> tl = null;
	@Autowired
	private HttpClientService httpClientService;
	private static final ObjectMapper MAPPER = new ObjectMapper();
	
	//在执行controller的方法前出发
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String ticket = CookieUtils.getCookieValue(request, UserController.UserCookieName);
		if(StringUtils.isNotEmpty(ticket)){
			//访问SSO业务接口按ticket查询
			String url = "http://sso.jt.com/user/query/" + ticket;
			String jsonData = httpClientService.doGet(url);
			if(StringUtils.isNotEmpty(jsonData)){
				JsonNode jsonNode = MAPPER.readTree(jsonData);
				String curJson = jsonNode.get("data").asText();
				User curUser = MAPPER.readValue(curJson, User.class);
				
				UserThreadLocal.set(curUser);
			}
		}else{
			UserThreadLocal.set(null);
		}
		
		return true;//false代表不放行,日常情况下不管什么情况都要放行，不然就无法执行下去
	}
	
	//在执行controller的方法之后执行
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	//在整个controller进行渲染之后，在转向页面之前执行
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
