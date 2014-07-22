package com.blisscloud.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
	private static final String algorithm = "MD5"; // �����㷨

	/**
	 * �õ�ָ���ַ���ܺ������
	 * 
	 * @param str
	 *            Դ�ַ�
	 * @return ���ܰ��������
	 */
	public static String encode(String str) {
		StringBuffer stringbuffer = new StringBuffer();
		MessageDigest messagedigest = null;
		try {
			messagedigest = MessageDigest.getInstance(algorithm);
		} catch (NoSuchAlgorithmException e) {
			System.out.println("error>>cannot get MD5>>" + e);
			return "";
		}
		messagedigest.update((str).getBytes());
		byte abyte0[] = messagedigest.digest();
		for (int i = 0; i < abyte0.length; i++) {
			String _str = Integer.toHexString(0xff & abyte0[i]);
			if (_str != null && _str.length() == 1)
				_str = "0" + _str;
			stringbuffer.append(_str);
		}

		return stringbuffer.toString();
	}

	public static void main(String[] args) throws Exception {
		String info = "111111";
		System.out.println("region string:" + info);
		String hexenc = MD5Util.encode(info);
		System.out.println("hexenc-->" + hexenc);
	}
}
