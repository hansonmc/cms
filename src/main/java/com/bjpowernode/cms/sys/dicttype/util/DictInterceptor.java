package com.bjpowernode.cms.sys.dicttype.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.common.dao.DictParamUtil;

@Component // 把本类声明spring组件
@Aspect // 在本类上开启切面技术(把这个类声明成切面)

public class DictInterceptor {
	private Log logger = LogFactory.getLog(DictInterceptor.class);
	
	@Autowired
	private DictParamUtil util;// 注入字典参数工具类
	
	// annotation(initDict) 括号放的是注解类型变量

	@After("within(com.bjpowernode.cms.sys..*) && @annotation(initDict)")
	public void run(InitDict initDict) {
		util.initType();// 调用字典字典初始化方法
		logger.info("重新调用字典初始化方法");
	}

}
