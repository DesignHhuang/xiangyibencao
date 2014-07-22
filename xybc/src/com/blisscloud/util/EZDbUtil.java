/**
 * 
 */
package com.blisscloud.util;

import java.io.*;
import java.sql.*;
import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;


import com.blisscloud.common.StringBean;
import com.blisscloud.hibernate.EZHibernateUtil;

import jxl.Sheet;
import jxl.Workbook;

/**
 * @author Administrator
 * Ken.guo
 */
public class EZDbUtil {
	private static Log log = LogFactory.getLog(EZDbUtil.class);
	public EZDbUtil() {
	}

	public static String getCountSql(String querySql){
		String countSql="select count(0) as recordNum from ("+
															querySql+
														   ") as mycount";
		//int start=querySql.indexOf("from");
		//countSql=countSql+" "+querySql.substring(start);
		return  countSql;
	}
	
	/**
	 * 锟斤拷锟揭伙拷锟斤拷值锟皆的讹拷锟斤拷锟叫�锟斤拷一锟斤拷锟斤拷锟斤拷锟斤拷为Key锟斤拷锟节讹拷锟斤拷锟街讹拷锟斤拷锟斤拷value
	 * @param sql
	 * @return
	 */
	public static List getAttList(String sql){
		List list=null;
		 Connection con=null;
		 Statement pt=null;
		 ResultSet rs=null;
		 Session s=EZHibernateUtil.currentSession();
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
				EZDbUtil.closeInfo(con,pt,rs);
				EZHibernateUtil.closeSession();
			}
			return list;

	}
	
   /**
	*锟斤拷取锟斤拷锟斤拷锟斤拷锟皆碉拷锟叫憋拷 
	*/
   public static List getSingleAttList(String sql){
		List list=null;
		 Connection con=null;
		 Statement pt=null;
		 ResultSet rs=null;
		 Session s=EZHibernateUtil.currentSession();
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
				EZDbUtil.closeInfo(con,pt,rs);
				EZHibernateUtil.closeSession();
			}
			return list;
	}
	
    		
	 /**
	  *取锟斤拷一锟斤拷锟斤拷锟斤拷锟叫憋拷 
	  */
	 public static List getPropertyList(String sql){
			 List list=null;
			 Connection con=null;
			 Statement st=null;
			 ResultSet rs=null;
			 Session session=EZHibernateUtil.currentSession();
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
					EZDbUtil.closeInfo(con,st,rs);
					EZHibernateUtil.closeSession();
				}
				return list;
		}
	 
	 /**
	  * 锟斤拷锟絪ql锟斤拷取锟斤拷一锟斤拷锟街凤拷锟斤拷锟斤拷锟斤拷斜锟�
	  * @param sql
	  * @return
	  */
	 public static List getStringArrayList(String sql){
		 List list=null;
		 Connection con=null;
		 Statement st=null;
		 ResultSet rs=null;
		 Session session=EZHibernateUtil.currentSession();
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
				EZDbUtil.closeInfo(con,st,rs);
				EZHibernateUtil.closeSession();
			}
			return list;
	}
	 
	 /**
	  * 锟斤拷锟絪ql锟斤拷取锟斤拷一锟斤拷锟街凤拷锟斤拷锟斤拷锟斤拷斜锟�
	  * @param sql
	  * @return
	  */
	 public static String[] getSingleArrayList(String sql){
		 String[] s=null;
		 Connection con=null;
		 Statement st=null;
		 ResultSet rs=null;
		 Session session=EZHibernateUtil.currentSession();
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
				EZDbUtil.closeInfo(con,st,rs);
				EZHibernateUtil.closeSession();
			}
			return s;
	}
	 

	 /**
      * 执锟斤拷一锟斤拷sql锟斤拷锟�.
      * @param sql
      * @return
      */
     public static  boolean  runSql(String sql){
       boolean ok=false;
       Connection con=null;
       Statement st=null;
       Session session=EZHibernateUtil.currentSession();
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
          EZHibernateUtil.closeSession();
        }
       return ok;
     }
     
     /**
      * 通锟斤拷JDBCl锟斤拷锟斤拷菘锟�
      * @param url
      * @param usr
      * @param pwd
      * @param drv
      * @return
      */
     public static Connection getConnByJDBC(String url,String usr,String pwd,String drv){
 		Connection conn = null;
 		//锟斤拷取锟斤拷锟斤拷
 		try {
 			Class.forName(drv);
 			conn = DriverManager.getConnection(url, usr, pwd);
 		} catch (Exception e) {
 			log.trace("usr="+usr+";pwd="+pwd+";drv="+drv,e);
 		} 
 		return conn;
 	}
     
	    /**
		 * 锟截憋拷锟斤拷菘锟斤拷锟斤拷
		 * @param con
		 * @param st
		 * @param rs
		 */
		public static void closeInfo(Connection con,Statement st,ResultSet rs){
			/* disable by jeton.dong 
			try {if(rs!=null){rs.close();rs=null;}} catch (SQLException e) {e.printStackTrace();}
			try {if(st!=null){st.close();st=null;}} catch (SQLException e) {e.printStackTrace();}
			try {if(con!=null){con.close();con=null;}} catch (SQLException e) {e.printStackTrace();}
			*/
		}

		/**
		 * 锟截憋拷锟斤拷菘锟斤拷锟斤拷
		 * @param con
		 * @param st
	     */
		public static void closeInfo(Connection con,Statement st){
			/* disable by jeton.dong 
			try {if(st!=null){st.close();st=null;}} catch (SQLException e) {e.printStackTrace();}
			try {if(con!=null){con.close();con=null;}} catch (SQLException e) {e.printStackTrace();}
			*/
		}
		/**
		 * 锟截憋拷锟斤拷菘锟斤拷锟斤拷
		 * @param rs
		
	     */
		public static void closeInfo(ResultSet rs){
			/* disable by jeton.dong 
			try {if(rs!=null){rs.close();rs=null;}} catch (SQLException e) {e.printStackTrace();}
			*/
		}
		 /**
		  * 锟斤拷锟絪ql锟斤拷取锟斤拷一锟斤拷锟街凤拷锟斤拷锟斤拷锟斤拷斜锟�
		  * @param sql
		  * @return
		  */
		 public static String getOnlyStringValue(String sql){
			String str="";
			Connection con=null;
			 Statement st=null;
			 ResultSet rs=null;
			 Session session=EZHibernateUtil.currentSession();
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
					EZDbUtil.closeInfo(con,st,rs);
					EZHibernateUtil.closeSession();
				}
            return str;
		}
		 
		 
		 /**
		   * 
		   * @param sSQL
		   * @return
		   */
		  public static Map getMapQuery(String sSQL){
			  	Connection con=null;
			    Statement st=null;
			    ResultSet rs=null;
			    Session session=EZHibernateUtil.currentSession();
			  	Map map = new HashMap();
				  try{
					  con=session.connection();
					  st=con.createStatement();
					  rs=st.executeQuery(sSQL);
						if(rs!=null){
							while(rs.next()){
				        		map.put(rs.getObject(1),rs.getObject(2));
				        	}
						}
			      }catch(SQLException e){
			    	  e.printStackTrace();
					  System.out.println("getMapQuery==>"+sSQL);
			      }finally{
			    	  EZDbUtil.closeInfo(con,st,rs);
				      EZHibernateUtil.closeSession();
			      }
			    return map;
			  }
		  
		  /**
		   * 
		   * @param sSQL
		   * @return
		   */
		  public static LinkedHashMap getLinkMapQuery(String sSQL){
			  	Connection con=null;
			    Statement st=null;
			    ResultSet rs=null;
			    Session session=EZHibernateUtil.currentSession();
			    LinkedHashMap map = new LinkedHashMap();
				  try{
					  con=session.connection();
					  st=con.createStatement();
					  rs=st.executeQuery(sSQL);
						if(rs!=null){
							while(rs.next()){
				        		map.put(rs.getObject(1),rs.getObject(2));
				        	}
						}
			      }catch(SQLException e){
			    	  e.printStackTrace();
					  System.out.println("getMapQuery==>"+sSQL);
			      }finally{
			    	  EZDbUtil.closeInfo(con,st,rs);
				      EZHibernateUtil.closeSession();
			      }
			    return map;
			  }
		  
		 
		    /**
			 * 锟斤拷锟絪eq锟斤拷锟饺★拷锟斤拷碌锟絪eq锟斤拷
			 * @param seqName
			 * @return
			 */
		    public static  String   getLastNumBySeqName(String seqName){
				String lastSeq="";
				Connection con=null;
			    Statement st=null;
			    ResultSet rs=null;
			    String sql="select nextval('"+seqName+"') ";
			    Session session=null;
			    try {
			      session=EZHibernateUtil.currentSession();
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
			    	EZDbUtil.closeInfo(con,st,rs);
			        EZHibernateUtil.closeSession();
				}
			   return lastSeq;
			}
		    
		    /**
			 * 锟斤拷锟絪eq锟斤拷锟饺★拷锟斤拷碌锟絪eq锟斤拷
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
			      session=EZHibernateUtil.currentSession();
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
			    	EZDbUtil.closeInfo(con,st,rs);
			        EZHibernateUtil.closeSession();
			  }
			   return rtv;
			}
		   
		    /**
		     * 锟斤拷莸锟铰硷拷锟絣id锟矫碉拷锟斤拷前锟斤拷录锟斤拷锟斤拷锟斤拷锟接诧拷锟斤拷id
		     * @param lEmployeeID
		     * @return 锟斤拷","锟街革拷锟斤拷锟斤拷锟斤拷硬锟斤拷锟絠d
		     */
			public static  String getChildDepIDs(String lEmployeeID){
				String rtv="";	//锟斤拷锟斤拷值
				//锟斤拷锟絣EmployeeID锟矫碉拷锟斤拷锟斤拷锟斤拷锟斤拷锟铰碉拷锟斤拷锟斤拷锟接诧拷锟斤拷ID
				String tmpSQL="select emp.ldepartmentid from tb000000employee emp "+
						   		"where emp.lid="+lEmployeeID;
				String sql="select t.lid "+
						   "from tb000000department t "+
						   "where t.lid!=("+tmpSQL+") "+
						   "start with t.lid=(" 
						   		+tmpSQL+
						   ") "+
						   "connect by prior t.lid=t.lparentid";	
				Connection con=null;
			    Statement st=null;
			    ResultSet rs=null;
			    Session session=null;
			    try {
			      session=EZHibernateUtil.currentSession();
			      con =session.connection();
			      st = con.createStatement();
			      rs=st.executeQuery(sql);
			      if(rs!=null){
			    	  StringBuffer buffer=new StringBuffer();
			    	  String value="";
						while(rs.next()){
							value=rs.getString(1); 
							buffer.append(value+",");
						}
						rtv=buffer.toString();
						if(rtv!=null&&!"".equals(rtv)){
							if(rtv.endsWith(",")){
								rtv=rtv.substring(0,rtv.length()-",".length());
							}
						}
						
			      }
			     }catch (SQLException ex) {
			    ex.printStackTrace();
			    }finally {
			    	EZDbUtil.closeInfo(con,st,rs);
			        EZHibernateUtil.closeSession();
			  }
			   return rtv;
			}
			
			/**
		     * 锟斤拷莸锟铰硷拷锟絣id锟矫碉拷锟斤拷前锟斤拷录锟斤拷锟斤拷锟斤拷锟接诧拷锟斤拷员锟斤拷id
		     * @param lEmployeeID
		     * @return 锟斤拷","锟街革拷锟斤拷锟斤拷锟斤拷硬锟斤拷锟皆憋拷锟絠d
		     */
			public static  String getChildEmpIDs(String lEmployeeID){
				String rtv="";	//锟斤拷锟斤拷值
				String depIDs = getChildDepIDs(lEmployeeID);
				//锟斤拷没锟斤拷锟接诧拷锟斤拷只锟斤拷询锟皆硷拷锟斤拷锟斤拷锟�
				if(StringUtil.isNullOrEmpty(depIDs)){
					rtv=lEmployeeID;
					return rtv;
				}
				//锟斤拷锟絣EmployeeID锟矫碉拷锟斤拷锟斤拷锟斤拷锟斤拷锟铰碉拷锟斤拷锟斤拷员锟斤拷ID
				String sql="select emp.lid from tb000000employee emp "+
						   "where emp.ldepartmentid in ("+depIDs+")";	
				Connection con=null;
			    Statement st=null;
			    ResultSet rs=null;
			    Session session=null;
			    try {
			      session=EZHibernateUtil.currentSession();
			      con =session.connection();
			      st = con.createStatement();
			      rs=st.executeQuery(sql);
			      if(rs!=null){
			    	  StringBuffer buffer=new StringBuffer();
			    	  String value="";
						while(rs.next()){
							value=rs.getString(1); 
							buffer.append(value+",");
						}
						rtv=buffer.toString();
						if(rtv!=null&&!"".equals(rtv)){
							if(rtv.endsWith(",")){
								rtv=rtv.substring(0,rtv.length()-",".length());
							}
						}
						
			      }
			     }catch (SQLException ex) {
			    ex.printStackTrace();
			    }finally {
			    	EZDbUtil.closeInfo(con,st,rs);
			        EZHibernateUtil.closeSession();
			  }
			   //锟斤拷询锟斤拷锟斤拷锟斤拷锟斤拷员锟斤拷锟斤拷锟斤拷前锟介长锟斤拷锟斤拷锟斤拷锟斤拷息
			   if(rtv!=null&&!"".equals(rtv)){
				   rtv +=","+lEmployeeID;
			   } 
			   return rtv;
			}
			/**
			 * 删锟斤拷ezqform_user锟斤拷锟斤拷锟截革拷锟结交锟斤拷锟绞撅拷锟斤拷lclientid
			 *
			 */
			public static void delQFormMultiRecord(){
				//1锟斤拷锟斤拷询锟角凤拷锟斤拷锟截革拷锟结交锟斤拷锟绞撅拷
				String hasMultiSQL = "select b.lclientid from ( "+
										 	"select t.lclientid,count(t.lclientid) as myrec from ezqform_user t "+
										 	"where t.nisdelete=0 "+
										 	"group by t.lclientid "+
										") b "+
                                     "where b.myrec>1";
				List MultiNumList = getStringArrayList(hasMultiSQL);
				//2锟斤拷删锟斤拷锟斤拷锟斤拷锟结交锟斤拷锟绞撅拷锟斤拷息,锟斤拷锟斤拷锟斤拷锟斤拷峤伙拷锟斤拷示锟斤拷锟絣clientid
				if(MultiNumList!=null&&MultiNumList.size()>0){
				      for(int i=0;i<MultiNumList.size();i++){
				    	  Object[] s =(Object[])MultiNumList.get(i);
				    	  String lid = String.valueOf(s[0]); //锟斤拷锟斤拷锟斤拷锟絣id
				    	  //锟斤拷询锟斤拷锟斤拷锟结交锟绞撅拷strID
				    	  String strIDSQL="select strid from ezqform_user equ where equ.nisdelete=0 and equ.lclientid="+lid;
				    	  String strID = getOnlyStringValue(strIDSQL);
				    	  //伪删锟斤拷锟斤拷锟斤拷锟结交锟斤拷锟绞撅拷strID,锟斤拷锟斤拷ezqform_user锟斤拷nisdelete为1
				    	  String delIDSQL="update ezqform_user equ "+
				    	  					"set equ.nisdelete=1 "+
				    	  				   "where equ.strid="+strID;
				    	  //System.out.println(""+delIDSQL);
				    	  runSql(delIDSQL);
				      }
				}	
			}
			/**
			 * 锟斤拷莸锟角帮拷锟较疞ID锟斤拷取锟斤拷席锟斤拷锟斤拷
			 * @param lID
			 * @return
			 */
			
			public static String getAgentName(String lID){
				//lID为锟斤拷时
				if(StringUtil.isNullOrEmpty(lID)){
					return "";
				}
				String agentName = "";
				String sql = "select emp.strname from tb000000employee emp where emp.lid="+lID;
				agentName = getOnlyStringValue(sql);
				return agentName;
			}

			public static void main(String[] args) 
			{
//				String pathID = "1242876451.40";
//				String tmp = getSumPath(pathID);
//				System.out.println("tmp--->"+tmp);
				
				String pathID = "140";
				String tmp = getSumPathById(pathID);
				System.out.println("tmp--->"+tmp);
			}
			
			/**
			   * 循环读取Excel文件内容将其导入数据库中
			   * @param fileName 循环读取Excel文件
			   * @throws SQLException 
			   */
				public static int importToDB(String fileName,String campaignListID) throws SQLException{
					int procFlag = 0;
					List list=null;
					Connection con=null;
					Statement pt=null;
					ResultSet rs=null;
					PreparedStatement pstmt  = null;		//PreparedStatement
					Session s=EZHibernateUtil.currentSession();
					//1、Excel文件路径
					//System.out.println("---->"+StringTools.getTimeDir());
					String excelFilePath = StringTools.getTimeDir();			//upload path
					String strSQL = "insert into tb_ips_autocampaignlist(seqid,telephone,campaignlistid) "+
									" values(nextval('SEQ_TB_IPS_AUTOCAMPAIGNLIST'),?,"+campaignListID+")";
					
					//得到excel文件内容
					Workbook wb = null;
					InputStream is = null;
					Sheet sheet = null;
					String content = "";
					try{
						con=s.connection();
						con.setAutoCommit(false);
						content = "";
						is = new FileInputStream(excelFilePath+fileName);
						wb = Workbook.getWorkbook(is);				//Workbook
						int sheetNums = wb.getNumberOfSheets();		//Workbook中sheet数量
						String[] sheetNames = wb.getSheetNames();	//Workbook中sheet名称
						for(int k = 0;k < sheetNums;k++)
						{
							int tmp = k+1;
							pstmt = con.prepareStatement(strSQL);
							
							sheet = wb.getSheet(k);						//sheet 下标从0开始
							int sheetRows = sheet.getRows();			//sheet总行数
							int sheetColumns = sheet.getColumns();		//sheet总列数
							
							if(sheetRows>1){
								log.info("开始更新"+sheetNames[k]+"......");
								//从第二行开始读数据(因第一行为标题)
								for(int i=1;i<sheetRows ;i++){	
									for(int j=0;j<sheetColumns ;j++){					  				
										content = sheet.getCell(j,i).getContents();
										if(StringUtil.isNullOrEmpty(content))
										{
											content = "";
										}else
										{
											content = content.trim();
										}
										//将excel单元格中的内容插入至数据库中 
										pstmt.setObject(j+1,content);
									}
									//pstmt.addBatch();	//加入至批处理
									pstmt.execute();
								}
									//pstmt.executeBatch();	//执行pstmt	
									con.commit();
									log.info(sheetRows-1+"条数据被导入!");
							}//endif	
						}
					}catch(Exception e){
						procFlag=10;
						log.error(e.toString());
					}finally{
						try{
							if(wb != null)wb.close();
							if(is != null)is.close();
							EZDbUtil.closeInfo(con,pt,rs);
							EZHibernateUtil.closeSession();
						}catch(Exception e){
							log.error(e.toString());
						}
					}
					return procFlag;
				}
				
				
				/**
				   * 循环读取Excel文件内容将其导入数据库中
				   * @param fileName 循环读取Excel文件
				   * @throws SQLException 
				   */
					public static int importToDBForWin(String fileName,String campaignListID) throws SQLException{
						int procFlag = 0;
						List list=null;
						Connection con=null;
						Statement pt=null;
						ResultSet rs=null;
						PreparedStatement pstmt  = null;		//PreparedStatement
						Session s=EZHibernateUtil.currentSession();
						//1、Excel文件路径
						//System.out.println("---->"+StringTools.getTimeDir());
						String excelFilePath = StringTools.getTimeDir();			//upload path
						String strSQL = "insert into tb_ips_autocampaignlist(seqid,telephone,campaignlistid) "+
										" values(nextval('SEQ_TB_IPS_AUTOCAMPAIGNLIST'),?,"+campaignListID+")";
						
						//得到excel文件内容
						Workbook wb = null;
						InputStream is = null;
						Sheet sheet = null;
						String content = "";
						try{
							con=s.connection();
							con.setAutoCommit(false);
							content = "";
							is = new FileInputStream(excelFilePath+fileName);
							wb = Workbook.getWorkbook(is);				//Workbook
							int sheetNums = wb.getNumberOfSheets();		//Workbook中sheet数量
							String[] sheetNames = wb.getSheetNames();	//Workbook中sheet名称
							for(int k = 0;k < sheetNums;k++)
							{
								int tmp = k+1;
								pstmt = con.prepareStatement(strSQL);
								
								sheet = wb.getSheet(k);						//sheet 下标从0开始
								int sheetRows = sheet.getRows();			//sheet总行数
								int sheetColumns = sheet.getColumns();		//sheet总列数
								
								if(sheetRows>1){
									log.info("开始更新"+sheetNames[k]+"......");
									//从第二行开始读数据(因第一行为标题)
									for(int i=1;i<sheetRows ;i++){	
										for(int j=0;j<sheetColumns ;j++){					  				
											content = sheet.getCell(j,i).getContents();
											if(StringUtil.isNullOrEmpty(content))
											{
												content = "";
											}else
											{
												content = content.trim();
											}
											//将excel单元格中的内容插入至数据库中 
											pstmt.setObject(j+1,content);
										}
										pstmt.addBatch();	//加入至批处理
									}
										pstmt.executeBatch();	//执行pstmt	
										con.commit();
										log.info(sheetRows-1+"条数据被导入!");
								}//endif	
							}
						}catch(Exception e){
							procFlag=10;
							log.error(e.toString());
						}finally{
							try{
								if(wb != null)wb.close();
								if(is != null)is.close();
								EZDbUtil.closeInfo(con,pt,rs);
								EZHibernateUtil.closeSession();
							}catch(Exception e){
								log.error(e.toString());
							}
						}
						return procFlag;
					}
					/**
					 * 根据interactionID取得联络事项的路径
					 * @param interactionID
					 * @return
					 */
					public static String getSumPath(Object interactionID)
					{
						String mySumPath = "";
						String pathSQL = "";
						if(interactionID == null)
						{
							return mySumPath;
						}else
						{
							//联络事项
							String reasonTypeSQL = " select ltypeid,strname               "+
												   " from tb000000summary sm              "+
												   " left join tb000000interview intv     "+
												   "    on intv.strreasontype::int=sm.lid "+
												   " left join tb000000contact cont       "+
												   "	on cont.lid=intv.lcontactid       "+
												   " where intv.strreasontype is not null "+
												   "      and intv.lcontactid>0           "+
												   "      and cont.strinteractionid='"+interactionID+"'";
							List reasonList = EZDbUtil.getStringArrayList(reasonTypeSQL);
							for(int i = 0;i< reasonList.size();i++)
							{
								Object[] s =(Object[])reasonList.get(i);
								if(s!=null)
								{
									String reasonType =	String.valueOf(s[0]);
									String reasonName = String.valueOf(s[1]);
									pathSQL = "select getMySummaryPath('"+reasonType+"','->')";
									String myPath = EZDbUtil.getOnlyStringValue(pathSQL);
									if(!StringUtil.isNullOrEmpty(myPath))
									{
										mySumPath += myPath+reasonName+";";
									}
								}
							}
						}
						return mySumPath;
					}
					/**
					 * 根据电话小结的末节点取得联络事项的路径
					 * @param interactionID
					 * @return
					 */
					public static String getSumPathById(Object summaryId)
					{
						String mySumPath = "";
						String pathSQL = "";
						if(summaryId == null)
						{
							return mySumPath;
						}else
						{
							//联络事项
							String reasonTypeSQL = " select ltypeid,strname "+
												   " from tb000000summary   "+
												   " where lid ='"+summaryId+"'";
							List reasonList = EZDbUtil.getStringArrayList(reasonTypeSQL);
							for(int i = 0;i< reasonList.size();i++)
							{
								Object[] s =(Object[])reasonList.get(i);
								if(s!=null)
								{
									String reasonType =	String.valueOf(s[0]);
									String reasonName = String.valueOf(s[1]);
									pathSQL = "select getMySummaryPath('"+reasonType+"','->')";
									String myPath = EZDbUtil.getOnlyStringValue(pathSQL);
									if(!StringUtil.isNullOrEmpty(myPath))
									{
										mySumPath += myPath+reasonName;
									}
								}
							}
						}
						return mySumPath;
					}
					
					/**
					 * 根据产品ID取得产品的路径
					 * @param interactionID
					 * @return
					 */
					public static String getProductPathById(Object productId)
					{
						String myProductPath = "";
						String pathSQL = "";
						if(productId == null)
						{
							return myProductPath;
						}else
						{
							//产品名称
							String productTypeSQL = " select ltypeid,strname "+
												    " from tb000000product   "+
												    " where lid ='"+productId+"'";
							List productTypeList = EZDbUtil.getStringArrayList(productTypeSQL);
							for(int i = 0;i< productTypeList.size();i++)
							{
								Object[] s =(Object[])productTypeList.get(i);
								if(s!=null)
								{
									String productType =	String.valueOf(s[0]);
									String productName = String.valueOf(s[1]);
									pathSQL = "select getMyProductPath('"+productType+"','->')";
									String myPath = EZDbUtil.getOnlyStringValue(pathSQL);
									if(!StringUtil.isNullOrEmpty(myPath))
									{
										myProductPath += myPath+productName;
									}
								}
							}
						}
						return myProductPath;
					}
					
}
