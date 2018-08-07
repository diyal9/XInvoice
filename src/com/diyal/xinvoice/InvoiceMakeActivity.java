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
 * @ClassName: InvoiceMakeActivity
 * @Description: 发票信息录入
 * @author diyal.yin
 * @date 2014-8-15 上午12:24:22
 * 
 */
public class InvoiceMakeActivity extends BaseActivity {
	@ViewInject(R.id.tv_title_label)
	private TextView mTitle;
	// 购方
	@ViewInject(R.id.sp_buyunit_name)
	private Spinner mBuyName;

	@ViewInject(R.id.sp_buyunit_taxpayerno)
	private Spinner mBuyTaxpayerno;

	@ViewInject(R.id.sp_buyunit_addphone)
	private Spinner mBuyAddphone;

	@ViewInject(R.id.sp_buyunit_accountnum)
	private Spinner mBuyAccNum;

	// 售方
	@ViewInject(R.id.tv_saleunit_name)
	private TextView mSaleUnitName;

	@ViewInject(R.id.tv_saleunit_taxpayerno)
	private TextView mSaleTaxpayerNo;

	@ViewInject(R.id.tv_saleunit_addphone)
	private TextView mSaleuintAddphone;

	@ViewInject(R.id.sp_saleunit_accountnum)
	private Spinner mSaleAccnum;

	// 商品
	@ViewInject(R.id.sp_goods_name)
	private Spinner mGoodsname;

	@ViewInject(R.id.sp_goods_goodsmoden)
	private Spinner mGoodsmoden;

	@ViewInject(R.id.et_goods_num)
	private EditText mGoodsNum;

	@ViewInject(R.id.tv_goods_costunit)
	private Spinner mGoodsCostUnit;

	@ViewInject(R.id.et_goods_unitprice)
	private EditText mGoodsUnitPrice;

	@ViewInject(R.id.tv_goods_amount)
	private TextView mGoodsAmount;

	@ViewInject(R.id.tv_goods_tax)
	private TextView mGoodsTax;

	@ViewInject(R.id.tv_goods_taxamount)
	private TextView mGoodsTaxAmount;

	// 合计
	@ViewInject(R.id.tv_goods_sumamount)
	private TextView mSumAmount;

	@ViewInject(R.id.tv_goods_sumtaxamount)
	private TextView mSumTaxAmount;

	@ViewInject(R.id.tv_pricetaxsum_up)
	private TextView mPriceSumUp;

	@ViewInject(R.id.tv_pricetaxsum_down)
	private TextView mPriceSumDown;

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

	// 事件注解 方法名要按照onClick后缀
	@OnClick(R.id.top_back)
	public void goBackToMainonClick(View v) {
		Intent intent = new Intent();
		intent.setClass(InvoiceMakeActivity.this, MainActivity.class);
		startActivity(intent);
		InvoiceMakeActivity.this.finish();
	}

	// 确认按钮监听
	@OnClick(R.id.top_send_btn)
	public void sureOnClick(View v) {
		// TODO 之前应该有个CHeck的，目前随便判断是否为空
		if (TextUtils.isEmpty(mGoodsNum.getText())) {
			Toast.makeText(context, "数量不能为空!", Toast.LENGTH_SHORT).show();
			return;
		}

		if (TextUtils.isEmpty(mGoodsUnitPrice.getText())) {
			Toast.makeText(context, "单价不能为空!", Toast.LENGTH_SHORT).show();
			return;
		}

		postFormSure();

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

						setViewInit(inv);
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

		Gson gson = new Gson();
		String jsonStr = gson.toJson(getInvoiceBeanInfo());
		params.addBodyParameter("forminfo", jsonStr);

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

	private Invoice getInvoiceBeanInfo() {
		Invoice invc = new Invoice();
		invc.setB_name(mBuyName.getSelectedItem().toString());
		invc.setB_taxerno(mBuyTaxpayerno.getSelectedItem().toString());
		invc.setB_addphone(mBuyAddphone.getSelectedItem().toString());
		invc.setB_bankacc(mBuyAccNum.getSelectedItem().toString());

		invc.setS_name(mSaleUnitName.getText().toString());
		invc.setS_taxerno(mSaleTaxpayerNo.getText().toString());
		invc.setS_addphone(mSaleuintAddphone.getText().toString());
		invc.setS_bankacc(mSaleAccnum.getSelectedItem().toString());

		invc.setG_name(mGoodsname.getSelectedItem().toString());
		invc.setG_moden(mGoodsmoden.getSelectedItem().toString());
		invc.setG_num(mGoodsNum.getText().toString());
		invc.setG_price(mGoodsUnitPrice.getText().toString());
		invc.setG_amount(mGoodsAmount.getText().toString());
		invc.setG_taxRate(mGoodsTax.getText().toString());

		return invc;

	}

	private void setViewInit(Invoice inv) {

		dynamicBuyInfo(inv);
		mBuyName.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		// 购货方数据设置
		// DbUtils dbUtils = DbUtils.create(context, Consts.SQLITE_NAME);
		// dbUtils.configAllowTransaction(true);
		// dbUtils.configDebug(true);
		// List<GoodsBean> saveList = null;
		//
		// try {
		// saveList = dbUtils.findAll(Selector.from(GoodsBean.class));
		//
		// } catch (DbException e) {
		// e.printStackTrace();
		// }
		//
		// GoodsBean goodsBean = null;
		//
		// String[] mItems = {};
		// for (int i = 0; i < saveList.size(); i++) {
		// goodsBean = saveList.get(i);
		// mItems[i] = goodsBean.getGoodsname();
		// }

		// ArrayAdapter<String> _Adapter = new ArrayAdapter<String>(this,
		// android.R.layout.simple_spinner_item, inv.getB_names());
		// mBuyName.setAdapter(_Adapter);
		//
		// // String[] mItems2 = { inv.getB_taxerno() };
		// // ArrayAdapter<String> _Adapter2 = new ArrayAdapter<String>(this,
		// // android.R.layout.simple_spinner_item, mItems2);
		// // mBuyTaxpayerno.setAdapter(_Adapter2);
		//
		// ArrayAdapter<String> _Adapter2 = new ArrayAdapter<String>(this,
		// android.R.layout.simple_spinner_item, inv.getB_taxernos());
		// mBuyTaxpayerno.setAdapter(_Adapter2);
		//
		// // String[] mItems3 = { inv.getB_addphone() };
		// // ArrayAdapter<String> _Adapter3 = new ArrayAdapter<String>(this,
		// // android.R.layout.simple_spinner_item, mItems3);
		// // mBuyAddphone.setAdapter(_Adapter3);
		//
		// ArrayAdapter<String> _Adapter3 = new ArrayAdapter<String>(this,
		// android.R.layout.simple_spinner_item, inv.getB_addphones());
		// mBuyAddphone.setAdapter(_Adapter3);
		//
		// // String[] mItems4 = { inv.getB_bankacc() };
		// // ArrayAdapter<String> _Adapter4 = new ArrayAdapter<String>(this,
		// // android.R.layout.simple_spinner_item, mItems4);
		// // mBuyAccNum.setAdapter(_Adapter4);
		//
		// ArrayAdapter<String> _Adapter4 = new ArrayAdapter<String>(this,
		// android.R.layout.simple_spinner_item, inv.getB_bankaccs());
		// mBuyAccNum.setAdapter(_Adapter4);

		// 销方信息
		mSaleUnitName.setText(inv.getS_name());

		mSaleTaxpayerNo.setText(inv.getS_taxerno());

		mSaleuintAddphone.setText(inv.getS_addphone());

		String[] mSItems4 = { inv.getB_bankacc() };
		ArrayAdapter<String> _sAdapter4 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, mSItems4);
		mSaleAccnum.setAdapter(_sAdapter4);

		// 商品名称
		String[] mGoodsNameItems = { inv.getG_name() };
		ArrayAdapter<String> _goodsAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, mGoodsNameItems);
		mGoodsname.setAdapter(_goodsAdapter);

