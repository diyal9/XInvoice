/**   
 * @Title: CustomerViewDialog.java 
 * @Package com.diyal.xinvoice 
 * @System TODO  
 * @author 670924505@qq.com  
 * @date 2014-8-25 
 * @Copyright (c) Diyal All Rights Reserved.  
 */
package com.diyal.xinvoice;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import com.diyal.xinvoice.ui.base.BaseActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/** 
 * @ClassName: CustomerViewDialog 
 * @Description: TODO(用一句话描述这个类的作用) 
 * @author diyal.yin 
 * @date 2014-8-25 下午3:36:40 
 *  
 */
public class CustomerViewDialog extends BaseActivity {
	
	// 客户id
	@ViewInject(R.id.customer_view_dialog_id)
	private TextView mCustomerID;

	// 客户名称
	@ViewInject(R.id.customer_view_dialog_name)
	private TextView mCustomerName;

	// 客户其它信息
	@ViewInject(R.id.customer_view_dialog_otherinfo)
	private TextView mCustomerOtherinfo;

	private Context context;

	protected boolean _onCreate(Bundle savedInstanceState) {
		if (super._onCreate(savedInstanceState)) {
			// No Title bar
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			requestWindowFeature(Window.FEATURE_PROGRESS); // 进度指示器功能
			setContentView(R.layout.view_customer_dialog);

			ViewUtils.inject(this);

			context = getApplicationContext();

//			getInitData();
			setViewInit();
			return true;
		} else {
			return false;
		}

	}

	private void getInitData() {

//		Intent intent = this.getIntent(); // 获取已有的intent对象
//		Bundle bundle = intent.getExtras(); // 获取intent里面的bundle对象
//		String key1 = bundle.getString("type"); // 获取Bundle里面的字符串
//		String key2 = bundle.getString("id"); // 获取Bundle里面的字符串
//
//		RequestParams params = new RequestParams();
//		params.addQueryStringParameter("msgid", Consts.HTTP_MSGID_10009);
//		params.addQueryStringParameter("type", key1);
//		params.addQueryStringParameter("id", key2);
//
//		HttpUtils http = new HttpUtils();
//		http.send(HttpRequest.HttpMethod.POST, Consts.NET_URL, params,
//				new RequestCallBack<String>() {
//
//					@Override
//					public void onStart() {
//					}
//
//					@Override
//					public void onLoading(long total, long current,
//							boolean isUploading) {
//					}
//
//					@Override
//					public void onSuccess(ResponseInfo<String> responseInfo) {
//						Invoice inv = JsonUtil.fromJson(responseInfo.result,
//								Invoice.class);
//						System.out.println("转换成功：" + inv.getB_name());
//
//						setViewInit(inv);
//					}
//
//					@Override
//					public void onFailure(HttpException error, String msg) {
//						Toast.makeText(context, "数据加载失败", Toast.LENGTH_SHORT)
//								.show();
//					}
//				});
	}

//	private void setViewInit(Invoice inv) {
	private void setViewInit() {

		if(mCustomerID != null)
		{
			mCustomerID.setText("23456");
		}
		
		if(mCustomerName != null)
		{
			mCustomerName.setText("苏州阿小test1");
		}
		
		if(mCustomerOtherinfo != null)
		{
			mCustomerOtherinfo.setText("客户其它信息！");
		}

	}

}
