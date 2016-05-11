package com.epam.DAO.DBmanagment;

import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.epam.DAO.HibernateUtil;
import com.epam.DAO.LibraryDB;
import com.epam.DB.entities.BookToDB;
import com.epam.DB.entities.CategoriesToDB;

public class DBWriter {

	public void write(Map<CategoriesToDB, LibraryDB> map) {
		
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		int category_id = 1;

		for (Map.Entry<CategoriesToDB, LibraryDB> entry : map.entrySet()) {
			LibraryDB libraryDB = entry.getValue();
			CategoriesToDB categoryDB = entry.getKey();
			categoryDB.setId(category_id++);
			System.out.println(categoryDB + " " + categoryDB.getId());
			for (BookToDB book : libraryDB) {
				book.setCategory_id(categoryDB.getId());
				session.save(book);
			}
			
			categoryDB.setLibrary(libraryDB);
			session.save(categoryDB);
		}

		session.getTransaction().commit();
		session.close();
		sessionFactory.close();
	}

}
