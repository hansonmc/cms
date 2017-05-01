package com.bjpowernode.cms.cms.home.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bjpowernode.cms.cms.home.service.IHomeService;
import com.common.dao.DictParamUtil;
import com.common.http.HttpUtil;
import com.common.util.FileUtil;

@Service
public class HomeServiceImpl implements IHomeService {

	@Autowired
	private HttpUtil httpUtil;// http请求工具
	@Autowired
	private DictParamUtil util;// 注入字典参数工具类

	@Override
	public void run_static() {
		String shop_pc_path = util.findValue("shop_pc_path");// 从参数表取pc端应用根路径
		String static_host = util.findValue("static_host");// 静态资源所在的虚拟主机前缀

		String html = httpUtil.get(shop_pc_path + "home");// 从webshop请求页面对应的文本

		shop_pc_path = shop_pc_path.equals("/") ? "" : shop_pc_path;
		html = html.replaceAll("/" + shop_pc_path + "resources/", static_host + "resources/");

		FileUtil.writeFile("D:/static/home/index.html", html);
	}

}
