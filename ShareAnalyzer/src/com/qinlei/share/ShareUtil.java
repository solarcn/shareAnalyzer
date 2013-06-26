package com.qinlei.share;

import java.util.Calendar;

public class ShareUtil {
	
	public static String year = "year";
	public static String month = "month";
	public static String day = "day";
	
	static public String generateDataFetcheringURL(String shareCode, Calendar from, Calendar to, String dataType){
		String url = "http://yahoo.compass.cn/stock/frames/frmHistoryDetail.php?";
		url= url + "start_year=" + from.get(Calendar.YEAR) +
				   "&start_month=" +(from.get(Calendar.MONTH)+1) +
				   "&start_day=" + from.get(Calendar.DATE) +
				   "&end_year=" + to.get(Calendar.YEAR) + 
				   "&end_month=" + (to.get(Calendar.MONTH)+1) +
				   "&end_day=" + to.get(Calendar.DATE) +
				   "&code=" + shareCode +
				   "&his_type=" + dataType;
		return url;
	}

	/**
	 * For Test Purpose only
	 */
	public static void main(String[] args) {

		
	}

}  
