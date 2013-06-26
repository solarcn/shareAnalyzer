package com.qinlei.share.model;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Share {
	private Date date;
	private double open;
	private double high;
	private double low;
	private double close;
	private int volume;
	private double totalValue;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public double getOpen() {
		return open;
	}

	public void setOpen(double open) {
		this.open = open;
	}

	public double getHigh() {
		return high;
	}

	public void setHigh(double high) {
		this.high = high;
	}

	public double getLow() {
		return low;
	}

	public void setLow(double low) {
		this.low = low;
	}

	public double getClose() {
		return close;
	}

	public void setClose(double close) {
		this.close = close;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	public double getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(double totalValue) {
		this.totalValue = totalValue;
	}
	
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
		return String
				.format("Date: %s Open: %7.2f High: %7.2f Low: %7.2f Close: %7.2f Volume: %15d TotalValue: %15.2f",
						sdf.format(date), open, high, low, close, volume,
						totalValue);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
