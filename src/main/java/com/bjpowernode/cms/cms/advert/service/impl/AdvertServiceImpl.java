package com.bjpowernode.cms.cms.advert.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bjpowernode.cms.cms.advert.dao.IAdvertDao;
import com.bjpowernode.cms.cms.advert.model.Advert;
import com.bjpowernode.cms.cms.advert.model.AdvertCond;
import com.bjpowernode.cms.cms.advert.service.IAdvertService;
import com.bjpowernode.cms.cms.banner.service.impl.BannerServiceImpl;
import com.bjpowernode.cms.cms.home.util.AutoStatic;
import com.common.dao.DictParamUtil;
import com.common.redis.RedisUtil;
import com.common.util.FileUtil;

import redis.clients.jedis.Jedis;

/**
 * @功能说明: 广告位service实现类
 * @author: bjpowernode
 * @date:2016-09-09 14:00:00
 */
@Service
public class AdvertServiceImpl implements IAdvertService {
	@Autowired
	private DictParamUtil util;// 字典参数工具类

	@Autowired
	private IAdvertDao dao;

	@Autowired
	private RedisUtil redisUtil;// redis工具

	private final Log logger = LogFactory.getLog(BannerServiceImpl.class);// 日志类

	@Override
	@AutoStatic // 这个方法被 执行后要去执行首页静态化
	public int insert(Advert advert, MultipartFile image) {
		advert.setStatus(1);
		advert.setTs(new Date());
		if (!image.isEmpty()) {
			saveFile(advert, image);// 调用保存文件方法
		}
		clearRedis(advert.getType());// 调用请缓存方法
		return dao.insert(advert);
	}

	/**
	 * 保存文件方法
	 */
	private void saveFile(Advert advert, MultipartFile image) {

		String OriName = image.getOriginalFilename();// 原始文件名
		String extName = OriName.substring(OriName.indexOf("."));// 扩展名
		Long longTime = new Date().getTime();// 时间长整型
		String advert_path = "advert/";// 轮播图保存路径
		String fileRoot = util.findValue("fileRoot");// 文件写入根路径
		String fullName = fileRoot + advert_path + longTime + extName;// 包括路径的完整文件名
		advert.setPicture_path(fullName);// 设置文件的保存路径
		String fileUrl = util.findValue("fileUrl");// 文件展示根

		advert.setPicture_url(fileUrl + advert_path + longTime + extName);// 设置文件的展示路径
		try {
			FileUtil.createDir(fullName);// 如果上级文件夹不存则建立他
			image.transferTo(new File(fullName));// 保存文件
		} catch (IllegalStateException e) {
			logger.error("保存文件时出现IllegalStateException异常");
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("保存文件时出现IOException异常");
			e.printStackTrace();
		}
	}

	/**
	 * 删除文件方法
	 */
	private void deleteFile(int id) {
		/// 删除文件
		Advert advert = dao.findById(id);
		clearRedis(advert.getType());// 调用清除轮播图缓存数据
		if (advert.getPicture_path() != null && !advert.getPicture_path().equals("")) {
			new File(advert.getPicture_path()).delete();
		}
	}

	// 按终端类型清除轮播图缓存数据
	private void clearRedis(int type) {
		redisUtil.clearRedis("advert_" + type);
	}

	@Override
	@AutoStatic // 这个方法被 执行后要去执行首页静态化
	public int delete(Integer[] ids) {
		for (Integer id : ids) {
			deleteFile(id);// 调用删除文件
		}
		return dao.delete(ids);
	}

	@Override
	@AutoStatic // 这个方法被 执行后要去执行首页静态化
	public int update(Advert advert, MultipartFile image) {
		advert.setTs(new Date());
		if (!image.isEmpty()) {
			deleteFile(advert.getId());// 调用删除文件
			saveFile(advert, image);// 调用保存文件
		}
		clearRedis(advert.getType());// 调用请缓存方法
		return dao.update(advert);
	}

	@Override
	public Advert findById(int id) {
		Advert advert = dao.findById(id);
		advert.setStatus_name(util.findDictValue(12, advert.getStatus()));// 翻译广告位状态
		advert.setType_name(util.findDictValue(10, advert.getType()));// 翻译终端类状态
		return advert;
	}

	@Override
	public void queryList(AdvertCond cond, Map<String, Object> map) {
		dao.queryList(cond, map);

		@SuppressWarnings("unchecked")
		List<Advert> list = (List<Advert>) map.get("dataList");
		for (Advert advert : list) {
			advert.setStatus_name(util.findDictValue(12, advert.getStatus()));// 翻译广告位状态
			advert.setType_name(util.findDictValue(10, advert.getType()));// 翻译终端类状态
		}

	}

	/**
	 * 按条件查询记录个数
	 */
	@Override
	public int queryCount(AdvertCond cond) {
		return dao.queryCount(cond);
	}

	@Override
	@AutoStatic // 这个方法被 执行后要去执行首页静态化
	public int updateStatus(Advert advert) {
		clearRedis(advert.getType());// 调用请缓存方法
		return dao.updateStatus(advert);
	}
}
