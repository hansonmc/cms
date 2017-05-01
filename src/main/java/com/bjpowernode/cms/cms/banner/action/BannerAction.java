package com.bjpowernode.cms.cms.banner.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.bjpowernode.cms.cms.banner.model.Banner;
import com.bjpowernode.cms.cms.banner.model.BannerCond;
import com.bjpowernode.cms.cms.banner.service.IBannerService;
import com.common.dao.DictParamUtil;

/**
 * @功能描述：轮播图控制器类
 * @author gzz
 * @date 2016-09-06 09:18:55
 */
@Controller
@RequestMapping("banner")
public class BannerAction {
	@Autowired
	private IBannerService service; // 注入轮播图servcie接口

	@Autowired
	private DictParamUtil util;// 注入字典参数工具类
	private final Log logger = LogFactory.getLog(BannerAction.class);// 日志类

	/**
	 * @功能描述：转到列表页面
	 */
	@RequestMapping("list")
	public String queryList(@ModelAttribute("cond") BannerCond cond, Map<String, Object> map) {
		service.queryList(cond, map);
		map.put("typeMap", util.getDictMap(10, true));// 生成终端类型的map
		map.put("statusMap", util.getDictMap(11, true));// 生成轮播图状态的map
		return "cms/banner/list";
	}

	/**
	 * @功能描述：转到新增页面
	 */
	@RequestMapping("toinsert")
	public String toinsert(@ModelAttribute("banner") Banner banner, Map<String, Object> map) {
		map.put("typeMap", util.getDictMap(10, false));// 生成终端类型的map
		return "cms/banner/insert";
	}

	/**
	 * @功能描述：保存数据-新增
	 */
	@RequestMapping("insert")
	// BindingResult 用来绑定验证结果
	// <form:errors path="name" /> 跟BindingResult配合使用
	public String insert(Banner banner, BindingResult result, Map<String, Object> map ,MultipartFile image) {
		validate(banner, result, 1);// 调用验证方法
		if (result.hasErrors()) {// 存在验证未通过项
			map.put("typeMap", util.getDictMap(10, false));// 生成终端类型的map
			return "cms/banner/insert";
		}
		service.insert(banner,  image);
		return "redirect:list";
	}

	/**
	 * @功能描述：公用验证方法method=2时是在修改处调用
	 */
	private void validate(Banner banner, BindingResult result, int method) {
		BannerCond cond = new BannerCond();// 构造查询条件
		cond.setName_v(banner.getName());
		// if (banner.getId() != null) {
		// cond.setId_c(banner.getId());
		// }
		if (method == 2) {
			cond.setId_c(banner.getId());
		}
		int count = service.queryCount(cond);// 到数据去查询当前输入的轮播图名有几个
		if (count > 0) {// 验证未通过
			result.rejectValue("name", "", "[轮播图名称]不能重复！！");
		}
	}

	/**
	 * @功能描述：删除数据
	 */
	@RequestMapping("delete")
	@ResponseBody
	// springmvc接收json数据同时转成数据
	// list<user> 用在批量插入数据
	public Integer delete(@RequestParam("ids[]") Integer ids[]) {
		return service.delete(ids);
	}

	/**
	 * @功能描述：转到修改页面
	 */
	@RequestMapping("toupdate")
	public String toupdate(Map<String, Object> map, int id) {
		map.put("banner", service.findById(id));
		map.put("typeMap", util.getDictMap(10, false));// 生成终端类型的map
		return "cms/banner/update";
	}

	/**
	 * @功能描述： 修改 --保存数据
	 */
	@RequestMapping("update")
	public String update(Banner banner, BindingResult result, Map<String, Object> map, MultipartFile image) {
		validate(banner, result, 2);// 调用验证方法
		if (result.hasErrors()) {// 存在验证未通过项
			map.put("typeMap", util.getDictMap(10, false));// 生成终端类型的map
			return "cms/banner/update";
		}
		service.update(banner,image);
		return "redirect:list";
	}

	/**
	 * @功能描述：转到详细页面
	 */
	@RequestMapping("detail")
	public String detail(Map<String, Object> map, int id) {
		map.put("banner", service.findById(id));
		return "cms/banner/detail";
	}

	/**
	 * @功能描述：修改记录的状态
	 */
	@RequestMapping("updatestatus")
	public String updateStatus(Banner banner) {
		service.updateStatus(banner);
		return "redirect:list";
	}

	/**
	 * @功能描述： 日期属性编辑器
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));// true允许为空
	}
}
