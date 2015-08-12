package com.dsw.activity.view;

import java.util.ArrayList;
import java.util.List;

import com.dsw.constant.ConstantValues;
import com.dsw.entity.LotteryNews;
import com.dsw.entity.LotteryNumber;
import com.dsw.lotteryticket.R;
import com.dsw.utils.JoupAndHtmlClient;
import com.dsw.utils.PromptManager;
import com.dsw.view.adapter.LotteryNewsAdapter;
import com.dsw.view.manager.ContentManager;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MoreNewsView extends BaseUI{
	private ListView listview;
	private List<LotteryNews> listLottery;
	private LotteryNewsAdapter adapter;
	public MoreNewsView(Context context) {
		super(context);
	}

	@Override
	public void init() {
		listLottery = new ArrayList<LotteryNews>();
		contentView = LayoutInflater.from(context).inflate(R.layout.new_list, null);
		listview = (ListView) contentView.findViewById(R.id.listview_news);
		new MyLotteryNumber().execute();
	}

	@Override
	public void setListener() {
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if(adapter != null){
					//获取对应的Url
					LotteryNews news = (LotteryNews) adapter.getItem(arg2);
					ConstantValues.setUrlNewsDetail(news.getNewUrl());
					ContentManager.getInstance().changeUI(NewsDetailView.class);
				}
			}
		});
	}

	@Override
	public int getID() {
		return ConstantValues.MORENEWS_VIEW;
	}

	class MyLotteryNumber extends AsyncTask<Void, Void, Void>{
		List<LotteryNumber> list;
		
		@Override
		protected void onPreExecute() {
			PromptManager.showProgressDialog(context);
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(Void... params) {
			listLottery = JoupAndHtmlClient.getNews(true);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			adapter = new LotteryNewsAdapter(listLottery, context);
			listview.setAdapter(adapter);
			PromptManager.closeProgressDialog();
			super.onPostExecute(result);
		}
		
	}

	@Override
	public void onPause() {
		
	}

	@Override
	public void onResume() {
		
	}
}
