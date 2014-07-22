package com.blisscloud.util;

import java.sql.*;
import java.util.*;
import java.io.Reader;
import java.io.BufferedReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class JDBCConn {
	
  private  String hostIP   			=  "";		//机器IP
  private  String hostPort 			=  "";		//数据库端口
  private  String instanse 			=  "";		//数据库实例
  private  String url      			=  "";		//数据库连接字符串
  private  String username 			=  "";		//数据库用户名
  private  String password 			=  "";		//数据库密码
  private  ResultSet rs    			= null;		//Resultset
  private  Connection conn 			= null;		//Connection
  private  Statement stmt  			= null;		//Statement
  private  List resultList 			= null;		//结果列表
  private  PreparedStatement pstmt  = null;		//PreparedStatement
  private  CallableStatement  cstmt = null;		//CallableStatement
  
  private Log JDBCConnLog = LogFactory.getLog(JDBCConn.class);	//日志类
  
  public JDBCConn() {
	  
	  //配置文件中记取配置参数
	  hostIP   =  PropertiesTools.readResourceProperty("hostIP");
      hostPort =  PropertiesTools.readResourceProperty("hostPort");
      instanse =  PropertiesTools.readResourceProperty("instanse");
      if(!StringUtil.isNullOrEmpty(hostIP)&&!StringUtil.isNullOrEmpty(hostPort)&&!StringUtil.isNullOrEmpty(instanse)){
      	url      =  "jdbc:oracle:thin:@"+hostIP+":"+hostPort+":"+instanse;
      }
      username =  PropertiesTools.readResourceProperty("username");
      password =  PropertiesTools.readResourceProperty("password");
      
	  
      /*
	  hostIP   =  "10.0.31.32";	//测试环境
	  //hostIP   =  "188.0.55.117";	//生产环境
      hostPort =  "1521";
      instanse =  "shaip";
      if(!StringUtil.isNullOrEmpty(hostIP)&&!StringUtil.isNullOrEmpty(hostPort)&&!StringUtil.isNullOrEmpty(instanse)){
      	url      =  "jdbc:oracle:thin:@"+hostIP+":"+hostPort+":"+instanse;
      }
      username =  "ezactor751ums";
      password =  "ezactor751ums";
	  */
  }
  
  private void getJDBCConnection(){
	  try {
	      //第一步：加载JDBC驱动
	      Class.forName("oracle.jdbc.driver.OracleDriver");
	      //第二步：创建数据库连接
          conn =DriverManager.getConnection(url, username, password);
	  	  } catch (Exception e) {
	  		JDBCConnLog.error(e.toString());
		}
  }
  
    
  private void colseConnection(){
	  try{
	    if(rs!=null){
           rs.close();
	    }
	  }catch(Exception e){
		  JDBCConnLog.error("Close resultset error:"+e.getMessage());
	  }finally{
		  rs = null;
	  }
	  
	  try{
		  if(stmt!=null){
			  stmt.close();
		  }
	  }catch(Exception e){
		  JDBCConnLog.error("Close statment error:"+e.getMessage());  
      }finally{
			  stmt = null;
	  }
	  
	  try{
		  if(conn!=null){
			  conn.close();
		  }
      }catch(Exception e){
    	  JDBCConnLog.error("Close connection error:"+e.toString());
	  }finally{
		   conn = null;
	  }	  
  }
  
  public List executeQuery(String sSQL){
	resultList = new ArrayList();
	getJDBCConnection();
    if(conn != null){
      try{
        stmt = conn.createStatement();
        rs = stmt.executeQuery(sSQL);
        ResultSetMetaData rsmd = rs.getMetaData();
        int rows = 0;
        if(rs != null){
        	while(rs.next()){
        		Map map = new HashMap();
        		for(int i=1; i<=rsmd.getColumnCount(); i++){
        			map.put(rsmd.getColumnName(i),rs.getObject(i));
        		}
        		resultList.add(rows,map);
        		rows++;
        	}
        }
        return resultList;
      }catch(SQLException e){
    	  JDBCConnLog.error(e.toString());
      }finally{
    	colseConnection();
      }
      
    }
    return null;
  }
  /**
   * 
   * @param sSQL
   * @return
   */
  public Map getMapQuery(String sSQL){
	  	Map map = new HashMap();
		getJDBCConnection();
	    if(conn != null){
	      try{
	        stmt = conn.createStatement();
	        rs = stmt.executeQuery(sSQL);
	        if(rs != null){
	        	while(rs.next()){
	        		map.put(rs.getObject(1),rs.getObject(2));
	        	}
	        }
	        return map;
	      }catch(SQLException e){
	    	  JDBCConnLog.error(e.toString());
	      }finally{
	    	colseConnection();
	      }
	      
	    }
	    return null;
	  }
  
  /**
	  * 根据sql，取得一个字符串的数组列表
	  * @param sql
	  * @return
	  */
	 public  String getOnlyStringValue(String sSQL){
		    String str = "";
			getJDBCConnection();
		    if(conn != null){
		      try{
		        stmt = conn.createStatement();
		        rs = stmt.executeQuery(sSQL);
		        if(rs != null){
		        	if(rs.next()){
						str=rs.getString(1);
					 }
		        }
		        return str;
		      }catch(SQLException e){
		    	  JDBCConnLog.error(e.toString());
		      }finally{
		    	colseConnection();
		      }
		      
		    }
		    return null;
	}
	 
  
  /********begin************add jeton.dong 20090312******************begin*********/
  /**
   * 执行指定的SQL语句并将结果元素以List返回
   * add by jeton.dong 20090312
   * @param sSQL 欲执行的SQL语句
   * @param isHashMap　是否将结果以hashMap进行封装 true:以hashMap进行封装 false:以Object进行封装
   * @return List 结果对象
   */
  public List executeQuery(String sSQL,boolean isHashMap){
		resultList = new ArrayList();
		getJDBCConnection();
		//是否将执行结果以hashMap进行封装
	    if(conn != null && isHashMap){
	    	resultList = executeQuery(sSQL);
	    }else{
	      try{
	        stmt = conn.createStatement();
	        rs = stmt.executeQuery(sSQL);
	        ResultSetMetaData rsmd = rs.getMetaData();
	        if(rs != null){
	        	while(rs.next()){
	        		for(int i=1; i<=rsmd.getColumnCount(); i++){
	        			resultList.add(rs.getObject(i));
	        		}
	        	}
	        }
	        return resultList;
	      }catch(SQLException e){
	    	  JDBCConnLog.error(e.toString());
	      }finally{
	    	colseConnection();
	      }
	    }
	    return null;
	  }
  /********end************add jeton.dong 20090312******************end*********/
  
  public int getCount(String sSQL){
		int count = 0;
		getJDBCConnection();
	    if(conn != null){
	      try{
	        stmt = conn.createStatement();
	        rs = stmt.executeQuery(sSQL);
	        if(rs != null){
	        	while(rs.next()){
                   count = rs.getInt(1);
	        	}
	        }
	      }catch(SQLException e){
	    	  JDBCConnLog.error(e.toString());
	      }finally{
	    	colseConnection();
	      }
	      
	    }
	    return count;
  }
  
  
  public int executeUpdate(String sSQL){
    getJDBCConnection();
    int result = 0;
    
    if(conn != null){
      try{
        stmt = conn.createStatement();
        result = stmt.executeUpdate(sSQL);
      }catch(SQLException e){
    	  JDBCConnLog.error(e.toString());
      }finally{
    	  colseConnection();
      }
    }
    return result;
  }
  /**
   * executePreStatement:执行预处理语句
   * @param sql:欲执行的sql语句
   * @param para:参数
   * @return List
   */
  public List executePreStatement(String sql,String para){
	  resultList = new ArrayList();
	  getJDBCConnection();
	  try{
		 pstmt = conn.prepareStatement(sql);
		 pstmt.setString(1,para);	//设定参数
	     rs = pstmt.executeQuery();
	     ResultSetMetaData rsmd = rs.getMetaData();
	     int rows = 0;
	     if(rs != null){
        	while(rs.next()){
        		Map map = new LinkedHashMap();
        		for(int i=1; i<=rsmd.getColumnCount(); i++){
        			Object obj = rs.getObject(i);
        			String clobContent = "";
        			//若是clob字段，则读取其内容
        			if(obj instanceof Clob){
        				clobContent = readOracleClob((Clob)obj);
        				map.put(rsmd.getColumnName(i),clobContent);
        			}else{
        				map.put(rsmd.getColumnName(i),obj);
        			}
        		}
        		resultList.add(rows,map);
        		rows++;
        	}
	     }
	    }catch(Exception e)
		{
	    	  JDBCConnLog.error(e.toString());
		}finally{
	    	  colseConnection();
	    }
		return resultList; 
  }
    /**
	 * readOracleClob:读取Oracle中的Clob字段的值
	 * @param clob 欲读取的Clob
	 * @return String 以String形式返回Clob中的内容
	 */
	public String readOracleClob(Clob clob){
		String info = "";	//返回的信息
		Reader reader = null;
		if(clob != null){
			try {
				reader = clob.getCharacterStream();
				BufferedReader bf = new BufferedReader(reader);
				String tmp = bf.readLine();
				while(!StringUtil.isNullOrEmpty(tmp)){
					info +=tmp;
					tmp = bf.readLine();
				}
			} catch (Exception e) {
				JDBCConnLog.error(e.toString());
			}
		}
		return info;
	}
	
	
	
	
	/**
	   * fillPreStatement:填充预处理语句
	   * @param  pstmt:欲填充的pstmt
	   * @param  paras:参数
	   * @return PreparedStatement:填充后的pstmt
	   */
	  public PreparedStatement fillPreStatement(PreparedStatement pstmt,List paras){
		  PreparedStatement  retPstmt = pstmt;	//要返回的retPstmt
		  //循环paras设置preSQL所要参数
		  if(paras != null){
			  for(int i=0;i<paras.size();i++){
				  String val = (String)paras.get(i);	//要设置的值
				  if(val == null){
					  val = "";
				  }
				  try {
					pstmt.setString(i+1,val);
				 } catch (SQLException e) {
					 JDBCConnLog.error(e.toString());
				 }
			  }//endfor
		  }//endif
		  return retPstmt;
	  }
	  
	  /**
	   * 得到参数值List
	   * @param 　 map		 参数Map		
	   * @param   paraOrder  所需要参数对应的编号
	   * @return  List 		 参数List
	   */
	  public List getParasList(LinkedHashMap map,String paraOrder){
		  List parasList = new ArrayList();	//参数列表
		  String[] orders = paraOrder.split(";"); 
		  if(orders != null && orders.length>0){
			  for(int i=0;i<orders.length;i++){
				  String val = (String)map.get(orders[i]);
				  parasList.add(val);
				}
			}
		  return parasList;
	  }
	  
	  /**
	   * executePreStatement:执行预处理语句
	   * @param sql:欲执行的sql语句
	   * @return int  0:失败 非0:成功
	   */
	  public int executePreStatement(String strSQL,List excelContent,String paraOrder){
		  int flag = 0 ;		//0:失败 非0:成功
		  getJDBCConnection();
		  
		  try{
			  if(excelContent != null){
					for(int i=0;i<excelContent.size();i++){
					    //List rowList = (List)excelContent.get(i);						//List形式
						LinkedHashMap rowsMap = (LinkedHashMap)excelContent.get(i);		//以Map形式
						List paras = new ArrayList();									//得到参数列表
						if(rowsMap != null){
					    	  paras = getParasList(rowsMap,paraOrder);
							  pstmt = conn.prepareStatement(strSQL);
							  pstmt = fillPreStatement(pstmt,paras);
							  int tmp = pstmt.executeUpdate();
							  if(tmp > 0){
								  flag = 1;
							  }
						}  //endif  
					}//endfor
				}//endif	
			  
		  }catch(Exception e)
		  {
			  JDBCConnLog.error(e.toString());
		  }finally{
		    colseConnection();
		  }
		  
		return flag; 
	  }
	  
	  
	  public int executePreStatementList(String strSQL,List excelContent){
		  int flag = 0 ;		//0:失败 非0:成功
		  getJDBCConnection();
		  
		  try{
			  if(excelContent != null){
				
					    //List rowList = (List)excelContent.get(i);						//List形式
						//String  rowsMap = (LinkedHashMap)excelContent.get(i);		//以Map形式
						//List paras = new ArrayList();									//得到参数列表
						
					    	  //paras = getParasList(rowsMap,paraOrder);
							  pstmt = conn.prepareStatement(strSQL);
							  pstmt = fillPreStatement(pstmt,excelContent);
							  int tmp = pstmt.executeUpdate();
							  if(tmp > 0){
								  flag = 1;
							  }
						  //endif  
					//endfor
				}//endif	
			  
		  }catch(Exception e)
		  {
			  JDBCConnLog.error(e.toString());
		  }finally{
		    colseConnection();
		  }
		  
		return flag; 
	  }
	 /**
	  * 调用指定的存储过程
	  * @param proceName 存储过程的名称
	  */
	  public void callProcedure(String proceName){
		  getJDBCConnection();
		  try {
			cstmt = conn.prepareCall("{call "+proceName+"()}");
			cstmt.execute();
			} catch (SQLException e) {
				JDBCConnLog.error(e.toString());
			}finally{
			    colseConnection();
			}
	  }
	  
}  
	  
