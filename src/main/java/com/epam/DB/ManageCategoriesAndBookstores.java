package com.epam.DB;

import com.epam.DB.entities.BookstoreDB;
import com.epam.DB.entities.CategoryDB;
import org.hibernate.Session;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by aga on 27.04.16.
 */
public class ManageCategoriesAndBookstores {


    private static String categoriesPath = "src/Categories.txt";
    private static String bookstoresPath = "src/FreeBooksAdressSite.txt";

    public static void main(String[] args) {
        setDatabase();
    }
    /**
     * method using categories and bookstores to put them into DB @see entriesFetcher, it is the first thing done after running program.
     * DB is dropped (data could be outdated), injects categories and bookstores from files within project.
     */
    private static void setDatabase() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        ArrayList<String> listOfCategories = (ArrayList<String>) entriesFetcher(categoriesPath);
        ArrayList<String> listOfBookstores = (ArrayList<String>) entriesFetcher(bookstoresPath);

        for (String string : listOfCategories) {
            CategoryDB category = new CategoryDB();
            category.setName(string);

            session.beginTransaction();
            session.save(category);
            session.getTransaction().commit();
        }
        for (String string : listOfBookstores) {
            BookstoreDB bookstoreDB = new BookstoreDB();
            bookstoreDB.setName(string);

            session.beginTransaction();
            session.save(bookstoreDB);
            session.getTransaction().commit();
        }
        session.close();
    }

    private static List<String> entriesFetcher(String fileName) {
        List<String> entriesList = new ArrayList<>();

        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            entriesList = stream.collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return entriesList;
    }
}

