package com.jt.manage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.manage.pojo.ItemCat;
import com.jt.manage.service.ItemCatService;

@Controller
public class ItemCatController {
	@Autowired
	private ItemCatService itemCatService;
	
	@ResponseBody
	@RequestMapping("/itemcat/list")
	public List<ItemCat> queryAll(){
		return itemCatService.queryAll();
	}
	@ResponseBody
	@RequestMapping("/item/cat/list")
	public List<ItemCat> queryListById(@RequestParam(defaultValue="0") Long id){
		//判断id是否为null，也可以用注解的形式设置默认值
//		if(id == null){
//			id = 0l;
//		}
		return itemCatService.queryListById(id);
	}
}
