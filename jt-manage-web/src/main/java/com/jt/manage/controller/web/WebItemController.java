package com.jt.manage.controller.web;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.RedisService;
import com.jt.manage.pojo.Item;
import com.jt.manage.pojo.ItemDesc;
import com.jt.manage.service.ItemCatService;
import com.jt.manage.service.ItemService;

@Controller
public class WebItemController {
	
	@Autowired
	private ItemService itemService;
    @Autowired
    private RedisService redisService;
    private static final ObjectMapper MAPPER = new ObjectMapper();
	//log4j
	private static final Logger log = Logger.getLogger(ItemCatService.class);
	
	
	@RequestMapping("/web/item/{itemId}")
	@ResponseBody
	public Item getItem(@PathVariable Long itemId){
		String key = "JT_ITEM_" + itemId;
		String jsonData = redisService.get(key);
		if(StringUtils.isNoneEmpty(jsonData)){
			try {
				return MAPPER.readValue(jsonData, Item.class);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		}
		Item item = itemService.queryById(itemId);
		try {
			redisService.set(key, MAPPER.writeValueAsString(item));
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return item;
	}
	
	@RequestMapping("/web/itemdesc/{itemId}")
	@ResponseBody
	public ItemDesc getItemDesc(@PathVariable Long itemId){
		return itemService.getItemDescByItemId(itemId);
	}
}
