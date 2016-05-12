package com.epam.DB;

import java.util.*;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.epam.DAO.HibernateUtil;
import com.epam.file.Category;
import com.epam.library.LibrariesMap;
import com.epam.library.Library;

/**
 * Class responsible for fetching results according to user preferences and send them to GUI.
 */
public class DBDAO {
    private Map<Category, Library> librariesMap;

    public DBDAO() {
    }

    public DBDAO(LibrariesMap librariesMap) {
        this.librariesMap = librariesMap.getMap();
    }

    /**
     * @return List of avaiable bookstores which would be listed in GUI
     */
    public List<String> listOfBookstoresForGUI() {

        try (Session session = HibernateUtil.getSession()) {
            List<String> bookstoresForGUI = new ArrayList<>();
            List<Object> bookstores;

            Query query = session.createSQLQuery("SELECT DISTINCT bookstore FROM BookToDB;");

            bookstores = query.list();
            bookstores.forEach(e -> bookstoresForGUI.add(e.toString()));
            return bookstoresForGUI;

        } catch (HibernateException e) {
            System.err.print("Cannot make query for bookstores.");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * method called after clicking by user \"show books\" button.
     * It gives list of strings uset to set text in text area in GUI.
     *
     * @param bookstore        name of bookstore from which books would be taken
     * @param categoriesToShow list of categories from which books would be taken
     * @return list of books with given properties (bookstore, categories)
     */
    public List<String> prepareBooksAfterClickButton(String bookstore, Set<String> categoriesToShow) {

        DBDAOLibrary library = this.new DBDAOLibrary();

       Session session = HibernateUtil.getSession();
           if(!session.isOpen()) session = HibernateUtil.getSessionFactory().openSession();
           Query query = session.createSQLQuery("SELECT title, author, bookstore, CategoriesToDB.category from BookToDB left join CategoriesToDB ON BookToDB.category_id=CategoriesToDB.id;");

           List<Object[]> dataFromQuery = query.list();

           for (Object[] book : dataFromQuery) {
               library.addBookIfConditionsAreFulfill(bookstore, categoriesToShow, book);
           }
           return library;


    }

    /**
     * private class for filtering results according to given bookstores name and categories
     */
    private class DBDAOLibrary extends AbstractList<String> {

        List<String> list = new ArrayList<>();

        @Override
        public String get(int index) {
            return list.get(index);
        }

        @Override
        public int size() {
            return list.size();
        }

        /**
         * Lord Imperator of coding... :(
         *
         * @param bookstore
         * @param categoriesToShow
         * @param book
         */
        private void addBookIfConditionsAreFulfill(String bookstore, Set<String> categoriesToShow, Object[] book) {
            if (((String) (book[2])).equals(bookstore)) {
                if (categoriesToShow.contains((String) book[3])) {
                    this.addBookToList(book);
                }

            }
        }

        private DBDAOLibrary addBookToList(Object[] book) {
            list.add("\n"+(String) book[0] + " " + (String) book[1] + " (category: "+(String) book[3]+")");
            return this;
        }


    }


}
