package com.bjpowernode.cms.cms.advert.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.bjpowernode.cms.cms.advert.model.Advert;
import com.bjpowernode.cms.cms.advert.model.AdvertCond;

/**
 * @功能说明: 广告位service接口
 * @author: bjpowernode
 * @date:2016-09-09 14:00:00
 */
public interface IAdvertService {

	int insert(Advert advert,MultipartFile image);

	int delete(Integer ids[]);

	int update(Advert advert,MultipartFile image);

	/**
	 * 查询单个广告位实体类
	 */
	Advert findById(int id);

	/**
	 * 查询广告位分页列表
	 */
	void queryList(AdvertCond cond, Map<String, Object> map);
	/**
	 * 按条件查询记录个数
	 */
	int queryCount(AdvertCond cond);
	
	int updateStatus(Advert advert);
}
