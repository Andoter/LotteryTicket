package com.dsw.activity.main;


import java.util.Timer;
import java.util.TimerTask;

import com.dsw.activity.view.HomeView;
import com.dsw.constant.ConstantValues;
import com.dsw.lotteryticket.R;
import com.dsw.view.manager.BottomManager;
import com.dsw.view.manager.ContentManager;
import com.dsw.view.manager.TitleManager;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends Activity {

	private Activity _this;
	private RelativeLayout relative_content;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.main_activity);
		_this = this;
		init();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			boolean result = ContentManager.getInstance().goBack();
			if(!result){
				exitBy2Click();
			}
			return false;	//拦截返回按钮事件
		}
		return super.onKeyDown(keyCode, event);
	}
	/**
	* 双击退出
	*/
	private static Boolean isExit = false;
	
	private void exitBy2Click() {
		Timer tExit = null;
		if(isExit == false) {
			isExit = true; // 不退出
			Toast.makeText(this, "在按一次退出应用", Toast.LENGTH_SHORT).show();
			tExit = new Timer();
			tExit.schedule(new TimerTask() {
			  @Override
			  public void run() {
				// 准备退出
				  isExit = false;
			  }
			}, 2000); // 2秒内没按下返回键，则取消操作
		} else {
			finish();
			System.exit(0);
		}
	}
	private void init() {
		//获取配置
		DisplayMetrics dm = new DisplayMetrics();  
        getWindowManager().getDefaultDisplay().getMetrics(dm);  
        ConstantValues.SCREEN_WIDTH = dm.widthPixels;  
	        
		TitleManager titleManager = TitleManager.getInstance();
		titleManager.initView(_this);
		titleManager.showUnLoginTitle();
		
		BottomManager bottomManager = BottomManager.getInstance();
		bottomManager.init(_this);
		bottomManager.showCommonBottom();
		
		relative_content = (RelativeLayout) _this.findViewById(R.id.relative_content);
		//addUnloginView();
		ContentManager contentManager = ContentManager.getInstance();
		//绑定观察者
		ContentManager.getInstance().addObserver(titleManager);
		ContentManager.getInstance().addObserver(bottomManager);
		contentManager.setRelative_content(relative_content);
		contentManager.changeUI(HomeView.class);
	}


}
