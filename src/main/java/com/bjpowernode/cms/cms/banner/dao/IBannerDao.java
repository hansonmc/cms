package com.bjpowernode.cms.cms.banner.dao;

import java.util.Map;

import com.bjpowernode.cms.cms.banner.model.Banner;
import com.bjpowernode.cms.cms.banner.model.BannerCond;

/**
 * @功能描述：轮播图DAO接口
 * @author gzz
 * @date 2016-09-06 09:18:55
 */

public interface IBannerDao {

	/**
	 * @功能描述：新增轮播图记录
	 */
	int insert(Banner banner);

	/**
	 * @功能描述：修改轮播图记录
	 */
	int update(Banner banner);

	/**
	 * @功能描述：删除轮播图记录(多条)
	 */
	int delete(Integer ids[]);

	/**
	 * @功能描述：查询单个轮播图记录
	 */
	Banner findById(int id);

	/**
	 * @功能描述：按条件查询分页列表
	 */
	void queryList(BannerCond cond, Map<String, Object> map);

	/**
	 * @功能描述：按条件查询记录个数
	 */
	int queryCount(BannerCond cond);
	
	/**
	 * @功能描述：修改轮播图记录状态
	 */
	int updatestatus(Banner banner);

}
