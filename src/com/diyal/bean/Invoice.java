/**   
 * @Title: Invoice.java 
 * @Package com.diyal.bean 
 * @System TODO  
 * @author 670924505@qq.com  
 * @date 2014-8-17 
 * @Copyright (c) Diyal All Rights Reserved.  
 */
package com.diyal.bean;

import java.util.List;

/**
 * @ClassName: Invoice
 * @Description: TODO(用一句话描述这个类的作用)
 * @author diyal.yin
 * @date 2014-8-17 下午12:46:05
 * 
 */
public class Invoice {

	/** 发票号 */
	private String invoicecatid;
	/** 开票时间 */
	private String makeintime;
	/** 发票序列号 */
	private String no;

	/** 购方名 */
	private String b_name;
	/** 购方名数组 */
	private List b_names;

	/** 购方纳税人识别号 */
	private String b_taxerno;
	/** 购方纳税人识别号数组 */
	private List b_taxernos;

	/** 购方地址电话 */
	private String b_addphone;
	/** 购方地址电话 数组 */
	private List b_addphones;

	/** 购方开户行及账号 */
	private String b_bankacc;
	/** 购方开户行及账号数组 */
	private List b_bankaccs;

	/** 售方名 */
	private String s_name;
	/** 售方纳税人识别号 */
	private String s_taxerno;
	/** 售方地址电话 */
	private String s_addphone;
	/** 售方开户行及账号 */
	private String s_bankacc;
	/** 商品名称 */
	private String g_name;
	/** 商品规格 */
	private String g_moden;
	/** 数量 */
	private String g_num;
	/** 单价 */
	private String g_price;
	/** 金额 */
	private String g_amount;
	/** 单位 */
	private String g_unit;
	/** 税率 */
	private String g_taxRate;
	/** 税额 */
	private String g_taxAmount;

	public String getB_name() {
		return b_name;
	}

	public void setB_name(String b_name) {
		this.b_name = b_name;
	}

	public String getB_taxerno() {
		return b_taxerno;
	}

	public void setB_taxerno(String b_taxerno) {
		this.b_taxerno = b_taxerno;
	}

	public String getB_addphone() {
		return b_addphone;
	}

	public void setB_addphone(String b_addphone) {
		this.b_addphone = b_addphone;
	}

	public String getB_bankacc() {
		return b_bankacc;
	}

	public void setB_bankacc(String b_bankacc) {
		this.b_bankacc = b_bankacc;
	}

	public String getS_name() {
		return s_name;
	}

	public void setS_name(String s_name) {
		this.s_name = s_name;
	}

	public String getS_taxerno() {
		return s_taxerno;
	}

	public void setS_taxerno(String s_taxerno) {
		this.s_taxerno = s_taxerno;
	}

	public String getS_addphone() {
		return s_addphone;
	}

	public void setS_addphone(String s_addphone) {
		this.s_addphone = s_addphone;
	}

	public String getS_bankacc() {
		return s_bankacc;
	}

	public void setS_bankacc(String s_bankacc) {
		this.s_bankacc = s_bankacc;
	}

	public String getG_name() {
		return g_name;
	}

	public void setG_name(String g_name) {
		this.g_name = g_name;
	}

	public String getG_moden() {
		return g_moden;
	}

	public void setG_moden(String g_moden) {
		this.g_moden = g_moden;
	}

	public String getG_unit() {
		return g_unit;
	}

	public void setG_unit(String g_unit) {
		this.g_unit = g_unit;
	}

	public String getG_taxRate() {
		return g_taxRate;
	}

	public void setG_taxRate(String g_taxRate) {
		this.g_taxRate = g_taxRate;
	}

	public String getG_num() {
		return g_num;
	}

	public void setG_num(String g_num) {
		this.g_num = g_num;
	}

	public String getG_price() {
		return g_price;
	}

	public void setG_price(String g_price) {
		this.g_price = g_price;
	}

	public String getG_amount() {
		return g_amount;
	}

	public void setG_amount(String g_amount) {
		this.g_amount = g_amount;
	}

	public String getInvoicecatid() {
		return invoicecatid;
	}

	public void setInvoicecatid(String invoicecatid) {
		this.invoicecatid = invoicecatid;
	}

	public String getMakeintime() {
		return makeintime;
	}

	public void setMakeintime(String makeintime) {
		this.makeintime = makeintime;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public List getB_names() {
		return b_names;
	}

	public void setB_names(List b_names) {
		this.b_names = b_names;
	}

	public List getB_taxernos() {
		return b_taxernos;
	}

	public void setB_taxernos(List b_taxernos) {
		this.b_taxernos = b_taxernos;
	}

	public List getB_addphones() {
		return b_addphones;
	}

	public void setB_addphones(List b_addphones) {
		this.b_addphones = b_addphones;
	}

	public List getB_bankaccs() {
		return b_bankaccs;
	}

	public void setB_bankaccs(List b_bankaccs) {
		this.b_bankaccs = b_bankaccs;
	}

	public String getG_taxAmount() {
		return g_taxAmount;
	}

	public void setG_taxAmount(String g_taxAmount) {
		this.g_taxAmount = g_taxAmount;
	}

}
