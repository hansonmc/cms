package com.bjpowernode.cms.sys.param.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.common.dao.DictParamUtil;

@Component // 把本类声明spring组件
@Aspect // 在本类上开启切面技术(把这个类声明成切面)

public class ParamInterceptor {
	private Log logger = LogFactory.getLog(ParamInterceptor.class);
	@Autowired
	private DictParamUtil util;// 注入字典参数工具类
	// annotation(initParam) 括号放的是注解类型变量

	@After("within(com.bjpowernode.cms.sys.param.service.impl..*) && @annotation(initParam)")
	public void run(InitParam initParam) {
		util.initParma();// 调用参数初始化方法
		logger.info("重新调用参数初始化方法");
	}

}
