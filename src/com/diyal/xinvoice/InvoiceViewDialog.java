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

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import com.diyal.bean.Invoice;
import com.diyal.sync.ConstsSync;
import com.diyal.sync.RemoteDataSynchManager;
import com.diyal.util.BigAmountUtil;
import com.diyal.util.Consts;
import com.diyal.util.JsonUtil;
import com.diyal.xinvoice.ui.base.BaseActivity;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * @ClassName: InvoiceMakeActivity
 * @Description: Invoice预览
 * @author diyal.yin
 * @date 2014-8-15 上午12:24:22
 * 
 */
public class InvoiceViewDialog extends BaseActivity {
	// 购方
	@ViewInject(R.id.sp_buyunit_name)
	private TextView mBuyName;

	@ViewInject(R.id.sp_buyunit_taxpayerno)
	private TextView mBuyTaxpayerno;

	@ViewInject(R.id.sp_buyunit_addphone)
	private TextView mBuyAddphone;

	@ViewInject(R.id.sp_buyunit_accountnum)
	private TextView mBuyAccNum;

	// 售方
	@ViewInject(R.id.tv_saleunit_name)
	private TextView mSaleUnitName;

	@ViewInject(R.id.tv_saleunit_taxpayerno)
	private TextView mSaleTaxpayerNo;

	@ViewInject(R.id.tv_saleunit_addphone)
	private TextView mSaleuintAddphone;

	@ViewInject(R.id.sp_saleunit_accountnum)
	private TextView mSaleAccnum;

	// 商品
	@ViewInject(R.id.sp_goods_name)
	private TextView mGoodsname;

	@ViewInject(R.id.sp_goods_goodsmoden)
	private TextView mGoodsmoden;

	@ViewInject(R.id.et_goods_num)
	private TextView mGoodsNum;

	@ViewInject(R.id.tv_goods_costunit)
	private TextView mGoodsCostUnit;

	@ViewInject(R.id.et_goods_unitprice)
	private TextView mGoodsUnitPrice;

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
			setContentView(R.layout.view_invoice_dialog);

			ViewUtils.inject(this);

			context = getApplicationContext();

			getInitData();

			return true;
		} else {
			return false;
		}

	}

	private void getInitData() {

		Intent intent = this.getIntent(); // 获取已有的intent对象
		Bundle bundle = intent.getExtras(); // 获取intent里面的bundle对象
		String key1 = bundle.getString("type"); // 获取Bundle里面的字符串
		String key2 = bundle.getString("id"); // 获取Bundle里面的字符串

		RequestParams params = new RequestParams();
		params.addQueryStringParameter("msgid", Consts.HTTP_MSGID_10009);
		params.addQueryStringParameter("type", key1);
		params.addQueryStringParameter("id", key2);

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

	private void setViewInit(Invoice inv) {

		mBuyName.setText(inv.getB_name());

		mBuyTaxpayerno.setText(inv.getB_taxerno());

		mBuyAddphone.setText(inv.getB_addphone());

		mBuyAccNum.setText(inv.getB_bankacc());

		// 销方信息
		mSaleUnitName.setText(inv.getS_name());

		mSaleTaxpayerNo.setText(inv.getS_taxerno());

		mSaleuintAddphone.setText(inv.getS_addphone());

		mSaleAccnum.setText(inv.getS_bankacc());

		// 商品名称
		mGoodsname.setText(inv.getG_name());

		// 规格型号
		mGoodsmoden.setText(inv.getG_moden());

		mGoodsNum.setText(inv.getG_num());

		// 单位
		mGoodsCostUnit.setText(inv.getG_unit());

		mGoodsUnitPrice.setText(inv.getG_price());

		mGoodsAmount.setText(inv.getG_amount());

		// 税率
		mGoodsTax.setText(inv.getG_taxRate());

		mGoodsTaxAmount.setText(inv.getG_taxAmount());

		mSumAmount.setText(inv.getG_amount());

		mSumTaxAmount.setText(inv.getG_taxAmount());

		String amountUnTax = inv.getG_amount();
		String amountTax = inv.getG_taxAmount();

		Double amountInt = 0.0;
		if (amountUnTax != null && !"".equals(amountUnTax)) {
			amountInt = Double.valueOf(amountUnTax);
		}
		Double pTaxAmount = 0.0;
		if (amountTax != null && !"".equals(amountTax)) {
			pTaxAmount = Double.valueOf(amountTax.toString());
		}

		Double amountTaxSum = (amountInt + pTaxAmount);

		mPriceSumUp.setText(BigAmountUtil.toBigAmt(amountTaxSum));
		mPriceSumDown.setText(String.valueOf(amountTaxSum));

	}

}
