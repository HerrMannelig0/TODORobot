package com.epam.DB;

import com.epam.DB.entities.BookstoreDB;
import com.epam.file.Category;
import com.epam.robot.Library;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;
import java.util.Map;

/**
 * Created by aga on 02.05.16.
 */
public class DAO {
    private Map<Category, Library> librariesMap;

    public DAO() {
    }

    public static void main(String[] args) {

    }
    /**
     * @return List of bookstores which would be listed in GUI
     */
    public List<String> listOfBookstoresForGUI() {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<String> bookstoresForGUI = null;
            List<BookstoreDB> bookstores;
            bookstores = session.createSQLQuery("SELECT bookstore_name FROM bookstoreDB")
                    .addEntity(BookstoreDB.class)
                    .list();
            bookstores.forEach(e -> bookstoresForGUI.add(e.toStringGUI()));
            return bookstoresForGUI;

        } catch (HibernateException e) {
            System.err.print("Something went wrong in creating query for fetching bookstores names.");
            e.printStackTrace();
        }
        return null;
    }


    public String listOfBooksForGUI(String bookstore, String category) {
        String result = "";
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query query = session.createSQLQuery(
                    "select s.Author, s.Title from books s where s.BookstoreDB.bookstore_name = :bookstore")
                    .setParameter("bookstore", "bookstore");
            List lst = query.list();
        } catch (HibernateException e) {
            System.err.print("Something went wrong in creating query for fetching books names.");
            e.printStackTrace();
        }
        return result;
    }
}
