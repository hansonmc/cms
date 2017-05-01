package com.bjpowernode.cms.cms.point.dao.impl;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.common.util.DataUtil;
import com.common.dao.BaseDao;
import com.common.util.Util;

import com.bjpowernode.cms.cms.point.model.Point;
import com.bjpowernode.cms.cms.point.model.PointCond;
import com.bjpowernode.cms.cms.point.dao.IPointDao;

/**
 * @类说明:卖点图Dao实现类
 *
 * @author:gzz_gzz@163.com
 * @date:2016-09-10 14:43:41
 **/
@Repository
public class PointDaoImpl extends BaseDao <Point> implements IPointDao {
	private final String insertSql = "INSERT INTO CMS_POINT (NAME,TEXT,ORDER_NUM,PICTURE_PATH,PICTURE_URL,JUMP_URL,REMARK,STATUS,TYPE,TS) VALUES (?,?,?,?,?,?,?,?,?,?) ";
	private final String updateSql = "UPDATE CMS_POINT SET NAME=?,TEXT=?,ORDER_NUM=?,PICTURE_PATH=?,PICTURE_URL=?,JUMP_URL=?,REMARK=?,STATUS=?,TYPE=?,TS=? WHERE ID=? ";
	private StringBuilder selectSql = new StringBuilder();

	/**
	 * @方法说明:构造方法,用于拼加SELECT-SQL及其它的初始化工作
	 **/
	public PointDaoImpl () {
		selectSql.append("SELECT T.ID,T.NAME,T.TEXT,T.ORDER_NUM,T.PICTURE_PATH,T.PICTURE_URL,T.JUMP_URL,T.REMARK,T.STATUS,T.TYPE,T.TS FROM CMS_POINT T WHERE 1=1");
	}

	/**
	 * @方法说明:新增记录
	 **/
	@Override
	public int insert(Point vo) {
		//DataUtil.trim(vo);//去掉字符串型字段值前后的空格
		Object[] params = new Object[]{vo.getName(),vo.getText(),vo.getOrder_num(),vo.getPicture_path(),vo.getPicture_url(),vo.getJump_url(),vo.getRemark(),vo.getStatus(),vo.getType(),vo.getTs()};
		logger.debug(DataUtil.showSql(insertSql, params));//显示SQL语句
		return jdbcTemplate.update(insertSql, params);
	}

	/**
	 * @方法说明:新增记录(返回自增涨主键值:用于替换以上新增方法)
	 * 使用场景：主子表同时维护
	 **/
	@Override
	public int insertReturnPK(Point point) {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO CMS_POINT (NAME,TEXT,ORDER_NUM,PICTURE_PATH,PICTURE_URL,JUMP_URL,REMARK,STATUS,TYPE,TS) VALUES ");
		sb.append(" (:Name,:Text,:Order_num,:Picture_path,:Picture_url,:Jump_url,:Remark,:Status,:Type,:Ts)");
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("Name", point.getName());
		params.addValue("Text", point.getText());
		params.addValue("Order_num", point.getOrder_num());
		params.addValue("Picture_path", point.getPicture_path());
		params.addValue("Picture_url", point.getPicture_url());
		params.addValue("Jump_url", point.getJump_url());
		params.addValue("Remark", point.getRemark());
		params.addValue("Status", point.getStatus());
		params.addValue("Type", point.getType());
		params.addValue("Ts", point.getTs());
		//主键拾取器
		KeyHolder keyholder = new GeneratedKeyHolder();
		
		namedJdbcTemplate.update(sb.toString(), params, keyholder);
		
		return  keyholder.getKey().intValue();
	}

	/**
	 * @方法说明:物理删除记录(多条)
	 **/
	@Override
	public int delete(String ids) {
		String updateStr = "DELETE FROM CMS_POINT WHERE ID" + Util.ArrayToIn(ids);//数值型ID使用ArrayToInNum
		return jdbcTemplate.update(updateStr);
	}

	/**
	 * @方法说明:按ID查找单个实体
	 **/
	@Override
	public Point findById(Integer id) {
		StringBuilder sb = new StringBuilder(selectSql);
		sb.append(" AND T.ID=?");
		return jdbcTemplate.queryForObject(sb.toString(), new Object[]{id}, new BeanPropertyRowMapper<Point>(Point.class));
	}

