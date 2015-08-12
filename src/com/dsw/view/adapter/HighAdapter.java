package com.dsw.view.adapter;

import java.util.ArrayList;
import java.util.List;

import com.dsw.lotteryticket.R;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HighAdapter extends BaseAdapter {
	private Context context;
	private List<HighLotteryKind> list;
	public HighAdapter(Context context){
		this.context = context;
		init();
	}
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View view, ViewGroup arg2) {
		view = LayoutInflater.from(context).inflate(R.layout.high_item, null);
		TextView tv_title = (TextView) view.findViewById(R.id.tv_name);
		TextView tv_date = (TextView) view.findViewById(R.id.tv_date);
		LinearLayout linear = (LinearLayout) view.findViewById(R.id.linear_number);
		tv_title.setText(list.get(position).name);
		tv_date.setText(list.get(position).date);
		String number = list.get(position).number;
		int start = 0,end = 0;
		TextView tv_ball;
		while(end < number.length()-1){
			end =start+2;
			tv_ball = new TextView(context);
			tv_ball.setTextColor(Color.WHITE);
			tv_ball.setBackgroundResource(R.drawable.id_redball);
			tv_ball.setGravity(Gravity.CENTER);
			tv_ball.setText(number.subSequence(start, end));
			start = end;
			linear.addView(tv_ball);
		}
		return view;
	}
	
	public void init(){
		list = new ArrayList<HighLotteryKind>();
		HighLotteryKind kind = new HighLotteryKind();
		kind.name = "江西11选5";
		kind.date = "第2015071676期";
		kind.number = "0804090701";
		list.add(kind);
		kind = new HighLotteryKind();
		kind.name="重庆时时彩";
		kind.date="第20150716094期";
		kind.number="0502080902";
		list.add(kind);
		kind = new HighLotteryKind();
		kind.name="山东11选5";
		kind.date="第15071677期";
		kind.number="1007110405";
		list.add(kind);
		kind = new HighLotteryKind();
		kind.name="广东快乐十分";
		kind.date="第2015071584期";
		kind.number="0320151407120603";
		list.add(kind);
		kind = new HighLotteryKind();
		kind.name="江苏快3";
		kind.date="第150716082期";
		kind.number="010102";
		list.add(kind);
		kind = new HighLotteryKind();
		kind.name="江西时时彩";
		kind.date="第20150716084期";
		kind.number="0907030503";
		list.add(kind);
		kind = new HighLotteryKind();
		kind.name="山东群英会";
		kind.date="第20150716078期";
		kind.number="0308120210";
		list.add(kind);
		
		kind = new HighLotteryKind();
		kind.name="广东11选5";
		kind.date="第15071684期";
		kind.number="0201040908";
		list.add(kind);
		
		kind = new HighLotteryKind();
		kind.name="北京快乐8";
		kind.date="第706249期";
		kind.number="067203309802753156934";
		list.add(kind);
		
		kind = new HighLotteryKind();
		kind.name="北京pk拾";
		kind.date="第500820期";
		kind.number="08050910040702060103";
		list.add(kind);
	}

}

class HighLotteryKind{
	String name;
	String date;
	String number;
}
