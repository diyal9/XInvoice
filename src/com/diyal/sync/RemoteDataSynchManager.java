/**   
 * @Title: RemoteDataSynchronization.java 
 * @Package com.diyal.DataSynchronization 
 * @System TODO  
 * @author 670924505@qq.com  
 * @date 2014-8-19 
 * @Copyright (c) Diyal All Rights Reserved.  
 */
package com.diyal.sync;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;

import com.diyal.util.Consts;
import com.diyal.util.ReqListener;
import com.diyal.xinvoice.R;
import com.google.gson.Gson;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;

/**
 * @ClassName: RemoteDataSynchronization
 * @Description: 数据同步公共类
 * @author diyal.yin
 * @date 2014-8-19 下午2:51:49
 * 
 */
public class RemoteDataSynchManager {

	private static DbUtils dbUtils = null;

	private Context context;

	/** 请求的同步的对象 */
	private int[] requestDataType = null;

	private ProgressDialog progressBar;

	public RemoteDataSynchManager(Context context, int[] requestDataType) {
		this.context = context;
		this.requestDataType = requestDataType;

		dbUtils = DbUtils.create(context, Consts.SQLITE_NAME);
		dbUtils.configAllowTransaction(true);
		dbUtils.configDebug(true);

		showProcessBar();
	}

	// 显示进度条
	private void showProcessBar() {
		progressBar = new ProgressDialog(context);
		progressBar.setTitle("数据同步");
		progressBar.setMessage("请稍后，正在加载服务器数据");
		progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressBar.setCancelable(false);
		progressBar.show();
	}

	/**
	 * 查找本地更新时间
	 * 
	 * @param synchStep
	 *            更新的步骤
	 * @throws DbException
	 */
	public String getLocalLastUpdateTime(int synchStep) throws DbException {
		String localLastTime = "";

		// 查找最后更新时间
		LocalUpdateVersion lVersion = dbUtils.findFirst(Selector.from(
				LocalUpdateVersion.class).where("sqlite_tableId", "=",
				synchStep));

		if (lVersion != null) {
			localLastTime = lVersion.getSqlite_LastUpdateTime();
		}

		return localLastTime;
	}

	/**
	 * 更新本地数据
	 * 
	 * @param synchStep
	 *            更新的步骤
	 * @throws DbException
	 */
	public void updateLocalDB(String responseStr) throws DbException {
		SyncRequestBean syncBean = null;
		Gson gson = new Gson();
		syncBean = gson.fromJson(responseStr, SyncRequestBean.class);
		System.out.println(syncBean);

		// 存储
		List goodsList = syncBean.getGoods();
		List<GoodsBean> saveList = new ArrayList<GoodsBean>();
		GoodsBean goodsBean = null;
		for (int i = 0; i < goodsList.size(); i++) {
			String jsonStr = goodsList.get(i).toString();
			goodsBean = gson.fromJson(jsonStr, GoodsBean.class);
			System.out.println("sdfa");
			saveList.add(goodsBean);
		}
		dbUtils.saveOrUpdateAll(saveList);

		progressBar.dismiss();
	}

	/**
	 * 更新本地的更新版本信息
	 * 
	 * @param synchStep
	 *            更新的步骤
	 * @throws DbException
	 */
	private boolean updateVersionInfo(int constsSynchron, String updateTime)
			throws DbException {
		LocalUpdateVersion localVersion = null;

		localVersion = dbUtils.findFirst(Selector
				.from(LocalUpdateVersion.class).where("sqlite_tableId", "=",
						constsSynchron));
		if (localVersion.getSqlite_LastUpdateTime() != null
				&& !"".equals(localVersion.getSqlite_LastUpdateTime())) { // 取不到数据
			LocalUpdateVersion tmpUV = new LocalUpdateVersion();
			tmpUV.setSqlite_tableId(constsSynchron);
			tmpUV.setSqlite_LastUpdateTime(updateTime);
			dbUtils.save(tmpUV);
		}

		return false;

	}

	/**
	 * 同步商品数据处理
	 * 
	 * @param synchStep
	 *            更新的步骤
	 * @throws DbException
	 */
	public void synch() throws DbException {

		String localDBTime[] = {};

		// 查找最后更新时间
		for (int i = 0; i < requestDataType.length; i++) {
			String localTime = getLocalLastUpdateTime(ConstsSync.CONSTS_SYNCH_TYPE_GOODS);
			if (localTime != null && !"".equals(localTime)) {
				localDBTime[i] = localTime;
			}
		}

		// 请求数据
		RemoteDataRequest remoteDataRequest = new RemoteDataRequest();
		remoteDataRequest.setRequestDataType(requestDataType);
		remoteDataRequest.setTimeArray(localDBTime);
		remoteDataRequest.post(new ReqListener() {

			@Override
			public void onSuccess(String retJson) {
				// 更新本地数据
				try {
					updateLocalDB(retJson);
				} catch (DbException e) {
				}
			}

			@Override
			public void onFailure(int returnCode, String info) {
			}
		});

	}

}
