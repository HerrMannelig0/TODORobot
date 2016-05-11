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

        try (Session session = HibernateUtil.getSession()) {
            List<String> bookstoresForGUI = new ArrayList<>();
            List<Object> bookstores;

            Query query = session.createSQLQuery("SELECT DISTINCT bookstore FROM BookToDB;");

            bookstores = query.list();
            bookstores.forEach(e -> bookstoresForGUI.add(e.toString()));
            return bookstoresForGUI;

        } catch (HibernateException e) {
            System.err.print("Something went wrong in creating query for fetching bookstores names.");
            e.printStackTrace();
        }
        return null;
    }

    public List<String> prepareBooksAfterClickButton(String bookstore, Set<String> categoriesToShow) {

        List<String> books = new ArrayList<>();
        DBDAOLibrary library = this.new DBDAOLibrary();

        Session session = HibernateUtil.getSession();
        Query query = session.createSQLQuery("SELECT title, author, bookstore, CategoriesToDB.category from BookToDB left join CategoriesToDB ON BookToDB.category_id=CategoriesToDB.id;");

        List<Object[]> dataFromQuery = query.list();

        for (Object[] book : dataFromQuery) {

            library.addBookIfConditionsAreFulfill(bookstore, categoriesToShow, book);
        }

        return library;


    }

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

    private void addBookIfConditionsAreFulfill(String bookstore, Set<String> categoriesToShow, Object[] book) {
        if (((String) (book[2])).equals(bookstore)) {
            if (categoriesToShow.contains((String) book[3])) {
                this.addBookToList(book);
            }

        }
    }

    private DBDAOLibrary addBookToList(Object[] book) {
        list.add((String) book[0] + " " + (String) book[1] + " " + (String) book[2] +
                " " + (String) book[3]);
        return this;
    }


}


}
