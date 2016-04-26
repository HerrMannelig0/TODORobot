package com.epam.DAO;

import java.util.ArrayList;

import org.hibernate.Session;

public class AddingRecords {

    public static void main(String[] args) {

        setCategories();
        setBookstores();

    }

    /**
     * method using categories @see AddingCategories.categoriesFetcher to put them into DB
     */
    private static void setCategories() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        ArrayList<String> listOfCategories = (ArrayList<String>) AddingCategories.categoriesFetcher();
        for (String string : listOfCategories) {
            Category category = new Category();
            category.setCategoryName(string);

            session.beginTransaction();
            session.save(category);
            session.getTransaction().commit();
        }
        session.close();
    }

    /**
     * method using categories @see AddingCategories.categoriesFetcher to put them into DB
     */
    private static void setBookstores() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        ArrayList<String> listOfCategories = (ArrayList<String>) AddingBookstores.bookstoresFetcher();
        for (String string : listOfCategories) {
            Bookstore bookstore = new Bookstore();
            bookstore.setBookstorename(string);

            session.beginTransaction();
            session.save(bookstore);
            session.getTransaction().commit();
        }
        session.close();
    }
}
