package com.common.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.common.dao.DictParamUtil;

import redis.clients.jedis.Jedis;

@Component
// redis工具
public class RedisUtil {
	@Autowired
	private DictParamUtil util;// 字典参数工具类

	public void clearRedis(String key) {
		Jedis jedis = new Jedis(util.findValue("redis_host"), new Integer(util.findValue("redis_port")));
		jedis.del(key);// 清缓存
		jedis.close();// 关闭连接
	}

}
