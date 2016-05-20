package com.epam.DB;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.epam.DAO.DBDAO;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.database.DatabaseSequenceFilter;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.FilteredDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.Session;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

/**
 * Tests of DBDAO class.
 */
public class DAOTests {

    //creating settings from BD
    /*public static void main(String[] args) throws Exception {


        Class driverClass = Class.forName("com.mysql.jdbc.Driver");
        Connection jdbcConnection = DriverManager.getConnection("jdbc:mysql://localhost/TEST", "root", "your_new_password");
        IDatabaseConnection connection = new DatabaseConnection(jdbcConnection);
        // partial database export

        QueryDataSet partialDataSet = new QueryDataSet(connection);
        partialDataSet.addTable("BookToDB");
        FlatXmlDataSet.write(partialDataSet, new FileOutputStream("created-dataset.xml"));
    }*/

    public static void main(String[] args) {
        Session session = new DBTestsUtil().getSessionFactory().openSession();
        session.beginTransaction();
        session.createSQLQuery("drop table CategoriesToDB_BookToDB;").executeUpdate();
        session.createSQLQuery("drop table CategoriesToDB;").executeUpdate();
        session.createSQLQuery("drop table BookToDB;").executeUpdate();
        session.close();
    }


    private IDataSet createDataSet() throws MalformedURLException, DataSetException {
        return new FlatXmlDataSetBuilder().build(new File("created-dataset.xml"));
    }

    private void cleanlyInsertDataset(IDataSet dataSet) throws Exception {
        IDatabaseTester databaseTester = new JdbcDatabaseTester("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/NTest", "root", "your_new_password");
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.setDataSet(dataSet);
        databaseTester.onSetup();
    }

    @BeforeTest
    public void importDataSet() throws Exception {

        IDataSet dataSet = createDataSet();
        cleanlyInsertDataset(dataSet);
    }

    /**
     * Test of {@code istOfBookstoresForGUI()} method.
     */
    @Test
    public void testGettingListOfBooks() {
        DBDAO dbdao = new DBDAO();
        List<String> list = dbdao.listOfBookstoresForGUI((new DBTestsUtil()));
        assertTrue(list.contains("bookrix"));
    }

    /**
     * Test of getting the list of books when the mouse is clicked.
     */
    @Test
    public void testPreparingBookList(){
        DBDAO dbdao = new DBDAO();
        String bookstore = "bookrix";
        Set<String> categories = new HashSet<>();
        categories.add("Horror");
        categories.add("Romance");

        List<String> list = dbdao.prepareBooksAfterClickButton(bookstore, categories, new DBTestsUtil());

        assertTrue(list.get(1).contains("Practical Witchery! - John Stormm (category: Romance)"));

    }



}

