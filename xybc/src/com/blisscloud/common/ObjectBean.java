package com.blisscloud.common;
/**
 * 
 * @author Ken.Guo
 * 用于存储一个对象
 * key为键,value为值
 *
 */     
public class ObjectBean {
    private Object key;
    private Object value;
	public ObjectBean() {
	}
    
	public ObjectBean(Object _key,Object _value) {
     this.key=_key;
     this.value=_value;
	}

	
	public Object getKey() {
		return key;
	}

	public void setKey(Object key) {
		this.key = key;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

}
