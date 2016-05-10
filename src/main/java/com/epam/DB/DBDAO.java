package com.epam.DB;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.epam.DAO.HibernateUtil;
import com.epam.DB.entities.BookstoreToDB;
import com.epam.file.Category;
import com.epam.library.LibrariesMap;
import com.epam.library.Library;

/**
 * Created by aga on 02.05.16.
 */
public class DBDAO {
    private Map<Category, Library> librariesMap;

    public DBDAO() {
	}
    
    public DBDAO(LibrariesMap librariesMap) {
        this.librariesMap = librariesMap.getMap();
    }

    /**
     * @return List of bookstores which would be listed in GUI
     */
    @SuppressWarnings("unchecked")
	public List<String> listOfBookstoresForGUI() {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<String> bookstoresForGUI = null;
            List<BookstoreToDB> bookstores;
            bookstores = session.createSQLQuery("SELECT bookstore_name FROM bookstoreDB")
                    .addEntity(BookstoreToDB.class)
                    .list();
            bookstores.forEach(e -> bookstoresForGUI.add(e.toString()));
            return bookstoresForGUI;

        } catch (HibernateException e) {
            System.err.print("Something went wrong in creating query for fetching bookstores names.");
            e.printStackTrace();
        }
        return null;
    }
        
    public List<String> addBooks (){

             List<String> bookstores = new ArrayList<>();
            bookstores.add("kotek");
            bookstores.add("koteczek");

            return bookstores;
    }


}
