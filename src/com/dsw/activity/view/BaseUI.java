package com.dsw.activity.view;

import android.content.Context;
import android.view.View;

public abstract class BaseUI {
	protected Context context;
	public BaseUI(Context context) {
		this.context = context;
		init();
		setListener();
	}
	/**
	 * 内容中间列表
	 */
	protected View contentView;
	
	/**
	 * 初始化函数
	 */
	public abstract void init();
	
	/**
	 *设置监听
	 */
	public abstract void setListener();
	
	public abstract void onPause();
	public abstract void onResume();
	/**
	 * 获取view的id编号
	 */
	public abstract int getID();
	
	public View getContentView(){
		return contentView;
	}
}
