package com.epam.DAO;

import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class DBWriter {

	public void write(Map<CategoryDB, LibraryDB> map) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		for (Map.Entry<CategoryDB, LibraryDB> entry : map.entrySet()) {
			for (BookToDB book : entry.getValue()) {
				session.save(book);
			}
			session.save(entry.getKey());
		}

		session.getTransaction().commit();
		sessionFactory.close();
	}

}
