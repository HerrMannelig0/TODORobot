package com.epam.DB;

import com.epam.DB.entities.BookstoreDB;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;
import org.junit.BeforeClass;
import org.testng.annotations.Test;


import java.io.File;
import java.util.List;

/**
 * Testing DB requires having some records, tests won't pass if there is no database.
 */
public class DAOTests {

	private static final String JDBC_DRIVER = com.mysql.jdbc.Driver.class.getName();
	private static final String JDBC_URL = "jdbc:mysql://localhost:3306/tests;DB_CLOSE_DELAY=-1";
	private static final String USER = "root";
	private static final String PASSWORD = "";


    private IDataSet readDataSet() throws Exception {
        return new FlatXmlDataSetBuilder().build(new File("dataCategoriesAndBookstores.xml"));
    }

    private void cleanlyInsertDataset(IDataSet dataSet) throws Exception {
        IDatabaseTester databaseTester = new JdbcDatabaseTester(
                "org.h2.Driver", "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "sa", "");
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.setDataSet(dataSet);
        databaseTester.onSetup();
    }

    @Before
    public void importDataSet() throws Exception {
        IDataSet dataSet = readDataSet();
        cleanlyInsertDataset(dataSet);
    }

    @Test
    public void ifBookstoresListInitHave3Bookshops() {




    }

}
