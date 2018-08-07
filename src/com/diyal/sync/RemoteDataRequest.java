/**   
 * @Title: RemoteDataRequest.java 
 * @Package com.diyal.DataSynchronization 
 * @System TODO  
 * @author 670924505@qq.com  
 * @date 2014-8-19 
 * @Copyright (c) Diyal All Rights Reserved.  
 */
package com.diyal.sync;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.diyal.util.Consts;
import com.diyal.util.ReqListener;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

/**
 * @ClassName: RemoteDataRequest
 * @Description: 同步数据请求处理
 * @author diyal.yin
 * @date 2014-8-19 下午2:55:16
 * 
 */
public class RemoteDataRequest {

	/** 请求的同步的对象 */
	private int[] requestDataType = null;

	/** 请求的同步的时间 */
	private String[] timeArray = null;

	private ReqListener req;

	public void post(final ReqListener req) {
		if (requestDataType == null) // 如果
			return;

		this.req = req;

		RequestParams params = new RequestParams();
		params.addQueryStringParameter("msgid", Consts.HTTP_MSGID_10004);

		JSONObject json = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		for (int i = 0; i < requestDataType.length; i++) {
			jsonArray.put(requestDataType[i]);
		}
		try {
			json.put("sychType", jsonArray);
		} catch (JSONException e) {
		}

		JSONArray jsonTimeArray = new JSONArray();
		for (int i = 0; i < timeArray.length; i++) {
			jsonTimeArray.put(timeArray[i]);
		}
		try {
			json.put("time", jsonTimeArray);
		} catch (JSONException e) {
		}

		params.addBodyParameter("sychkey", json.toString());

		HttpUtils http = new HttpUtils();
		http.configResponseTextCharset("UTF-8");
		http.send(HttpRequest.HttpMethod.POST, Consts.NET_URL, params,
				new RequestCallBack<String>() {

					@Override
					public void onStart() {
					}

					@Override
					public void onLoading(long total, long current,
							boolean isUploading) {
					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						String resultStr = responseInfo.result;
						req.onSuccess(resultStr);
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						req.onFailure(404, msg);
					}
				});
	}

	public int[] getRequestDataType() {
		return requestDataType;
	}

	public void setRequestDataType(int[] requestDataType) {
		this.requestDataType = requestDataType;
	}

	public String[] getTimeArray() {
		return timeArray;
	}

	public void setTimeArray(String[] timeArray) {
		this.timeArray = timeArray;
	}

}
