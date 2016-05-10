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
			LibraryDB libraryDB = entry.getValue();
			CategoryDB categoryDB = entry.getKey();
			
			for (BookToDB book : libraryDB) {
				session.save(book);
			}
			
			CategoriesToDB categoriesToDB = new CategoriesToDB();
			categoriesToDB.setLibrary(libraryDB);
			categoriesToDB.setCategory(categoryDB.getName());
			session.save(categoriesToDB);
		}

		session.getTransaction().commit();
		session.close();
		sessionFactory.close();
	}

}
