package com.epam.DAO;


import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class DBReader {
	
	public void readCategory(String category){
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		
		
		
		//System.out.println(l);
		
		sessionFactory.close();
	}
}
