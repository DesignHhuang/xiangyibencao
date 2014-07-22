
package com.blisscloud.util;


import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author Ken.Guo
 * 本类用于系统启动时对属性文件
 * /properties/SysParaSet.properties的操作
 */
public class SysParaUtil {
	
	public static String WEB_APP_ROOT_IP = "";
	
    //产品数据库配置参数
	public static String PRODUCT_DB_USER = "";
	public static String PRODUCT_DB_PWD = "";
	//知识库数据配置
	public static String KM_DB_SQL_SERVER_URL = "";
	public static String KM_DB_SQL_SERVER_DRIVER_PATH = "";
	public static String KM_DB_SQL_SERVER_USER = "";
	public static String KM_DB_SQL_SERVER_PWD = "";
	
    //语音库数据配置
	public static String VOX_MY_SQL_URL = "";
	public static String VOX_MY_SQL_URL_DRIVER_PATH = "";
	public static String VOX_MY_SQL_URL_USER = "";
	public static String VOX_MY_SQL_URL_PWD = "";
	
	//联合办证平台数据库,客户资料处与税友接口数据库配置
	public static String CUSTOMER_SYBASE_URL="";
	public static String CUSTOMER_SYBASE_DRIVER_PATH="";
	public static String CUSTOMER_SYBASE_USER="";
	public static String CUSTOMER_SYBASE_PWD="";
	
	//发票查询接口数据库配置
	public static String INVOICE_SYBASE_URL="";
	public static String INVOICE_SYBASE_DRIVER_PATH="";
	public static String INVOICE_SYBASE_USER="";
	public static String INVOICE_SYBASE_PWD="";
	
	
	//系统角色配置  
	public static String ROLE_FORE="";               //前台
	public static String ROLE_BACK="";               //后台
	public static String ROLE_BIZ_EXPORT="";  	     //业务专家
	public static String ROLE_MONITOR="";  			 //质监
	public static String ROLE_OA_SYS="";  			 //OA公文系统
	public static String ROLE_DIRECTOR="";  		 //主任
	public static String ROLE_SECOND_DIRECTOR="";  	 //副主任
	public static String ROLE_RUN_MASTER="";  		 //运营主管
	public static String ROLE_SECOND_RUN_MASTER="";  //副运营主管
	public static String ROLE_CALLER_CUSTOMER="";    //纳税人
	
	
	public static String OA_GET_MSG_FRUSH_TIME="";    //OA自动取回数据时间间隔
	public static String GET_OA_START_FLAG="";        //是否启动自动抓取数据线程
 	
	public static Properties pro=null;
	
	public SysParaUtil() {}
     
	private static void init(){
		pro=getSysProperties();
	}
	
	/**
	 * 得到系统中关键的角色配置表
	 * @return
	 */
	public static Map getRolesMap(){
		Map map=new HashMap();	
		map.put(SysParaUtil.ROLE_FORE,"前台");
		map.put(SysParaUtil.ROLE_BACK,"后台");
		map.put(SysParaUtil.ROLE_BIZ_EXPORT,"业务专家");
		map.put(SysParaUtil.ROLE_OA_SYS,"OA公文系统");
		map.put(SysParaUtil.ROLE_DIRECTOR,"主任");
		map.put(SysParaUtil.ROLE_SECOND_DIRECTOR,"副主任");
		map.put(SysParaUtil.ROLE_RUN_MASTER,"运营主管");
		map.put(SysParaUtil.ROLE_SECOND_RUN_MASTER,"副运营主管");
		map.put(SysParaUtil.ROLE_CALLER_CUSTOMER,"纳税人");
		return map;
	}
	public static void main(String[] args) {
		String s=SysParaUtil.ROLE_BIZ_EXPORT;
		System.out.println(s);
	}
    /**
     * Java中提供了一个java.util.Properties工具类
     * 使用Properties类您可以方便的从一个.properties属性文件中读取设置参数
     * @param PROPERTIES_FILE
     * @return
     */
	public static Properties getProperties(String PROPERTIES_FILE) {
     Properties props = new Properties();
     try {
         URL rr=SysParaUtil.class.getResource(PROPERTIES_FILE);
         props.load(rr.openStream());
     } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
       return props;
    }
	
	public static Properties getSysProperties() {
		String pathFile="/properties/SysParaSet.properties";
	    return getProperties(pathFile);
	}
	
	/**
	 * 根据key，访问key所对应的数值
	 * @param key
	 * @return
	 */
	public static String  getProParaValue(String key){
		String value="";
		if(pro==null){
			init();
			System.out.println("访问系统配置参数....");
		}
		value=(String)pro.get(key);
		if(value!=null){
			value=value.trim();
		}
		return value;
	}
	
	 /**
	   * 取得系统配置参数的值
	   * @param paramName
	   * @return
	   */
	public static String getSysParamValue(String paramName)
	{
	  String sql="select t.param_value from tb_ips_sys_set t where t.param_name='"+paramName+"'";
	  String value=EZDbUtil.getOnlyStringValue(sql);
	  return value;
	}
}
