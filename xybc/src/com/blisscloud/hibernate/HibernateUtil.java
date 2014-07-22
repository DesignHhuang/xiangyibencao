package com.blisscloud.hibernate;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * @param args
 */

public class HibernateUtil {

	private static Log log = LogFactory.getLog(HibernateUtil.class);

	private static String CONFIG_FILE_LOCATION = "/hibernate.cfg.xml";

	private static final SessionFactory sessionFactory;
	static {
		try {
			// Create the SessionFactory
			sessionFactory = new Configuration()
					.configure(CONFIG_FILE_LOCATION).buildSessionFactory();

		} catch (Throwable ex) {
			// System.out.println("Configuration "+ ex.toString());
			log.error("Initial SessionFactory creation failed.", ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static final ThreadLocal session = new ThreadLocal();

	public static Session currentSession() throws HibernateException {
		Session s = (Session) session.get();
		// Open a new Session, if this Thread has none yet
		if (s == null) {
			s = sessionFactory.openSession();
			session.set(s);
		}
		return s;
	}

	public static void closeSession() throws HibernateException {
		Session s = (Session) session.get();
		// session.set(null);
		if (s != null) {
			s.close();
			session.set(null);
		}
	}

	/**
	 * Call Old System History Procedure 老系统联络历史记录数
	 * 
	 * @return
	 */
	public static String callOldSysHistoryNumProce(String userName,
			String userCall) {
		Connection con = null;
		Session session = null;
		CallableStatement cstmt = null;
		String retHisNum = "";
		try {
			session = HibernateUtil.currentSession();
			con = session.connection();
			cstmt = con.prepareCall("{call hj_ldcl_num(?,?,?)}");
			cstmt.setString(1, userName); // 用户姓名
			cstmt.setString(2, userCall); // 用户电话
			cstmt.registerOutParameter(3, Types.VARCHAR);// Output Parameter
			cstmt.execute();
			retHisNum = cstmt.getString(3);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			HibernateUtil.closeSession();
		}
		return retHisNum;
	}

	/**
	 * Call Reply Procedure 反馈记录敄1�7
	 * 
	 * @return
	 */
	public static String callReplyNumProce(String longinName) {
		Connection con = null;
		Session session = null;
		CallableStatement cstmt = null;
		String retReplyNum = "";
		try {
			session = HibernateUtil.currentSession();
			con = session.connection();
			cstmt = con.prepareCall("{call hj_fkxx_num(?,?)}");
			cstmt.setString(1, longinName); // 用户登录各1�7
			cstmt.registerOutParameter(2, Types.VARCHAR);// Output Parameter
			cstmt.execute();
			retReplyNum = cstmt.getString(2);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			HibernateUtil.closeSession();
		}
		return retReplyNum;
	}

	public static void main(String[] args) {

	}

}
