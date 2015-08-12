package com.dsw.activity.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import com.dsw.constant.ConstantValues;
import com.dsw.entity.LotteryKind;
import com.dsw.lotteryticket.R;
import com.dsw.ui.HighLotterTicketView;
import com.dsw.view.adapter.HighAdapter;
import com.dsw.view.adapter.HomeAdapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class HomeUnlogin extends BaseUI{
	private ListView listView;
	private HomeAdapter adapter;
	private ViewPager viewPager;
	private PagerAdapter pagerAdapter;
	private ImageView iv_underLine;
	private List<View> pagers;
	private int currentPostion;	//当前位置
	private int offset;	//偏移量
	private int bmpWidth;
	private TextView tv_luckyLoterry,tv_gameLoterry,tv_highLoterry;
	public HomeUnlogin(Context context){
		super(context);
	}
	@Override
	public void init() {
		contentView = LayoutInflater.from(context).inflate(R.layout.unlogin_view, null);
		listView = new ListView(context);
		List<LotteryKind> list = initLotteryType();
		adapter = new HomeAdapter(context, list);
		listView.setAdapter(adapter);
		//listView.setFadingEdgeLength(0);
		initPagerViews();
		viewPager =	(ViewPager) contentView.findViewById(R.id.ii_viewpager);
		pagerAdapter = new MyPagerAdapter();
		viewPager.setAdapter(pagerAdapter);
		iv_underLine = (ImageView) contentView.findViewById(R.id.iv_category_selector);
		tv_luckyLoterry = (TextView) contentView.findViewById(R.id.tv_luckyLoterry);
		tv_gameLoterry = (TextView) contentView.findViewById(R.id.tv_gameLoterry);
		tv_highLoterry = (TextView) contentView.findViewById(R.id.tv_highLoterry);
		initViewStrip();
	}
	
	/*
	 * 初始化viewpager
	 */
	private void initPagerViews(){
		pagers = new ArrayList<View>();
		pagers.add(listView);
		
		TextView item = new TextView(context);
		item.setText("体彩");
		pagers.add(item);
		
		View view = new HighLotterTicketView(context, null);
		((ListView)view.findViewById(R.id.listview_lottery)).setAdapter(new HighAdapter(context));
		pagers.add(view);
	}
	
	/*
	 * 初始化下划线
	 */
	private void initViewStrip(){
        bmpWidth = BitmapFactory.decodeResource(context.getResources(), R.drawable.id_category_selector).getWidth();  
       
        offset = (ConstantValues.SCREEN_WIDTH / 3 - bmpWidth) / 2;  
        Matrix matrix = new Matrix();  
        matrix.postTranslate(offset, 0);  
        iv_underLine.setImageMatrix(matrix);
	}
	
	@Override
	public void setListener() {
		tv_luckyLoterry.setOnClickListener(new TextViewClick(0));
		tv_gameLoterry.setOnClickListener(new TextViewClick(1));
		tv_highLoterry.setOnClickListener(new TextViewClick(2));
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
			}
		});
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				int one = offset * 2 + bmpWidth;// 1——》2
		        //平移动画
		        Animation anim = null; 
		        anim = new TranslateAnimation(one*currentPostion, one*arg0, 0, 0);  
		        anim.setFillAfter(true);// True
		        anim.setDuration(300);  
		        iv_underLine.startAnimation(anim);  
		        currentPostion= arg0;  
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}

	@Override
	public int getID() {
		return ConstantValues.HOMEUNLOGIN_VIEW;
	}
	
	/*
	 * PagerViewAdapter
	 */
	class MyPagerAdapter extends PagerAdapter{

		@Override
		public int getCount() {
			return pagers != null ? pagers.size() : 0;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			View view = pagers.get(position);
			container.removeView(view);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			View view = pagers.get(position);
			container.addView(view);
			return view;
		}
	}
	/*
	 * textview的点击改变下划线位置
	 */
	class TextViewClick implements OnClickListener{
		private int index = 0;
		public TextViewClick(int index){
			this.index = index;
		}
		@Override
		public void onClick(View view) {
			if(viewPager != null){
				viewPager.setCurrentItem(index);
			}
		}
	}
	
	private List<LotteryKind> initLotteryType(){
		File file = new File(ConstantValues.xmlPath);
		if(!file.exists())return null;
		List<LotteryKind> listKind = null;
		try {
			FileInputStream reader = new FileInputStream(file);
			//方式1：
			//XmlPullParser xpp = Xml.newPullParser();
			//方式2：
			XmlPullParserFactory facotry = XmlPullParserFactory.newInstance();
			XmlPullParser parser = facotry.newPullParser();
			parser.setInput(reader, ConstantValues.ENCODING);
			LotteryKind kind = null;
			int eventType = parser.getEventType();
			String tagNmae="";
			while(XmlPullParser.END_DOCUMENT != eventType){
				switch(eventType){
				case XmlPullParser.START_DOCUMENT:
					listKind= new ArrayList<LotteryKind>();
					kind = new LotteryKind();
					break;
				case XmlPullParser.START_TAG:
					if("DoubleBall".equals(parser.getName())){
						kind.setType(ConstantValues.DOUBLECOLORBALL);
					}else if("ThreeBall".equals(parser.getName())){
						kind.setType(ConstantValues.THREEBALL);
					}else if("SenvenBall".equals(parser.getName())){
						kind.setType(ConstantValues.SENVENBALL);
					}else if("East6".equals(parser.getName())){
						kind.setType(ConstantValues.EAST6);
					}else if("PriceNotice".equals(parser.getName())){
						tagNmae = "PriceNotice";
					}else if("OpenTime".equals(parser.getName())){
						tagNmae = "OpenTime";
					}else if("Number".equals(parser.getName())){
						tagNmae = "Number";
					}else if("DateTime".equals(parser.getName())){
						tagNmae = "DateTime";
					}else if("TotalGold".equals(parser.getName())){
						tagNmae = "TotalGold";
					}
					break;
				case XmlPullParser.TEXT:
					if("PriceNotice".equals(tagNmae)){
						kind.setPriceNotice(parser.getText());
					}else if("OpenTime".equals(tagNmae)){
						kind.setOpenTime(parser.getText());
					}else if("Number".equals(tagNmae)){
						kind.setNumber(Integer.valueOf(parser.getText()));
					}else if("DateTime".equals(tagNmae)){
						kind.setDateTime(parser.getText());
					}else if("TotalGold".equals(tagNmae)){
						kind.setTotalGold(Integer.valueOf(parser.getText()));
					}
					break;
				case XmlPullParser.END_TAG:
					if("DoubleBall".equals(parser.getName())){
						listKind.add(kind);
						kind = new LotteryKind();
					}else if("ThreeBall".equals(parser.getName())){
						listKind.add(kind);
						kind = new LotteryKind();
					}else if("SenvenBall".equals(parser.getName())){
						listKind.add(kind);
						kind = new LotteryKind();
					}else if("East6".equals(parser.getName())){
						listKind.add(kind);
						kind = new LotteryKind();
					}
					tagNmae="";	//进行清空
				}
				eventType = parser.next(); 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listKind;
	}
	@Override
	public void onPause() {
		
	}
	@Override
	public void onResume() {
		
	}
}
