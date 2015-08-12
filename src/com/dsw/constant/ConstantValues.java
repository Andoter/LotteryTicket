package com.dsw.constant;

import com.dsw.lotteryticket.R;

public class ConstantValues {
	public static final int HOMEUNLOGIN_VIEW = 1;
	public static final int USERLOGIN_VIEW =2;
	public static final int HOME_VIEW = 0;
	public static final int MORENEWS_VIEW = 3;
	public static final int NEWSDETAIL_VIEW = 4;
	public static final int SSQ_VIEW = 5;
	public static int SCREEN_WIDTH;
	//xml解析格式
	public static final String ENCODING = "UTF-8";
	
	public static final int DOUBLECOLORBALL = 0;	//双色球
	public static final int THREEBALL = 1;			//10中3
	public static final int SENVENBALL = 2;			//七彩乐
	public static final int EAST6 = 3;				//东方6+1
		
	
	public static String xmlPath;
	
	public static int[] imageIds = { R.drawable.a, R.drawable.b, R.drawable.c,
			R.drawable.d, R.drawable.e };
	
	public static String url = "http://www.zhcw.com/";
	//设置新闻详情url地址
	private static String urlNewsDetail;
	public static String getUrlNewsDetail() {
		return urlNewsDetail;
	}
	public static void setUrlNewsDetail(String urlNewsDetail) {
		ConstantValues.urlNewsDetail = urlNewsDetail;
	}
	
}
