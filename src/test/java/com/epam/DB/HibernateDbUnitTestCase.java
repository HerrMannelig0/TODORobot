package com.epam.DB;

import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.DAO.HibernateUtil;

/**
 * Created by aga on 04.05.16.
 */
public abstract class HibernateDbUnitTestCase extends DBTestCase {

        private static final Logger LOG = LoggerFactory.getLogger(HibernateDbUnitTestCase.class);

        private static SessionFactory sessionFactory;
        protected Session session;

        /**
         * system properties initializing constructor.
         */
        public HibernateDbUnitTestCase() {
            System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, "org.hsqldb.jdbcDriver");
            System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, "jdbc:hsqldb:mem:DBNAME");
            System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, "sa");
            System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, "");
        }

        /**
         * Start the server.
         * @throws Exception in case of startup failure.
         */
        @Before
        public void setUp() throws Exception {

            LOG.info("Loading hibernate...");
            if (sessionFactory == null) {
                sessionFactory = HibernateUtil.newSessionFactory("hibernate.test.cfg.xml");
            }

            session = sessionFactory.openSession();

            super.setUp();
        }

        /**
         * shutdown the server.
         * @throws Exception in case of errors.
         */
        @After
        public void tearDown() throws Exception {
            session.close();
            super.tearDown();
        }


        /** {@inheritDoc} */
        protected DatabaseOperation getSetUpOperation() throws Exception {
            return DatabaseOperation.REFRESH;
        }

        /** {@inheritDoc} */
        protected DatabaseOperation getTearDownOperation() throws Exception {
            return DatabaseOperation.NONE;
        }


    }
