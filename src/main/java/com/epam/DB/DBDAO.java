package com.epam.DB;

import com.epam.DB.DAO.BookDAOimpl;
import com.epam.DB.entities.BookstoreDB;
import com.epam.DB.entities.CategoryDB;
import com.epam.GUI.model.Bookstore;
import com.epam.file.Category;
import com.epam.robot.LibrariesMap;
import com.epam.robot.Library;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Projections;

import javax.persistence.Query;
import java.util.List;
import java.util.Map;

/**
 * Created by aga on 02.05.16.
 */
public class DBDAO {
    private Map<Category, Library> librariesMap;

    public DBDAO(LibrariesMap librariesMap) {
        this.librariesMap = librariesMap.getMap();
    }
    public DBDAO(){}


    public List<String> listOfBookstoresForGUI() {

       return null;
    }

    public void addBooks (){

        BookDAOimpl bookDao = new BookDAOimpl(new Configuration().configure().buildSessionFactory());


    }


}
