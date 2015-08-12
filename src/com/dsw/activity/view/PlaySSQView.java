package com.dsw.activity.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.dsw.constant.ConstantValues;
import com.dsw.lotteryticket.R;
import com.dsw.ui.MyGridView;
import com.dsw.ui.MyGridView.OnActionUpListener;
import com.dsw.view.adapter.PoolAdapter;
import com.dsw.view.manager.BottomManager;
import com.dsw.view.manager.BottomManager.clearselect;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;

public class PlaySSQView extends BaseUI implements OnClickListener{
	private Button randomRed;
	private Button randomBlue;
	
	private MyGridView myGridView;
	private GridView gridView;
	
	private PoolAdapter redAdapter;
	private PoolAdapter blueAdapter;
	
	private List<Integer> selectRed;
	private List<Integer> selectBlue;
	public PlaySSQView(Context context) {
		super(context);
	}

	@Override
	public void init() {
		contentView = LayoutInflater.from(context).inflate(R.layout.ssq_play, null);
		myGridView = (MyGridView) contentView.findViewById(R.id.ii_ssq_red_number_container);
		gridView = (GridView) contentView.findViewById(R.id.ii_ssq_blue_number_container);
		
		randomRed = (Button) contentView.findViewById(R.id.ii_ssq_random_red);
		randomBlue = (Button) contentView.findViewById(R.id.ii_ssq_random_blue);

		selectRed = new ArrayList<Integer>();
		selectBlue = new ArrayList<Integer>();
		redAdapter = new PoolAdapter(context, 33, selectRed, R.drawable.id_redball);
		blueAdapter = new PoolAdapter(context, 16, selectBlue, R.drawable.id_blueball);
		myGridView.setAdapter(redAdapter);
		gridView.setAdapter(blueAdapter);
		
	}

	@Override
	public void setListener() {
		randomRed.setOnClickListener(this);
		randomBlue.setOnClickListener(this);
		BottomManager.getInstance().setClearListener(new clearselect() {
			
			@Override
			public void clearSelect() {
				selectBlue.clear();
				selectRed.clear();
				changeNotice();
				redAdapter.notifyDataSetChanged();
				blueAdapter.notifyDataSetChanged();
			}
		});
		myGridView.setOnUpLitener(new OnActionUpListener() {
			@Override
			public void OnActionUp(View view, int position) {
				if(selectRed.contains(position + 1)){
					// 被选中
					// 还原背景图片
					view.setBackgroundResource(R.drawable.id_defalut_ball);
					selectRed.remove((Object) (position + 1));
				}else if(selectRed.size() < 6){
					// 如果没有被选中
					// 背景图片切换到红色
					view.setBackgroundResource(R.drawable.id_redball);
					selectRed.add(position + 1);
				}
				changeNotice();
			}
		});
		
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// 判断当前点击的item是否被选中了
				if (!selectBlue.contains(position + 1) && selectBlue.size() == 0) {
					// 如果没有被选中
					// 背景图片切换到红色
					view.setBackgroundResource(R.drawable.id_blueball);
					// 摇晃的动画
					view.startAnimation(AnimationUtils.loadAnimation(context, R.anim.ia_ball_shake));
					selectBlue.add(position + 1);
				} else if(selectBlue.contains(position + 1)){
					// 被选中
					// 还原背景图片
					view.setBackgroundResource(R.drawable.id_defalut_ball);
					selectBlue.remove((Object) (position + 1));
				}
				changeNotice();
			}
		});
	}
	
	

	@Override
	public void onPause() {
		
	}

	@Override
	public void onResume() {
		changeNotice();
	}

	@Override
	public int getID() {
		return ConstantValues.SSQ_VIEW;
	}

	@Override
	public void onClick(View view) {
		Random random = new Random();
		switch(view.getId()){
		case R.id.ii_ssq_random_red:
			selectRed.clear();
			while(selectRed.size()<6){
				int num = random.nextInt(33) + 1;
				if(selectRed.contains(num))continue;
				selectRed.add(num);
			}
			redAdapter.notifyDataSetChanged();
			changeNotice();
			break;
		case R.id.ii_ssq_random_blue:
			selectBlue.clear();
			int num = random.nextInt(16) + 1;
			selectBlue.add(num);
			blueAdapter.notifyDataSetChanged();
			changeNotice();
			break;
		}
	}
	
	/**
	 * 改变底部导航的提示信息
	 */
	private void changeNotice() {
		String notice = "";
		// 以一注为分割
		if (selectRed.size() < 6) {
			notice = "您还需要选择" + (6 - selectRed.size()) + "个红球";
		} else if (selectBlue.size() == 0) {
			notice = "您还需要选择" + 1 + "个蓝球";
		} else {
			notice = "共 " + 1 + " 注 " + 2 + " 元";
		}

		BottomManager.getInstance().setNotice(notice);
	}

	
}
