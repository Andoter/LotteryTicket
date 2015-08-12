package com.dsw.view.adapter;

import java.util.List;

import com.dsw.activity.view.PlaySSQView;
import com.dsw.constant.ConstantValues;
import com.dsw.entity.LotteryKind;
import com.dsw.lotteryticket.R;
import com.dsw.view.manager.ContentManager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class HomeAdapter extends BaseAdapter {
	private Context context;
	private List<LotteryKind> list;
	private ViewHolder holder;
	public HomeAdapter(Context context,List<LotteryKind> list){
		this.context = context;
		this.list = list;
	}
	@Override
	public int getCount() {
		return list == null ? 0 : list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return list == null ? null : list.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null || convertView.getTag() == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.home_item, null);
			holder = new ViewHolder();
			holder.tv_goldTotal = (TextView) convertView.findViewById(R.id.totle_gold);
			holder.tv_number = (TextView) convertView.findViewById(R.id.tv_times);
			holder.tv_openTime = (TextView) convertView.findViewById(R.id.tv_date);
			holder.tv_priceNotice = (TextView) convertView.findViewById(R.id.tv_price);
			holder.iv_lotteryType = (ImageView) convertView.findViewById(R.id.lottery_type);
			holder.btn_bat = (Button) convertView.findViewById(R.id.btn_bat);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tv_priceNotice.setText(list.get(position).getPriceNotice());
		holder.tv_openTime.setText(list.get(position).getOpenTime());
		holder.tv_number.setText("第" + list.get(position).getNumber() +"期      " + list.get(position).getDateTime());
		holder.tv_goldTotal.setText("奖金总额：" + list.get(position).getTotalGold() + "万");
		final int location = position;
		holder.btn_bat.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				switch(list.get(location).getType()){
				case ConstantValues.DOUBLECOLORBALL:
					ContentManager.getInstance().changeUI(PlaySSQView.class);
					break;
				case ConstantValues.THREEBALL:
					Toast.makeText(context, "3D", Toast.LENGTH_SHORT).show();
					break;
				case ConstantValues.SENVENBALL:
					Toast.makeText(context, "七乐彩", Toast.LENGTH_SHORT).show();
					break;
				case ConstantValues.EAST6:
					Toast.makeText(context, "东方6+1", Toast.LENGTH_SHORT).show();
					break;
				}
			}
		});
		switch(list.get(position).getType()){
		case ConstantValues.DOUBLECOLORBALL:
			holder.iv_lotteryType.setImageResource(R.drawable.id_ssq);
			break;
		case ConstantValues.THREEBALL:
			holder.iv_lotteryType.setImageResource(R.drawable.id_3d);
			break;
		case ConstantValues.SENVENBALL:
			holder.iv_lotteryType.setImageResource(R.drawable.id_qlc);
			break;
		case ConstantValues.EAST6:
			holder.iv_lotteryType.setImageResource(R.drawable.east6);
			break;
		}
		
		return convertView;
	}
	
	class ViewHolder{
		ImageView iv_lotteryType;
		TextView tv_priceNotice;
		TextView tv_openTime;
		TextView tv_number;
		TextView tv_goldTotal;
		Button btn_bat;
	}

}
