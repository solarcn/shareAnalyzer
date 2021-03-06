package com.qinlei.share.loader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.qinlei.share.model.FileType;
import com.qinlei.share.model.Share;

public class SharePriceLoader {
	final boolean debug = false;
	public List<Share> sharePrices = new ArrayList<Share>();

	public SharePriceLoader(FileType fileType, String file) {

		BufferedReader bf;
		String aShareRecord;
		try {
			bf = new BufferedReader(new FileReader(file));
			while (true) {
				aShareRecord = bf.readLine();
				if (aShareRecord == null)
					break;
				if(debug) System.out.println(aShareRecord);
				parseRecord(aShareRecord);
			}

		} catch (FileNotFoundException e) {
			System.out.println("file " + file + " not found!");
			e.printStackTrace();
			return;
		} catch (IOException e) {
			System.out.println("Something Wrong when reading from the file.");
			e.printStackTrace();
		}
	}

	private Share parseRecord(String shareRecord) {
		Share aRecord = new Share();
		String records[] = shareRecord.split(",");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");

		try {
			aRecord.setDate(sdf.parse(records[0]));
		} catch (ParseException e) {
			System.out.println("ParseException while parse date:" + e);
			e.printStackTrace();
		}
		aRecord.setOpen(new Double(records[1]));
		aRecord.setHigh(new Double(records[2]));
		aRecord.setLow(new Double(records[3]));
		aRecord.setClose(new Double(records[4]));
		aRecord.setVolume(new Integer(records[5]));
		aRecord.setTotalValue(new Double(records[6]));
		sharePrices.add(aRecord);

		return null;
	}

	public static void main(String[] args) {
		SharePriceLoader spl = new SharePriceLoader(FileType.MYDOWNLOADFROMYAHOO, "./shareData/中国银行.csv");
		int size = spl.sharePrices.size();
		double currentPrice;
		int totalPurchased; 
		double threshold = 0.03;
		
		
		for(int i = size; i >=0; i -- ){
			
		}
		
		
		
//		System.out.println(spl.sharePrices.size());
//		System.out.println(spl.sharePrices.get(0).getDate());
	}
}
