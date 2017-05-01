package com.common.dao;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bjpowernode.cms.sys.dict.model.Dict;
import com.bjpowernode.cms.sys.dict.model.DictCond;
import com.bjpowernode.cms.sys.dict.service.IDictService;
import com.bjpowernode.cms.sys.dicttype.model.DictType;
import com.bjpowernode.cms.sys.dicttype.model.DictTypeCond;
import com.bjpowernode.cms.sys.dicttype.service.IDictTypeService;
import com.bjpowernode.cms.sys.param.model.Param;
import com.bjpowernode.cms.sys.param.model.ParamCond;
import com.bjpowernode.cms.sys.param.service.IParamService;

/**
 * @类说明:字典/参数辅助工具类
 * @author GZZ
 */
@Component

public class DictParamUtil {

	private Map<String, String> paramMap = new HashMap<>();// 用来存放系统参数

	private Map<Integer, Map<Integer, String>> typeMap = new HashMap<>();// 用来存放数据字典

	@Autowired
	private IDictService dictService;// 注入字典的service接口
	@Autowired
	private IDictTypeService typeService;// 注入字典类型的service接口
	@Autowired
	private IParamService paramService;// 系统参数service接口

	private Log logger = LogFactory.getLog(DictParamUtil.class);

	/**
	 * @功能描述: 初始化系统参数Map:从数据里读取出来list>>>map
	 */
	@PostConstruct // spring扫描结束时就执行这个方法
	// lamda java
	public void initParma() {
		// 从数据读取所有系统参数
		paramMap.clear();
		List<Param> paramList = paramService.queryAllObj(new ParamCond());
		for (Param param : paramList) {/// 转存的参数map中
			paramMap.put(param.getParam_key(), param.getParam_value());
		}
		logger.info("系统参数初始化完成");
		logger.info(paramMap);
	}

	/**
	 * @功能描述: 初始化数据字典Map
	 */
	@PostConstruct // spring扫描结束时就执行这个方法
	public void initType() {
		typeMap.clear();
		List<DictType> typeList = typeService.queryAllObj(new DictTypeCond());// 查询一共有多少组数据
		Map<Integer, String> dictMap;
		DictCond cond;
		List<Dict> dictList;
		for (DictType dictType : typeList) {
			dictMap = new HashMap<>();// 每组字典值的map,每组都新开内存空间
			cond = new DictCond();
			cond.setType_code_c(dictType.getType_code());// 构造查询字典表的条件

			dictList = dictService.queryAllObj(cond);// 每组字典值的list
			for (Dict dict : dictList) {// 把list转到dictmap
				dictMap.put(dict.getData_key(), dict.getData_value());
			}

			typeMap.put(dictType.getType_code(), dictMap);

		}
		logger.info("枚举字典初始化完成");
		logger.info(typeMap);
	}

	/**
	 * @功能描述: 构建下拉框参照Map
	 */
	public Map<Integer, Object> getDictMap(Integer typeCode, boolean addBlank) {
		Map<Integer, Object> map = new LinkedHashMap<>();
		if (addBlank) {
			map.put(null, "--请选择--");
		}
		Map<Integer, String> dictMap= typeMap.get(typeCode) ;
		for (Map.Entry<Integer, String> entry : dictMap.entrySet()) {
			map.put(entry.getKey(), entry.getValue());
		}
		return map;
	}

	/**
	 * @功能描述: 按类型编码与关键字查找显示值
	 */
	public String findDictValue(Integer typecode, Integer datakey) {
		return typeMap.get(typecode).get(datakey);
	}

	/**
	 * @功能描述: 参数表按键找值
	 */
	public String findValue(String key) {
		return paramMap.get(key);
	}
}
