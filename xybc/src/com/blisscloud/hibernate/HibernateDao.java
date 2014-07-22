package com.blisscloud.hibernate;

import java.io.*;
import java.util.*;

import org.hibernate.*;
import org.apache.commons.logging.LogFactory;


import org.apache.commons.logging.Log;
/**
 *
 * @author Ken.guo
 * 通过Hibernate操作数据库
 */

public class HibernateDao {
    private static Log log = LogFactory.getLog(HibernateDao.class);

    /**
     * 通过ID和类路径得到一个实体对象
     * @param args
     */
    public static Object getObjectById(Class _class,Serializable id) {
        Object obj=null;
        Session session=null;
        try {
        	session=  HibernateUtil.currentSession();
            obj=  session.get(_class,id);
        } catch (HibernateException e) {
            e.printStackTrace();
        }finally{
           HibernateUtil.closeSession();
        }
        return obj;
   }
    
    /**
     * 获取符合sql的数组对象列表
     * @param args
     */
   public static List getColsListBySql(String sql){
	   List list=null;
	   Session session=null;
	   try {
		     session=HibernateUtil.currentSession();
			 list=session.createSQLQuery(sql).list();
           } catch (HibernateException e) {
    	   System.out.println("getColsListBySql==>"+sql);
           e.printStackTrace();
        }finally{
          HibernateUtil.closeSession();
       }		 
	  return list;
	}
   public static void main(String[] s){
	   HibernateDao.getSingleObject("from Customer  where customerId=12");
   }
   /**
    * 获取单个字符串数组对象
    * @param args
    */
   public static String[] getSingleAttArray(String hsql){
	   List list=null;
	   Session session=null;
	   try {
		     session=HibernateUtil.currentSession();
		     list=session.createSQLQuery(hsql).list();
       } catch (HibernateException e) {
    	   System.out.println("getSingleAttArray==>"+hsql);
           e.printStackTrace();
        }finally{
          HibernateUtil.closeSession();
       }	      
       if(list!=null){
    	  int count=list.size();
    	  if(count==0){
    		  return  null;
    	  }
    	  if(count==1){
    		  return  new String[]{String.valueOf(list.get(0))};
    	  }
    	  String[] ok=new String[count];
    	  for(int k=0;k<count;k++){
    		  ok[k]=String.valueOf(list.get(k));
    	  }
    	  return ok;
       }
	   return null;
   }
   

    /**
     * 获取单个对象
     * @param args
     */
    public static Object getSingleObject(String hsql) {
      Object obj=null;
      Session session=null;
      try {
            session=  HibernateUtil.currentSession();
            List list=  session.createQuery(hsql).list();
            if(list!=null){
            	if(list.size()==1){
            		obj=list.get(0);
            	}
            }
        } catch (HibernateException e) {
            e.printStackTrace();
        }finally{
           HibernateUtil.closeSession();
        }
        return obj;
   }

    

    /**
	 * 取得一个对象列表
	 * @param hsql
	 * @return
	 */
	public static List getObjectList(String hsql){
		List list=null;
		Session session=null;
		try {
            session=  HibernateUtil.currentSession();
            Query query=  session.createQuery(hsql);
            list=query.list();
        } catch (HibernateException e) {
            e.printStackTrace();
        }finally{
           HibernateUtil.closeSession();
        }
		return  list;
	}
	
	 /**
	 * 取得一个对象迭代器
	 * @param hsql
	 * @return
	 */
	public static Iterator getObjectIterator(String hsql){
		Iterator It=null;
		 Session session=null;
		try {
            session=  HibernateUtil.currentSession();
            It=  session.createQuery(hsql).iterate();
         } catch (HibernateException e) {
            e.printStackTrace();
         }finally{
           HibernateUtil.closeSession();
        }
		return  It;
	}
	
	/**
	 * 向数据库中插入一条对象记录
	 * @param obj
	 */
	public static String saveObject(Object obj){
            Object ok=null;
            Session session =null;
            Transaction tx =null;
            try {
                session = HibernateUtil.currentSession();
                tx = session.beginTransaction();
                ok=session.save(obj);
                tx.commit();
            } catch (Exception e) {
                e.printStackTrace();
                tx.rollback();
            } finally {
                HibernateUtil.closeSession();
            }
            return String.valueOf(ok);
	}
	
	/**
	 * 向数据库中插入或保存一条对象记录
	 * 成功为0 失败为1
	 * @param obj
	 */
	public static int saveOrUpdateObject(Object obj){
		    int success=0;
		    Session session =null;
            Transaction tx =null;
            try {
            	session = HibernateUtil.currentSession();
                tx = session.beginTransaction();
                session.saveOrUpdate(obj);
                tx.commit();
            } catch (Exception e) {
            	success=1;
                e.printStackTrace();
                tx.rollback();
            } finally {
                HibernateUtil.closeSession();
            }
            return success;
	}
	

	/**
	 * 向数据库中插入一条对象记录
	 * @param obj
	 */
	public static Serializable addObject(Object obj){
		    Serializable ok=null;
		    Session session =null;
            Transaction tx =null;
            try {
            	session = HibernateUtil.currentSession();
                tx = session.beginTransaction();
                ok=session.save(obj);
                tx.commit();
            } catch (Exception e) {
                e.printStackTrace();
                tx.rollback();
            } finally {
                HibernateUtil.closeSession();
            }
            return String.valueOf(ok);
	}
	
	/**
	 * 更新一个对象
	 * @param obj
	 */
	public static void updateObject(Object obj){
		Session session =null;
        Transaction tx =null;
        try {
        	   session = HibernateUtil.currentSession();
               tx = session.beginTransaction();
               session.update(obj);
               tx.commit();
           } catch (Exception e) {
               e.printStackTrace();
               tx.rollback();
           } finally {
               HibernateUtil.closeSession();
           }
    	}

    /**
     * 删除一个对象
     */
    public static void deleteObject(Object obj){
    	Session session =null;
        Transaction tx =null;
        try {
        	session = HibernateUtil.currentSession();
            tx = session.beginTransaction();
        	session.delete(obj);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            HibernateUtil.closeSession();
        }
    }
    /**
     * 批删除一个或多个对象
     */
    public static int deleteObject(String hsql){
    	int ok=0;
    	Session session =null;
        Transaction tx =null;
        try {
        	session = HibernateUtil.currentSession();
            tx = session.beginTransaction();
        	ok=session.createQuery(hsql).executeUpdate();
            tx.commit();
        } catch (Exception e) {
        	ok=-1;
            e.printStackTrace();
            tx.rollback();
        } finally {
            HibernateUtil.closeSession();
        }
        return ok;
    }
    
    /**
     * 批更新一个或多个对象
     */
    public static int updateObjects(String updateHsql){
    	int row=0;
    	Session session =null;
        Transaction tx =null;
        try {
        	session = HibernateUtil.currentSession();
            tx = session.beginTransaction();
        	row=session.createQuery(updateHsql).executeUpdate();
            tx.commit();
        } catch (Exception e) {
        	row=-1;
            e.printStackTrace();
            tx.rollback();
        } finally {
            HibernateUtil.closeSession();
        }
        return row;
    }
    

    public static Session getSession(){
    	return  HibernateUtil.currentSession();
    }
}
