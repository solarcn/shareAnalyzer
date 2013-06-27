package com.qinlei.share.analyzers;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import com.qinlei.share.loader.THSCSVLoder;
import com.qinlei.share.model.Share;

public class 农夫播种术 {
	private final static SimpleDateFormat SDF = new SimpleDateFormat(
			"yyyy.mm.dd");

	double buyAShare(Share share, int volume) {
		double price = share.getOpen();
		Calendar cal = Calendar.getInstance();
		cal.setTime(share.getDate());

		System.out.printf("%s 买入               %7d股 at ￥%8.2f", SDF.format(share.getDate()),
				volume, share.getOpen());
		return price * volume; 
	}

	double sellAShare(Share share, int volume) {
		double price = share.getOpen();
		Calendar cal = Calendar.getInstance();
		cal.setTime(share.getDate());
		// System.out.print(SDF.format(share.getDate()) + "   卖出 " + volume +
		// "股 at ￥" + share.getOpen());
		System.out.printf("%s     卖出 %7d股 at ￥%8.2f", SDF.format(share.getDate()),
				volume, share.getOpen());  
		return price * volume;
	}
	
	static void  analyze(List<Share> shareList , Date from, Date to){
		for(Share share : shareList){
			if(share.getDate().before(from) || share.getDate().after(to)) shareList.remove(share);
		}
		analyze(shareList);
	}
//	static void  analyze(List<Share> shareList, Date from, Date to){
//		
//	}
	
	
	static void  analyze(List<Share> shareList){
		Share currentShare = null;
		final int volume = 1000;
		int size = shareList.size();
		size--;
		double currentPrice = 0;
		int totalVolume = 0;
		final double THRESHOLD = 0.03;
		final double GETBACK_THRESHOLD = 0.1;  
		double myBalance = 0;
		农夫播种术 aInstance = new 农夫播种术();
		boolean shouldIBuyTomorrow = true;
		boolean shouldISellTomorrow = false;
		for (int i = 0; i < size; i++) {
			currentShare = shareList.get(i);
			if (shouldIBuyTomorrow) {
				myBalance = myBalance
						- aInstance.buyAShare(currentShare, volume);
				totalVolume += volume;
				currentPrice = currentShare.getOpen();
				// System.out.println("我还剩：￥" + + "以及股票：" + + "股， 价值：￥" +
				// currentPrice*totalVolume + "盈亏：" + (myBalance +
				// currentPrice*totalVolume));
				System.out.printf("我还剩 ￥%9.0f 以及股票 %6d股, 价值￥%9.0f 盈亏￥%9.0f%n", myBalance,
						totalVolume, currentPrice * totalVolume,
						(myBalance + currentPrice * totalVolume));
				shouldIBuyTomorrow = false;
			}
			if (shouldISellTomorrow) {
				myBalance = myBalance
						+ aInstance.sellAShare(currentShare, volume);
				totalVolume -= volume;
				currentPrice = currentShare.getOpen();
				System.out.printf("我还剩 ￥%9.0f 以及股票 %6d股, 价值￥%9.0f 盈亏￥%9.0f%n", myBalance,
						totalVolume, currentPrice * totalVolume,
						(myBalance + currentPrice * totalVolume));
				shouldISellTomorrow = false;
			}
			double newPrice = currentShare.getClose();
			//如果处于空仓位置，那么一旦股价跌过GETBACK_THRESHOLD，重新进入  //10%
			if(totalVolume == 0 && (currentPrice - newPrice) / currentPrice >= GETBACK_THRESHOLD) {
					shouldIBuyTomorrow = true;
			}
			
			if ((newPrice - currentPrice) / currentPrice >= THRESHOLD) {
					shouldIBuyTomorrow = true;
			} else if ((currentPrice - newPrice) / currentPrice >= THRESHOLD
						&& totalVolume >= volume) {
					shouldISellTomorrow = true;
			}
			
		} // end of for
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// SharePriceLoader spl = new
		// SharePriceLoader(FileType.MYDOWNLOADFROMYAHOO,
		// "./shareData/中国银行.csv");
		// List<Share> shareList = spl.sharePrices;

		List<Share> shareList;
		shareList = THSCSVLoder.getShareListTHSCSV("./shareData/zgyh.csv");
		
		
		农夫播种术.analyze(shareList);

	} // end of main

}
