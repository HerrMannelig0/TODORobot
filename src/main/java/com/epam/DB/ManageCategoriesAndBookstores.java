package com.epam.DB;

import com.epam.DAO.BookToDB;
import com.epam.DB.entities.BookDB;
import com.epam.DB.entities.BookstoreDB;
import com.epam.DB.entities.CategoryDB;
import com.epam.file.Category;
import com.epam.robot.Bookstores;
import com.epam.robot.LibrariesMap;
import com.epam.robot.Library;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by aga on 27.04.16.
 */
public class ManageCategoriesAndBookstores {

    private static Logger logger = Logger.getLogger("ManageCategoriesAndBookstores");


    private static String categoriesPath = "src/Categories.txt";
    private static String bookstoresPath = "src/FreeBooksAdressSite.txt";


    /**
     * method using categories and bookstores to put them into DB @see entriesFetcher, it is the first thing done after running program.
     * DB is dropped (data could be outdated), injects categories and bookstores from files within project.
     */
    public static void setDatabase(LibrariesMap mapCatLibrary) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        for (Map.Entry<Category, Library> entry : mapCatLibrary.entrySet()) {

            Category category = entry.getKey();
            Library lib = entry.getValue();

            CategoryDB categoryDB = new CategoryDB();
            categoryDB.setName(category.getName());
            
            ArrayList<BookDB> bookDBList = (ArrayList<BookDB>) lib.convertToDBList();
            categoryDB.setListOfBooks(bookDBList);
            session.beginTransaction();
            session.save(categoryDB);
            session.flush();
    		session.clear();
            session.getTransaction().commit();
            logger.info("database entries: List<BookDAO> for Categories and Categories itself.");
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

