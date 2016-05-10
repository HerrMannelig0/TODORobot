package com.epam.DAO;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class DBDropper {

	public void drop(){
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
				
		session.createSQLQuery("drop table CategoriesToDB_BookToDB;").executeUpdate();
		session.createSQLQuery("drop table BookToDB;").executeUpdate();
		session.createSQLQuery("drop table CategoriesToDB;").executeUpdate();
		
		session.close();
		sessionFactory.close();
	}
	
}
