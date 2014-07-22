/**
 *
 */
package com.blisscloud.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.io.*;

/**
 * @author peter Chen
 * 
 */
public class StringUtil {
	/**
	 * �ж��ִ����ǿմ���NULL
	 * 
	 * @param _str
	 *            �ַ�
	 * @return �Ƿ��ǿմ���null
	 */
	public static boolean isNullOrEmpty(String _str) {
		if (_str == null) {
			return true;
		}
		String s = _str;
		s = s.trim();
		if (s.equals("")) {
			return true;
		}
		if (s.equals("null")) {
			return true;
		}
		return false;
	}

	public static int getIntValue(String s, int errorValue) {
		int ok = 0;
		try {
			ok = Integer.parseInt(s);
		} catch (NumberFormatException e) {
			ok = errorValue;
		}
		return ok;
	}

	public static String Encoding(String code) {
		String temp = "";
		try {
			if (code != null) {
				temp = new String(code.getBytes("iso-8859-1"), "GBK");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return temp;
	}

	public static String toCn2312To8859(String utf8_value) {
		if (utf8_value == null) {
			return null;
		}
		try {
			byte[] b;
			b = utf8_value.getBytes("GB2312"); // �м���ISO-8859-1���
			String name = new String(b, "8859_1"); // ת����GB2312�ַ�
			return name;
		} catch (UnsupportedEncodingException ex) {
			return null;
		}
	}

	public static String getStrFromArray(String[] s, String splite) {
		String ok = "";
		if (s == null) {
			return null;
		}
		StringBuffer buffer = new StringBuffer();
		for (int k = 0; k < s.length; k++) {
			buffer.append(s[k] + splite);
		}
		ok = buffer.toString();
		if (ok.endsWith(splite)) {
			ok = ok.substring(0, ok.length() - splite.length());
		}
		return ok;
	}

	public static String getStrFromList(List s, String splite) {
		String ok = "";
		if (s == null) {
			return null;
		}
		if (s.isEmpty()) {
			return null;
		}
		StringBuffer buffer = new StringBuffer();
		for (int k = 0; k < s.size(); k++) {
			buffer.append(s.get(k) + splite);
		}
		ok = buffer.toString();
		if (ok.endsWith(splite)) {
			ok = ok.substring(0, ok.length() - splite.length());
		}
		return ok;
	}

	public static String toCn8859To2312(String utf8_value) {
		if (utf8_value == null) {
			return null;
		}
		try {
			byte[] b;
			b = utf8_value.getBytes("8859_1"); // �м���ISO-8859-1���
			String name = new String(b, "GB2312"); // ת����GB2312�ַ�
			return name;
		} catch (UnsupportedEncodingException ex) {
			return null;
		}
	}

	/**
	 * �ж��ִ����ǿմ���NULL,"null"
	 * 
	 * @param _str
	 *            �ַ�
	 * @return �Ƿ��ǿմ���null,"null",�䷵�ؽ����"";
	 */
	public static String filterNullorEmpty(String _str) {
		if (_str == null) {
			//
			return "";
		} else if (_str.equals("null")) {
			return "";
		}
		return _str;
	}

	/**
	 * �ж��ִ����ǿմ���NULL,"null"
	 * 
	 * @param _str
	 *            �ַ�
	 * @return �Ƿ��ǿմ���null,"null",�䷵�ؽ����"";
	 */
	public static String toNull(String _str) {
		if (_str == null) {
			//
			return "";
		}

		if ("null".equals(_str)) {
			return "";
		}
		return _str;
	}

	public static String filterNullorEmpty(Date _date) {
		if (_date == null) {
			//
			return "";
		}
		return String.valueOf(_date);
	}

	public static String replaceNull4html(String _str) {
		if ((_str == null) || ("".equals(_str))) {
			return "&nbsp;";
		}

		return _str;
	}

	public static String getTelNoSplit(String tel) {
		String telOk = "";
		if (tel == null || "".equals(tel) || "-".equals(tel)) {
			return "";
		}
		if (tel.indexOf("-") > -1) {
			String[] s = tel.split("-");
			if (s != null) {
				if (s.length == 2) {
					String pre = s[0];
					String fre = s[1];
					telOk = pre + fre;
				}
			}
		}
		return telOk;
	}

	/**
	 * ��Select�ؼ���Ƿ�Ҫ����ѡ������ж�,
	 * 
	 * @param _item
	 *            the value of option
	 * @param _param
	 *            need for the value of selection
	 * @return
	 */

	static public String strSelected(String _item, String _param) {
		if ((_item == null) || (_param == null)) {// ��ָ��Ĵ���
			return "";
		}
		if (_item.equals(_param)) {
			return "selected=\"selected\"";
		} else {
			return "";
		}
	}

	public static String utfTo2312(String utf8_value) {
		byte[] b;
		String name = "";
		if (utf8_value == null || "".equals(utf8_value)) {
			return utf8_value;
		}
		try {
			b = utf8_value.getBytes("8859_1"); // �м���ISO-8859-1���
			name = new String(b, "UTF-8");
			System.out.println("name:" + name);
			b = name.getBytes("GB2312"); // �м���ISO-8859-1���
			name = new String(b, "GBK");
			System.out.println("name1:" + name);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return name;

	}

	public static String toCn(String s) {
		if (s == null) {
			return null;
		} else if (s.length() == 0) {
			return "";
		}
		try {
			String rs = null;
			byte t[] = null;
			t = s.getBytes("GBK");
			rs = new String(t);
			return rs;
		} catch (Exception e) {
			e.printStackTrace(System.err);
			return s;
		}
	}

	public static String utf1To2312(String utf8_value) {
		byte[] b;
		String name = "";
		if (utf8_value == null || "".equals(utf8_value)) {
			return utf8_value;
		}
		try {
			b = utf8_value.getBytes("GBK"); // �м���ISO-8859-1���
			name = new String(b, "UTF-8");
			System.out.println("name:" + name);
			b = name.getBytes("8859_1"); // �м���ISO-8859-1���
			name = new String(b, "GBK");
			System.out.println("name1:" + name);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return name;

	}

	static public String getBizType(String _biztype) {
		if (_biztype == null)
			return "";
		else
			_biztype = _biztype.trim();
		if ("1".equals(_biztype)) {
			return "����";
		} else if ("2".equals(_biztype)) {
			return "��˰";
		} else if ("3".equals(_biztype)) {
			return "��˰";
		} else if ("4".equals(_biztype)) {
			return "��ƹ���";
		} else if ("5".equals(_biztype)) {
			return "������������";
		} else if ("6".equals(_biztype)) {
			return "Ͷ�߾ٱ�";
		} else {
			return "����";
		}
	}

	static public String getWorkType(String _workType) {
		// "1", �ǹ���ʱ��
		// "2"æʱ
		if ("1".equals(_workType)) {
			return "�ǹ���ʱ��";
		} else {
			return "æʱ";
		}
	}

	public static void main(String[] args) {
		String[] s = new String[] { "ssd", "dasds" };
		List a = new ArrayList();
		a.add("2");
		a.add("22");

		// String ss=StringUtil.getStrFromArray(s,",");
		String ss = StringUtil.getStrFromList(a, ",");
		System.out.print(ss);
	}

	public static String subString(String str, int length) {
		if (str != null) {
			if (str.length() > length) {
				str = str.substring(0, length);
			}
		} else {
			str = "";
		}
		return str;
	}

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

}
