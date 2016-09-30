package com.jt.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.web.service.ItemService;

@Controller
public class ItemController {
	@Autowired
	private ItemService itemService;
	
	@RequestMapping("/items/{itemId}")
	public String getItem(@PathVariable Long itemId, Model model) throws Exception{
		model.addAttribute("item", itemService.getItem(itemId));
		model.addAttribute("itemdesc", itemService.getItemDesc(itemId));
		return "item";
	}
}
