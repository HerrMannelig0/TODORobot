package com.epam.DAO;

import com.epam.DAO.DBmanagment.DBUtilities;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class HibernateUtil implements DBUtilities{

	private static final SessionFactory SESSION_FACTORY = buildSessionFactory();
	private static final Session SESSION = new Configuration().configure().buildSessionFactory().openSession();

	
	private static SessionFactory buildSessionFactory() {
		try {
			return new Configuration().configure().buildSessionFactory();
			
		} catch (Throwable ex) {
			ex.printStackTrace();
			throw new ExceptionInInitializerError(ex);
		}
	}

	@Override
	public SessionFactory getSessionFactory() {
		return SESSION_FACTORY;
	}

	/**
	 * Close caches and connection pools
	 */
	public void shutdown() {
		getSessionFactory().close();
	}
	
	/**
	 * @param pathToHibernateCfgXml path to where hibernate.test.cfg.xml is located
	 *        if file is in /my/dir/with/my.cfg.xml, where name of config file is my.cfg.xml
	 *        then all all path with files name must be provided as parameter.
	 * @return Hibernate session factory.
	 */
	public static SessionFactory newSessionFactory(final String pathToHibernateCfgXml) {
		Configuration hibernateConfiguration = new Configuration();
		hibernateConfiguration.configure(pathToHibernateCfgXml);
		return hibernateConfiguration.buildSessionFactory();
	}

	public static Session getSession() {
		return SESSION;
	}
	
	public static void closeSession(){
		SESSION.close();
		SESSION_FACTORY.close();
	}

}