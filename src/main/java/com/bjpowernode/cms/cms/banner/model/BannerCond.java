package com.bjpowernode.cms.cms.banner.model;

import java.util.Date;

import com.common.condition.BaseCondition;

/**
 * @功能描述：轮播图查询条件实体类
 * @author gzz
 * @date 2016-09-06 09:18:55
 */

public class BannerCond extends BaseCondition {

	private Integer id_c;// 主键
	private String name_c;// 名称--列表页面
	private String name_v;// 名称--验证
	private Integer order_num_c;// 顺序
	private String picture_path_c;// 存储路径
	private String picture_url_c;// 访问地址
	private String jump_url_c;// 跳转地址
	private String remark_c;// 备注
	private Integer status_c;// 状态
	private Integer type_c;// 所属终端
	private Date ts_c;// 时间戳

	@Override
	public void addCondition() {
		
		add(name_c, "AND name LIKE ?", 3);
		add(order_num_c, "AND order_num=?");
		add(status_c, "AND status=?");
		add(type_c, "AND type=?");
		
		add(name_v, "AND name =?");
		add(id_c, "AND id<>?");
	}

	
	
	public String getName_v() {
		return name_v;
	}



	public void setName_v(String name_v) {
		this.name_v = name_v;
	}



	public Integer getId_c() {
		return id_c;
	}

	public void setId_c(Integer id_c) {
		this.id_c = id_c;
	}

	public String getName_c() {
		return name_c;
	}

	public void setName_c(String name_c) {
		this.name_c = name_c;
	}

	public Integer getOrder_num_c() {
		return order_num_c;
	}

	public void setOrder_num_c(Integer order_num_c) {
		this.order_num_c = order_num_c;
	}

	public String getPicture_path_c() {
		return picture_path_c;
	}

	public void setPicture_path_c(String picture_path_c) {
		this.picture_path_c = picture_path_c;
	}

	public String getPicture_url_c() {
		return picture_url_c;
	}

	public void setPicture_url_c(String picture_url_c) {
		this.picture_url_c = picture_url_c;
	}

	public String getJump_url_c() {
		return jump_url_c;
	}

	public void setJump_url_c(String jump_url_c) {
		this.jump_url_c = jump_url_c;
	}

	public String getRemark_c() {
		return remark_c;
	}

	public void setRemark_c(String remark_c) {
		this.remark_c = remark_c;
	}

	public Integer getStatus_c() {
		return status_c;
	}

	public void setStatus_c(Integer status_c) {
		this.status_c = status_c;
	}

	public Integer getType_c() {
		return type_c;
	}

	public void setType_c(Integer type_c) {
		this.type_c = type_c;
	}

	public Date getTs_c() {
		return ts_c;
	}

	public void setTs_c(Date ts_c) {
		this.ts_c = ts_c;
	}

}