		// 规格型号
		String[] mGoodsModenItems = { inv.getG_moden() };
		ArrayAdapter<String> _goodsModenAdapter = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item, mGoodsModenItems);
		mGoodsmoden.setAdapter(_goodsModenAdapter);

		// 单位
		String[] mGoodsUnitItems = { inv.getG_unit() };
		ArrayAdapter<String> _goodsUnitAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, mGoodsUnitItems);
		mGoodsCostUnit.setAdapter(_goodsUnitAdapter);

		// 税率
		mGoodsTax.setText(inv.getG_taxRate());

		// 单价
		mGoodsUnitPrice.addTextChangedListener(new TextWatcher() {
			private CharSequence temp;

			@Override
			public void afterTextChanged(Editable arg0) {
				onTextChange(temp.toString(), mGoodsNum.getText().toString());
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
			}

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				temp = arg0.toString();
			}

		});

		// 数量
		mGoodsNum.addTextChangedListener(new TextWatcher() {
			private CharSequence temp;

			@Override
			public void afterTextChanged(Editable arg0) {
				onTextChange(temp.toString(), mGoodsUnitPrice.getText()
						.toString());
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {

			}

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				temp = arg0.toString();
			}

		});
	}

	private void dynamicBuyInfo(Invoice inv) {
		ArrayAdapter<String> _Adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, inv.getB_names());
		mBuyName.setAdapter(_Adapter);

		ArrayAdapter<String> _Adapter2 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, inv.getB_taxernos());
		mBuyTaxpayerno.setAdapter(_Adapter2);

		ArrayAdapter<String> _Adapter3 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, inv.getB_addphones());
		mBuyAddphone.setAdapter(_Adapter3);

		ArrayAdapter<String> _Adapter4 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, inv.getB_bankaccs());
		mBuyAccNum.setAdapter(_Adapter4);
	}

	/**
	 * 输入框变化，值得变更
	 * 
	 */
	private void onTextChange(String num, String price) {
		int goodsNumInt = 0;
		if (num != null && !"".equals(num)) {
			goodsNumInt = Integer.valueOf(num);
		}
		int pTmpAmount = 0;
		if (price != null && !"".equals(price)) {
			pTmpAmount = Integer.valueOf(price.toString());
		}

		double tax = 0;
		if (mGoodsTax != null && !"".equals(mGoodsTax.getText().toString())) {
			String taxStr = mGoodsTax.getText().toString().replace("%", "");
			tax = Double.valueOf(taxStr) * 0.01;
		}

		DecimalFormat df = new DecimalFormat("######0.00");
		String taxamount = df.format(pTmpAmount * goodsNumInt * tax);
		mGoodsAmount.setText(String.valueOf(pTmpAmount * goodsNumInt));
		mGoodsTaxAmount.setText(taxamount);

		// 合计
		mSumAmount.setText(String.valueOf(pTmpAmount * goodsNumInt));
		mSumTaxAmount.setText(taxamount);

		Double priceSum = (pTmpAmount * goodsNumInt)
				+ (pTmpAmount * goodsNumInt * tax);

		mPriceSumUp.setText(BigAmountUtil.toBigAmt(priceSum));
		mPriceSumDown.setText(String.valueOf(priceSum));
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
