package com.blisscloud.util;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import com.blisscloud.constant.*;

public class StringTools {

	// ��ISO8859-1�ַ�ת��ΪGBK
	public static String ISO8859ToGBK(String convertString) {
		// ԴString
		String resourceString = null;
		// Ŀ��String
		String destination = null;

		if (convertString != null) {
			resourceString = convertString.trim();
			try {
				destination = new String(resourceString.getBytes("ISO8859-1"),
						"GBK");
			} catch (UnsupportedEncodingException e) {
				System.out.println("��ISO8859-1�ַ�ת��ΪGBKʱ����!");
				e.printStackTrace();
			}
		}
		return destination;
	}

	// ��ISO8859-1�ַ�ת��ΪGBK
	public static String ISO8859ToUTF8(String convertString) {
		// ԴString
		String resourceString = null;
		// Ŀ��String
		String destination = null;

		if (convertString != null) {
			resourceString = convertString.trim();
			try {
				destination = new String(resourceString.getBytes("ISO8859-1"),
						"UTF-8");
			} catch (UnsupportedEncodingException e) {
				System.out.println("��ISO8859-1�ַ�ת��ΪUTF-8ʱ����!");
				e.printStackTrace();
			}
		}
		return destination;
	}

	// ��yyyy-MM-dd��ʽ�ַ�ת��Ϊyyyy-MM-dd HH:mm:ss��ʽ����
	public static Date StringToDate(String convertString) {
		return StringToDate(convertString, false);
	}

	// ��yyyy-MM-dd��ʽ�ַ�ת��Ϊyyyy-MM-dd HH:mm:ss��ʽ���� isEnd:�Ƿ�Ϊ����ʱ��
	public static Date StringToDate(String convertString, boolean isEnd) {
		// ԴString
		String resourceString = null;
		// Ŀ��Date
		Date destinationDate = null;

		if (convertString != null) {
			if (isEnd) {
				resourceString = convertString.trim() + " 23:59:59";
			} else {
				resourceString = convertString.trim() + " 00:00:00";
			}
			SimpleDateFormat formatter = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			try {
				destinationDate = formatter.parse(resourceString);
			} catch (ParseException e) {
				System.out
						.println("��yyyy-MM-dd��ʽ�ַ�ת��Ϊyyyy-MM-dd HH:mm:ss��ʽ����ʱ����!");
				e.printStackTrace();
			}
		}
		return destinationDate;
	}

	// ������ת��Ϊyyyy-MM-dd HH:mm:ss��ʽ�ַ�
	public static String DateToString(Date convertDate) {
		// ԴDate
		Date resourceDate = null;
		// Ŀ��String
		String destinationString = null;

		if (convertDate != null) {
			resourceDate = convertDate;
			SimpleDateFormat formatter = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			try {
				destinationString = formatter.format(resourceDate);
			} catch (Exception e) {
				System.out.println("������ת��Ϊyyyy-MM-dd HH:mm:ss��ʽ�ַ�ʱ����!");
				e.printStackTrace();
			}
		}
		return destinationString;
	}

	// ������ת��Ϊyyyy-MM-dd��ʽ�ַ�
	public static String DateToString(Date convertDate, String format) {
		// ԴDate
		Date resourceDate = null;
		// Ŀ��String
		String destinationString = null;

		if (convertDate != null) {
			resourceDate = convertDate;
			SimpleDateFormat formatter = new SimpleDateFormat(format);
			try {
				destinationString = formatter.format(resourceDate);
			} catch (Exception e) {
				System.out.println("������ת��Ϊָ����ʽ�ַ�ʱ����!");
				e.printStackTrace();
			}
		}
		return destinationString;
	}

	// �ж��ַ��Ƿ�Ϊ��
	public static boolean isNull(String strIn) {
		if (strIn == null || strIn.equals("")) {
			return true;
		} else {
			return false;
		}
	}

	// ת��ָ���ַ�Ϊ""
	public static String ToNullStr(String strIn) {
		if (strIn != null) {
			return strIn;
		} else {
			return "";
		}
	}

	// ����Ե�ǰʱ��Ϊ���е��ַ����
	public static String getTimeSequeue() {
		String timeSequue = "";
		Calendar calendar = Calendar.getInstance();
		String year = String.valueOf(calendar.get(Calendar.YEAR));// ��
		String month = "00" + String.valueOf(calendar.get(Calendar.MONTH) + 1);// ��
		String day = "00" + String.valueOf(calendar.get(Calendar.DATE));// ��
		String hour = "00" + String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));// ʱ
		String minute = "00" + String.valueOf(calendar.get(Calendar.MINUTE));// ��
		String send = "00" + String.valueOf(calendar.get(Calendar.SECOND));// ��
		timeSequue = year + month.substring(month.length() - 2, month.length())
				+ day.substring(day.length() - 2, day.length())
				+ hour.substring(hour.length() - 2, hour.length())
				+ minute.substring(minute.length() - 2, minute.length())
				+ send.substring(send.length() - 2, send.length());
		return timeSequue;
	}

	// �жϵ�ǰ�ַ�Ĳ�ָ���
	public static int getSplitNums(String sourceStr) {
		int splitNubs = 0;
		if (!isNull(sourceStr)) {
			String[] tmp = sourceStr.split(";");
			if (tmp.length > 1) {
				splitNubs = tmp.length;
			}
		}

		return splitNubs;

	}

	// ����Ե�ǰʱ��Ϊ���е�Ŀ¼·�� add by jeton.dong 20110902
	public static String getTimeDir() {
		String timeDirs = "";
		Calendar calendar = Calendar.getInstance();
		String year = String.valueOf(calendar.get(Calendar.YEAR));// ��
		String month = "00" + String.valueOf(calendar.get(Calendar.MONTH) + 1);// ��
		String day = "00" + String.valueOf(calendar.get(Calendar.DATE));// ��

		timeDirs = MY_SYS_CONSTANT.UPLOAD_EXCEL_PATH + year + "//"
				+ month.substring(month.length() - 2, month.length()) + "//"
				+ day.substring(day.length() - 2, day.length()) + "//";
		return timeDirs;
	}

	// ����Ե�ǰʱ��Ϊ���е�Ŀ¼·�� add by jeton.dong 20110902
	public static String getTimeDirForWin() {
		String timeDirs = "";
		Calendar calendar = Calendar.getInstance();
		String year = String.valueOf(calendar.get(Calendar.YEAR));// ��
		String month = "00" + String.valueOf(calendar.get(Calendar.MONTH) + 1);// ��
		String day = "00" + String.valueOf(calendar.get(Calendar.DATE));// ��

		timeDirs = MY_SYS_CONSTANT.UPLOAD_EXCEL_PATH + year + "\\"
				+ month.substring(month.length() - 2, month.length()) + "\\"
				+ day.substring(day.length() - 2, day.length()) + "\\";
		return timeDirs;
	}

	public static void main(String[] args) {
		System.out.println("time::::" + getTimeDir());
	}
}
