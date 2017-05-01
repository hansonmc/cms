package com.bjpowernode.cms.cms.advert.dao.impl;

import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.bjpowernode.cms.cms.advert.dao.IAdvertDao;
import com.bjpowernode.cms.cms.advert.model.Advert;
import com.bjpowernode.cms.cms.advert.model.AdvertCond;
import com.common.dao.BaseDao;
import com.common.util.DataUtil;
import com.common.util.Util;

/**
 * @功能说明: 广告位dao实现类
 * @author: bjpowernode
 * @date:2016-09-09 14:00:00
 */
@Repository
public class AdvertDaoImpl extends BaseDao<Advert> implements IAdvertDao {

	@Override
	public int insert(Advert advert) {
		String sql = "insert into cms_advert (name,order_num,picture_path,picture_url,jump_url,remark,status,type,ts) values(?,?,?,?,?,?,?,?,?)";
		Object param[] = new Object[] { advert.getName(), advert.getOrder_num(), advert.getPicture_path(),
				advert.getPicture_url(), advert.getJump_url(), advert.getRemark(), advert.getStatus(), advert.getType(),
				advert.getTs() };
		return jdbcTemplate.update(sql, param);
	}

	@Override
	public int delete(Integer ids[]) {
		String sql = "delete from cms_advert where id" + Util.ArrayToInNum(ids);
		return jdbcTemplate.update(sql);
	}

	@Override
	public int update(Advert advert) {
		String sql = "update cms_advert set name=?,order_num=?,picture_path=?,picture_url=?,jump_url=?,remark=?,status=?,type=?,ts=? where id=?";
		Object param[] = new Object[] { advert.getName(), advert.getOrder_num(), advert.getPicture_path(),
				advert.getPicture_url(), advert.getJump_url(), advert.getRemark(), advert.getStatus(), advert.getType(),
				advert.getTs(), advert.getId() };
		return jdbcTemplate.update(sql, param);
	}

	@Override
	public Advert findById(int id) {
		String sql = "select id,name,order_num,picture_path,picture_url,jump_url,remark,status,type,ts from cms_advert where id=?";
		Object param[] = new Object[] { id };
		return jdbcTemplate.queryForObject(sql, param, new BeanPropertyRowMapper<>(Advert.class));
	}

	@Override
	public void queryList(AdvertCond cond, Map<String, Object> map) {
		StringBuffer sb = new StringBuffer();
		sb.append("select id,name,order_num,picture_path,picture_url,jump_url,remark,status,type,ts ");
		sb.append(" from cms_advert where 1=1");
		sb.append(cond.getCondition());
		logger.info(DataUtil.showSql(sb.toString(), cond.getArray()));
		this.queryPage(map, sb.toString(), cond, Advert.class);

	}

	@Override
	public int queryCount(AdvertCond cond) {
		StringBuffer sb = new StringBuffer();
		sb.append("select count(id) from cms_advert where 1=1");
		sb.append(cond.getCondition());
		logger.info(DataUtil.showSql(sb.toString(), cond.getArray()));
		return jdbcTemplate.queryForObject(sb.toString(), cond.getArray(), Integer.class);
	}

	@Override
	public int updateStatus(Advert advert) {
		String sql = "update cms_advert set  status=? where id=?";
		Object param[] = new Object[] { advert.getStatus(), advert.getId() };
		return jdbcTemplate.update(sql, param);
	}

}
