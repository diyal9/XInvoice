/**   
 * @Title: User.java 
 * @Package com.diyal.bean 
 * @System TODO  
 * @author 670924505@qq.com  
 * @date 2014-8-13 
 * @Copyright (c) Diyal All Rights Reserved.  
 */
package com.diyal.bean;

/**
 * @ClassName: User
 * @Description: 用户对象
 * @author diyal.yin
 * @date 2014-8-13 下午11:37:02
 * 
 */
public class User {
	private String acountid;
	private String userid;
	private String password;
	private String companyid;
	private String content; // 企业描述
	private int relation; // 关系
	private int activity; // 活动标识

	public String getAcountid() {
		return acountid;
	}

	public void setAcountid(String acountid) {
		this.acountid = acountid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCompanyid() {
		return companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getRelation() {
		return relation;
	}

	public void setRelation(int relation) {
		this.relation = relation;
	}

	public int getActivity() {
		return activity;
	}

	public void setActivity(int activity) {
		this.activity = activity;
	}

}
