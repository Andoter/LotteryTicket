package com.dsw.view.manager;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import com.dsw.activity.view.BaseUI;
import com.dsw.utils.AnimationUtils;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;

public class ContentManager extends Observable {
	private static ContentManager instance = new ContentManager();
	private ContentManager(){
		
	}
	
	public static ContentManager getInstance() {
		return instance;
	}
	
	private RelativeLayout relative_content;
	
	private Map<String,BaseUI> mapViewsCache = new HashMap<String, BaseUI>();
	private LinkedList<String> historyView = new LinkedList<String>();
	private BaseUI currentUI;

	public void setRelative_content(RelativeLayout relative_content) {
		this.relative_content = relative_content;
	}
	
	
	// 每增加一个界面150K——16M
	// 内存不足
	// 处理的方案：
	// 第一种：控制VIEWCACHE集合的size
	// 第二种：Fragment代替，replace方法，不会缓存界面
	// 第三种：降低BaseUI的应用级别
	// 强引用：当前（GC宁可抛出OOM，不会回收BaseUI）
	// 软引用：在OOM之前被GC回收掉
	// 弱引用：一旦被GC发现了就回收
	// 虚引用：一旦创建了就被回收了

	// 都存在优缺点
	// 第一种：代码实现简单，适应性不强
	// 第二种：上一个Fragment被回收了，当内存充足的时候，运行速度损失过多
	// 第三种：优点，缺点：虽然引用级别降低，但是必须等待GC去回收，必须要提供给GC一个回收的时间，所以一旦申请内存速度过快，不适用
	// 瀑布流——Lrucache
	
	public BaseUI getCurrentUI(){
		return currentUI;
	}
	public Context getContext(){
		return relative_content.getContext();
	}
	public void changeUI(Class<? extends BaseUI> clazz){
		if(currentUI != null && currentUI.getClass() == clazz){
			return;
		}
		
		BaseUI targetUI = null;
		String key = clazz.getSimpleName();
		if(mapViewsCache.containsKey(key) && !"NewsDetailView".equals(key)){//不要重复利用新闻详情，要进行刷新，防止点击不同item不刷县
			targetUI = mapViewsCache.get(key);
		}else{
			try {
				Constructor<? extends BaseUI> construct = clazz.getConstructor(Context.class);
				targetUI = construct.newInstance(getContext());
				mapViewsCache.put(key, targetUI);
			} catch (Exception e) {
				e.printStackTrace();
				return;
			} 
		}
		
		relative_content.removeAllViews();
		View child = targetUI.getContentView();
		relative_content.addView(child);
		AnimationUtils.fadeInAnim(child, 2000);
		currentUI = targetUI;
		if(!historyView.contains(key))
		historyView.addFirst(key);
		//ͨ改变菜单
		changeTitleAndBottom();
	}
	
	private void changeTitleAndBottom() {
		// 1、界面一对应未登陆标题和通用导航
		// 2、界面二对应通用标题和玩法导航

		// 当前正在展示的如果是第一个界面
		// 方案一：存在问题，比对的依据：名称 或者 字节码
		// 在界面处理初期，将所有的界面名称确定
		// 如果是字节码，将所有的界面都的创建完成
		// if(currentUI.getClass()==FirstUI.class){
		// TitleManager.getInstance().showUnLoginTitle();
		// BottomManager.getInstrance().showCommonBottom();
		// }
		// if(currentUI.getClass().getSimpleName().equals("SecondUI")){
		// TitleManager.getInstance().showCommonTitle();
		// BottomManager.getInstrance().showGameBottom();
		// }

		// 方案二：更换比对依据

		/*
		 * switch (currentUI.getID()) { case ConstantValue.VIEW_FIRST: TitleManager.getInstance().showUnLoginTitle(); BottomManager.getInstrance().showCommonBottom(); //
		 * LeftManager\RightManager break; case ConstantValue.VIEW_SECOND: TitleManager.getInstance().showCommonTitle(); BottomManager.getInstrance().showGameBottom(); break; case
		 * 3: TitleManager.getInstance().showCommonTitle(); BottomManager.getInstrance().showGameBottom(); break; }
		 */

		// 降低三个容器的耦合度
		// 当中间容器变动的时候，中间容器“通知”其他的容器，你们该变动了，唯一的标示传递，其他容器依据唯一标示进行容器内容的切换
		// 通知：
		// 广播：多个应用
		// 为中间容器的变动增加了监听——观察者设计模式

		// ①将中间容器变成被观察的对象
		// ②标题和底部导航变成观察者
		// ③建立观察者和被观察者之间的关系（标题和底部导航添加到观察者的容器里面）
		// ④一旦中间容器变动，修改boolean，然后通知所有的观察者.updata()
		setChanged();
		notifyObservers(currentUI.getID());
	}

	/*
	 * 返回键返回界面
	 */
	public boolean goBack(){
		if(historyView.size() >0){
			if(historyView.size() == 1){
				return false;
			}
			historyView.removeFirst();
			if(historyView.size() > 0){
				String key = historyView.getFirst();
				BaseUI targetUI = mapViewsCache.get(key);
				
				relative_content.removeAllViews();
				relative_content.addView(targetUI.getContentView());
				currentUI = targetUI;
				changeTitleAndBottom();
			}
			return true;
		}
		return false;
	}
}
