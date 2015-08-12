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
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class HomeView extends BaseUI{
	private ViewPager viewPager;
	private LinearLayout point_Group;
	private ImageView iv_more;
	private List<ImageView> listImageView;
	TextView tv_ssq_date;
	LinearLayout linear_ssq;
	TextView tv_3d_date;
	LinearLayout linear_3d;
	private ListView listview;
	private List<LotteryNews> listLottery;
	/**
	 * 上一个页面的位置
	 */
	protected int lastPosition;
	/**
	 * 判断是否自动滚动
	 */
	private boolean isRunning = false;
	
	private MyHandler handler;
	public LotteryNewsAdapter adapter;
	
	public HomeView(Context context) {
		super(context);
	}

	@Override
	public void init() {
		listImageView = new ArrayList<ImageView>();
		contentView = LayoutInflater.from(context).inflate(R.layout.home_view, null);
		tv_ssq_date = (TextView) contentView.findViewById(R.id.ssq_date);
		linear_ssq = (LinearLayout) contentView.findViewById(R.id.ssq_ball);
		tv_3d_date = (TextView) contentView.findViewById(R.id.d_date);
		linear_3d = (LinearLayout) contentView.findViewById(R.id.d_ball);
		viewPager = (ViewPager) contentView.findViewById(R.id.viewpager);
		point_Group = (LinearLayout)contentView.findViewById(R.id.point_group);
		listview = (ListView) contentView.findViewById(R.id.lottery_listview);
		iv_more = (ImageView) contentView.findViewById(R.id.iv_lottery_newsmore);
		initImageViews();
		viewPager.setAdapter(new MyPagerAdapter());
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				//改变指示点的状态
				//把当前点enbale 为true 
				point_Group.getChildAt(position % listImageView.size()).setEnabled(true);
				//把上一个点设为false
				point_Group.getChildAt(lastPosition).setEnabled(false);
				lastPosition = position % listImageView.size();
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				
			}
		});
		
		//进行延迟操作
		 /*
		  * 自动循环：
		  * 1、定时器：Timer
		  * 2、开子线程 while  true 循环
		  * 3、ColckManager 
		  * 4、 用handler 发送延时信息，实现循环
		  */
		isRunning = true;
		handler = new MyHandler();
		handler.sendEmptyMessageDelayed(0, 2000);
		new MyLotteryNumber().execute();
	}

	@Override
	public void setListener() {
		iv_more.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				ContentManager.getInstance().changeUI(MoreNewsView.class);
			}
		});
		
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				//获取对应的Url
				LotteryNews news = (LotteryNews) adapter.getItem(arg2);
				ConstantValues.setUrlNewsDetail(news.getNewUrl());
				ContentManager.getInstance().changeUI(NewsDetailView.class);
			}
		});
		
	}

	@Override
	public int getID() {
		return ConstantValues.HOME_VIEW;
	}
	
	class MyHandler extends Handler{

		@Override
		public void handleMessage(Message msg) {
			//让viewPager 滑动到下一页
			viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
			isRunning = true;
			if(isRunning){
				handler.sendEmptyMessageDelayed(0, 2000);
			}
		}
	}
	
	private void initImageViews(){
		for(int i=0;i<ConstantValues.imageIds.length;i++){
			//初始化图片资源
			ImageView image = new ImageView(context);
			image.setBackgroundResource(ConstantValues.imageIds[i]);
			listImageView.add(image);
			//添加指示点
			ImageView point =new ImageView(context);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			
			params.rightMargin = 20;
			point.setLayoutParams(params);
			
			point.setBackgroundResource(R.drawable.point_bg);
			if(i==0){
				point.setEnabled(true);
			}else{
				point.setEnabled(false);
			}
			point_Group.addView(point);
		}
	}
	
	class MyPagerAdapter extends PagerAdapter{

		@Override
		public int getCount() {
			//设置为最大值，进行循环
			return Integer.MAX_VALUE;
		}

		@Override
		public boolean isViewFromObject(View view, Object obj) {
			return view == obj;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View)object);
			object = null;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(listImageView.get(position % listImageView.size()));
			return listImageView.get(position % listImageView.size());
		}
		
		
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
			list = JoupAndHtmlClient.getLotteryNumber();
			listLottery = JoupAndHtmlClient.getNews(false);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			adapter = new LotteryNewsAdapter(listLottery, context);
			listview.setAdapter(adapter);
			if(list != null && list.size() >0){
				for(int i=0;i<list.size();i++){
					LotteryNumber number = list.get(i);
					if(number.getType() == ConstantValues.DOUBLECOLORBALL){
						tv_ssq_date.setText("第" + number.getDate()+ "期");
						String redBall = number.getRedNumber();
						int start = 0,end = 0;
						TextView tv_ball;
						while(end < redBall.length()-1){
							end =start+2;
							tv_ball = new TextView(context);
							tv_ball.setTextColor(Color.WHITE);
							tv_ball.setBackgroundResource(R.drawable.id_redball);
							tv_ball.setGravity(Gravity.CENTER);
							tv_ball.setText(redBall.subSequence(start, end));
							start = end;
							linear_ssq.addView(tv_ball);
						}
						tv_ball = new TextView(context);
						tv_ball.setText(number.getBlueNumber());
						tv_ball.setGravity(Gravity.CENTER);
						tv_ball.setTextColor(Color.WHITE);
						tv_ball.setBackgroundResource(R.drawable.id_blueball);
						linear_ssq.addView(tv_ball);
					}else if(number.getType() == ConstantValues.THREEBALL){
						tv_3d_date.setText("第" + number.getDate()+ "期");
						TextView tv_ball;
						String blueNumber = number.getBlueNumber();
						int start =0,end =0;
						while(end < blueNumber.length()){
							tv_ball = new TextView(context);
							end = start + 1;
							tv_ball.setText(blueNumber.subSequence(start, end));
							tv_ball.setGravity(Gravity.CENTER);
							tv_ball.setTextColor(Color.WHITE);
							tv_ball.setBackgroundResource(R.drawable.id_blueball);
							linear_3d.addView(tv_ball);
							start = end;
						}
					}
				}
			}
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
