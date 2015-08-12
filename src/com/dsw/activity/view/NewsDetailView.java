package com.dsw.activity.view;

import com.dsw.constant.ConstantValues;
import com.dsw.lotteryticket.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.webkit.WebView;

public class NewsDetailView extends BaseUI{
	private WebView webView;
	
	public NewsDetailView(Context context) {
		super(context);
	}

	@Override
	public void init() {
		contentView = LayoutInflater.from(context).inflate(R.layout.news_detail, null);
		webView = (WebView) contentView.findViewById(R.id.webview_news);
		webView.loadUrl(ConstantValues.getUrlNewsDetail());
	}

	@Override
	public void setListener() {
		
	}

	@Override
	public int getID() {
		return ConstantValues.NEWSDETAIL_VIEW;
	}

	@Override
	public void onPause() {
		
	}

	@Override
	public void onResume() {
		
	}

}
