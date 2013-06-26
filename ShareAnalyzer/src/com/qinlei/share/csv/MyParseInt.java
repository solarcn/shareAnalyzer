package com.qinlei.share.csv;

import org.supercsv.util.CsvContext;

public class MyParseInt extends org.supercsv.cellprocessor.ParseInt {

	public Object execute(final Object value, final CsvContext context) {

		//To get rid of , in a number, for example a number is 1,000,000 will be changed to 1000000
		if (value != null && value instanceof String) {
			return super.execute(((String) value).replace(",", ""), context);
		}
		return super.execute(value, context);
	}
}
