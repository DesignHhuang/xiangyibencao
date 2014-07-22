/*
 * Jeton.dong
 *
 * Created on 2007-12-06
 *
 * Copyright :blisscloud
 */
package com.blisscloud.util;

import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;

public class PropertiesTools {
	// �����ļ�����·��
	final static String resource_properties_path = "myConf\\general.properties";

	// final static String resource_properties_path =
	// "\\web\\WEB-INF\\classes\\general.properties";

	/**
	 * �ж��ļ��Ƿ����
	 * 
	 * @param filename
	 *            :�ļ�·��
	 * @param createOnNoExists
	 *            :�ļ�������ʱ���Ƿ
	 *            񴴽����ļ���true:�ļ�������ʱ�������ļ���false�����������ļ�
	 * @return �����ļ��Ƿ���� true:�ļ����ڣ�false:�ļ�������
	 */
	public static boolean isFileExists(String filename, boolean createOnNoExists) {
		File file = new File(filename);

		try {
			if (!file.exists()) {
				if (createOnNoExists) {
					File temp = file.getParentFile();
					temp.mkdirs();
					file.createNewFile();
				}
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static String readResourceProperty(String name) {
		return readProperty(resource_properties_path, name);
	}

	public static boolean writeResourceProperty(String name, String value) {
		return writeProperty(resource_properties_path, name, value);
	}

	/**
	 * ��ָ���������ļ�����"���=ֵ"��ʽд������
	 * 
	 * @param filename
	 *            :�ļ�·��
	 * @param name
	 *            :������ƣ�value������ֵ
	 * @return д�����ݲ����Ƿ�ɹ���true���ɹ� false:���ɹ�
	 */
	public static boolean writeProperty(String filename, String name,
			String value) {
		boolean rv = false;
		try {
			// �ж��ļ��Ƿ���ڣ��������򴴽�
			isFileExists(filename, true);

			// 1���������ļ����뵽�ڴ�
			Properties props = new Properties();
			// InputStream is =
			// this.getClass().getClassLoader().getResourceAsStream(filename);
			FileInputStream is = new FileInputStream(filename);
			try {
				props.load(is);
			} finally {
				is.close();
			}

			// 2���޸Ļ�������ԣ��ٱ��浽�����ļ���
			FileOutputStream out = new FileOutputStream(filename);
			try {
				// Properties props = new Properties();
				props.setProperty(name, value);
				// Object userobj = props.put(name,value);
				// props.store(out,(String)userobj);
				props.store(out, "this " + filename + " file");
				rv = true;
			} finally {
				out.close();
			}
		} catch (Exception e) {
			// e.printstacktrace();
		}
		return rv;
	}

	/**
	 * ��ָ���������ļ��ж�ȡ�����Ե�ֵ
	 * 
	 * @param filename
	 *            :�ļ�·��
	 * @param name
	 *            :�������
	 * @return ����ָ�����Ե�ֵ
	 */
	public static String readProperty(String filename, String name) {
		String rv = "";
		try {
			isFileExists(filename, true);// �ж��ļ��Ƿ���ڣ��������򴴽�

			Properties props = new Properties();
			// InputStream is =
			// this.getClass().getClassLoader().getResourceAsStream(filename);
			FileInputStream is = new FileInputStream(filename);
			try {
				props.load(is);
				rv = props.getProperty(name);
				if (rv != null)
					rv = rv.trim();
			} finally {
				is.close();
			}
		} catch (Exception e) {
			return "";
			// e.printstacktrace();
		}
		return rv;
	}

	/**
	 * ��ָ���������ļ��ж�ȡ�����Ե�ֵ
	 * 
	 * @param filename
	 *            :�ļ�·��
	 * @param name
	 *            :�������
	 * @return ����ָ�����Ե�ֵ(���)
	 */
	public static List readMultipleProperty(String filename, String name) {
		List valueList = null;// ָ�����Ե�ֵ
		Configuration config;// ��������PropertiesConfiguration��ȡָ���ļ�
		try {
			isFileExists(filename, true);// �ж��ļ��Ƿ���ڣ��������򴴽�
			config = new PropertiesConfiguration(filename);
			if (!StringUtil.isNullOrEmpty(name)) {
				List temp = config.getList(name);
				if (temp != null && temp.size() > 0) {
					valueList = temp;
				}
			}
		} catch (Exception e) {
			System.out
					.println("ִ��com.general.tools->public static List readMultipleProperty(String filename,String name)����ʱ�쳣!");
		}
		return valueList;
	}

	public static List readMultipleProperty(String name) {
		return readMultipleProperty(resource_properties_path, name);
	}

	public static void main(String[] args) {
		List test = PropertiesTools.readMultipleProperty("recordIp");
		if (test != null && test.size() > 0) {
			for (Iterator iterator = test.iterator(); iterator.hasNext();) {
				System.out.println(iterator.next());
			}
		}
	}

}
