package com.jt.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.HttpClientService;
import com.jt.common.spring.exetend.PropertyConfig;
import com.jt.web.pojo.ItemDesc;
import com.mysql.jdbc.StringUtils;
import com.jt.web.pojo.Item;
@Service
public class ItemService {
	@Autowired
	private HttpClientService httpClientService;
	private static ObjectMapper MAPPER = new ObjectMapper();
	
	//直接获取属性文件的属性
	@PropertyConfig
	private String MANAGE_URL;
	
	public Item getItem(Long itemId) throws Exception{
		//模拟发起http请求
		String url = "http://manage.jt.com/web/item/" + itemId;
		String jsonData = httpClientService.doGet(url, "utf-8");
		//将json串转换成java对象,第一个参数是json串，第二个参数是转换类，内容必须匹配
		Item item = MAPPER.readValue(jsonData, Item.class);
		
		return item;
	}
	public ItemDesc getItemDesc(Long itemId) throws Exception {
		String url = "http://manage.jt.com/web/itemdesc/" + itemId;
		String jsonData = httpClientService.doGet(url, "utf-8");
		if(StringUtils.isEmptyOrWhitespaceOnly(jsonData)){
			return null;
		}
		ItemDesc itemDesc = MAPPER.readValue(jsonData, ItemDesc.class);
		return itemDesc;
	}
}
