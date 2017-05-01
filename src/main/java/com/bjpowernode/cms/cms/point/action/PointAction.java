package com.bjpowernode.cms.cms.point.action;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.common.util.Util;
import com.bjpowernode.cms.cms.point.model.Point;
import com.bjpowernode.cms.cms.point.model.PointCond;
import com.bjpowernode.cms.cms.point.service.IPointService;

/**
 * @类说明:卖点图控制器类
 *
 * @author:gzz_gzz@163.com
 * @date:2016-09-10 14:43:41
 **/
@Controller
@RequestMapping("point")
public class PointAction {
	private final Log logger = LogFactory.getLog(getClass());
	@Autowired
	private IPointService service; // 卖点图Service

	/**
	 * @方法说明:跳转到新增页面
	 **/
	@RequestMapping("toInsert")
	public String toInsert(Map<String, Object> map,@ModelAttribute("point")Point point, HttpSession session) {
		if (Util.getTimeOut(session)) { // 登录过期跳转到登录页或弹出登录窗口
			return "redirect:/user/tologin?msg=1";
		}
		logger.debug("用户名:" + Util.getLoginName(session) + ",登录名:" + Util.getLoginId(session) + ",用户ID(主键):" + Util.getUser(session).getId());
		return "/cms/point/insert";
	}

	/**
	 * @方法说明:新增记录
	 **/
	@RequestMapping("insert")
	public String insert(Map<String, Object> map, @ModelAttribute("point") Point point, BindingResult result) {
		validate(point, result, 1);// 调用新增验证方法
		if (result.hasErrors()) {
			return "/cms/point/insert";
		}
		service.insert(point);
		return "redirect:/point/list";
	}

	/**
	 * @方法说明:新增/修改后台验证
	 **/
	private void validate(Point point, BindingResult result, int method) {//method:1新增2修改
		//ValidationUtils.rejectIfEmpty(result, "field01", "", "字段01不能为空");
		//ValidationUtils.rejectIfEmptyOrWhitespace(result, "field02", "", "字段02不能为空或空格");
		PointCond cond = new PointCond();
		//cond.setId_c(point.getField03());
		if (method == 2) {
		
		}
		if (service.findCountByCond(cond) > 0) {
			//result.rejectValue("result", "", "字段03不能重复!");
		}
	}

	/**
	 * @方法说明:删除记录(多条)
	 **/
	@RequestMapping("delete")
	public String delete(Map<String, Object> map, String id) {
		service.delete(id);
		return "redirect:/point/list";
	}

	/**
	 * @方法说明:跳转到修改页面
	 **/
	@RequestMapping("toUpdate")
	public String toUpdate(Map<String, Object> map, Integer id) {
		map.put("point",service.findById(id));
		return "/cms/point/update";
	}

	/**
	 * @方法说明:修改记录
	 **/
	@RequestMapping("update")
	public String update(Map<String, Object> map, @ModelAttribute("point")Point point, BindingResult result) {
		validate(point, result, 2);// 调用修改验证方法
		if (result.hasErrors()) {
			return "/cms/point/update";
		}
		service.update(point);
		return "redirect:/point/list";
	}

	/**
	 * @方法说明:按条件查询分页列表页面
	 **/
	@RequestMapping("list")
	public String queryList(Map<String, Object> map, @ModelAttribute("cond") PointCond cond ) {
		service.queryList(cond, map);
		return "/cms/point/list";
	}

	/**
	 * @方法说明:跳转到详细页面
	 **/
	@RequestMapping("detail")
	public String detail(Map<String, Object> map, Integer id) {
		map.put("point",service.findById(id));
		return "/cms/point/detail";
	}

	/**
	 * @方法说明:日期属性编辑器(新增/修改/查询条件中String自动转换成Date)
	 **/
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));//true允许为空
	}

	/************************************************************
	 * 以下方法请跟据实际业务进行取舍或修改、把不用的方法清理掉!!!!*
	 ************************************************************/

	/**
	 * @方法说明:导出Excel
	 **/
	@RequestMapping("export")
	public String exportExcel(@ModelAttribute("cond") PointCond cond, HttpServletResponse response, HttpSession session) {
		try {
			String name = null;
			if (Util.getTimeOut(session)) {
				return "redirect:/user/tologin?msg=1";
			} else {
				name = Util.getLoginName(session);
			}
			service.exportExcel(cond, response, name);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("导出卖点图列表时发生异常!");
		}
		return null;
	}

	/**
	 * @方法说明:导入Excel
	 **/
	@RequestMapping("import")
	public String importExcel(Map<String, Object> map, MultipartFile file) {
		try {
			map.put("msg", service.importExcel(file));
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", "导入卖点图列表时发生异常,请您核对模版是否正确!");
			logger.error("导入卖点图列表时发生异常!");
		}
		return "/common/importExcel";
	}

	/**
	 * @方法说明:Json列表(Ajax调用)
	 **/
	@RequestMapping("jsonList")
	//@RequestMapping("jsonList", produces = "text/html;charset=UTF-8")//反回String类型出现乱码解决办法
	@ResponseBody
	public Map<String, Object> ajaxJson(@ModelAttribute("cond") PointCond cond) {
		//cond.setItem_id_c("字段值");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("jsonData", service.queryAllObj(cond));
		return map;
	}

	/**
	 * @方法说明:按条件查询分页列表(参照选择页)
	 **/
	@RequestMapping("ref")
	public String queryRef(Map<String, Object> map, @ModelAttribute("cond") PointCond cond ) {
		service.queryList(cond, map);
		return "/cms/point/listRef";
	}

	/**
	 * @方法说明:简单列表;带查询,无增删改,分页可选
	 **/
	@RequestMapping("listSimple")
	public String queryListSimple(Map<String, Object> map, @ModelAttribute("cond") PointCond cond ) {
		//map.put("dataList", service.queryAllMap(cond));//不分页
		service.queryList(cond, map);//分页
		return "/cms/point/listSimple";
	}
	/**
	 * @方法说明:简单列表;带查询,无增删改,分页可选
	 **/
	@RequestMapping("insertBatch")
	@ResponseBody
	public int[] insertBatch() {
		return service.insertBatch();
	}
	
}