package com.blisscloud.entity;

public class DictionaryBean implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long seqID;				//ÐòÁÐID
	private long parentID;			//¸¸ID
	private long orderNum;			//ÅÅÐòºÅ
	private String dictionaryType;	//×ÖµäÀàÐÍ
	private String dictionaryName;	//×ÖµäÃû³Æ
	private String dictionaryCode;	//×Öµä±àÂë
	private String comm;			//±¸×¢
	
	
	public String getComm() {
		return comm;
	}
	public void setComm(String comm) {
		this.comm = comm;
	}
	public String getDictionaryCode() {
		return dictionaryCode;
	}
	public void setDictionaryCode(String dictionaryCode) {
		this.dictionaryCode = dictionaryCode;
	}
	public String getDictionaryName() {
		return dictionaryName;
	}
	public void setDictionaryName(String dictionaryName) {
		this.dictionaryName = dictionaryName;
	}
	public String getDictionaryType() {
		return dictionaryType;
	}
	public void setDictionaryType(String dictionaryType) {
		this.dictionaryType = dictionaryType;
	}
	public long getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(long orderNum) {
		this.orderNum = orderNum;
	}
	public long getParentID() {
		return parentID;
	}
	public void setParentID(long parentID) {
		this.parentID = parentID;
	}
	public long getSeqID() {
		return seqID;
	}
	public void setSeqID(long seqID) {
		this.seqID = seqID;
	}

	
}