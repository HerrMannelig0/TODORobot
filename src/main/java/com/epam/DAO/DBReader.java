package com.epam.DAO;


import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class DBReader {
	
	public void readAll(){
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		Query query = session.createQuery("from BookToDB");
		List<BookToDB> result = query.list();
		System.out.println(result);
		
		sessionFactory.close();
	}
}
