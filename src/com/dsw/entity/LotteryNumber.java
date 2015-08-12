package com.dsw.entity;

import java.io.Serializable;

public class LotteryNumber implements Serializable {
	private static final long  serialVersionUID = 1L;
	private int type;
	private String redNumber;
	private String blueNumber;
	private String date;
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getRedNumber() {
		return redNumber;
	}
	public void setRedNumber(String redNumber) {
		this.redNumber = redNumber;
	}
	public String getBlueNumber() {
		return blueNumber;
	}
	public void setBlueNumber(String blueNumber) {
		this.blueNumber = blueNumber;
	}
}
