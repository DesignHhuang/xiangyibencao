package com.blisscloud.hibernate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * @param args
 */

public class EZHibernateUtil {

	private static Log log = LogFactory.getLog(EZHibernateUtil.class);

	private static String CONFIG_FILE_LOCATION = "/EZHibernate.cfg.xml";

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
	 * ��Hsql��õ����м�¼��List���
	 * 
	 * @param _hsql
	 * @return
	 */
	// public static List getObjectList(String _hsql){
	//
	// }

	public static void main(String[] args) {

	}

}
