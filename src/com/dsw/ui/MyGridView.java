package com.dsw.ui;

import com.dsw.lotteryticket.R;
import com.dsw.utils.DensityUtil;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.TextView;

public class MyGridView extends GridView {
	/**
	 * 说明，自定义的gridview实现效果，点击到对应的text号码，展示大头像
	 */
	private PopupWindow popWindow;
	private TextView ball;
	private OnActionUpListener actionUpListener;
	
	/**
	 * 抬起事件
	 * @author dsw
	 *
	 */
	public interface OnActionUpListener{
		public void OnActionUp(View view, int position);
	}
	
	
	public MyGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		View view = View.inflate(context, R.layout.gridview_tip, null);
		ball = (TextView) view.findViewById(R.id.ii_pretextView);
		popWindow = new PopupWindow(context);
		popWindow.setContentView(view);
		popWindow.setBackgroundDrawable(null);
		popWindow.setAnimationStyle(0);	//可以设置为自定义，还可以设置为默认（-1），0代表没有动画
		popWindow.setWidth(DensityUtil.dip2px(context, 55));
		popWindow.setHeight(DensityUtil.dip2px(context, 55));
	}
	
	
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		int x = (int) ev.getX();
		int y = (int) ev.getY();
		int position = pointToPosition(x, y);
		if(position == INVALID_POSITION){
			return false;
		}
		TextView child = (TextView) getChildAt(position);
		switch(ev.getAction()){
		case MotionEvent.ACTION_DOWN:
			this.getParent().getParent().requestDisallowInterceptTouchEvent(true);
			showPop(child);
			break;
		case MotionEvent.ACTION_MOVE:
			updatePop(child);
			break;
		case MotionEvent.ACTION_UP:
			this.getParent().getParent().requestDisallowInterceptTouchEvent(false);
			hiddenPop();
			// 增加一个监听
			if (actionUpListener != null)
				actionUpListener.OnActionUp(child, position);
			break;
		default:
			hiddenPop();
			break;
			
		}
		return super.onTouchEvent(ev);
	}

	private void showPop(TextView child) {
		int yOffset = -(popWindow.getHeight() + child.getHeight());
		int xOffset = -(popWindow.getWidth() - child.getWidth()) / 2;

		ball.setText(child.getText());
		

		// Display the content view in a popup window anchored to the bottom-left corner of the anchor view.
		// pop.showAsDropDown(child);
		popWindow.showAsDropDown(child, xOffset, yOffset);
	}

	private void updatePop(TextView child) {
		ball.setText(child.getText());
		int yOffset = -(popWindow.getHeight() + child.getHeight());
		int xOffset = -(popWindow.getWidth() - child.getWidth()) / 2;
		// width the new width, can be -1 to ignore
		// height the new height, can be -1 to ignore
		popWindow.update(child, xOffset, yOffset, -1, -1);
	}
	private void hiddenPop() {
		if (popWindow.isShowing())
			popWindow.dismiss();
	}
	/**
	 * 设置监听事件
	 * @param listener
	 */
	public void setOnUpLitener(OnActionUpListener listener){
		this.actionUpListener = listener;
	}
}
