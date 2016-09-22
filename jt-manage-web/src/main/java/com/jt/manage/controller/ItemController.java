package com.jt.manage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.EasyUIResult;
import com.jt.common.vo.SysResult;
import com.jt.manage.pojo.Item;
import com.jt.manage.service.ItemService;

@Controller
@RequestMapping("/item")
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	//商品的新增保存
	@RequestMapping("/save")
	@ResponseBody
	public SysResult saveItem(Item item, String desc){
		return itemService.saveItem(item, desc);
	}
	//商品查询列表
	@ResponseBody
	@RequestMapping("/query")
	public EasyUIResult queryList(Integer page, Integer rows){
		return itemService.queryList(page, rows);
	}
	
	@RequestMapping("update")
	@ResponseBody
	public SysResult updateItem(Item item, String desc){
		return itemService.updateItem(item, desc);
	}
	//商品删除
	@RequestMapping("/delete")
	@ResponseBody
	public SysResult deleteItem(@RequestParam(value="ids") String id){
		String[] ids = id.split(",");
		itemService.deleteItem(ids);
		return SysResult.ok();
	}
	
	@RequestMapping("/query/item/desc/{itemId}")
	@ResponseBody
	public SysResult updateItemDesc(@PathVariable Long itemId){
		return itemService.getItemDesc(itemId);
	}
}
