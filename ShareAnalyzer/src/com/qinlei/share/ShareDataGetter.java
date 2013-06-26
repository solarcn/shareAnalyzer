/**
 * 
 */
package com.qinlei.share;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * @author qinlei
 * 
 */
public class ShareDataGetter {

	String urlString = "http://yahoo.compass.cn/stock/frames/frmHistoryDetail.php?start_year=2013"
			+ "&start_month=1&start_day=1&end_year=2013&end_month=1&end_day=30&code=sh600808&his_type=day";

	// URL url = new URL()

	String sendRequest(String urlString) {
		URL url;
		HttpURLConnection connection = null;
		try {
			// Create connection
			url = new URL(urlString);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");

			// connection.setRequestProperty("Content-Length", "" +
			// Integer.toString(urlParameters.getBytes().length));
			connection.setRequestProperty("Content-Language", "utf-8");
			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);

			// Send request
			DataOutputStream wr = new DataOutputStream(
					connection.getOutputStream());
			// wr.writeBytes (urlParameters);
			wr.flush();
			wr.close();

			// Get Response
			InputStream is = connection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			String line;
			StringBuffer response = new StringBuffer();
			while ((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\r');
			}
			rd.close();
//			return URLEncoder.encode(response.toString(),"UTF-16");
			return response.toString();

		} catch (Exception e) {

			e.printStackTrace();
			return null;

		} finally {

			if (connection != null) {
				connection.disconnect();
			}
		}
	}

	void getShareDateForAWholeYears(int year, String shareCode, String saveTo) {
		FileUtil.initializeAPrintWriter(saveTo); // ./resource/马钢股份.csv
		for (int i = 12; i > 0;) {
			GregorianCalendar to = new GregorianCalendar(year, i, 01);
			Calendar from = new GregorianCalendar(year, --i, 01);

			Calendar today = Calendar.getInstance();
			if (from.after(today))
				continue;
			// System.out.println();
			// System.out.println(to.getActualMaximum(Calendar.DATE));
			to.add(Calendar.DATE, -1);
			// System.out.println(to);
			String urlString = ShareUtil.generateDataFetcheringURL(shareCode, // sh600808
					from, to, ShareUtil.day);
			// System.out.println(urlString);
			// ///////
			String response = sendRequest(urlString);
			System.out.println(urlString);
			System.out.println(response);
			processARequest(response);
			FileUtil.flush();
			// ///////////
		} // End of for cycle
		FileUtil.closeFileOutputStream();
	}

	void processARequest(String response) {
		String str = response;

		String firstHalf = "";
		int index = 0;
		index = str.indexOf("<tbody>");
		str = str.substring(index);
		index = str.indexOf("<th>");
		str = str.substring(index);
		index = str.indexOf("</tbody>");
		str = str.substring(0, index);
		// System.out.println(str);
		while (true) {
			index = str.indexOf("><th>");
			if (index == -1)
				break;
			index++;
			firstHalf = str.substring(0, index);
			processOneRecord(firstHalf);
			// System.out.println(firstHalf);
			str = str.substring(index);

		}
		// System.out.println(str);
		processOneRecord(str);
	}

	String processOneRecord(String record) {
		int index = 0;
		index = record.indexOf("<th>");
		record = record.substring(index);
		record = record.substring(index + 4);

		index = record.lastIndexOf("</td></tr>");
		record = record.substring(0, index);
		record = record.replace(",", "");
		record = record.replace("</th><td>", ",");
		record = record.replace("</td><td>", ",");
		System.out.println(record);
		FileUtil.saveALine(record);
		return record;
	}

	// void saveARecord(String record){
	//
	// }
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ShareDataGetter sdg = new ShareDataGetter();
		for (int i = 2013; i > 2000; i--) {
			sdg.getShareDateForAWholeYears(i, "sh601988", "./resource/中国银行.csv");
		}
		// sh600808
	}
}
