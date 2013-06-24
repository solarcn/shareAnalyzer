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

/**
 * @author qinlei
 * 
 */
public class ShareDataGetter {

	String urlString = "http://yahoo.compass.cn/stock/frames/frmHistoryDetail.php?start_year=2013"
			+ "&start_month=1&start_day=1&end_year=2013&end_month=1&end_day=30&code=sh600808&his_type=day";

	// URL url = new URL()

	String test() {
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
			connection.setRequestProperty("Content-Language", "utf-16");

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

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// for (int i = 0; i < 12;) {
		// Calendar from = new GregorianCalendar(2013, i, 01);
		// GregorianCalendar to = new GregorianCalendar(2013, ++i, 01);
		// // System.out.println();
		// // System.out.println(to.getActualMaximum(Calendar.DATE));
		// to.add(Calendar.DATE, -1);
		// // System.out.println(to);
		// String urlString = ShareUtil.generateDataFetcheringURL("sh600808",
		// from, to, ShareUtil.day);
		//
		//
		// } //End of for cycle
		new ShareDataGetter().processARequest();

	}

	void processARequest() {
		String str = new ShareDataGetter().test();
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

	String processOneRecord(String str) {
		int index = 0;
		index = str.indexOf("<th>");
		str = str.substring(index);
		str = str.substring(index + 4);

		index = str.lastIndexOf("</td></tr>");
		str = str.substring(0, index);
		str = str.replace("</th><td>", ";");
		str = str.replace("</td><td>", ";");
		// System.out.println(str);
		return str;
	}
}
