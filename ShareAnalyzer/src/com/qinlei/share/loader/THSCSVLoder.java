package com.qinlei.share.loader;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ParseDate;
import org.supercsv.cellprocessor.ParseDouble;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import com.qinlei.share.csv.MyParseDouble;
import com.qinlei.share.csv.MyParseInt;
import com.qinlei.share.model.Share;

public class THSCSVLoder {
	static private final boolean DEBUG = false;

	private static CellProcessor[] getProcessors() {
		final CellProcessor[] processors = new CellProcessor[] {
				new ParseDate("yyyy-mm-dd"),// 时间
				new ParseDouble(), // 开盘
				new ParseDouble(), // 最高
				new ParseDouble(), // 最低
				new ParseDouble(), // 收盘
				null, //new Optional(new ParseDouble()), // 涨幅
				null, //new Optional(new ParseDouble()),// 振幅
				new Optional(new MyParseInt()), // 总手数
				new Optional(new MyParseDouble()), //new Optional(new ParseInt()),// 金额
				null, //new Optional(new ParseDouble()),// 换手率
				null //new Optional(new ParseDouble())// 成交次数
		};
		return processors;
	}

	public static List<Share> getShareListTHSCSV(String fileName) throws Exception {
		ICsvBeanReader beanReader = null;
		List<Share> shareList= new ArrayList<Share>();
		try {
			beanReader = new CsvBeanReader(new FileReader(fileName),
					CsvPreference.STANDARD_PREFERENCE);

			beanReader.getHeader(true); // skip past the header (we're defining
										// our own)

			// only map the first 3 columns - setting header elements to null
			// means those columns are ignored
			final String[] header = new String[] { "Date", "Open",
					"High", "Low", "Close", null, null, "Volume", "TotalValue", null, null };

			Share share;
			while ((share = beanReader.read(Share.class, header,
					getProcessors())) != null) {
				shareList.add(share);
				if(DEBUG) System.out.println(share);
			}

		} finally {
			if (beanReader != null) {
				beanReader.close();
			}
		}
		return shareList;
	}

	/**
	 * @param arg;
	 */
	public static void main(String[] args) {
//		try {
//			getShareListTHSCSV("./shareData/zgyh.csv");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	}

}
