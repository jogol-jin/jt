package com.jt.cart.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.cart.mapper.CartMapper;
import com.jt.cart.pojo.Cart;
import com.jt.cart.pojo.Item;
import com.jt.common.service.HttpClientService;
import com.jt.common.vo.SysResult;

@Service
public class CartService extends BaseService{
	
	@Autowired
	private CartMapper cartMapper;
	@Autowired
	private HttpClientService httpClientService;
	private static final ObjectMapper MAPPER = new ObjectMapper();
	
	//保存购物车数据
	public SysResult saveCart(Cart cart){
		//判断，如果是当前用户，他的商品是否存在，如果存在只修改数量
		
		Cart params = new Cart();
		params.setUserId(cart.getUserId());
		params.setItemId(cart.getItemId());
		try{
			Integer count = cartMapper.selectCount(params);
			if(count == 0){
				//不存在
				//冗余字段的信息通过heetclient查询
				String url = "http://manage.jt.com/web/item/" + cart.getItemId();
				String jsonData = httpClientService.doGet(url);
				Item item = MAPPER.readValue(jsonData, Item.class);
				cart.setItemPrice(item.getPrice());
				cart.setItemTitle(item.getTitle());
				cart.setCreated(new Date());
				cart.setUpdated(cart.getCreated());
				
				try {
					cart.setItemImage(item.getImage().split(",")[0]);
				} catch (Exception e) {}
				
				
				cartMapper.insertSelective(cart);
			}else{
				List<Cart> cartList = cartMapper.select(params);
				if(cartList != null && cartList.size()>0){
					Cart _cart = cartList.get(0);
					_cart.setNum(_cart.getNum() + cart.getNum());
					_cart.setUpdated(new Date());
					cartMapper.updateByPrimaryKeySelective(_cart);
				}
			}
			return SysResult.ok();
		}catch(Exception e){
			return SysResult.build(201, "加入购物车失败！！");
		}
	}
	//我的购物车
	public SysResult queryByUserId(Long userId){
		Cart cart = new Cart();
		cart.setUserId(userId);
		List<Cart> cartList = cartMapper.select(cart);
		return SysResult.ok(cartList);
	}
	//商品数量的修改
	public SysResult updateNum(Long userId, Long itemId, Integer num) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("num", num);
		params.put("userId", userId);
		params.put("itemId", itemId);
		
		cartMapper.updateNum(params);
		return SysResult.ok();
	}
	public SysResult deleteById(Long userId, Long itemId) {
		Cart cart = new Cart();
		cart.setUserId(userId);
		cart.setItemId(itemId);
		cartMapper.delete(cart);
		return SysResult.ok();
	}
}
