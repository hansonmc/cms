package com.bjpowernode.cms.cms.home.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bjpowernode.cms.cms.home.service.IHomeService;

@Controller
@RequestMapping("home")

public class HomeAction {
	@Autowired
	private IHomeService service;

	// 跳到一个页面
	@RequestMapping("static")
	public String to_home_static() {

		return "cms/home/static";
	}

	// 执行静态化
	@RequestMapping("runstatic")
	public String run_home_static() {
		service.run_static();
		return "cms/home/static";
	}

}
