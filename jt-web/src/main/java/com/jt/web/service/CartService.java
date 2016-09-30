package com.jt.web.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.HttpClientService;
import com.jt.common.vo.SysResult;
import com.jt.web.pojo.Cart;

@Service
public class CartService{
	
	@Autowired
	private HttpClientService httpClientService;
	
	private static final ObjectMapper MAPPER = new ObjectMapper();
	
	public Integer add(Long userId, Long itemId, Integer num) throws Exception {
		String url = "http://cart.jt.com/cart/save";
		Map<String, String> params = new HashMap<String, String>();
		params.put("userId", String.valueOf(userId));
		params.put("itemId", String.valueOf(itemId));
		params.put("num", String.valueOf(num));
		String jsonData = httpClientService.doPost(url, params, "utf-8");
		JsonNode jsonNode = MAPPER.readTree(jsonData);
		Integer status = jsonNode.get("status").asInt();
		return status;
	}
	//我的购物车
	public List<Cart> queryByUserId(Long userId) throws Exception {
		String url = "http://cart.jt.com/cart/query/" + userId;
		String jsonData = httpClientService.doGet(url);
		JsonNode jsonNode = MAPPER.readTree(jsonData);
		JsonNode cartListNode = jsonNode.get("data");
		return MAPPER.readValue(cartListNode.traverse(),
                MAPPER.getTypeFactory().constructCollectionType(List.class, Cart.class));
	}
	public SysResult updateNum(Long userId, Long itemId, Integer num) throws Exception {
		String url = "http://cart.jt.com/cart/update/num/"+userId+"/"+itemId+"/"+num;
		httpClientService.doGet(url, "utf-8");
		return SysResult.ok();
	}
	public void delete(Long userId, Long itemId) throws Exception {
		String url = "http://cart.jt.com/cart/delete/"+userId+"/"+itemId;
		httpClientService.doGet(url, "utf-8");
	}

}
