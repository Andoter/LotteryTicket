package com.dsw.entity;

import java.io.Serializable;

public class LotteryKind implements Serializable {
	private static final long serialVersionUID = 1L;
	/*
	 * 彩票类型
	 */
	private int type;
	/*
	 *价格提示，例如2元中1500万 
	 */
	private String priceNotice;
	/*
	 * 开奖日期
	 */
	private String openTime;
	/*
	 * 期号
	 */
	private int number;
	/*
	 * 剩余日期
	 */
	private String dateTime;
	/*
	 * 奖金总额
	 */
	private int totalGold;
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getPriceNotice() {
		return priceNotice;
	}
	public void setPriceNotice(String priceNotice) {
		this.priceNotice = priceNotice;
	}
	public String getOpenTime() {
		return openTime;
	}
	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public int getTotalGold() {
		return totalGold;
	}
	public void setTotalGold(int totalGold) {
		this.totalGold = totalGold;
	}
}
