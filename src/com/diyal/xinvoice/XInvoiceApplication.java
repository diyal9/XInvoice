/**   
 * @Title: XInvoiceApplication.java 
 * @Package com.diyal.xinvoice 
 * @System TODO  
 * @author 670924505@qq.com  
 * @date 2014-8-13 
 * @Copyright (c) Diyal All Rights Reserved.  
 */
package com.diyal.xinvoice;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.diyal.bean.User;
import com.diyal.xinvoice.app.Preferences;
import com.lidroid.xutils.util.LogUtils;

/**
 * @ClassName: XInvoiceApplication
 * @Description: 项目启动类
 * @author diyal.yin
 * @date 2014-8-13 上午10:15:43
 */
public class XInvoiceApplication extends Application {

	public static Context mContext;

	public static SharedPreferences mPref; // 储存对象

	public static User userCache;

	/*
	 * 创建
	 * 
	 * @see android.app.Application#onCreate()
	 */
	@Override
	public void onCreate() {
		LogUtils.d(this.getClass().getName());
		super.onCreate();

		mContext = this.getApplicationContext();

		mPref = PreferenceManager.getDefaultSharedPreferences(this);
		String username = mPref.getString(Preferences.USERNAME_KEY, "");
		String password = mPref.getString(Preferences.PASSWORD_KEY, "");
		userCache = new User();
		userCache.setUserid(username);
		userCache.setPassword(password);

	}

	public static String getLocalUserId() {
		return mPref.getString(Preferences.USERNAME_KEY, "");
	}

	public static String getLocalPwd() {
		return mPref.getString(Preferences.PASSWORD_KEY, "");
	}
}
