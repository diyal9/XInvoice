/**   
 * @Title: InvoiceMakeActivity.java 
 * @Package com.diyal.xinvoice 
 * @System TODO  
 * @author 670924505@qq.com  
 * @date 2014-8-15 
 * @Copyright (c) Diyal All Rights Reserved.  
 */
package com.diyal.xinvoice;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.diyal.bean.Invoice;
import com.diyal.sync.ConstsSync;
import com.diyal.sync.GoodsBean;
import com.diyal.sync.LocalUpdateVersion;
import com.diyal.sync.RemoteDataSynchManager;
import com.diyal.util.BigAmountUtil;
import com.diyal.util.Consts;
import com.diyal.util.JsonUtil;
import com.diyal.xinvoice.ui.base.BaseActivity;
import com.google.gson.Gson;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * @ClassName: InvoiceManageActivity
 * @Description: 发票管理
 * @author diyal.yin
 * @date 2014-8-15 上午12:24:22
 * 
 */
public class InvoiceManageActivity extends BaseActivity {
	@ViewInject(R.id.tv_title_label)
	private TextView mTitle;
	// 购方
	@ViewInject(R.id.sp_buyunit_name)
	private Spinner mBuyName;

	@ViewInject(R.id.sp_buyunit_taxpayerno)
	private Spinner mBuyTaxpayerno;

	@ViewInject(R.id.sp_buyunit_addphone)
	private Spinner mBuyAddphone;

	private Context context;

	protected boolean _onCreate(Bundle savedInstanceState) {
		if (super._onCreate(savedInstanceState)) {
			// No Title bar
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			requestWindowFeature(Window.FEATURE_PROGRESS); // 进度指示器功能
			setContentView(R.layout.write_invoice);

			ViewUtils.inject(this);

			context = getApplicationContext();

			mTitle.setText("增值税专用发票填开");

			getInitData();

			return true;
		} else {
			return false;
		}

	}

	private void getInitData() {
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("msgid", "10002");

		HttpUtils http = new HttpUtils();
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
						Invoice inv = JsonUtil.fromJson(responseInfo.result,
								Invoice.class);
						System.out.println("转换成功：" + inv.getB_name());

					}

					@Override
					public void onFailure(HttpException error, String msg) {
						Toast.makeText(context, "数据加载失败", Toast.LENGTH_SHORT)
								.show();
					}
				});
	}

	private void postFormSure() {
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("msgid", Consts.HTTP_MSGID_10003);

		// Gson gson = new Gson();
		// String jsonStr = gson.toJson(getInvoiceBeanInfo());
		// params.addBodyParameter("forminfo", jsonStr);

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
						Toast.makeText(context, responseInfo.result,
								Toast.LENGTH_SHORT).show();

					}

					@Override
					public void onFailure(HttpException error, String msg) {
						Toast.makeText(context, "数据加载失败", Toast.LENGTH_SHORT)
								.show();
					}
				});
	}

	/**
	 * 检查是否需要同步数据
	 **/
	private boolean checkRemoteDate() {
		boolean isNeedSync = false;

		// 同步处理
		int sychTypeArray[] = { ConstsSync.CONSTS_SYNCH_TYPE_GOODS,
				ConstsSync.CONSTS_SYNCH_TYPE_PURCHASE };
		RemoteDataSynchManager sync = new RemoteDataSynchManager(context,
				sychTypeArray); // 同步管理器

		return isNeedSync;

	}
}
