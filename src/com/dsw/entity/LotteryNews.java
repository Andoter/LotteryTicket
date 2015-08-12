package com.dsw.entity;

import java.io.Serializable;

import android.graphics.Bitmap;

public class LotteryNews implements Serializable {
	private static final long serialVersionUID = 1L;
	private String newTitle;
	private String newUrl;
	private String newContent;
	private String newDate;
	private Bitmap bitmap;
	
	public Bitmap getBitmap() {
		return bitmap;
	}
	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	public String getNewTitle() {
		return newTitle;
	}
	public void setNewTitle(String newTitle) {
		this.newTitle = newTitle;
	}
	public String getNewUrl() {
		return newUrl;
	}
	public void setNewUrl(String newUrl) {
		this.newUrl = newUrl;
	}
	public String getNewContent() {
		return newContent;
	}
	public void setNewContent(String newContent) {
		this.newContent = newContent;
	}
	public String getNewDate() {
		return newDate;
	}
	public void setNewDate(String newDate) {
		this.newDate = newDate;
	}
}
