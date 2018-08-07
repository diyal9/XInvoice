/**   
 * @Title: LoginActivity.java 
 * @Package com.diyal.xinvoice 
 * @System TODO  
 * @author 670924505@qq.com  
 * @date 2014-8-12 
 * @Copyright (c) Diyal All Rights Reserved.  
 */
package com.diyal.xinvoice;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.diyal.util.Consts;
import com.diyal.xinvoice.app.Preferences;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

/**
 * @ClassName: LoginActivity
 * @Description: 登录Activity
 * @author diyal.yin
 * @date 2014-8-12 下午11:00:43
 * 
 */
public class LoginActivity extends Activity {
	private static final String TAG = "LoginActivity";

	private static Context context;

	private EditText mtv_username;

	private EditText mtv_pwd;

	private Button mBtn_login;

	private SharedPreferences mPreferences;

	/*
	 * Activity创建
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate");
		super.onCreate(savedInstanceState);

		context = this.getApplicationContext();

		// No Title bar
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		requestWindowFeature(Window.FEATURE_PROGRESS);

		setContentView(R.layout.login);

		mtv_username = (EditText) findViewById(R.id.username_edit);
		mtv_pwd = (EditText) findViewById(R.id.password_edit);
		mBtn_login = (Button) findViewById(R.id.signin_button);
		mBtn_login.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				doLogin();
			}
		});
	}

	/**
	 * @Title: doLogin
	 * @Description: 登录处理
	 */
	private void doLogin() {
		if (!TextUtils.isEmpty(mtv_username.getText())
				& !TextUtils.isEmpty(mtv_pwd.getText())) {
			doPost();
		} else {
			Toast.makeText(context,
					R.string.login_status_null_username_or_password,
					Toast.LENGTH_SHORT).show();
		}
	}

	// @OnClick(R.id.download_btn)
	public void doPost() {
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("msgid", "10001");
		params.addQueryStringParameter("userid", mtv_username.getText()
				.toString());
		params.addQueryStringParameter("password", mtv_pwd.getText().toString());

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
						Toast.makeText(context, responseInfo.toString(),
								Toast.LENGTH_SHORT).show();

						String obj = responseInfo.result;

						saveUserInfo();

						// 这个是直接跳转到MainActivity
						Intent intent = new Intent();
						intent.setClass(LoginActivity.this, MainActivity.class);
						startActivity(intent);
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						Toast.makeText(context, "Login failure",
								Toast.LENGTH_SHORT).show();
					}
				});
	}

	public void saveUserInfo() {
		mPreferences = XInvoiceApplication.mPref;
		SharedPreferences.Editor editor = mPreferences.edit();
		editor.putString(Preferences.USERNAME_KEY, mtv_username.getText()
				.toString());

		// add 存储当前用户的id
		editor.putString(Preferences.PASSWORD_KEY, mtv_pwd.getText().toString());
		editor.commit();

	}

}
