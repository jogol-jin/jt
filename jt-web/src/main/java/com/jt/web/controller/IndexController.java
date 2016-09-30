package com.jt.web.controller;

import org.apache.http.client.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jt.common.service.HttpClientService;

@Controller
public class IndexController {
	
	@Autowired
	private HttpClient httpClient;
	@Autowired
	private HttpClientService httpClientService;
	
	@RequestMapping("/index")
	public String index(){
		return "index";
	}
}
