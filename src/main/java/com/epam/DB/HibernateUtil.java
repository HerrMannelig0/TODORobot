package com.epam.DB;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Utilities for Hibernate usage.
 */
public class HibernateUtil {

	private static final SessionFactory SESSION_FACTORY = buildSessionFactory();
	
	private static final Session SESSION = new Configuration().configure().buildSessionFactory().openSession();

	public static Session getSession() {
		return SESSION;
	}
	
	public static void closeSession(){
		SESSION.close();
	}

	public static SessionFactory getSessionFactory() {
		return SESSION_FACTORY;
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

	/**
	 * Close caches and connection pools
	 */
	public static void shutdown() {
		getSessionFactory().close();
	}

	private static SessionFactory buildSessionFactory() {

		// For XML mapping
		// return new Configuration().configure().buildSessionFactory();

		// For Annotation - wczesniej bylo to AnnotationConfiguration, ale
		// od Hibernate 3.6 jest deprecated i funkcjonalnosci przeniesione
		// do Configuration
		return new Configuration().configure().buildSessionFactory();

	}



}