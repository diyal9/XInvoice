/**   
 * @Title: JsonUtil.java 
 * @Package com.diyal.util 
 * @System TODO  
 * @author 670924505@qq.com  
 * @date 2014-8-17 
 * @Copyright (c) Diyal All Rights Reserved.  
 */
package com.diyal.util;

import java.lang.reflect.Type;

import com.google.gson.Gson;

/**
 * @ClassName: JsonUtil
 * @Description: Json 对象 处理工具类
 * @author diyal.yin
 * @date 2014-8-17 下午1:20:04
 * 
 */
public final class JsonUtil {

	private JsonUtil() {
	}

	/**
	 * 对象转换成json字符串
	 * 
	 * @param obj
	 * @return
	 */
	public static String toJson(Object obj) {
		Gson gson = new Gson();
		return gson.toJson(obj);
	}

	/**
	 * json字符串转成对象
	 * 
	 * @param str
	 * @param type
	 * @return
	 */
	public static <T> T fromJson(String str, Type type) {
		Gson gson = new Gson();
		return gson.fromJson(str, type);
	}

	/**
	 * json字符串转成对象
	 * 
	 * @param str
	 * @param type
	 * @return
	 */
	public static <T> T fromJson(String str, Class<T> type) {
		Gson gson = new Gson();
		return gson.fromJson(str, type);
	}

}
