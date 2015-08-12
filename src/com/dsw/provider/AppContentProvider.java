package com.dsw.provider;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlSerializer;

import com.dsw.constant.ConstantValues;
import com.dsw.entity.LotteryKind;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.util.Xml;

public class AppContentProvider extends ContentProvider {

	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {
		return 0;
	}

	@Override
	public String getType(Uri uri) {
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		return null;
	}

	@Override
	public boolean onCreate() {
		createLotteryType();
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		return null;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		return 0;
	}
	/*
	 * 初始化创建xml配置文件
	 */
	private void createLotteryType(){
		List<LotteryKind> listKind = new ArrayList<LotteryKind>();
		initLotteryType(listKind);
		ConstantValues.xmlPath = getContext().getFilesDir().getPath() + "/xmlLottery";
		Log.d("Tag", "xmlPath:" + ConstantValues.xmlPath);
		FileWriter fileWriter;
		StringWriter writer = new StringWriter();
		XmlSerializer xmlSerializ = Xml.newSerializer();
		//方式2
		//XmlPullParserFactory factory = XmlPullParserFactory.newInstance();  
        //XmlSerializer xmlSerializer = factory.newSerializer();  
		try {
			fileWriter = new FileWriter(new File(getContext().getFilesDir().getPath() + "/xmlLottery"));
			xmlSerializ.setOutput(writer);
			xmlSerializ.startDocument(ConstantValues.ENCODING, null);
			xmlSerializ.startTag(null, "LotteryType");
			for(int i=0;i<listKind.size();i++){
				LotteryKind kind = listKind.get(i);
				switch(kind.getType()){
				case ConstantValues.DOUBLECOLORBALL:
					xmlSerializ.startTag(null, "DoubleBall");
					xmlSerializer(xmlSerializ,kind);
					xmlSerializ.endTag(null, "DoubleBall");
					break;
				case ConstantValues.THREEBALL:
					xmlSerializ.startTag(null, "ThreeBall");
					xmlSerializer(xmlSerializ,kind);
					xmlSerializ.endTag(null, "ThreeBall");
					break;
				case ConstantValues.SENVENBALL:
					xmlSerializ.startTag(null, "SenvenBall");
					xmlSerializer(xmlSerializ,kind);
					xmlSerializ.endTag(null, "SenvenBall");
					break;
				case ConstantValues.EAST6:
					xmlSerializ.startTag(null, "East6");
					xmlSerializer(xmlSerializ,kind);
					xmlSerializ.endTag(null, "East6");
					break;
				}
			}
			xmlSerializ.endTag(null, "LotteryType");
			xmlSerializ.endDocument();
			fileWriter.write(writer.toString().toCharArray());
			fileWriter.close();		//注意要进行关闭，清除缓存
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void initLotteryType(List<LotteryKind> listKind){
		LotteryKind kind = new LotteryKind();
		kind.setDateTime("2天0时0分");
		kind.setNumber(98);
		kind.setOpenTime("每周一、三、五开奖");
		kind.setPriceNotice("2元中2000万");
		kind.setType(ConstantValues.DOUBLECOLORBALL);
		kind.setTotalGold(5000);
		listKind.add(kind);
		//添加10中3
		kind = new LotteryKind();
		kind.setDateTime("24时0分");
		kind.setNumber(150);
		kind.setOpenTime("每天8:30开奖");
		kind.setPriceNotice("2元中500万");
		kind.setType(ConstantValues.THREEBALL);
		kind.setTotalGold(5000);
		listKind.add(kind);
		//七彩乐
		kind = new LotteryKind();
		kind.setDateTime("2天0时0分");
		kind.setNumber(130);
		kind.setOpenTime("每周二、四、六");
		kind.setPriceNotice("2元中1000万");
		kind.setTotalGold(5000);
		kind.setType(ConstantValues.SENVENBALL);
		listKind.add(kind);
		
		//七彩乐
		kind = new LotteryKind();
		kind.setDateTime("2天0时0分");
		kind.setNumber(130);
		kind.setOpenTime("每周二、四、六");
		kind.setPriceNotice("2元中1000万");
		kind.setTotalGold(5000);
		kind.setType(ConstantValues.EAST6);
		listKind.add(kind);
	}
	
	/*
	 * 序列化xml节点
	 */
	private void xmlSerializer(XmlSerializer xmlSerializ,LotteryKind kind){
		try {
			xmlSerializ.startTag(null, "PriceNotice");
			xmlSerializ.text(kind.getPriceNotice());
			xmlSerializ.endTag(null, "PriceNotice");
			
			xmlSerializ.startTag(null, "OpenTime");
			xmlSerializ.text(kind.getOpenTime());
			xmlSerializ.endTag(null, "OpenTime");
			
			xmlSerializ.startTag(null, "Number");
			xmlSerializ.text(kind.getNumber()+"");
			xmlSerializ.endTag(null, "Number");
			
			xmlSerializ.startTag(null, "DateTime");
			xmlSerializ.text(kind.getDateTime());
			xmlSerializ.endTag(null, "DateTime");
			
			xmlSerializ.startTag(null, "TotalGold");
			xmlSerializ.text(kind.getTotalGold()+"");
			xmlSerializ.endTag(null, "TotalGold");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
