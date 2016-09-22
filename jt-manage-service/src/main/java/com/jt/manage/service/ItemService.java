package com.jt.manage.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jt.common.vo.EasyUIResult;
import com.jt.common.vo.SysResult;
import com.jt.manage.mapper.ItemDescMapper;
import com.jt.manage.mapper.ItemMapper;
import com.jt.manage.pojo.Item;
import com.jt.manage.pojo.ItemDesc;

@Service
public class ItemService extends BaseService<Item>{
	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private ItemDescMapper itemDescMapper;
	
	public SysResult saveItem(Item item, String desc){
		try {
			//初始化信息
			item.setCreated(new Date());
			item.setUpdated(item.getCreated());
			
			itemMapper.insertSelective(item);
			//新增完item id就有了 mybatis会给你设置， 根据mysql的一个函数来设置 select LAST_INSERT_ID
			//这个函数并没有线程问题，因为mysql可以拿到insert的线程
			
			//新增商品描述
			ItemDesc itemDesc = new ItemDesc();
			itemDesc.setItemId(item.getId());
			itemDesc.setItemDesc(desc);
			itemDesc.setCreated(new Date());
			itemDesc.setUpdated(itemDesc.getCreated());
			
			itemDescMapper.insertSelective(itemDesc);
			
			return SysResult.ok();
		} catch (Exception e) {
			return SysResult.build(201, "商品新增失败");
		}
	}
	
	public EasyUIResult queryList(Integer page, Integer rows){
		//开启了分页拦截器的监听，只监听之后的第一条select语句
		PageHelper.startPage(page, rows);
		//被pageHelper拦截，返回的不再是所有的数据，而是分页的数据
		List<Item> itemList = itemMapper.queryList();
		//？？因为多线程的问题,所以不能直接返回这个list集合
		PageInfo<Item> pageInfo = new PageInfo<>(itemList);
		return new EasyUIResult(pageInfo.getTotal(), pageInfo.getList());
	}

	public SysResult updateItem(Item item, String desc) {
		item.setUpdated(new Date());
		itemMapper.updateByPrimaryKeySelective(item);
		
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemId(item.getId());
		itemDesc.setUpdated(new Date());
		itemDesc.setItemDesc(desc);
		itemDescMapper.updateByPrimaryKeySelective(itemDesc);
		return SysResult.ok();
	}
	
	//根据商品ID查询商品描述
	public SysResult getItemDesc(Long itemId){
		ItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(itemId);
		return SysResult.ok(itemDesc);
	}
	
	//商品级联删除
	public SysResult deleteItem(String[] ids){
		//先删除字表信息，再删除主表信息
		itemDescMapper.deleteByIDS(ids);
		itemMapper.deleteByIDS(ids);
		return SysResult.ok();
	}
}