	/**
	 * @方法说明:更新记录
	 **/
	@Override
	public int update(Point vo) {
		Object[] params = new Object[]{vo.getName(),vo.getText(),vo.getOrder_num(),vo.getPicture_path(),vo.getPicture_url(),vo.getJump_url(),vo.getRemark(),vo.getStatus(),vo.getType(),vo.getTs(),vo.getId()};
		return jdbcTemplate.update(updateSql, params);
	}

	/**
	 * @方法说明:按条件查询分页列表-根据需要替换成自己的SQL
	 **/
	@Override
	public void queryList(PointCond cond, Map<String, Object> map) {
		StringBuilder sb = new StringBuilder(selectSql);
		sb.append(cond.getCondition());
		//sb.append(" ORDER BY T.ID");//MS_SQL中不能加此句
		logger.debug(DataUtil.showSql(sb.toString(), cond.getArray()));//显示SQL语句
		queryPage(map, sb.toString(),cond,Point.class);//(使用范型)
	}

	/************************************************************
	 * 以下方法请跟据实际业务进行取舍或修改、把不用的方法清理掉!!!!*
	 ************************************************************/

	/**
	 * @方法说明:按条件查询不分页列表(使用范型)-根据需要替换成自己的SQL
	 **/
	@Override
	public List<Point> queryAllObj(PointCond cond) {
		StringBuilder sb = new StringBuilder(selectSql);
		sb.append(cond.getCondition());
		return jdbcTemplate.query(sb.toString(), cond.getArray(), new BeanPropertyRowMapper<Point>(Point.class));
	}

	/**
	 * @方法说明:按条件查询不分页列表(不使用范型)-根据需要替换成自己的SQL
	 **/
	@Override
	public List<Map<String, Object>> queryAllMap(PointCond cond) {
		StringBuilder sb = new StringBuilder(selectSql);
		sb.append(cond.getCondition());
		return jdbcTemplate.queryForList(sb.toString(), cond.getArray());
	}

	/**
	 * @方法说明:按条件查询记录个数
	 **/
	@Override
	public int findCountByCond(PointCond cond) {
		String countSql = "SELECT COUNT(T.ID) FROM CMS_POINT T WHERE 1=1" + cond.getCondition();
		return jdbcTemplate.queryForObject(countSql, cond.getArray(), Integer.class);
	}

	/**
	 * @方法说明:批量插入记录
	 **/
	@Override
	public int[] insertBatch(List<Point> list) {
		// return DataUtil.insertBatch(list, jdbcTemplate, insertSql);
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO CMS_POINT (NAME,TEXT,ORDER_NUM,PICTURE_PATH,PICTURE_URL,JUMP_URL,REMARK,STATUS,TYPE,TS) VALUES ");
		sb.append(" (:Name,:Text,:Order_num,:Picture_path,:Picture_url,:Jump_url,:Remark,:Status,:Type,:Ts)");
		MapSqlParameterSource params[] = new MapSqlParameterSource[list.size()];
		for (int i = 0; i < list.size(); i++) {
			params[i]=new MapSqlParameterSource();
			params[i].addValue("Name", list.get(i).getName());
			params[i].addValue("Text", list.get(i).getText());
			params[i].addValue("Order_num", list.get(i).getOrder_num());
			params[i].addValue("Picture_path", list.get(i).getPicture_path());
			params[i].addValue("Picture_url", list.get(i).getPicture_url());
			params[i].addValue("Jump_url", list.get(i).getJump_url());
			params[i].addValue("Remark", list.get(i).getRemark());
			params[i].addValue("Status", list.get(i).getStatus());
			params[i].addValue("Type", list.get(i).getType());
			params[i].addValue("Ts", list.get(i).getTs());
		}
		return namedJdbcTemplate.batchUpdate(sb.toString(), params);
	}

	/**
	 * @方法说明:逻辑删除记录(多条)
	 **/
	@Override
	public int deleteLogic(String ids) {
		String updateStr = "UPDATE CMS_POINT SET DR = 1 WHERE ID" + Util.ArrayToIn(ids);
		return jdbcTemplate.update(updateStr);
	}
}