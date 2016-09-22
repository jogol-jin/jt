package com.jt.manage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.manage.mapper.ItemCatMapper;
import com.jt.manage.pojo.ItemCat;

@Service
public class ItemCatService{
	@Autowired
	private ItemCatMapper itemCatMapper;
	
	public List<ItemCat> queryAll(){
		return itemCatMapper.select(null);
	}
	
	//异步加载树，参数为当前节点id
	public List<ItemCat> queryListById(Long id){
		return itemCatMapper.queryListById(id);
	}
}
