package com.jt.manage.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.RedisService;
import com.jt.common.vo.ItemCatData;
import com.jt.common.vo.ItemCatResult;
import com.jt.manage.mapper.ItemCatMapper;
import com.jt.manage.pojo.ItemCat;

@Service
public class ItemCatService extends BaseService<ItemCat>{
	//log4j
	private static final Logger log = Logger.getLogger(ItemCatService.class);
	@Autowired
	private ItemCatMapper itemCatMapper;
	@Autowired
	private RedisService redisService;
	private static  ObjectMapper MAPPER = new ObjectMapper();
	
	public List<ItemCat> queryAll(){
		return itemCatMapper.select(null);
	}
	
	//异步加载树，参数为当前节点id
	public List<ItemCat> queryListById(Long id){
		return itemCatMapper.queryListById(id);
	}
	//获取ItemCatResult，为前台页面展现使用
	public ItemCatResult getItemCatAll(){
		//三级结构实现的不好,需要一个一个处理，当需求有变化时，需要重新修改代码
		ItemCatResult result = new ItemCatResult();
		
		//从redis中读取数据
		String key = "JT_ITEM_CAT";
		String jsonData = redisService.get(key);
		if(StringUtils.isNoneEmpty(jsonData)){
			//缓存中有值
			try {
				return MAPPER.readValue(jsonData, ItemCatResult.class);
			} catch (Exception e) {
				return null;
			} 
		}
		
        // 全部查出，并且在内存中生成树形结构
        List<ItemCat> cats = super.queryAll();

        // 转为map存储，key为父节点ID，value为数据集合
        Map<Long, List<ItemCat>> itemCatMap = new HashMap<Long, List<ItemCat>>();
        for (ItemCat itemCat : cats) {
            if (!itemCatMap.containsKey(itemCat.getParentId())) {
                itemCatMap.put(itemCat.getParentId(), new ArrayList<ItemCat>());
            }
            itemCatMap.get(itemCat.getParentId()).add(itemCat);
        }

        // 封装一级对象
        List<ItemCat> itemCatList1 = itemCatMap.get(0L);
        for (ItemCat itemCat : itemCatList1) {
            ItemCatData itemCatData = new ItemCatData();
            itemCatData.setUrl("/products/" + itemCat.getId() + ".html");
            itemCatData.setName("<a href='" + itemCatData.getUrl() + "'>" + itemCat.getName() + "</a>");
            result.getItemCats().add(itemCatData);
            if (itemCat.getIsParent() == 0) {
                continue;
            }

            // 封装二级对象
            List<ItemCat> itemCatList2 = itemCatMap.get(itemCat.getId());
            List<ItemCatData> itemCatData2 = new ArrayList<ItemCatData>();
            itemCatData.setItems(itemCatData2);
            for (ItemCat itemCat2 : itemCatList2) {
                ItemCatData id2 = new ItemCatData();
                id2.setName(itemCat2.getName());
                id2.setUrl("/products/" + itemCat2.getId() + ".html");
                itemCatData2.add(id2);
                if (itemCat2.getIsParent() == 1) {
                    // 封装三级对象
                    List<ItemCat> itemCatList3 = itemCatMap.get(itemCat2.getId());
                    List<String> itemCatData3 = new ArrayList<String>();
                    id2.setItems(itemCatData3);
                    for (ItemCat itemCat3 : itemCatList3) {
                        itemCatData3.add("/products/" + itemCat3.getId() + ".html|" + itemCat3.getName());
                    }
                }
            }
            if (result.getItemCats().size() >= 14) {
                break;
            }
        }
        //写缓存
        try {
			redisService.set(key, MAPPER.writeValueAsString(result));
		} catch (JsonProcessingException e) {
			log.error(e.getMessage());
		}
        return result;
    }
}
