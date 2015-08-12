package com.dsw.view.manager;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import org.apache.commons.lang3.StringUtils;

import com.dsw.activity.view.HomeUnlogin;
import com.dsw.activity.view.HomeView;
import com.dsw.constant.ConstantValues;
import com.dsw.lotteryticket.R;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BottomManager implements Observer {
	private static BottomManager instance = new BottomManager();
	private BottomManager(){
		
	}
	public static BottomManager getInstance() {
		return instance;
	}

	private LinearLayout linear_bottom_menu;
	private LinearLayout linear_botton_common;
	private LinearLayout linear_botton_lottery;
	private ImageButton ib_home;
	private ImageButton ib_hall;
	private ImageButton ib_money;
	private ImageButton ib_mylottery;
	
	private ImageButton ib_clear;
	private ImageButton ib_ok;
	private TextView tv_notice;
	
	clearselect clearLitener;
	public void init(Activity activity){
		linear_bottom_menu = (LinearLayout) activity.findViewById(R.id.linear_bottom_menu);
		linear_botton_common = (LinearLayout) activity.findViewById(R.id.linear_bottom_common);
		linear_botton_lottery = (LinearLayout) activity.findViewById(R.id.linear_bottom_buylottery);
		
		ib_home = (ImageButton) activity.findViewById(R.id.ib_home);
		ib_hall = (ImageButton) activity.findViewById(R.id.ib_hall);
		ib_money = (ImageButton) activity.findViewById(R.id.ib_money);
		ib_mylottery = (ImageButton) activity.findViewById(R.id.ib_mylottery);
		ib_clear = (ImageButton) activity.findViewById(R.id.ib_clear);
		ib_ok = (ImageButton) activity.findViewById(R.id.ib_ok);
		tv_notice = (TextView) activity.findViewById(R.id.tv_notice);
		setListener();
	}
	private void setListener() {
		ib_home.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ContentManager.getInstance().changeUI(HomeView.class);
			}
		});
		ib_hall.setOnClickListener(new OnClickListener() {
					
			@Override
			public void onClick(View v) {
				ContentManager.getInstance().changeUI(HomeUnlogin.class);
			}
		});
		ib_money.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			}
		});
		ib_mylottery.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			}
		});
		ib_ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			}
		});
		ib_clear.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(clearLitener != null){
					clearLitener.clearSelect();
				}
			}
		});
		
	}
	
	private void setGone(){
		linear_botton_common.setVisibility(View.GONE);
		linear_botton_lottery.setVisibility(View.GONE);
	}
	public void showCommonBottom(){
		setGone();
		linear_botton_common.setVisibility(View.VISIBLE);
	}
	
	public void showLottery(){
		setGone();
		linear_botton_lottery.setVisibility(View.VISIBLE);
	}
	
	@Override
	public void update(Observable observable, Object data) {
		if(data != null && StringUtils.isNumeric(data.toString())){
			int id = Integer.parseInt(data.toString());
			switch(id){
			case ConstantValues.HOME_VIEW:
			case ConstantValues.HOMEUNLOGIN_VIEW:
			case ConstantValues.USERLOGIN_VIEW:
				showCommonBottom();
				break;
			case ConstantValues.SSQ_VIEW:
				showLottery();
				break;
			}
		}
	}
	
	/*
	 * 设置提示语
	 */
	public void setNotice(String text){
		tv_notice.setText(text);
	}
	
	/*
	 * 删除的回调接口
	 */
	public interface clearselect{
		public void clearSelect();
	}
	
	public void setClearListener(clearselect clear){
		this.clearLitener = clear;
	}
}
