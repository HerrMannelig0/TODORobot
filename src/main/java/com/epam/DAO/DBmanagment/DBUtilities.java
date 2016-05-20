package com.epam.DAO.DBmanagment;

import org.hibernate.SessionFactory;

/**
 * Created by michalr on 19.05.16.
 */
public interface DBUtilities {
    SessionFactory getSessionFactory();
}
