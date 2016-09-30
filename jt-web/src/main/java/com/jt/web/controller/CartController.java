package com.jt.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.SysResult;
import com.jt.web.pojo.Cart;
import com.jt.web.pojo.User;
import com.jt.web.service.CartService;

@Controller
public class CartController {
	
	@Autowired
	private CartService cartService;
	
	//加入商品到购物车
	@RequestMapping("/cart/add/{itemId}")
	public String toCart(@PathVariable Long itemId,Integer num) throws Exception{
		Long userId = 7l;
		Integer status = cartService.add(userId, itemId, num);
		if(status == 200){
			//springMVC返回值redirect时必须写全路径名,既浏览器上写的内容
			return "redirect:/cart/show.html";
		}else{
			throw new RuntimeException("新增上商品到购物车失败");
		}
	}
	//我的购物车
	@RequestMapping("/cart/show")
	public String show(Model model) throws Exception{
//		Long userId = 7l;
		User curUser = UserThreadLocal.get();
		if(curUser == null){
			return "login";
		}
		Long userId = curUser.getId();
		List<Cart> cartList = cartService.queryByUserId(userId);
		model.addAttribute("cartList", cartList);
		return "cart";
	}
	//商品数量的增减
	//http://cart.jt.com/cart/update/num/{userId}/{itemId}/{num}
	@RequestMapping("/cart/update/num/{itemId}/{num}")
	@ResponseBody
	public SysResult updateNum(@PathVariable Long itemId,@PathVariable Integer num) throws Exception{
		Long userId = 7l;
		return cartService.updateNum(userId, itemId, num);
	}
	//商品的删除
	///cart/delete/1474391945.html
	@RequestMapping("/cart/delete/{itemId}")
	@ResponseBody
	public String deleteCart(@PathVariable Long itemId) throws Exception{
		Long userId = 7l;
		cartService.delete(userId, itemId);
		return "redirect:/cart/show.html";
	}
}
