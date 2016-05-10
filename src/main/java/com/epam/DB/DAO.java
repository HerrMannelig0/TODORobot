package com.epam.DB;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.epam.DAO.HibernateUtil;
import com.epam.file.Category;
import com.epam.library.LibrariesMap;
import com.epam.library.Library;

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
