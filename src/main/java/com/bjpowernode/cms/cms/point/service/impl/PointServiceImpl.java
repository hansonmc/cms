package com.bjpowernode.cms.cms.point.service.impl;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.bjpowernode.cms.cms.point.dao.IPointDao;
import com.bjpowernode.cms.cms.point.model.Point;
import com.bjpowernode.cms.cms.point.model.PointCond;
import com.bjpowernode.cms.cms.point.service.IPointService;
import com.common.util.DataUtil;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @类说明:卖点图Service实现类
 *
 * @author:gzz_gzz@163.com
 * @date:2016-09-10 14:43:41
 **/
@Service
public class PointServiceImpl  implements IPointService {
	private Log logger = LogFactory.getLog(getClass());
	@Autowired
	private IPointDao dao; //卖点图Dao

	/**
	 * @方法说明:新增记录
	 **/
	@Override
	@Transactional
	public int insert(Point point) {
		int key=dao.insertReturnPK(point);
		logger.info("key="+key);
		return key;
	}

	/**
	 * @方法说明:删除记录(多条)
	 **/
	@Override
	public int delete(String id) {
		//return dao.deleteLogic(id);//逻辑删除
		return dao.delete(id);//物理删除
	}

	/**
	 * @方法说明:按ID查找单个实体
	 **/
	@Override
	public Point findById(Integer id) {
		return dao.findById(id);
	}

	/**
	 * @方法说明:更新记录
	 **/
	@Override
	@Transactional
	public int update(Point point) {
		return dao.update(point);
	}

	/**
	 * @方法说明:按条件查询分页列表
	 **/
	@Override
	public void queryList(PointCond cond, Map<String, Object> map) {
		dao.queryList(cond, map);
	}

	/************************************************************
	 * 以下方法请跟据实际业务进行取舍或修改、把不用的方法清理掉!!!!*
	 ************************************************************/

	/**
	 * @方法说明:导出Excel
	 **/
	@Override
	public void exportExcel(PointCond cond, HttpServletResponse response, String name) throws Exception {
		List<Map<String, Object>> list = dao.queryAllMap(cond);
		String[] headers = {"主键", "名称", "显示文字", "顺序", "存储路径", "访问地址", "跳转地址", "备注", "状态", "所属终端", "时间戳"};
		DataUtil.export(headers, list, response, "卖点图列表", name);//转换成excel并输出到文件(如有个性化设置只需将DataUtil.export中的代码复制至此)
		logger.debug("导出数据结束!");
	}

	/**
	 * @方法说明:导入Excel
	 **/
	@Override
	public String importExcel(MultipartFile file) throws Exception {
		int insert = 0, update = 0;
		List<Point> list = DataUtil.setList(file,Point.class);//把excel文件转成list
		insert=dao.insertBatch(list).length;//批量导入不检测(高效)
		//批量导入带检测ID相同则更新(很慢)根据不同场景选取不同导入方式
		/*
		for (Point point:list) {
			if (dao.findCountByCond(new PointCond(new Object[][]{{"id_c",point.getId()}})) > 0) {
				dao.update(point);
				update++;
			} else {
				dao.insert(point);
				insert++;
			}
		}*/
		return "插入" + insert + "条记录,更新" + update + "条记录.";
	}

	/**
	 * @方法说明:按条件查询不分页列表(使用范型)
	 **/
	@Override
	public List<Point> queryAllObj(PointCond cond) {
		return dao.queryAllObj(cond);
	}

	/**
	 * @方法说明:按条件查询不分页列表(不使用范型)
	 **/
	@Override
	public List<Map<String, Object>> queryAllMap(PointCond cond) {
		return dao.queryAllMap(cond);
	}

	/**
	 * @方法说明:按条件查询记录个数
	 **/
	@Override
	public int findCountByCond(PointCond cond) {
		return dao.findCountByCond(cond);
	}

	@Override
	public int[] insertBatch( ) {
		 String json="[{\"text\":\"1\",\"id\":13,\"name\":\"孙悟空\",\"picture_path\":\"D:/img/banner/1473390215851.jpg\",\"picture_url\":\"http://img.power.com/banner/1473390215851.jpg\",\"order_num\":1,\"jump_url\":\"213213\",\"remark\":\"\",\"status\":1,\"type\":1,\"ts\":\"2016-09-09 03:03:35\"},{\"text\":\"2\",\"id\":4,\"name\":\"1\",\"picture_path\":\"D:/img/banner/1473297022642.jpg\",\"picture_url\":\"http://img.power.com/banner/1473297022642.jpg\",\"order_num\":10,\"jump_url\":\"1\",\"remark\":\"1\",\"status\":1,\"type\":1,\"ts\":\"2016-09-08 02:31:11\"},{\"text\":\"3\",\"id\":5,\"name\":\"2\",\"picture_path\":\"D:/img/banner/1473297036103.jpg\",\"picture_url\":\"http://img.power.com/banner/1473297036103.jpg\",\"order_num\":20,\"jump_url\":\"2\",\"remark\":\"12\",\"status\":1,\"type\":1,\"ts\":\"2016-09-08 01:10:36\"},{\"text\":\"4\",\"id\":6,\"name\":\"3\",\"picture_path\":\"D:/img/banner/1473297070893.jpg\",\"picture_url\":\"http://img.power.com/banner/1473297070893.jpg\",\"order_num\":30,\"jump_url\":\"3\",\"remark\":\"\",\"status\":1,\"type\":1,\"ts\":\"2016-09-08 01:11:10\"},{\"text\":\"5\",\"id\":7,\"name\":\"4\",\"picture_path\":\"D:/img/banner/1473297079588.jpg\",\"picture_url\":\"http://img.power.com/banner/1473297079588.jpg\",\"order_num\":40,\"jump_url\":\"4\",\"remark\":\"\",\"status\":1,\"type\":1,\"ts\":\"2016-09-08 03:01:39\"},{\"text\":\"6\",\"id\":8,\"name\":\"5\",\"picture_path\":\"D:/img/banner/1473297089908.jpg\",\"picture_url\":\"http://img.power.com/banner/1473297089908.jpg\",\"order_num\":50,\"jump_url\":\"5\",\"remark\":\"5\",\"status\":1,\"type\":1,\"ts\":\"2016-09-08 01:11:29\"}]";
		 ObjectMapper mapper= new ObjectMapper();
		 Point[] points=null;
		 try {
			  points = mapper.readValue(json, Point[].class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 List<Point> list= Arrays.asList(points);
		return dao.insertBatch(list);
	}
}