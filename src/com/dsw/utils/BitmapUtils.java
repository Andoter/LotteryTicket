package com.dsw.utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BitmapUtils {
	
	/**
	 * 根据url地址获取图片
	 * @param url	网络地址
	 * @return
	 */
	public static Bitmap getBitmap(String url){
		Bitmap bitmap =	null;
		try {
			HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
			conn.setConnectTimeout(2000);
			conn.setReadTimeout(2000);
			conn.setRequestMethod("GET");
			
			int code = conn.getResponseCode();
			if(code == 200){
				bitmap = BitmapFactory.decodeStream(conn.getInputStream());
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return bitmap;
	}
}
