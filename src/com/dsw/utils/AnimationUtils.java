package com.dsw.utils;

import android.view.View;
import android.view.animation.AlphaAnimation;

public class AnimationUtils {
	/*
	 * 淡出动画
	 */
	public static void fadeOutAnim(View view,long duration){
		AlphaAnimation alphaAnim = new AlphaAnimation(1, 0);
		alphaAnim.setDuration(duration);
		view.startAnimation(alphaAnim);
	}
	
	/*
	 * 进入动画
	 */
	public static void fadeInAnim(View view ,long duration){
		AlphaAnimation alphaAnim = new AlphaAnimation(0, 1);
		alphaAnim.setDuration(duration);
		view.startAnimation(alphaAnim);
	}
}
