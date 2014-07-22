
package com.blisscloud.common;

import java.io.Serializable;

/**
 * @author Ken.Guo
 * 对象基类
 */
public class BaseObject implements Serializable{
	/**
	 * 自己的ID
	 */
    private int seqId;
    /**
	 * 自己的名字
	 */
    private String name;
	/**
	 * 空的构造子
	 */
	public BaseObject() {
	
	}
	/**
	 * 带参数的构造子
	 */
	public BaseObject(int seqId,String name) {
		this.seqId=seqId;
		this.name=name;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSeqId() {
		return seqId;
	}
	public void setSeqId(int seqId) {
		this.seqId = seqId;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
