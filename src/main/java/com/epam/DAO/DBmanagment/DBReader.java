package com.epam.DAO.DBmanagment;


import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.epam.DAO.BookToDB;
import com.epam.DAO.HibernateUtil;

public class DBReader {
	
	public void readAll(){
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
		
		Query query = session.createQuery("from BookToDB");
		List<BookToDB> result = query.list();
		System.out.println(result);
	}
}
