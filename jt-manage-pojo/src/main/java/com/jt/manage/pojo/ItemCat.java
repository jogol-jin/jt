package com.jt.manage.pojo;

import java.sql.SQLException;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.mysql.jdbc.PreparedStatement;

@Table(name="tb_item_cat")
public class ItemCat extends BasePojo{
//	@Id
//	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="parent_id")
	private Long parentId;
	private String name;
	private Integer status;
	
	@Column(name="sort_order")
	private Integer sortOrder;
	@Column(name="is_parent")
	private Integer isParent;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}
	public Integer getIsParent() {
		return isParent;
	}
	public void setIsParent(Integer isParent) {
		this.isParent = isParent;
	}
	
	//为EasyUI,tree增加get方法
	public String getText(){
		return this.getName();
	}
	//根据是不是有子节点来决定这个树节点是不是需要打开的
	public String getState() {
		return this.getIsParent()==1 ? "closed" : "open";
	}
	
}
