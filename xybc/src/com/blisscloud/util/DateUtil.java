package com.blisscloud.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.StringTokenizer;

public class DateUtil {
	private final static String formatStr1 = "yyyy-MM-dd";
	private final static String formatStr2 = "yyyy-MM-dd HH:mm:ss";
	private final static String formatStr3 = "yyyyƒÍMM‘¬dd»’";
	private final static String formatStr4 = "yyyyMMddHHmmssSS";
	private final static String formatStr5 = "HH:mm:ss";
	private final static String formatStr6 = "HH:mm";

	private final static SimpleDateFormat sdf1 = new SimpleDateFormat(
			formatStr1);
	private final static SimpleDateFormat sdf2 = new SimpleDateFormat(
			formatStr2);
	private final static SimpleDateFormat sdf3 = new SimpleDateFormat(
			formatStr3);
	private final static SimpleDateFormat sdf4 = new SimpleDateFormat(
			formatStr4);
	private final static SimpleDateFormat sdf5 = new SimpleDateFormat(
			formatStr5);
	private final static SimpleDateFormat sdf6 = new SimpleDateFormat(
			formatStr6);

	public DateUtil() {
	}

	public static Date stringToDate(String dateStr) {
		return stringToDate(dateStr, "yyyy-MM-dd");
	}

	public static Date stringToEndDate(String dateStr) {
		if (dateStr==null||"".equals(dateStr)) {
			return null;
		}
		return stringToDate(dateStr.trim() + " 23:59:59", "yyyy-MM-dd HH:mm:ss");
	}

	public static Date stringToDate(String dateStr, String dateFormat) {
		try {
			java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(
					dateFormat);
			Date date = formatter.parse(dateStr);
			return date;
		} catch (Exception e) {
			return null;
		}
	}

	public static String transDateByMM(java.util.Date date) {
		return sdf2.format(date);
	}

