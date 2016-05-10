package com.epam.DAO.DBmanagment;

import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.epam.DAO.BookToDB;
import com.epam.DAO.CategoriesToDB;
import com.epam.DAO.CategoryDB;
import com.epam.DAO.HibernateUtil;
import com.epam.DAO.LibraryDB;

public class DBWriter {

	public void write(Map<CategoryDB, LibraryDB> map) {
		Session session = HibernateUtil.getSession();
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
	}

}
