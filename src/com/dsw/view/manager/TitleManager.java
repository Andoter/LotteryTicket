package com.dsw.view.manager;
import java.util.Observable;
import java.util.Observer;

import org.apache.commons.lang3.StringUtils;

import com.dsw.activity.view.CopyOfUserLogin;
import com.dsw.activity.view.UserLogin;
import com.dsw.constant.ConstantValues;
import com.dsw.lotteryticket.R;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TitleManager implements Observer {
	//单例模式
	private static TitleManager instance = new TitleManager();
	private TitleManager(){
	
	}
	
	public static TitleManager getInstance(){
		return instance;
	}
	private RelativeLayout relative_common_container;
	private RelativeLayout relative_unlogin_container;
	private RelativeLayout relative_login_container;
	private ImageView iv_back;
	private ImageView iv_help;
	private ImageView iv_regist;
	private ImageView iv_login;
	private TextView tv_title;
	
	public void initView(Activity activity){
		relative_common_container = (RelativeLayout) activity.findViewById(R.id.top_title_common);
		relative_login_container = (RelativeLayout) activity.findViewById(R.id.top_login_title);
		relative_unlogin_container = (RelativeLayout) activity.findViewById(R.id.top_unlogin_title);
		
		iv_back = (ImageView) activity.findViewById(R.id.iv_back_common);
		iv_help = (ImageView) activity.findViewById(R.id.iv_help_common);
		tv_title = (TextView) activity.findViewById(R.id.tv_title_content);
		iv_regist = (ImageView) activity.findViewById(R.id.iv_title_regist);
		iv_login = (ImageView) activity.findViewById(R.id.iv_title_login);
		setListener();
	}
	
	private void setListener(){
		iv_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ContentManager.getInstance().goBack();
			}
		});
		
		iv_help.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.d("ViewManager", "ViewManager:help");
			}
		});
		
		iv_login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ContentManager.getInstance().changeUI(UserLogin.class);
			}
		});
		iv_regist.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ContentManager.getInstance().changeUI(UserLogin.class);
			}
		});
	}
	
	private void initAllView(){
		relative_common_container.setVisibility(View.GONE);
		relative_login_container.setVisibility(View.GONE);
		relative_unlogin_container.setVisibility(View.GONE);
	}
	
	/*
	 * 展示通用
	 */
	public void showCommonTitle(){
		initAllView();
		relative_common_container.setVisibility(View.VISIBLE);
	}
	
	/*
	 * 展示登录
	 */
	public void showLoginTitle(){
		initAllView();
		relative_login_container.setVisibility(View.VISIBLE);
	}
	
	/*
	 * 展示未登陆
	 */
	public void showUnLoginTitle(){
		initAllView();
		relative_unlogin_container.setVisibility(View.VISIBLE);
	}
	
	//改变标题
	public void changeTitle(String title){
		tv_title.setText(title);
	}

	@Override
	public void update(Observable observable, Object data) {
		if(data != null && StringUtils.isNumeric(data.toString())){
			int id = Integer.parseInt(data.toString());
			switch(id){
			case ConstantValues.HOME_VIEW:
			case ConstantValues.HOMEUNLOGIN_VIEW:
				showUnLoginTitle();
				break;
			case ConstantValues.USERLOGIN_VIEW:
				changeTitle("注册");
				showCommonTitle();
				break;
			case ConstantValues.MORENEWS_VIEW:
				changeTitle("新闻咨询");
				showCommonTitle();
				break;
			case ConstantValues.NEWSDETAIL_VIEW:
				changeTitle("新闻详情");
				showCommonTitle();
				iv_help.setVisibility(View.INVISIBLE);
			case ConstantValues.SSQ_VIEW:
				changeTitle("双色球选号");
				showCommonTitle();
				break;
			}
		}
	}
}
