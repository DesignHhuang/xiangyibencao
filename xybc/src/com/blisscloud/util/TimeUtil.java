/*
 * Jeton.dong
 *
 * Created on 2010-6-10
 *
 * Copyright :blisscloud
 */
package com.blisscloud.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
	/*
	 * �����������x��xʱx��x��x����
	 */
	public static String timeToDate(long ms) {
		int ss = 1000;
		int mi = ss * 60;
		int hh = mi * 60;
		int dd = hh * 24;

		long day = ms / dd;
		long hour = (ms - day * dd) / hh;
		long minute = (ms - day * dd - hour * hh) / mi;
		long second = (ms - day * dd - hour * hh - minute * mi) / ss;
		long milliSecond = ms - day * dd - hour * hh - minute * mi - second
				* ss;

		String strDay = day < 10 ? "0" + day : "" + day;
		String strHour = hour < 10 ? "0" + hour : "" + hour;
		String strMinute = minute < 10 ? "0" + minute : "" + minute;
		String strSecond = second < 10 ? "0" + second : "" + second;
		String strMilliSecond = milliSecond < 10 ? "0" + milliSecond : ""
				+ milliSecond;
		strMilliSecond = milliSecond < 100 ? "0" + strMilliSecond : ""
				+ strMilliSecond;
		return strDay + " " + strHour + ":" + strMinute + ":" + strSecond + " ";
		// return strDay + "�� " + strHour + "Сʱ" + strMinute + "����" +
		// strSecond + "��";
		// return strHour + ":" + strMinute + ":" + strSecond;
		// return strHour + ":" + strMinute;
	}

	public static void main(String[] args) {
		// String test="3";
		// System.out.println(timeToDate(60*1*1000));
		// System.out.println("test:::->"+Long.parseLong("9"));
		// System.out.println(getWeek("2008-11-10",true));

		/*
		 * Date date = new Date(1284094278*1000);//默认是当前时闄1�7 SimpleDateFormat
		 * sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); String s =
		 * sdf.format(date); System.out.println(s);//输出当前时间
		 * System.out.println(System.currentTimeMillis());//输出当前时间
		 */
		// String times = rs.getString("origtime");
		long times = 1284094278;
		java.util.Date now = new java.util.Date(times * 1000);
		java.text.SimpleDateFormat df = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String dt = df.format(now);
		System.out.println(dt);

	}
}
