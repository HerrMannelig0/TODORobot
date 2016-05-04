package com.epam.DB;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;

import java.io.FileOutputStream;
import java.sql.Connection;

import static java.sql.DriverManager.getConnection;

/**
 * Created by aga on 04.05.16.
 */
public class ExtractDBToXML {

    public static void main(String[] args) throws Exception {
        IDatabaseConnection connection;
        IDataSet fullDataSet;
        try (Connection jdbcConnection = getConnection("jdbc:mysql://localhost:3306/books", "root", "")) {
            connection = new DatabaseConnection(jdbcConnection);
            fullDataSet = connection.createDataSet();
            FlatXmlDataSet.write(fullDataSet, new FileOutputStream("dataCategoriesAndBookstores.xml"));
        }
    }


}
