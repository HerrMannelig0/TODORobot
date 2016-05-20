package com.epam.DB;

import com.epam.DAO.DBmanagment.DBUtilities;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Created by michalr on 19.05.16.
 */
public class DBTestsUtil implements DBUtilities{

    private static final SessionFactory SESSION_FACTORY = buildSessionFactory();

    @Override
    public SessionFactory getSessionFactory() {
        return SESSION_FACTORY;
    }

    private static SessionFactory buildSessionFactory() {
        return new Configuration().configure("hibernate.test.cfg.xml").buildSessionFactory();
    }


}
