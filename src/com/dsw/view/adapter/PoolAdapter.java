package com.dsw.view.adapter;

import java.text.DecimalFormat;
import java.util.List;

import com.dsw.lotteryticket.R;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PoolAdapter extends BaseAdapter {
	private Context context;
	private int count;
	private List<Integer> selectNumber;
	private int resId;
	public PoolAdapter(Context context,int cnt,List<Integer> selectNumber,int resId){
		this.context = context;
		this.count = cnt;
		this.selectNumber = selectNumber;
		this.resId = resId;
	}
	@Override
	public int getCount() {
		return count;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView text = new TextView(context);
		DecimalFormat decimalFormat = new DecimalFormat("00");
		text.setText(decimalFormat.format(position + 1));
		text.setTextSize(17);
		text.setGravity(Gravity.CENTER);
		// 获取到用户已选号码的集合，判读集合中有，背景图片修改为红色
		if (selectNumber.contains(position + 1)) {
			text.setBackgroundResource(resId);

		} else {
			text.setBackgroundResource(R.drawable.id_defalut_ball);
		}

		return text;
	}

}
