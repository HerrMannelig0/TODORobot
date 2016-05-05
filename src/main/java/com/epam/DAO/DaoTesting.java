package com.epam.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.epam.file.Category;
import com.epam.robot.Library;

public class DaoTesting {
	public static void main(String[] args) {
		
		LibraryDB libraryDB = new LibraryDB();
		CategoryDB categoryDB = new CategoryDB("Fantasy");
		BookToDB bookToDB = new BookToDB("Hobbit", "Tolkien");
		libraryDB.add(bookToDB);
		CategoriesToDB categoriesToDB = new CategoriesToDB("Fantasy", libraryDB);
		
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		session.save(categoryDB);
		session.save(bookToDB);
		session.save(categoriesToDB);

		
		session.getTransaction().commit();
		sessionFactory.close();
	}
}
