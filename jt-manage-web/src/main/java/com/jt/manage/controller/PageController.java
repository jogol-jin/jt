package com.jt.manage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
	
	//转向jsp页面
	@RequestMapping("/page/{pageName}")
	public String goHome(@PathVariable String pageName){
		return pageName;
	}
}
