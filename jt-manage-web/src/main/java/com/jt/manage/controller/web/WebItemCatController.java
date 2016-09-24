package com.jt.manage.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.ItemCatResult;
import com.jt.manage.service.ItemCatService;

@Controller
public class WebItemCatController {
	@Autowired
	private ItemCatService itemCatService;
	
	@ResponseBody
	@RequestMapping("/web/itemcat/all")
	public ItemCatResult getItemCatAll(){
		return itemCatService.getItemCatAll();
	}
}
