/**   
 * @Title: SyncRequestBean.java 
 * @Package com.diyal.sync 
 * @System TODO  
 * @author 670924505@qq.com  
 * @date 2014-8-20 
 * @Copyright (c) Diyal All Rights Reserved.  
 */
package com.diyal.sync;

import java.util.List;

/**
 * @ClassName: SyncRequestBean
 * @Description: 网络数据对象
 * @author diyal.yin
 * @date 2014-8-20 下午4:13:16
 * 
 */
public class SyncRequestBean {
	/** 消息ID */
	private String msgid;
	/** 购买方KEY */
	private List purchase;
	/** 商品数据 */
	private List goods;
	/** 时间数组 */
	private String time;

	public String getMsgid() {
		return msgid;
	}

	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}

	public List getPurchase() {
		return purchase;
	}

	public void setPurchase(List purchase) {
		this.purchase = purchase;
	}

	public List getGoods() {
		return goods;
	}

	public void setGoods(List goods) {
		this.goods = goods;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

}