	public static String tranDateToMdhm(Date date) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat formatter = new SimpleDateFormat("yy-MM-dd HH:mm");
		return formatter.format(date);
	}

	public static String transDateToString(java.util.Date date) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(date);
	}

	public static Double transData(Double d) {
		Double date;
		if (d == null)
			// date = 0.0; commented by jeton.dong 20070428

			date = new Double(0.0);
		else {
			date = d;
		}
		return date;
	}

	public static Double transData(double d) {
		Double date;
		// date = d; commented by jeton.dong 20070428

		date = new Double(d);
		return date;
	}

	/**
	 * hh:mm:ss
	 * 
	 * @return String
	 */
	public final static String getCurrHouMinSecond() {
		String currTime = "";
		java.util.Date currDate = new java.util.Date(System.currentTimeMillis());
		currTime = sdf5.format(currDate);
		return currTime;
	}

	/**
	 * hh:mm
	 * 
	 * @return String
	 */
	public final static String getCurrHouMin() {
		String currTime = "";
		java.util.Date currDate = new java.util.Date(System.currentTimeMillis());

		currTime = sdf6.format(currDate);

		return currTime;
	}

	public String[] splitStringOfDate(String date) {
		StringTokenizer tokenizer = new StringTokenizer(date, "-");
		String[] dateArray = new String[3];
		for (int i = 0; tokenizer.hasMoreTokens(); i++) {
			dateArray[i] = tokenizer.nextToken();
		}
		return dateArray;
	}

	static public int dateSpan(String strTimeStart, String strTimeEnd) {
		java.util.Date dateStart = toUtilDate(strTimeStart,
				"yyyy-MM-dd HH:mm:ss.S");
		java.util.Date dateEnd = toUtilDate(strTimeEnd, "yyyy-MM-dd HH:mm:ss.S");
		GregorianCalendar ggStart = new GregorianCalendar();
		GregorianCalendar ggEnd = new GregorianCalendar();
		ggStart.setTime(dateStart);
		ggEnd.setTime(dateEnd);
		int index = 0;
		while (!ggEnd.before(ggStart)) {
			ggStart.add(GregorianCalendar.DATE, 1);
			index++;
		}
		return index;
	}

	public static java.sql.Date toDateTime(String dateStr, String dateFormat) {
		try {
			java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(
					dateFormat);
			java.util.Date date = formatter.parse(dateStr);
			java.sql.Date date2 = new java.sql.Date(date.getTime());
			return date2;
		} catch (Exception e) {
			return null;
		}
	}

	public static java.util.Date toUtilDate(String dateStr, String dateFormat) {
		try {
			java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(
					dateFormat);
			java.util.Date date = formatter.parse(dateStr);
			return date;
		} catch (Exception e) {
			return null;
		}
	}

	public static String getDefaultFromDate(int formerDates) {
		long DATETIMEMILLIS = 2678400000l / 30;
		long formerDatesMillis = DATETIMEMILLIS * formerDates;
		java.util.Date date = new java.util.Date(System.currentTimeMillis()
				- formerDatesMillis);
		return DateUtil.transDateToString(date);
	}

	public static Double toDouble(String dateStr) {
		try {
			return Double.valueOf(dateStr);
		} catch (Exception e) {
			return null;
		}
	}

	public static String transDateToString(String date) {
		Date date2 = new Date();
		long l = date2.parse(date);
		date2 = new Date(l);
		return sdf1.format(date2);
	}

	public static Date[] getThisDay() {
		Date[] date = new Date[2];
		Calendar cal = Calendar.getInstance(Locale.CHINA);
		Date fromTime = cal.getTime();
		Date endTime = cal.getTime();
		initDate(date, fromTime, endTime);
		return date;
	}

	public static Date[] getThisWeek() {
		Date[] date = new Date[2];
		Calendar cal = Calendar.getInstance(Locale.CHINA);
		if (cal.get(Calendar.DAY_OF_WEEK) < Calendar.MONDAY) {
			cal.add(Calendar.DATE, -6);
		} else {
			cal.add(Calendar.DATE, Calendar.MONDAY
					- cal.get(Calendar.DAY_OF_WEEK));
		}
		Date fromDate = cal.getTime();
		cal.add(Calendar.DATE, +6);
		Date endDate = cal.getTime();
		initDate(date, fromDate, endDate);
		return date;
	}

	public static Date[] getThisMonth() {
		Date[] date = new Date[2];
		Calendar cal = Calendar.getInstance(Locale.CHINA);
		int maximum = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		Date fromDate = new Date();
		fromDate.setDate(1);
		Date endDate = new Date();
		endDate.setDate(maximum);
		initDate(date, fromDate, endDate);
		return date;
	}

	public static Date[] getThisYear() {
		Date[] date = new Date[2];
		Calendar cal = Calendar.getInstance(Locale.CHINA);
		int year = cal.get(Calendar.YEAR);
		System.out.print(year);
		cal.set(year, 0, 1);
		Date fromDate = cal.getTime();
		cal.set(year, 11, 31);
		Date endDate = cal.getTime();
		initDate(date, fromDate, endDate);
		return date;
	}

	private static void initDate(Date[] date, Date fromDate, Date endDate) {
		setFirstTime(fromDate);
		date[0] = fromDate;
		setEndTime(endDate);
		date[1] = endDate;
	}

	private static void setFirstTime(Date time) {
		time.setHours(0);
		time.setMinutes(0);
		time.setSeconds(0);
	}

	private static void setEndTime(Date time) {
		time.setHours(23);
		time.setMinutes(59);
		time.setSeconds(59);
	}

	public static String dateToString(java.util.Date date, String pattern) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		return formatter.format(date);
	}
	
	public static String dateToString(java.util.Date date) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(date);
	}

	// get year 
	public static String getYearStr(Date aDate) {
		String dateStr = dateToString(aDate, "yyyy-MM-dd");
		return dateStr.substring(0, 4);
	}

	// get month(two bits),for example:11,05
	public static String getMonthStr(Date aDate) {
		String dateStr = dateToString(aDate, "yyyy-MM-dd");
		return dateStr.substring(5, 7);
	}

	// get day,for example:25,04
	public static String getDayStr(Date aDate) {
		String dateStr = dateToString(aDate, "yyyy-MM-dd");
		return dateStr.substring(8, 10);
	}

	/*
	 * 
	 * Add 2007-3-30
	 * 
	 */

	public static Calendar start(Calendar cal) {
		cal.set(Calendar.AM_PM, 0);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal;
	}

	public static Calendar end(Calendar cal) {
		cal.set(Calendar.AM_PM, 0);
		cal.set(Calendar.HOUR, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		return cal;
	}

	public static Date start(Date date) {
		if (date == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return start(calendar).getTime();
	}

	public static Date end(Date date) {
		if (date == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return end(calendar).getTime();
	}

	public static void main(String[] args) {
		Date[] thisYear = getThisYear();
		System.out.println(thisYear[0]);
		System.out.println(thisYear[1]);

	}

}