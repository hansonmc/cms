package com.bjpowernode.cms.cms.advert.action;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.bjpowernode.cms.cms.advert.model.Advert;
import com.bjpowernode.cms.cms.advert.model.AdvertCond;
import com.bjpowernode.cms.cms.advert.service.IAdvertService;
import com.common.dao.DictParamUtil;

/**
 * @功能说明: 广告位dao实现类
 * @author: bjpowernode
 * @date:2016-09-09 14:00:00
 */
@Controller
@RequestMapping("advert")
public class AdvertAction {
	@Autowired
	private IAdvertService service;

	@Autowired

	private DictParamUtil util;// 字典参数工具类

	@RequestMapping("list")
	public String queryList(@ModelAttribute("cond") AdvertCond cond, Map<String, Object> map) {
		service.queryList(cond, map);
		map.put("typeMap", util.getDictMap(10, true));// 终端类型的下拉列表
		map.put("statusMap", util.getDictMap(12, true));// 广告位状态的下拉列表
		return "cms/advert/list";
	}

	@RequestMapping("toinsert")
	// 跳转到新增页面
	public String toinsert(@ModelAttribute("advert") Advert advert, Map<String, Object> map) {
		map.put("typeMap", util.getDictMap(10, false));// 终端类型的下拉列表
		return "cms/advert/insert";
	}

	/**
	 * @功能描述：公用验证方法method=2时是在修改处调用
	 */
	private void validate(Advert advert, BindingResult result, int method, MultipartFile image) {
		AdvertCond cond = new AdvertCond();// 构造查询条件
		cond.setName_v(advert.getName());
		// pc w 1200 h 380 size 500k
		// m w 1200 h 280 size 400k
		try {
			if (!image.isEmpty()) {// 只有用户上传图片我们才做如下的验证

				BufferedImage bi = ImageIO.read(image.getInputStream());
				String standard = util.findDictValue(13, advert.getType());// 取指定终端验证标准
				String stand[] = standard.split(",");// 分
				if (bi.getHeight() != new Integer(stand[1])) {
					result.rejectValue("name", "", "广告位的高度必须是" + stand[1] + "！！");
				}
				if (bi.getWidth() != new Integer(stand[0])) {
					result.rejectValue("name", "", "广告位的宽度必须是" + stand[0] + "！！");
				}
				if (image.getSize() / 1024 > new Integer(stand[2])) {
					result.rejectValue("name", "", "广告位的尺寸必须小于" + stand[2] + "K！！");
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (method == 2) {
			cond.setId_c(advert.getId());
		}
		int count = service.queryCount(cond);// 到数据去查询当前输入的广告位名有几个
		if (count > 0) {// 验证未通过
			result.rejectValue("name", "", "[广告位名称]不能重复！！");
		}
	}

	@RequestMapping("insert")
	// 跳转到新增页面
	public String insert(Advert advert, MultipartFile image, BindingResult result, Map<String, Object> map) {
		validate(advert, result, 1, image);
		if (result.hasErrors()) {// 如果验证未通过的项
			map.put("typeMap", util.getDictMap(10, false));// 终端类型的下拉列表
			return "cms/advert/insert";
		}
		service.insert(advert, image);
		return "redirect:list";
	}

	@RequestMapping("delete")
	@ResponseBody
	public int delete(@RequestParam("ids[]") Integer ids[]) {
		return service.delete(ids);
	}

	@RequestMapping("toupdate")
	// 跳转到修改页面
	public String toupdate(int id, Map<String, Object> map) {
		map.put("advert", service.findById(id));
		map.put("typeMap", util.getDictMap(10, false));// 终端类型的下拉列表
		return "cms/advert/update";
	}

	@RequestMapping("update")
	// 修改数据--保存
	public String update(Advert advert, MultipartFile image, BindingResult result, Map<String, Object> map) {
		validate(advert, result, 2, image);
		if (result.hasErrors()) {// 如果验证未通过的项
			map.put("typeMap", util.getDictMap(10, false));// 终端类型的下拉列表
			return "cms/advert/update";
		}
		service.update(advert, image);
		return "redirect:list";
	}

	@RequestMapping("detail")
	// 跳转到详细页面
	public String detail(int id, Map<String, Object> map) {
		map.put("advert", service.findById(id));
		return "cms/advert/detail";
	}

	@RequestMapping("updatestatus")
	// 跳转到详细页面
	public String updateStatus(Advert advert) {
		service.updateStatus(advert);
		return "redirect:list";
	}
}
