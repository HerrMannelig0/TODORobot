package com.epam.DAO.DBmanagment;


import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.epam.DAO.HibernateUtil;
import com.epam.DB.entities.BookToDB;

public class DBReader {
	
	public void readAll(){
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		Query query = session.createQuery("from BookToDB");
		List<BookToDB> result = query.list();
		System.out.println(result);
		
		session.close();
		sessionFactory.close();
	}
}
