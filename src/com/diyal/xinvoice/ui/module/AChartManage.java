/**   
 * @Title: AChartManage.java 
 * @Package com.diyal.xinvoice.ui.module 
 * @System 报表管理工具类 
 * @author 670924505@qq.com  
 * @date 2014-8-21 
 * @Copyright (c) Diyal All Rights Reserved.  
 */
package com.diyal.xinvoice.ui.module;

import org.achartengine.ChartFactory;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

/**
 * @ClassName: AChartManage
 * @Description: 饼状图报表
 * @author diyal.yin
 * @date 2014-8-21 下午9:28:08
 * 
 */
public class AChartManage {

	/** 近四个月报表图颜色 */
	int[] colors = new int[] { Color.BLUE, Color.GREEN, Color.CYAN,
			Color.YELLOW };

	/**
	 * Executes the chart demo.
	 * 
	 * @param context
	 *            the context
	 * @return the built intent
	 */
	public Intent getPieReport(Context context, String[] itemText,
			double[] values, String titleStr) {

		DefaultRenderer renderer = buildCategoryRenderer(colors);
		// renderer.setChartTitle("统计结果");// 设置图表的标题 默认是居中顶部显示
		renderer.setChartTitleTextSize(20); // 设置图表标题的文字大小
		renderer.setDisplayValues(true); // 是否显示值
		renderer.setShowLabels(false);
		renderer.setLegendTextSize(20); // 设置左下角标注的文字大小
		// renderer.setZoomButtonsVisible(true);// 设置显示放大缩小按钮
		renderer.setZoomEnabled(false);// 设置不允许放大缩小.
		renderer.setLabelsTextSize(30);// 饼图上标记文字的字体大小
		renderer.setLabelsColor(Color.BLACK);// 饼图上标记文字的颜色
		renderer.setPanEnabled(false);// 设置是否可以平移
		renderer.setClickEnabled(true);// 设置是否可以被点击

		// 默认选择出来的类容快
		SimpleSeriesRenderer r = renderer.getSeriesRendererAt(0);
		r.setGradientEnabled(true); // 设置倾斜度
		r.setGradientStart(0, Color.BLUE);
		r.setGradientStop(0, Color.MAGENTA);
		r.setHighlighted(true);

		Intent intent = ChartFactory.getPieChartIntent(context,
				buildCategoryDataset("Project budget", itemText, values),
				renderer, titleStr);
		return intent;
	}

	/**
	 * 设置颜色
	 * 
	 * @param colors
	 *            颜色值
	 */
	protected DefaultRenderer buildCategoryRenderer(int[] colors) {
		DefaultRenderer renderer = new DefaultRenderer();
		for (int color : colors) {
			SimpleSeriesRenderer r = new SimpleSeriesRenderer();
			r.setColor(color);
			renderer.addSeriesRenderer(r);
		}
		return renderer;
	}

	/**
	 * Builds a category series using the provided values.
	 * 
	 * @param titles
	 *            the series titles
	 * @param values
	 *            the values
	 * @return the category series
	 */
	protected CategorySeries buildCategoryDataset(String title,
			String[] itemText, double[] values) {
		CategorySeries series = new CategorySeries(title);
		// int k = 0;
		// for (double value : values) {
		// series.add("Project " + ++k, value);
		// }

		for (int i = 0; i < values.length; i++) {
			series.add(itemText[i], values[i]);
		}

		return series;
	}
}
