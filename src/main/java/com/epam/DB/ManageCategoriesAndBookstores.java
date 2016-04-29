package com.epam.DB;

import com.epam.DB.entities.Bookstore;
import com.epam.DB.entities.Category;
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



    private static String categoriesPath = "src/Keywords/categories";
    private static String bookstoresPath = "src/Keywords/bookstores";

    public static void main(String[] args) {
        setDatabase();
    }
    /**
     * method using categories to put them into DB @see entriesFetcher
     */
    private static void setDatabase() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        ArrayList<String> listOfCategories = (ArrayList<String>) entriesFetcher(categoriesPath);
        ArrayList<String> listOfBookstores = (ArrayList<String>) entriesFetcher(bookstoresPath);

        for (String string : listOfCategories) {
            Category category = new Category();
            category.setCategoryName(string);

            session.beginTransaction();
            session.save(category);
            session.getTransaction().commit();
        }
        for (String string : listOfBookstores) {
            Bookstore bookstore = new Bookstore();
            bookstore.setBookstorename(string);

            session.beginTransaction();
            session.save(bookstore);
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

