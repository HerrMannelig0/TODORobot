package com.epam.DB;

import com.epam.DB.entities.BookstoreDB;
import com.epam.DB.entities.CategoryDB;
import com.epam.GUI.model.Bookstore;
import com.epam.file.Category;
import com.epam.robot.LibrariesMap;
import com.epam.robot.Library;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by aga on 02.05.16.
 */
public class DAO {
    private Map<Category, Library> librariesMap;

    public DAO(LibrariesMap librariesMap) {
        this.librariesMap = librariesMap.getMap();
    }

    public DAO() {
    }

    /**
     * list of categories
     */
    public List<String> listOfBookstoresForGUI() {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            List<String> bookstores = new ArrayList<>();
            bookstores.add("kotek");
            bookstores.add("koteczek");

            return bookstores;

        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return null;
    }


}
