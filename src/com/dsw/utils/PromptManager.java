package com.dsw.utils;

import com.dsw.lotteryticket.R;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.widget.Toast;

public class PromptManager {
	private static ProgressDialog pbDialog;
	
	public static void showProgressDialog(Context context){
		pbDialog = new ProgressDialog(context);
		pbDialog.setIcon(R.drawable.icon1);
		pbDialog.setCancelable(false);
		pbDialog.setTitle(R.string.app_name);
		pbDialog.setMessage("正在加载中，请稍等......");
		pbDialog.show();
	}
	
	public static void closeProgressDialog(){
		if(pbDialog != null && pbDialog.isShowing()){
			pbDialog.dismiss();
		}
	}
	
	public static void showNetWork(final Context context){
		AlertDialog.Builder builder = new Builder(context);
		builder.setIcon(R.drawable.icon1)
				.setTitle(R.string.app_name)
				.setMessage("网络设置错误")
				.setPositiveButton("打开网络", new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						//启动打开网络意图
						Intent intent = new Intent();
						intent.setClassName("com.android.settings", "com.android.settings.WirelessSettings");
						context.startActivity(intent);
					}
				}).setNegativeButton("知道了", new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
	}
	
	public static void showErrorDialog(Context context, String msg) {
		new AlertDialog.Builder(context)//
				.setIcon(R.drawable.icon1)//
				.setTitle(R.string.app_name)//
				.setMessage(msg)//
				.setNegativeButton(context.getString(R.string.is_positive), null)//
				.show();
	}
	public static void showToast(Context context, String msg) {
		Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
	}
}
