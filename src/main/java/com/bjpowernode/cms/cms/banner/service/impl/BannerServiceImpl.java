package com.bjpowernode.cms.cms.banner.service.impl;

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

import com.bjpowernode.cms.cms.banner.dao.IBannerDao;
import com.bjpowernode.cms.cms.banner.model.Banner;
import com.bjpowernode.cms.cms.banner.model.BannerCond;
import com.bjpowernode.cms.cms.banner.service.IBannerService;
import com.bjpowernode.cms.cms.home.util.AutoStatic;
import com.common.dao.DictParamUtil;
import com.common.redis.RedisUtil;
import com.common.util.FileUtil;

/**
 * @功能描述：轮播图service实现类
 * @author gzz
 * @date 2016-09-06 09:18:55
 */
@Service
public class BannerServiceImpl implements IBannerService {
	@Autowired
	private IBannerDao dao; // 注入轮播图dao接口
	@Autowired
	private DictParamUtil util;// 注入字典参数工具类
	@Autowired
	private RedisUtil redisUtil;// redis工具

	private final Log logger = LogFactory.getLog(BannerServiceImpl.class);// 日志类

	@Override
	@AutoStatic // 当这个方法执行完成之后去执行首页静态化
	public int insert(Banner banner, MultipartFile image) {
		if (!image.isEmpty())
			saveFile(banner, image);// 调用保存文件方法
		// 字段默认值通常 service
		banner.setStatus(1);// 状态默认是可用
		banner.setTs(new Date());

		clearRedis(banner.getType());// 调用清除轮播图缓存数据

		return dao.insert(banner);
	}

	/**
	 * 保存文件方法
	 */
	private void saveFile(Banner banner, MultipartFile image) {

		String OriName = image.getOriginalFilename();// 原始文件名
		String extName = OriName.substring(OriName.indexOf("."));// 扩展名
		Long longTime = new Date().getTime();// 时间长整型
		String banner_path = "banner/";// 轮播图保存路径
		String fileRoot = util.findValue("fileRoot");// 文件写入根路径
		String fullName = fileRoot + banner_path + longTime + extName;// 包括路径的完整文件名
		banner.setPicture_path(fullName);// 设置文件的保存路径
		String fileUrl = util.findValue("fileUrl");// 文件展示根

		banner.setPicture_url(fileUrl + banner_path + longTime + extName);// 设置文件的展示路径
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
		Banner banner = dao.findById(id);
		clearRedis(banner.getType());// 调用清除轮播图缓存数据
		if (banner.getPicture_path() != null && !banner.getPicture_path().equals("")) {
			new File(banner.getPicture_path()).delete();
		}
	}

	@Override
	@AutoStatic // 当这个方法执行完成之后去执行首页静态化
	public int update(Banner banner, MultipartFile image) {
		if (!image.isEmpty()) {
			deleteFile(banner.getId());// 调用删除文件方法
			saveFile(banner, image);// 调用保存文件方法
		}
		banner.setTs(new Date());
		// 如果终端类型不能修改
		clearRedis(banner.getType());// 调用清除轮播图缓存数据
		// 如果终端类型可以修改
		// clearRedis(1);//调用清除轮播图缓存数据
		// clearRedis(2);//调用清除轮播图缓存数据
		// clearRedis(3);//调用清除轮播图缓存数据
		return dao.update(banner);
	}

	// 按终端类型清除轮播图缓存数据
	private void clearRedis(int type) {
		redisUtil.clearRedis("banner_" + type);
	}

	@Override
	@AutoStatic // 当这个方法执行完成之后去执行首页静态化
	public int delete(Integer ids[]) {
		for (Integer id : ids) {
			deleteFile(id);// 调用删除文件方法
		}

		return dao.delete(ids);
	}

	@Override
	public Banner findById(int id) {
		Banner banner = dao.findById(id);
		banner.setType_text(util.findDictValue(10, banner.getType()));// 翻译终端类型显示值
		banner.setStatus_text(util.findDictValue(11, banner.getStatus()));// 翻译轮播图状态显示值
		return banner;
	}

	@Override
	public void queryList(BannerCond cond, Map<String, Object> map) {
		dao.queryList(cond, map);
		@SuppressWarnings("unchecked")
		List<Banner> list = (List<Banner>) map.get("dataList");
		for (Banner banner : list) {
			banner.setType_text(util.findDictValue(10, banner.getType()));// 翻译终端类型显示值
			banner.setStatus_text(util.findDictValue(11, banner.getStatus()));// 翻译轮播图状态显示值
		}

	}

	@Override
	public int queryCount(BannerCond cond) {
		return dao.queryCount(cond);
	}

	@Override
	@AutoStatic // 当这个方法执行完成之后去执行首页静态化
	public int updateStatus(Banner banner) {
		clearRedis(banner.getType());// 调用清除轮播图缓存数据
		////////////////
		System.out.println();
		return dao.updatestatus(banner);
	}

}
