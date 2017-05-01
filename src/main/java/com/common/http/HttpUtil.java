package com.common.http;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

 

/**
 * http请求工具类
 */
@Component
public class HttpUtil {
 

	private static final Log logger = LogFactory.getLog(HttpUtil.class);// 日志类

	public String get(String url) {
		HttpClient client = HttpClients.createDefault();// 创建默认http连接
		HttpGet get = new HttpGet("http://localhost:9092/" + url);// 创建一个get请求
		String text = "";
		try {
			HttpResponse response = client.execute(get);
			HttpEntity entity = response.getEntity();// 从response中取到响实体
			text = EntityUtils.toString(entity);// 把响应实体转成字符串
		} catch (ClientProtocolException e) {
			logger.info("httpclient执行get请求进出现ClientProtocolException异常");
			e.printStackTrace();
		} catch (IOException e) {
			logger.info("httpclient执行get请求进出现ClientProtocolException异常");
			e.printStackTrace();
		} finally {
			get.releaseConnection();// 释放连接
		}
		return text;
	}

	public String post(String url, Map<String, String> map) {
		HttpClient client = HttpClients.createDefault();// 创建默认http连接
		HttpPost post = new HttpPost("http://localhost:9092/" + url);// 创建一个get请求
		HttpResponse response;
		String text = "";
		try {
			List<NameValuePair> paramList = new ArrayList<>();
			for (Map.Entry<String, String> entry : map.entrySet()) {
				paramList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
			HttpEntity entitya = new UrlEncodedFormEntity(paramList, "utf-8");
			post.setEntity(entitya);// 把请求实体放post请求中
			response = client.execute(post);
			HttpEntity entity = response.getEntity();// 从response中取到响实体
			text = EntityUtils.toString(entity);// 把响应实体转成json文本
		} catch (ClientProtocolException e) {
			logger.info("httpclient执行post请求进出现ClientProtocolException异常");
			e.printStackTrace();
		} catch (IOException e) {
			logger.info("httpclient执行post请求进出现ClientProtocolException异常");
			e.printStackTrace();
		} finally {
			post.releaseConnection();// 释放连接
		}
		return text;
	}

}
