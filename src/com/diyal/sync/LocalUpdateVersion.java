/**   
 * @Title: LocalUpdateTimeBean.java 
 * @Package com.diyal.DataSynchronization 
 * @System TODO  
 * @author 670924505@qq.com  
 * @date 2014-8-19 
 * @Copyright (c) Diyal All Rights Reserved.  
 */
package com.diyal.sync;

import com.lidroid.xutils.db.annotation.Column;

/**
 * @ClassName: LocalUpdateTimeBean
 * @Description: 更新时间Bean
 * @author diyal.yin
 * @date 2014-8-19 下午4:24:41
 * @category 记录最后一次跟新时间
 * 
 */
public class LocalUpdateVersion {

	/** Id */
	@Column(column = "sqlite_id")
	private int id;

	/** 对应的更新对象ID（ConstsSynchron定义） */
	@Column(column = "sqlite_tableId")
	private int sqlite_tableId;

	/** 最后一次更新时间 */
	@Column(column = "sqlite_LastUpdateTime")
	private String sqlite_LastUpdateTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSqlite_tableId() {
		return sqlite_tableId;
	}

	public void setSqlite_tableId(int sqlite_tableId) {
		this.sqlite_tableId = sqlite_tableId;
	}

	public String getSqlite_LastUpdateTime() {
		return sqlite_LastUpdateTime;
	}

	public void setSqlite_LastUpdateTime(String sqlite_LastUpdateTime) {
		this.sqlite_LastUpdateTime = sqlite_LastUpdateTime;
	}

}
