package com.bjpowernode.cms.cms.home.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bjpowernode.cms.cms.home.service.IHomeService;

@Component // 把本类声明spring组件
@Aspect // 在本类上开启切面技术(把这个类声明成切面)

public class HomeInterceptor {
	private Log logger = LogFactory.getLog(HomeInterceptor.class);
	@Autowired
	private IHomeService homeService;
	// annotation(as) 括号放的是注解类型变量

	@After("within(com.bjpowernode.cms.cms..*) && @annotation(as)")
	public void run(AutoStatic as) {
		homeService.run_static();// 调用首页静态化方法
		logger.info("调用首页静态化方法");
	}

}
