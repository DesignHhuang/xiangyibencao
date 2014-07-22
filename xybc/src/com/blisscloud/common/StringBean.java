package com.blisscloud.common;

/**
 * 
 * @author Ken.Guo
 * 用于存贮具有字符串性质的键值对对象
 *
 */
public class StringBean {
    private String key;
    private String value;
	
	public StringBean() {
	}
    
	public String getKey() {
		return key;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public StringBean(String _key,String _value) {
	this.setKey(_key);
	this.setValue(_value);
	}

	public String getValue() {
		return value;
	}

	public void setKey(String key) {
		this.key = key;
	}
}
