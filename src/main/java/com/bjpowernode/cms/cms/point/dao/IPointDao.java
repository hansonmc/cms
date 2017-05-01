package com.bjpowernode.cms.cms.point.dao;
import java.util.List;
import java.util.Map;
import com.bjpowernode.cms.cms.point.model.Point;
import com.bjpowernode.cms.cms.point.model.PointCond;

/**
 * @类说明:卖点图Dao接口类
 *
 * @author:gzz_gzz@163.com
 * @date:2016-09-10 14:43:41
 **/
public interface IPointDao {

	/**
	 * @方法说明:新增记录
	 **/
	int insert(Point point);

	/**
	 * @方法说明:新增记录(返回自增涨主键值:用于替换以上新增方法)
	 **/
	int insertReturnPK(Point point);

	/**
	 * @方法说明:物理删除记录(多条)
	 **/
	int delete(String id);

	/**
	 * @方法说明:按ID查找单个实体
	 **/
	Point findById(Integer id);

	/**
	 * @方法说明:更新记录
	 **/
	int update(Point point);

	/**
	 * @方法说明:按条件查询分页列表
	 **/
	void queryList(PointCond cond, Map<String, Object> map);

	/************************************************************
	 * 以下方法请跟据实际业务进行取舍或修改、把不用的方法清理掉!!!!*
	 ************************************************************/

	/**
	 * @方法说明:按条件查询不分页列表(使用范型)
	 **/
	List<Point> queryAllObj(PointCond cond);

	/**
	 * @方法说明:按条件查询不分页列表(不使用范型)
	 **/
	List<Map<String, Object>> queryAllMap(PointCond cond);

	/**
	 * @方法说明:按条件查询记录个数
	 **/
	int findCountByCond(PointCond cond);

	/**
	 * @方法说明:批量插入数据
	 **/
	int[] insertBatch(final List<Point> list);

	/**
	 * @方法说明:逻辑删除记录(多条)
	 **/
	int deleteLogic(String id);
}