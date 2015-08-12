package com.dsw.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;


import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.text.TextUtils;

import com.dsw.constant.ConstantValues;
import com.dsw.entity.LotteryNews;
import com.dsw.entity.LotteryNumber;

public class JoupAndHtmlClient {
	
	@SuppressLint("SetJavaScriptEnabled")
	public static List<LotteryNumber> getLotteryNumber(){
		List<LotteryNumber> listLottery = new ArrayList<LotteryNumber>();
		try {
				//解析双色球最新中将号码
				LotteryNumber number = new LotteryNumber();
				number.setType(ConstantValues.DOUBLECOLORBALL);
				number.setDate("2015076");
				number.setRedNumber("010217222627");
				number.setBlueNumber("04");
				listLottery.add(number);
				
				//福彩3D中奖号码
				number = new LotteryNumber();
				number.setType(ConstantValues.THREEBALL);
				number.setDate("2015170");
				number.setRedNumber("");
				number.setBlueNumber("313");
				listLottery.add(number);
				//七乐彩开奖号码

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return listLottery;
	}
	
	private static String urlBase = "http://www.zhcw.com/xinwen/caizhongxinwen";
	/*
	 * 获取新闻
	 */
	public static List<LotteryNews> getNews(boolean isNewsList){
		LotteryNews news;
		List<LotteryNews> listNews = new ArrayList<LotteryNews>();
		try {
			Document doc = Jsoup.connect(urlBase).get();
			List<Element> list = doc.getElementsByAttributeValue("class", "Nlink");
			int number = isNewsList ? list.size() : 5;
			for(int i=0;i< number;i++){
				news = new LotteryNews();
				Element link = list.get(i);
				String linkHref = ConstantValues.url + link.child(0).attr("href");
                String linkText = link.text().trim();
                System.out.println(linkHref);
                System.out.println(linkText);
                news.setNewUrl(linkHref);		//存储url地址
                news.setNewTitle(linkText.substring(1));				//存储标题
                
                //请求详细内容，获取缩略图片
                getContentAndBitmap(news,linkHref);
                listNews.add(news);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return listNews;
	}
	/*
	 * 获取新闻的详细内容和图片
	 */
	private static void getContentAndBitmap(LotteryNews news, String linkHref) {
		if(TextUtils.isEmpty(linkHref))return;
		try {
			Document doc = Jsoup.connect(linkHref).get();
			List<Element> list = doc.getElementsByAttributeValue("name", "description");
			if(list != null && list.size() >0){
				Element el = list.get(0);
				System.out.println(el.text());
				news.setNewContent(el.attr("content"));
			}

			Element listContent = doc.getElementById("news_content");
			List<Node> nodes = listContent.childNodes();
			for(int i=0;i<nodes.size();i++){
				if(nodes.get(i).childNodeSize() > 0 && !TextUtils.isEmpty(nodes.get(i).childNode(0).attr("src"))){
					String imgUrl = ConstantValues.url + nodes.get(i).childNode(0).attr("src");
					Bitmap bitmap = BitmapUtils.getBitmap(imgUrl);
					news.setBitmap(bitmap);
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
