package com.diyal.xinvoice;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.diyal.bean.MainListBean;
import com.diyal.sync.ConstsSync;
import com.diyal.sync.RemoteDataSynchManager;
import com.diyal.util.Consts;
import com.diyal.xinvoice.ui.base.BaseActivity;
import com.diyal.xinvoice.ui.module.AChartManage;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

public class MainActivity extends BaseActivity implements OnScrollListener {

	private ListView mMaintList;
	private Adapter mAdapter;
	private Adapter mInboxAdapter;
	private Adapter mSendboxAdapter;

	// list title
	private TextView tvListTitle;
	// 平铺布局功能菜单
	private RelativeLayout friendsLayout; // 我的发票
	private LinearLayout followersLayout; // 我的客户
	private LinearLayout statusesLayout; // 我的商品
	private LinearLayout favouritesLayout; // 统计

	Button mainbtn_b1;
	Button mainbtn_b2;
	Button mainbtn_b3;

	MyAdapter adapter = null;
	private RelativeLayout loadingparant;

	private int mDMType;
	private static final int DM_TYPE_ALL = 0;
	private static final int DM_TYPE_INBOX = 1;
	private static final int DM_TYPE_SENDBOX = 2;

	private MainListBean mainListBean = null;
	private List realListTmp; // 临时的List容器

	private Context context;

	private int flg = 1;// 记录点击了那个按钮

