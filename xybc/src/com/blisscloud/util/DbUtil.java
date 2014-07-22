/**
 * 
 */
package com.blisscloud.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;


import com.blisscloud.common.StringBean;
import com.blisscloud.hibernate.HibernateUtil;

/**
 * @author Administrator
 * Ken.guo
 */
public class DbUtil {
	private static Log log = LogFactory.getLog(DbUtil.class);
	public DbUtil() {
	}

	public static String getCountSql(String querySql){
		String countSql="select count(0) ";
		int start=querySql.indexOf("from");
		countSql=countSql+" "+querySql.substring(start);
		return  countSql;
	}
	
	public static void main(String[] args) {
		DbUtil.getPropertyList("select t.lid,t.lcallcenterid,t.lpersonctiid from ezactor7510.tb000000agent t");
	}
  
	/**
	 * 获得一个键值对的对象列表，第一个属性作为Key，第二个字段属于value
	 * @param sql
	 * @return
	 */
	public static List getAttList(String sql){
		List list=null;
		 Connection con=null;
		 Statement pt=null;
		 ResultSet rs=null;
		 Session s=HibernateUtil.currentSession();
		 try {
			con=s.connection();
			pt=con.createStatement();
			rs=pt.executeQuery(sql);
				if(rs!=null){
					list=new ArrayList();
					while(rs.next()){
					 list.add(new StringBean(rs.getString(1),rs.getString(2)));
					}
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				DbUtil.closeInfo(con,pt,rs);
				HibernateUtil.closeSession();
			}
			return list;

	}
	
   /**
	*获取单个属性的列表 
	*/
   public static List getSingleAttList(String sql){
		List list=null;
		 Connection con=null;
		 Statement pt=null;
		 ResultSet rs=null;
		 Session s=HibernateUtil.currentSession();
		 try {
			con=s.connection();
			pt=con.createStatement();
			rs=pt.executeQuery(sql);
				if(rs!=null){
					list=new ArrayList();
					while(rs.next()){
					 list.add(rs.getString(1));
					}
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				DbUtil.closeInfo(con,pt,rs);
				HibernateUtil.closeSession();
			}
			return list;
	}
	
    		
	 /**
	  *取得一个属性列表 
	  */
	 public static List getPropertyList(String sql){
			 List list=null;
			 Connection con=null;
			 Statement st=null;
			 ResultSet rs=null;
			 Session session=HibernateUtil.currentSession();
			 try {
				con=session.connection();
				st=con.createStatement();
				rs=st.executeQuery(sql);
				if(rs!=null){
				    ResultSetMetaData meta=rs.getMetaData();
					int colCount=rs.getMetaData().getColumnCount();
						list=new ArrayList();
						Properties pro=null;
						while(rs.next()){
						 pro=new Properties();
						 for(int k=1;k<colCount+1;k++){
							 String colName=meta.getColumnName(1);
							 pro.put(colName, rs.getString(colName));
						 }
						 list.add(pro);
						}
					}

				} catch (SQLException e) {
					e.printStackTrace();
				}finally{
					DbUtil.closeInfo(con,st,rs);
					HibernateUtil.closeSession();
				}
				return list;
		}
	 
	 /**
	  * 根据sql，取得一个字符串的数组列表
	  * @param sql
	  * @return
	  */
	 public static List getStringArrayList(String sql){
		 List list=null;
		 Connection con=null;
		 Statement st=null;
		 ResultSet rs=null;
		 Session session=HibernateUtil.currentSession();
		 try {
			con=session.connection();
			st=con.createStatement();
			rs=st.executeQuery(sql);
			if(rs!=null){
			  	int colCount=rs.getMetaData().getColumnCount();
				   String[] s=null;
					list=new ArrayList();
					while(rs.next()){
						 s=new String[colCount];
						 for(int k=0;k<colCount;k++){
						 s[k]=rs.getString(k+1);
					 }
					 list.add(s);
					}
				}

			} catch (SQLException e) {
				LogUtil.log.info("errorSql==>"+sql,e);
		 	}finally{
				DbUtil.closeInfo(con,st,rs);
				HibernateUtil.closeSession();
			}
			return list;
	}
	 
	 /**
	  * 根据sql，取得一个字符串的数组列表
	  * @param sql
	  * @return
	  */
	 public static String[] getSingleArrayList(String sql){
		 String[] s=null;
		 Connection con=null;
		 Statement st=null;
		 ResultSet rs=null;
		 Session session=HibernateUtil.currentSession();
		 try {
			con=session.connection();
			st=con.createStatement();
			rs=st.executeQuery(sql);
			if(rs!=null){
			    ResultSetMetaData meta=rs.getMetaData();
				int colCount=rs.getMetaData().getColumnCount();
					if(rs.next()){
						 s=new String[colCount];
						 for(int k=0;k<colCount;k++){
							 String colType = rs.getMetaData().getColumnTypeName(k+1);
							 if("DATE".equals(colType)){
								 s[k]=DateUtil.dateToString(rs.getDate(k+1))+" "+String.valueOf(rs.getTime(k+1));
							 }else{
								 s[k]=rs.getString(k+1);
							 }
					 }
					}
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				DbUtil.closeInfo(con,st,rs);
				HibernateUtil.closeSession();
			}
			return s;
	}
	 

	 /**
      * 执行一个sql语句.
      * @param sql
      * @return
      */
     public static  boolean  runSql(String sql){
       boolean ok=false;
       Connection con=null;
       Statement st=null;
       Session session=HibernateUtil.currentSession();
       Transaction tx = session.beginTransaction();
       try {
         con =session.connection();
         st = con.createStatement();
         st.execute(sql);
         tx.commit();
         ok=true;
       }
       catch (SQLException ex) {
         ex.printStackTrace();
         tx.rollback();
         System.out.println("runSql Error==>"+sql);
       }finally{
          closeInfo(con,st);
          HibernateUtil.closeSession();
        }
       return ok;
     }
     
     /**
      * 通过JDBC连接数据库
      * @param url
      * @param usr
      * @param pwd
      * @param drv
      * @return
      */
     public static Connection getConnByJDBC(String url,String usr,String pwd,String drv){
 		Connection conn = null;
 		//获取属性
 		try {
 			Class.forName(drv);
 			conn = DriverManager.getConnection(url, usr, pwd);
 		} catch (Exception e) {
 			log.trace("usr="+usr+";pwd="+pwd+";drv="+drv,e);
 		} 
 		return conn;
 	}
     
	    /**
		 * 关闭数据库操作
		 * @param con
		 * @param st
		 * @param rs
		 */
		public static void closeInfo(Connection con,Statement st,ResultSet rs){
			try {if(rs!=null){rs.close();rs=null;}} catch (SQLException e) {e.printStackTrace();}
			try {if(st!=null){st.close();st=null;}} catch (SQLException e) {e.printStackTrace();}
			try {if(con!=null){con.close();con=null;}} catch (SQLException e) {e.printStackTrace();}
		}

		/**
		 * 关闭数据库操作
		 * @param con
		 * @param st
	     */
		public static void closeInfo(Connection con,Statement st){
			try {if(st!=null){st.close();st=null;}} catch (SQLException e) {e.printStackTrace();}
			try {if(con!=null){con.close();con=null;}} catch (SQLException e) {e.printStackTrace();}
		}
		/**
		 * 关闭数据库操作
		 * @param rs
		
	     */
		public static void closeInfo(ResultSet rs){
			try {if(rs!=null){rs.close();rs=null;}} catch (SQLException e) {e.printStackTrace();}
		}
		 /**
		  * 根据sql，取得一个字符串的数组列表
		  * @param sql
		  * @return
		  */
		 public static String getOnlyStringValue(String sql){
			String str="";
			Connection con=null;
			 Statement st=null;
			 ResultSet rs=null;
			 Session session=HibernateUtil.currentSession();
			 try {
				con=session.connection();
				st=con.createStatement();
				rs=st.executeQuery(sql);
				if(rs!=null){
						if(rs.next()){
							str=rs.getString(1);
						 }
					}
				} catch (SQLException e) {
					e.printStackTrace();
					System.out.println("getOnlyStringValueSql==>"+sql);
				}finally{
					DbUtil.closeInfo(con,st,rs);
					HibernateUtil.closeSession();
				}
            return str;
		}
		 
		    /**
			 * 根据seq名称取最新的seq号
			 * @param seqName
			 * @return
			 */
		    public static  String   getLastNumBySeqName(String seqName){
				String lastSeq="";
				Connection con=null;
			    Statement st=null;
			    ResultSet rs=null;
			    String sql="select "+seqName+".NEXTVAL from dual";
			    Session session=null;
			    try {
			      session=HibernateUtil.currentSession();
			      con =session.connection();
			      st = con.createStatement();
			      rs=st.executeQuery(sql);
			      if(rs!=null){
			    	  if(rs.next()){
			    		  lastSeq=rs.getString(1); 
			    	  }
			      }
			     
			    }catch (SQLException ex) {
			    System.out.println("first not get mySeq.");
			    }finally {
			    	DbUtil.closeInfo(con,st,rs);
			        HibernateUtil.closeSession();
				}
			   return lastSeq;
			}
		    
		    /**
			 * 根据seq名称取最新的seq号
			 * @param seqName
			 * @return
			 */
		    public static  String   getSeperateValue(String sql,String seperator){
				String rtv="";
				Connection con=null;
			    Statement st=null;
			    ResultSet rs=null;
			    Session session=null;
			    try {
			      session=HibernateUtil.currentSession();
			      con =session.connection();
			      st = con.createStatement();
			      rs=st.executeQuery(sql);
			      if(rs!=null){
			    	  StringBuffer buffer=new StringBuffer();
			    	  String value="";
						while(rs.next()){
							value=rs.getString(1); 
							buffer.append(value+seperator);
						}
						rtv=buffer.toString();
						if(rtv!=null&&!"".equals(rtv)){
							if(rtv.endsWith(seperator)){
								rtv=rtv.substring(0,rtv.length()-seperator.length());
							}
						}
						
			      }
			     
			     }catch (SQLException ex) {
			    ex.printStackTrace();
			    }finally {
			    	DbUtil.closeInfo(con,st,rs);
			        HibernateUtil.closeSession();
			  }
			   return rtv;
			}
	 
}
