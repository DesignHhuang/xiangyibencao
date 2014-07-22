/**
 * 
 */
package com.blisscloud.common;
public class MethodCallBean {
   private boolean flag=false;
   private String msg;
   private Object value;
   
   public MethodCallBean(){
		
   }
   
   public MethodCallBean(boolean flag){
		this.flag=flag;
   }
   public MethodCallBean(boolean flag,String msg){
		this.flag=flag;
		this.msg=msg;
   }
   
   public MethodCallBean(boolean flag,String msg,Object value){
		this.flag=flag;
		this.msg=msg;
		this.value=value;
  }

public boolean isFlag() {
	return flag;
}

public void setFlag(boolean flag) {
	this.flag = flag;
}

public String getMsg() {
	return msg;
}

public void setMsg(String msg) {
	this.msg = msg;
}

public Object getValue() {
	return value;
}

public void setValue(Object value) {
	this.value = value;
}
   
   

}
