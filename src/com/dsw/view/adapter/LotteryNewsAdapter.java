package com.dsw.view.adapter;

import java.util.List;

import com.dsw.entity.LotteryNews;
import com.dsw.lotteryticket.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LotteryNewsAdapter extends BaseAdapter {
	private List<LotteryNews> list;
	private Context context;
	private ViewHolder holder;
	public LotteryNewsAdapter(List<LotteryNews> list,Context context){
		this.context = context;
		this.list = list;
	}
	@Override
	public int getCount() {
		return list == null ? 0 : list.size();
	}

	@Override
	public Object getItem(int position) {
		return list == null ? null : list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null || convertView.getTag() == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.lottery_news, null);
			holder = new ViewHolder();
			holder.tv_title = (TextView) convertView.findViewById(R.id.news_title);
			holder.tv_content = (TextView) convertView.findViewById(R.id.news_content);
			holder.iv_logo = (ImageView) convertView.findViewById(R.id.iv_news);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tv_title.setText(list.get(position).getNewTitle());
		holder.tv_content.setText(list.get(position).getNewContent());
		if(list.get(position).getBitmap() != null){
			holder.iv_logo.setImageBitmap(list.get(position).getBitmap());
		}else{
			holder.iv_logo.setBackgroundResource(R.drawable.id_ssq);
		}
		
		int width = holder.iv_logo.getWidth();
		int height = holder.iv_logo.getMeasuredWidth();
		Log.v("tag", "tag:" + width + "-" + height);
		return convertView;
	}
	
	class ViewHolder{
		ImageView iv_logo;
		TextView tv_title;
		TextView tv_content;
	}

}
