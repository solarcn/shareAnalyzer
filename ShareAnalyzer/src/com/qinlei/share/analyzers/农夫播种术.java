package com.qinlei.share.analyzers;

import java.util.Calendar;
import java.util.List;

import com.qinlei.share.SharePriceLoader;
import com.qinlei.share.model.Share;

public class 农夫播种术 {
	
	double buyAShare(Share share, int volume){
		double price = share.getOpen();
		Calendar cal = Calendar.getInstance();
		cal.setTime(share.getDate());
		System.out.println(cal.get(Calendar.YEAR) + "年"+(cal.get(Calendar.MONTH)+1) +"月" +
				cal.get(Calendar.DATE)+"日买入 " + volume + "股 at ￥" + share.getOpen());
		return price * volume;
	}
	
	double sellAShare(Share share, int volume){
		double price = share.getOpen();
		Calendar cal = Calendar.getInstance();
		cal.setTime(share.getDate());
		System.out.println(cal.get(Calendar.YEAR) + "年"+(cal.get(Calendar.MONTH)+1) +"月" +
				cal.get(Calendar.DATE)+"日卖出 " + volume + "股 at ￥" + share.getOpen());
		return price * volume;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SharePriceLoader spl = new SharePriceLoader("./shareData/中国银行.csv");
		List<Share> shareList =  spl.sharePrices;
		Share currentShare = null;
		final int volume = 1000;
		int size = shareList.size();
		size --;
		double currentPrice = 0;
		int totalVolume = 0; 
		double threshold = 0.03;
		double myBalance = 0;
		农夫播种术 aInstance = new 农夫播种术();
		boolean shouldIBuyTomorrow = true;
		boolean shouldISellTomorrow = false;
		for(int i = size; i > 0; i -- ){
			currentShare = shareList.get(i);
			if(shouldIBuyTomorrow) {
				myBalance = myBalance - aInstance.buyAShare(currentShare, volume);
				totalVolume +=  volume;
				currentPrice = currentShare.getOpen();
				System.out.println("我还剩：￥" + myBalance + "以及股票：" + totalVolume + "股， 价值：￥" + currentPrice*totalVolume + "盈亏：" + (myBalance + currentPrice*totalVolume));
				shouldIBuyTomorrow = false;
			}
			if(shouldISellTomorrow) {
				myBalance = myBalance + aInstance.sellAShare(currentShare, volume);
				totalVolume -=  volume;
				currentPrice = currentShare.getOpen();
				System.out.println("我还剩：￥" + myBalance + "以及股票：" + totalVolume + "股， 价值：￥" + currentPrice*totalVolume  + "盈亏：" + (myBalance + currentPrice*totalVolume));
				shouldISellTomorrow = false;
			}
			double price = currentShare.getClose();
			if(price >= currentPrice) {
				if((price-currentPrice)/currentPrice >= threshold) {
					shouldIBuyTomorrow = true;
				}
			}else{
				if((currentPrice - price)/currentPrice >= threshold && totalVolume >= volume) {
					shouldISellTomorrow = true;
				}
				
			}
		}  //end of for
		
	}  //end of main

}
