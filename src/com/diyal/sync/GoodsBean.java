package com.diyal.sync;

/**
 * @ClassName: goodsBean.java
 * @Description: TODO
 * @computer：ASUS-PC 下午2:10:02
 * @author terryZhang
 * @email:
 * @company: Lunabox Mobile Technology Co.,Ltd
 * @date 2014年8月20日
 */
public class GoodsBean {

	private int id;

	/** 商品id */
	private int goodsid;

	/** 商品name */
	private String goodsname;

	/** 规格型号 */
	private String model;

	/** 计量单位 */
	private String unitsofmeasurement;

	/** 数量 */
	private int number;

	/** 单价(不含税) */
	private double unitpricenotax;

	/** 金额(不含税) */
	private double moneynotax;

	/** 税率 */
	private double taxrate;

	/** 税额 */
	private double taxpaid;

	/** 插入数据的时间 */
	private String goodsinserttime;

	public int getGoodsid() {
		return goodsid;
	}

	public void setGoodsid(int goodsid) {
		this.goodsid = goodsid;
	}

	public String getGoodsname() {
		return goodsname;
	}

	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getUnitsofmeasurement() {
		return unitsofmeasurement;
	}

	public void setUnitsofmeasurement(String unitsofmeasurement) {
		this.unitsofmeasurement = unitsofmeasurement;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public double getUnitpricenotax() {
		return unitpricenotax;
	}

	public void setUnitpricenotax(double unitpricenotax) {
		this.unitpricenotax = unitpricenotax;
	}

	public double getMoneynotax() {
		return moneynotax;
	}

	public void setMoneynotax(double moneynotax) {
		this.moneynotax = moneynotax;
	}

	public double getTaxrate() {
		return taxrate;
	}

	public void setTaxrate(double taxrate) {
		this.taxrate = taxrate;
	}

	public double getTaxpaid() {
		return taxpaid;
	}

	public void setTaxpaid(double taxpaid) {
		this.taxpaid = taxpaid;
	}

	public String getGoodsinserttime() {
		return goodsinserttime;
	}

	public void setGoodsinserttime(String goodsinserttime) {
		this.goodsinserttime = goodsinserttime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
