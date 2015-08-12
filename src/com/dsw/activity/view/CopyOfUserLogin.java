package com.dsw.activity.view;

import com.dsw.constant.ConstantValues;
import com.dsw.lotteryticket.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class CopyOfUserLogin extends BaseUI{
	private EditText et_name;
	private EditText et_pwd;
	private Button btn_login;
	private Button btn_regiest;
	public CopyOfUserLogin(Context context) {
		super(context);
	}

	@Override
	public void init() {
		
	}

	@Override
	public void setListener() {
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
