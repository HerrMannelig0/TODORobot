package com.epam.DB;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

	private static final SessionFactory sessionFactory = buildSessionFactory();

	private static SessionFactory buildSessionFactory() {

		// For XML mapping
		// return new Configuration().configure().buildSessionFactory();

		// For Annotation - wczesniej bylo to AnnotationConfiguration, ale
		// od Hibernate 3.6 jest deprecated i funkcjonalnosci przeniesione
		// do Configuration
		return new Configuration().configure().buildSessionFactory();

	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
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

	public static void shutdown() {
		// Close caches and connection pools
		getSessionFactory().close();
	}

}