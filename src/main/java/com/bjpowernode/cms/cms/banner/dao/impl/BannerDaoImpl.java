package com.bjpowernode.cms.cms.banner.dao.impl;

import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.bjpowernode.cms.cms.banner.dao.IBannerDao;
import com.bjpowernode.cms.cms.banner.model.Banner;
import com.bjpowernode.cms.cms.banner.model.BannerCond;
import com.common.dao.BaseDao;
import com.common.util.DataUtil;
import com.common.util.Util;

/**
 * @功能描述：轮播图DAO实现类
 * @author gzz
 * @date 2016-09-06 09:18:55
 */
@Repository
public class BannerDaoImpl extends BaseDao<Banner> implements IBannerDao {

	@Override
	public int insert(Banner vo) {
		String sql = "insert into cms_banner (name,order_num,picture_path,picture_url,jump_url,remark,status,type,ts) values (?,?,?,?,?,?,?,?,?)";
		Object[] obj = new Object[] { vo.getName(), vo.getOrder_num(), vo.getPicture_path(), vo.getPicture_url(),
				vo.getJump_url(), vo.getRemark(), vo.getStatus(), vo.getType(), vo.getTs() };

		return jdbcTemplate.update(sql, obj);
	}

	@Override
	public int update(Banner vo) {
		String sql = "update cms_banner set name=?,order_num=?,picture_path=?,picture_url=?,jump_url=?,remark=?,status=?,type=?,ts=? where id=?";
		Object[] obj = new Object[] { vo.getName(), vo.getOrder_num(), vo.getPicture_path(), vo.getPicture_url(),
				vo.getJump_url(), vo.getRemark(), vo.getStatus(), vo.getType(), vo.getTs(), vo.getId() };
		return jdbcTemplate.update(sql, obj);
	}

	@Override
	public int delete(Integer ids[]) {
		String sql = "delete from cms_banner where id " + Util.ArrayToInNum(ids);
		return jdbcTemplate.update(sql);
	}

	@Override
	public Banner findById(int id) {
		String sql = "select id,name,order_num,picture_path,picture_url,jump_url,remark,status,type,ts from cms_banner where id=?";
		Object[] obj = new Object[] { id };
		return jdbcTemplate.queryForObject(sql, obj, new BeanPropertyRowMapper<>(Banner.class));
	}

	@Override
	public void queryList(BannerCond cond, Map<String, Object> map) {
		StringBuffer sb = new StringBuffer();
		sb.append("select id,name,order_num,picture_path,picture_url,jump_url,remark,status,type,ts ");
		sb.append("from cms_banner where 1=1");
		sb.append(cond.getCondition());
		logger.info(DataUtil.showSql(sb.toString(), cond.getArray()));// 显示完整SQL语句
		queryPage(map, sb.toString(), cond, Banner.class);
	}

	@Override
	public int queryCount(BannerCond cond) {
		StringBuffer sb = new StringBuffer();
		sb.append("select count(id) from cms_banner where 1=1");
		sb.append(cond.getCondition());
		logger.info(DataUtil.showSql(sb.toString(), cond.getArray()));
		return jdbcTemplate.queryForObject(sb.toString(), cond.getArray(), Integer.class);
	}

	@Override
	public int updatestatus(Banner vo) {
		String sql = "update cms_banner set  status=? where id=?";
		Object[] obj = new Object[] { vo.getStatus(), vo.getId() };
		return jdbcTemplate.update(sql, obj);
	}

}
