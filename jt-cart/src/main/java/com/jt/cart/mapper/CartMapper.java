package com.jt.cart.mapper;

import java.util.Map;

import com.jt.cart.mapper.base.mapper.SysMapper;
import com.jt.cart.pojo.Cart;

public interface CartMapper extends SysMapper<Cart>{

	void updateNum(Map<String, Object> params);

}
