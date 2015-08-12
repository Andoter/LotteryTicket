package com.dsw.activity.view;

import com.dsw.constant.ConstantValues;
import com.dsw.lotteryticket.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class UserLogin extends BaseUI{
	private EditText et_name;
	private EditText et_pwd;
	private Button btn_login;
	private Button btn_regiest;
	public UserLogin(Context context) {
		super(context);
	}

	@Override
	public void init() {
		contentView = LayoutInflater.from(context).inflate(R.layout.login_view, null);
		et_name = (EditText) contentView.findViewById(R.id.login_name);
		et_pwd = (EditText) contentView.findViewById(R.id.login_pwd);
		btn_login = (Button) contentView.findViewById(R.id.btn_user_login);
		btn_regiest = (Button) contentView.findViewById(R.id.btn_user_login_regist);
	}

	@Override
	public void setListener() {
		btn_login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			}
		});
		btn_regiest.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			}
		});
		
	}

	@Override
	public int getID() {
		return ConstantValues.USERLOGIN_VIEW;
	}

	@Override
	public void onPause() {
		
	}

	@Override
	public void onResume() {
		
	}
	
}
