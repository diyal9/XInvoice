/**   
 * @Title: Purchase.java 
 * @Package com.diyal.bean 
 * @System TODO  
 * @author 670924505@qq.com  
 * @date 2014-8-19 
 * @Copyright (c) Diyal All Rights Reserved.  
 */
package com.diyal.sync;

import com.lidroid.xutils.db.annotation.Column;

/**
 * @ClassName: Purchase
 * @Description: 购货单位SQLite同步存储Bean
 * @author diyal.yin
 * @date 2014-8-19 下午3:00:45
 * 
 */
public class PurchaseLocal {
	/** 购货单位Id */
	@Column(column = "b_id")
	private int b_id;

	/** 购货单位名称 */
	@Column(column = "b_name")
	private String b_name;

	/** 纳税人识别号数组 */
	@Column(column = "b_taxernos")
	private String[] b_taxernos;

	/** 购方地址电话 */
	@Column(column = "b_addphones")
	private String[] b_addphones;

	/** 购方开户行及账号 */
	@Column(column = "b_bankaccs")
	private String[] b_bankaccs;

	public int getB_id() {
		return b_id;
	}

	public void setB_id(int b_id) {
		this.b_id = b_id;
	}

	public String getB_name() {
		return b_name;
	}

	public void setB_name(String b_name) {
		this.b_name = b_name;
	}

	public String[] getB_taxernos() {
		return b_taxernos;
	}

	public void setB_taxernos(String[] b_taxernos) {
		this.b_taxernos = b_taxernos;
	}

	public String[] getB_addphones() {
		return b_addphones;
	}

	public void setB_addphones(String[] b_addphones) {
		this.b_addphones = b_addphones;
	}

	public String[] getB_bankaccs() {
		return b_bankaccs;
	}

	public void setB_bankaccs(String[] b_bankaccs) {
		this.b_bankaccs = b_bankaccs;
	}

}
