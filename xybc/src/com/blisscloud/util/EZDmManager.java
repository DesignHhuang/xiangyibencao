package com.blisscloud.util;

import java.util.*;
import java.sql.*;


import org.hibernate.Session;

import com.blisscloud.common.Constants;
import com.blisscloud.common.StringBean;
import com.blisscloud.hibernate.EZHibernateUtil;

/**
 * <p>Title:数据字典管理工具</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class EZDmManager {
	public static final String  quest_type = "quest_type"; 
	public static final String  direct_flag = "direct_flag"; 
	public static final String  task_status = "task_status"; 
	public static final String  charset_type = "charset_type"; 
	public static final String  is_inner = "is_inner"; 
	public static final String  bill_status = "bill_status"; 
	public static final String  area_code = "area_code"; 
	public static final String  boolean_type = "boolean_type"; 
	public static final String  activities_type = "activities_type";
	public static final String  em_level = "em_level";
	public static final String  km_file_type = "km_file_type";  
	
	public static final String  CALL_TYPE = "bill_type"; 
	public static final String  BIZ_TYPE = "biz_type";            
	
	public EZDmManager() {
	
	}

  public static String getZtbjOptions(String chuz){
	  String sql = "select dz.ztbj_code,dz.ztbj_name from dm_ztbj dz";
	  return EZDmManager.getOptionsBySql(sql,chuz);	  
  }

  public static  Map getSummryMap() {
	 	 String sql="select seq_id,name from dm_summary";
	     return getMapBySql(sql);
  }

  	/**
	 * 取得下拉选项的Options列表
	 * sql:select call_code,call_name from dm_call  order by call_order
	 * 只有两个字段
	 * @return
	 */
	public static  String getOptionsBySql(String sql){
  			return getOptionsBySql(sql,"");
    }

  /**
   * 获得某个选定key的selectedOption String
   * @param typeKey
   * @param selectKey
   * @return
   */
  public static  String getSelectedOption(Hashtable myTable,String selectKey){
	    StringBuffer buffer=null;
	    if(myTable!=null){
	     Enumeration enum2=myTable.keys();
	     String key="";
	     String name="";
	      buffer=new StringBuffer();
	     while(enum2.hasMoreElements()){
	    	 key=(String)enum2.nextElement();
	    	 name=(String)myTable.get(key);
	    	 String s="";
	    	 if(key.equals(selectKey)){
	    		 s="selected";
	    	 }
	          buffer.append("<option value=\""+key+"\"   "+s+">"+name+"</option> \n");
	      }
	    }
	    return  buffer.toString();
  }

  /**
   * 获得某个选定key的selectedOption String
   * @param typeKey
   * @param selectKey
   * @return
   */
  public static  String getSelectedOption(List list,String selectKey){
	    StringBuffer buffer=new StringBuffer();
	    if(list!=null){
	     Iterator enum2=list.iterator();
	     StringBean info=null;
	     while(enum2.hasNext()){
	    	 info=(StringBean)enum2.next();
	    	 String s="";
	    	 if(info.getKey().equals(selectKey)){
	    		 s="selected";
	    	 }
	          buffer.append("<option value=\""+info.getKey()+"\"   "+s+">"+info.getValue()+"</option> \n");
	      }
	    }
	    return  buffer.toString();
  }

  /**
   * 获得某个选定key的selectedOption String
   * @param typeKey
   * @param selectKey
   * @return
   */
  public static  String getSelectedOptionByArray(List list,String selectKey){
	    StringBuffer buffer=new StringBuffer();
	    if(list!=null){
	     Iterator enum2=list.iterator();
	     Object[] info=null;
	     while(enum2.hasNext()){
	    	 info=(Object[])enum2.next();
	    	 String s="";
	    	 if(info[0].equals(selectKey)){
	    		 s="selected";
	    	 }
	         buffer.append("<option value=\""+info[0]+"\"   "+s+">"+info[1]+"</option> \n");
	      }
	    }
	    return  buffer.toString();
  }


    /**
 	 * 取得话务类型Options
 	 * @return
 	 */
 	public static  String getCallTypeOptions(String selCalltype){
 		return EZDmManager.getOptionByType(EZDmManager.CALL_TYPE,selCalltype);
    }
 	
 	/**
 	 * 取得话务类型Options
 	 * @return
 	 */
 	public static  String getOptionByType(String mainType,String selKey){
    	String sql="select t.out_code,t.name from tb_accttype t where t.maintype='"+mainType+"' order by t.ordernum";
    	return getOptionsBySql(sql,selKey);
    }
 	
 	
 	/**
 	 * 取得某个人所在的地区下的人员列表
 	 * @param local_no
 	 * @param selUser
 	 * @return
 	 */
 	public static String getUserOptions(String local_no,String selUser){
		String sql="";
		StringBuffer buffer=new StringBuffer();
		buffer.append("select t.user_id,t.name from user_account t  where local_no='"+local_no+"' and t.is_Use='"+Constants.USE_TRUE+"'");
		sql=buffer.toString();
		return getOptionsBySql(sql,selUser);
 	}
 	
 	/**
 	 * 取得某个人所在的地区下的人员列表(监听打分模块)
 	 * @param selUser
 	 * @return
 	 */
 	public static String getUserListener(String localNo,String selUser ){
 		
 		if(selUser!=null)selUser=selUser.trim();
		String sql="";
		String roles=SysParaUtil.ROLE_FORE+","+SysParaUtil.ROLE_BACK;
		StringBuffer buffer=new StringBuffer();
		buffer.append("select distinct b.user_id, b.name  from user_account b left join v_employee t on b.user_id = t.use_id where b.local_no = '"+localNo+"'  and t.roleid in ("+roles+")");
		sql=buffer.toString();
		System.out.println(sql);
		return getOptionsBySql(sql,selUser);
 	}
 
 	/**
 	 * 取得地区Options
 	 * @return
 	 */
 	public static String getAreaOptions(String selArea){
 		return EZDmManager.getOptionByType("area_code",selArea);
 	}

 	/**
 	 * 取得部门类型Options
 	 */
 	public static String getDepartmentOptions(String selDept){
 		String PRODUCT_DB_USER=SysParaUtil.PRODUCT_DB_USER;
 		String sql = "select tbdept.lid,tbdept.strname from "+PRODUCT_DB_USER+".tb000000department tbdept";
 		return getOptionsBySql(sql,selDept);
 	}

 	/**
 	 * 取得话务类型Options
 	 * @return
 	 */
 	public static  String getBizTypeOptions(String selBizType){
 		return EZDmManager.getOptionByType(EZDmManager.BIZ_TYPE,selBizType);
    }
 	
 	/**
 	 * 取得话务类型Options
 	 * @return
 	 */
 	public static  String getBizTypeOptions(){
    	String sql="select biz_code,biz_name from dm_biz  order by biz_order";
 	 	return getOptionsBySql(sql);
    }
 	/**
 	 * 取得某个地区下的业务类型Options
 	 * @return
 	 */
 	public static  String getBizOptionsByLocal(String localNo,String selKey){
 		if(localNo==null||"".equals(localNo)||"null".equals(localNo)){
 			return "";
 		}
    	String sql2="select a.out_code,a.name from tb_accttype a "+
	  	"left join set_biz_permit t on a.ordernum=t.biz_type "+
	  	"where t.local_no='"+localNo.trim()+"' and a.maintype='biz_type' order by a.ordernum";
      	String optionList=EZDmManager.getOptionsBySql(sql2,selKey);
     	return optionList;
    }
 	/**
 	 * 取得话务类型Options
 	 * @return
 	 */
 	public static  String getCallTypeOptions(){
 		return EZDmManager.getOptionByType(EZDmManager.CALL_TYPE,"");
 	 }
 	 /**
 	 * 取得下拉选项的Options列表
 	 * sql:select call_code,call_name from dm_call  order by call_order
 	 * 只有两个字段
 	 * @return
 	 */
 	public static  String getOptionsBySql(String sql,String selKey){
    	 StringBuffer buffer=new StringBuffer();
 		 Connection con=null;
 		 Statement pt=null;
 		 ResultSet rs=null;
 		 Session session=EZHibernateUtil.currentSession();
 		 try {
 			con=session.connection();
 			pt=con.createStatement();
 			rs=pt.executeQuery(sql);
 				if(rs!=null){
 					String key="";
 					while(rs.next()){
 						key=rs.getString(1);
 						String  sel="";
 						if(key!=null){
 							if(key.equals(selKey)){
 								sel="selected";
 							}
 						}
 					 buffer.append("<option value='"+rs.getString(1)+"'   "+sel+"  >"+rs.getString(2)+"</option> \n");
 					}
 				}

 			} catch (SQLException e) {
 				System.out.println("getOptionsBySql==>"+sql);
 				e.printStackTrace();
 			}finally{
 				EZDbUtil.closeInfo(con,pt,rs);
 				EZHibernateUtil.closeSession();
 			}
 	  	return buffer.toString();
    }

 	/**
 	 * 取得数据字典对照表
 	 * @param sql
 	 * @return
 	 */
 	public static  Map getDmMap(String sql) {
 		return getMapBySql(sql);
 	 }
 	/**
 	 * 取得数据字典对照表
 	 * @param sql
 	 * @return
 	 */
 	public static  Map getDmMapByType(String type) {
 		String sql="select t.out_code,t.name from tb_accttype t where t.maintype='"+type+"'";
 		return getMapBySql(sql);
 	 }
 	
 	/**
 	 * 取得数据字典对照表
 	 * @param sql
 	 * @return
 	 */
 	public static  Map getMapBySql(String sql) {
 		  Map map=new HashMap();
 		  Connection con=null;
 		  Statement pt=null;
 		  ResultSet rs=null;
 		  Session s=EZHibernateUtil.currentSession();
 			 try {
 				con=s.connection();
 				pt=con.createStatement();
 				rs=pt.executeQuery(sql);
 					if(rs!=null){
 						String key="";
 						while(rs.next()){
 							key=rs.getString(1);
 							if(key!=null){
 								key=key.trim();
 								map.put(key,rs.getString(2));
 							}
 						}
 					}
 				} catch (SQLException e) {
 					System.out.println("getMapBySql==>"+sql);
 					e.printStackTrace();
 				}finally{
 					EZDbUtil.closeInfo(con,pt,rs);
 					EZHibernateUtil.closeSession();
 				}
 		  return  map;
 	  }
	public static String getAreaName(String selArea){
 		return getLabelName("area_code",selArea);
 	}
	public static String getLabelName(String mainType,String selArea){
		String sql="select t.name from tb_accttype t where t.maintype='"+mainType+"'and t.out_code='"+selArea+"' order by t.ordernum";
		String str=EZDbUtil.getOnlyStringValue(sql);
		return str;
 	}
	public static String getZtbjString(String chuz){
		  if("-".equals(chuz)||chuz==null||"".equals(chuz)){
			  chuz="3";
		  }
		  String sql = "select dz.ztbj_name from dm_ztbj dz where dz.ztbj_code='"+chuz+"'";
		  String str="";
		  List list= EZHibernateUtil.currentSession().createSQLQuery(sql).list();
		  EZHibernateUtil.closeSession();
		  if(list!=null){
			   if(list.size()==1){
				   Object obje=list.get(0);
				   if(obje!=null){
					   str= obje.toString();
				   }
			   }
		  }
		  return str;	  
	  }
	public static String getBackUser(String callId,String seluserId){
		String sql="select a.employee,d.strname from flow_task a ,flow_instances b , bill c ,"+SysParaUtil.PRODUCT_DB_USER+".tb000000employee d where a.instances_id=b.instances_id(+) and b.bill_id=c.bill_no(+) and c.call_id="+callId+" and a.employee=d.strloginid and a.roleno="+SysParaUtil.ROLE_BACK;
		return getOptionsBySql(sql,seluserId);
	}
	/**
	 * 根据话务业务找小结
	 * @param call_type
	 * @param biz_type
	 * @return
	 */
	public static String getSumBycall_biz(String call_type,String biz_type,String sum){
        String sql="select seq_id,name from dm_summary a where a.parent_id in (select b.sum_root  from set_sum_root  b where 1=1";
        if(!"full".equals(call_type)&&call_type!=null)sql=sql+" and call_type='"+call_type+"'";
        if(!"full".equals(biz_type)&&biz_type!=null)sql=sql+" and biz_type='"+biz_type+"'";
        sql=sql+")";
        if(call_type==null&&biz_type==null)return "";
	    if("full".equals(call_type)&&"full".equals(biz_type))return "";
		return getOptionsBySql(sql,sum);
	}
 	
}
