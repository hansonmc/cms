package com.bjpowernode.cms.cms.banner.model;

import java.util.Date;

/**
 * @功能描述：轮播图实体类
 * @author gzz
 * @date 2016-09-06 09:18:55
 */

public class Banner {

	private Integer id;// 主键
	private String name;// 名称
	private String picture_path;// 存储路径
	private String picture_url;// 访问地址
	private Integer order_num;// 顺序
	private String jump_url;// 跳转地址
	private String remark;// 备注
	private Integer status;// 状态
	private Integer type;// 所属终端
	private Date ts;// 时间戳
	/// 查询显示的辅助字段
	private String status_text;// 状态
	private String type_text;// 所属终端

	
	public String getStatus_text() {
		return status_text;
	}

	public void setStatus_text(String status_text) {
		this.status_text = status_text;
	}

	public String getType_text() {
		return type_text;
	}

	public void setType_text(String type_text) {
		this.type_text = type_text;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getOrder_num() {
		return order_num;
	}

	public void setOrder_num(Integer order_num) {
		this.order_num = order_num;
	}

	public String getPicture_path() {
		return picture_path;
	}

	public void setPicture_path(String picture_path) {
		this.picture_path = picture_path;
	}

	public String getPicture_url() {
		return picture_url;
	}

	public void setPicture_url(String picture_url) {
		this.picture_url = picture_url;
	}

	public String getJump_url() {
		return jump_url;
	}

	public void setJump_url(String jump_url) {
		this.jump_url = jump_url;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getTs() {
		return ts;
	}

	public void setTs(Date ts) {
		this.ts = ts;
	}

}