	protected boolean _onCreate(Bundle savedInstanceState) {
		if (super._onCreate(savedInstanceState)) {
			// No Title bar
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			requestWindowFeature(Window.FEATURE_PROGRESS); // 进度指示器功能
			setContentView(R.layout.main);

			TextView tvTitle = (TextView) findViewById(R.id.title);
			tvTitle.setText("XXX材料公司");

			context = getApplicationContext();

			bindFooterButtonEvent();
			mMaintList = (ListView) findViewById(R.id.tweet_list);

			mMaintList.setOnScrollListener(this); // 设置注册监听

			View footer = getLayoutInflater().inflate(R.layout.loading, null); // 加载视图
			mMaintList.addFooterView(footer);

			realListTmp = new ArrayList(); // 初始化容器
			loadData(flg); // 初始加载

			adapter = new MyAdapter(); // 创建自定义适配器对象
			mMaintList.setAdapter(adapter);

			// List Item 监听
			mMaintList.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					onListItemClickListener(arg1, arg2, arg3);
				}

			});

			tvListTitle = (TextView) findViewById(R.id.mainListViewTitile);
			friendsLayout = (RelativeLayout) findViewById(R.id.friendsLayout);
			followersLayout = (LinearLayout) findViewById(R.id.followersLayout);
			statusesLayout = (LinearLayout) findViewById(R.id.statusesLayout);
			favouritesLayout = (LinearLayout) findViewById(R.id.favouritesLayout);

			friendsLayout.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {

					tvListTitle.setText(R.string.profile_friends_count_title);
					flg = 1;
					loadData(1);

				}
			});
			followersLayout.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					tvListTitle.setText(R.string.profile_followers_count_title);

					flg = 2;
					loadData(flg);

				}
			});
			statusesLayout.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					tvListTitle.setText(R.string.profile_statuses_count_title);
					flg = 3;
					loadData(flg);
				}
			});
			favouritesLayout.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// flg = 4;
					// loadData(4);

					String[] itemText = { "六月", "七月", "八月", "九月" };
					double[] valuestmp = new double[] { 8596, 19485, 11889,
							10990 };
					Intent achartIntent = new AChartManage().getPieReport(
							context, itemText, valuestmp, "报表统计");

					startActivity(achartIntent);

				}
			});

			return true;
		} else {
			return false;
		}

	}

	// ListView按钮按下监听
	private void onListItemClickListener(View arg1, int arg2, long arg3) {

		String id = null;
		Gson gson = new Gson();

		MainListBean itemBean = null;

		Bundle bundle = new Bundle(); // 创建Bundle对象

		switch (flg) {
		case 1:
			if (arg2 < mainListBean.getMyinvoiceinfo().size()) {
				itemBean = gson.fromJson(
						mainListBean.getMyinvoiceinfo().get(arg2).toString(),
						MainListBean.class);
				bundle.putString("type", "1"); // 装入数据
				bundle.putString("id", itemBean.getInvoiceid()); // 装入数据
			}

			Intent intent = new Intent();
			intent.setClass(MainActivity.this, InvoiceViewDialog.class);
			intent.putExtras(bundle); // 把Bundle塞入Intent里面
			startActivity(intent);

			break;
			
		case 2:
			if (arg2 < mainListBean.getMycustomerinfo().size()) {
				itemBean = gson.fromJson(
						mainListBean.getMycustomerinfo().get(arg2).toString(),
						MainListBean.class);
				bundle.putString("type", "1"); // 装入数据
				bundle.putString("id", itemBean.getInvoiceid()); // 装入数据
			}

			Intent intent2 = new Intent();
			intent2.setClass(MainActivity.this, CustomerViewDialog.class);
			intent2.putExtras(bundle); // 把Bundle塞入Intent里面
			startActivity(intent2);

			break;
			
		case 3:
			if (arg2 < mainListBean.getMygoodsinfo().size()) {
				itemBean = gson.fromJson(
						mainListBean.getMygoodsinfo().get(arg2).toString(),
						MainListBean.class);
				bundle.putString("type", "1"); // 装入数据
				bundle.putString("id", itemBean.getInvoiceid()); // 装入数据
			}

			Intent intent3 = new Intent();
			intent3.setClass(MainActivity.this, GoodsViewDialog.class);
			intent3.putExtras(bundle); // 把Bundle塞入Intent里面
			startActivity(intent3);

			break;


		default:
			break;
		}

	}

	// 加载发票信息
	private void loadData(int dataFlg) {

		RequestParams params = new RequestParams();

		switch (dataFlg) {
		case 1:
			params.addQueryStringParameter("msgid", Consts.HTTP_MSGID_10005);
			break;
		case 2:
			params.addQueryStringParameter("msgid", Consts.HTTP_MSGID_10006);
			break;

		case 3:
			params.addQueryStringParameter("msgid", Consts.HTTP_MSGID_10007);
			break;

		default:
			break;
		}

		params.addQueryStringParameter("queryType", String.valueOf(dataFlg));

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
						String resultJsonStr = responseInfo.result.toString();
						if (resultJsonStr != null && !"".equals(resultJsonStr)) {
							Gson gson = new Gson();
							mainListBean = gson.fromJson(resultJsonStr,
									MainListBean.class);

							realListTmp.clear();

							switch (flg) {
							case 1:
								realListTmp = mainListBean.getMyinvoiceinfo();
								break;
							case 2:
								realListTmp = mainListBean.getMycustomerinfo();
								break;
							case 3:
								realListTmp = mainListBean.getMygoodsinfo();
								break;

							default:
								break;
							}

							handler.sendEmptyMessage(1); // 利用handler主线程刷新UI
						}
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						Toast.makeText(context, "数据加载失败", Toast.LENGTH_SHORT)
								.show();
					}
				});

	}

	/**
	 * 按钮监听绑定
	 */
	private void bindFooterButtonEvent() {
		mainbtn_b1 = (Button) findViewById(R.id.inbox);
		mainbtn_b2 = (Button) findViewById(R.id.sendbox);
		mainbtn_b3 = (Button) findViewById(R.id.new_message);

		mainbtn_b1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//				if (mDMType != DM_TYPE_INBOX) {
//					mDMType = DM_TYPE_INBOX;
//					mainbtn_b1.setEnabled(false);
//					mainbtn_b2.setEnabled(true);
//					Toast.makeText(context, "按下了按钮1", Toast.LENGTH_SHORT)
//							.show();
//
//				}
				
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, InvoiceMakeActivity.class);
				startActivity(intent);
			}
		});

		mainbtn_b2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mDMType != DM_TYPE_SENDBOX) {
					mDMType = DM_TYPE_SENDBOX;
					mainbtn_b1.setEnabled(true);
					mainbtn_b2.setEnabled(false);

					// 同步处理
					int sychTypeArray[] = { ConstsSync.CONSTS_SYNCH_TYPE_GOODS,
							ConstsSync.CONSTS_SYNCH_TYPE_PURCHASE };
					RemoteDataSynchManager sync = new RemoteDataSynchManager(
							MainActivity.this, sychTypeArray); // 同步管理器
					try {
						sync.synch();
					} catch (DbException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
		});

		mainbtn_b3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent intent = new Intent();
				intent.setClass(MainActivity.this, InvoiceMakeActivity.class);
				startActivity(intent);

			}
		});
	}

	/*
	 * scroll时间监听
	 * 
	 * @see android.widget.AbsListView.OnScrollListener#onScroll(android.widget.
	 * AbsListView, int, int, int)
	 */
	@Override
	public void onScroll(AbsListView view, int firstVisableitem,
			int visiableItemCount, int totalItemCount) {

		System.out.println("firstVisableitem:" + firstVisableitem);
		System.out.println("visiableItemCount:" + visiableItemCount);
		System.out.println("totalItemCount:" + totalItemCount);
		visibleLastNum = firstVisableitem + visiableItemCount - 1; // 可见的最后一行的索引
																	// =
																	// 第一行index
																	// + 可见的行数 -
																	// 1

	}

	/*
	 * scroll状态改变处理
	 * 
	 * @see
	 * android.widget.AbsListView.OnScrollListener#onScrollStateChanged(android
	 * .widget.AbsListView, int)
	 */
	// 记录屏幕可见范围最大的行index
	int visibleLastNum = 0;

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if ((visibleLastNum == adapter.getCount()) // 如果可见的最后一行index等于适配器对象
				// 的总数据行数（及最后一行）
				&& (scrollState == OnScrollListener.SCROLL_STATE_IDLE)) { //
			// 并且视图不处于scoll状态
			loadData(flg); // 加载数据
		}

	}

	int index = 1; // 加载的数据行数index

	/***
	 * 
	 * @ClassName: 自定义适配器类\
	 * @Description: listview的Adapter
	 * @author diyal.yin
	 * @date 2014-8-22 上午10:57:25
	 * 
	 */
	class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			int listCount = 0;
			if (realListTmp != null) {
				listCount = realListTmp.size();
			}

			return listCount;
		}

		@Override
		public Object getItem(int position) {

			if (realListTmp != null && realListTmp.size() > 0) {
				return realListTmp.get(position);
			}
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			ViewHolder vh = null;
			if (convertView == null) { // 如果convertView不存在，则去创建Listcell
				convertView = getLayoutInflater().inflate(
						R.layout.follower_item, null); // 通过listitem xml去填充视图
				vh = new ViewHolder();
				vh.iv_userheader = (ImageView) convertView
						.findViewById(R.id.profile_image);
				vh.tv_companyid = (TextView) convertView
						.findViewById(R.id.user_name);
				vh.tvConTextView = (TextView) convertView
						.findViewById(R.id.user_content);

				convertView.setTag(vh); // 设置Tag，为该list item的数据存储对象
			} else {
				vh = (ViewHolder) convertView.getTag();
			}

			Gson gson = new Gson();

			if (flg == 1) {

				MainListBean itemBean = null;
				if (position < mainListBean.getMyinvoiceinfo().size()) {
					itemBean = gson.fromJson(mainListBean.getMyinvoiceinfo()
							.get(position).toString(), MainListBean.class);
				}

				vh.tv_companyid.setText(itemBean.getInvoicename());
				vh.tvConTextView.setText(itemBean.getOther());
				vh.iv_userheader
						.setImageResource(R.drawable.ic_toolbar_geo_off_default);

			} else if (flg == 2) {
				MainListBean itemBean = null;
				if (position < mainListBean.getMycustomerinfo().size()) {
					itemBean = gson.fromJson(mainListBean.getMycustomerinfo()
							.get(position).toString(), MainListBean.class);
				}

				vh.tv_companyid.setText(itemBean.getCustomername());
				vh.tvConTextView.setText(itemBean.getOther());
				vh.iv_userheader
						.setImageResource(R.drawable.ic_toolbar_geo_off_focused);

			} else if (flg == 3) {
				MainListBean itemBean = null;
				if (position < mainListBean.getMygoodsinfo().size()) {
					itemBean = gson.fromJson(
							mainListBean.getMygoodsinfo().get(position)
									.toString(), MainListBean.class);
				}

				vh.tv_companyid.setText(itemBean.getGoodsname());
				vh.tvConTextView.setText(itemBean.getOther());
				vh.iv_userheader
						.setImageResource(R.drawable.ic_toolbar_geo_on_default);

			}

			return convertView;
		}
	}

	// 视图Holder 类似于Bean
	static class ViewHolder {
		ImageView iv_userheader;
		TextView tv_companyid;
		TextView tvConTextView;
		TextView textfollow_btn;
	}

	// Handler 线程之间通信的通道
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				// 更新UI
				adapter.notifyDataSetChanged();

				break;

			default:
				break;
			}
		};
	};

	// 网络数据获取（模拟加载数据）
	class LoadDataThread extends Thread {
		@Override
		public void run() {
			loadData(flg);
			// 通过handler处理器去通知主线程，修改UI
			handler.sendEmptyMessage(1);
		}
	}

}
