package com.jt.cart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.cart.pojo.Cart;
import com.jt.cart.service.CartService;
import com.jt.common.vo.SysResult;

@Controller
public class CartController {
	
	@Autowired
	private CartService cartService;
	//http://cart.jt.com/cart/save
	@RequestMapping("/cart/save")
	@ResponseBody
	public SysResult saveCart(Cart cart){
		return cartService.saveCart(cart);
	}
	
	//http://cart.jt.com/cart/query/{userId}
	//我的购物车
	@RequestMapping("/cart/query/{userId}")
	@ResponseBody
	public SysResult queryByUserId(@PathVariable Long userId){
		return cartService.queryByUserId(userId);
	}
	
	//更新商品数量
	@RequestMapping("/cart/update/num/{userId}/{itemId}/{num}")
	@ResponseBody
	public SysResult updateNum(@PathVariable Long userId, @PathVariable Long itemId, @PathVariable Integer num){
		return cartService.updateNum(userId, itemId, num);
	}
	//删除商品
	@RequestMapping("/cart/delete/{userId}/{itemId}")
	@ResponseBody
	public SysResult delete(@PathVariable Long userId, @PathVariable Long itemId){
		return cartService.deleteById(userId, itemId);
	}
}
