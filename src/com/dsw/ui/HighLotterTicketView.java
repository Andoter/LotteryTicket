package com.dsw.ui;

import com.dsw.lotteryticket.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

public class HighLotterTicketView extends LinearLayout{

	public HighLotterTicketView(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.high_lottery, this);
	}

}
