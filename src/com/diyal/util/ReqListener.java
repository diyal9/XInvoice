package com.diyal.util;

/**
 * 定义通用的请求listener
 * @author lawang
 *
 */
public interface ReqListener {
	/**
	 * 请求成功返回json string
	 * @param retJson
	 */
	public void onSuccess(String retJson);
	
	/**
	 * 请求失败
	 * @param returnCode
	 * @param info
	 */
	public void onFailure(int returnCode, String info);
}
