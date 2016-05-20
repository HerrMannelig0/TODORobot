package com.epam.DB;

import com.epam.DAO.DBmanagment.DBWriter;
import com.epam.DAO.LibraryDB;
import com.epam.DB.entities.BookToDB;
import com.epam.DB.entities.CategoriesToDB;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertFalse;

/**
 * Tests of DBWriter class.
 * @see DBWriter
 */
public class WriterTests {

    /**
     * Test of writing book to database.
     */
    @Test
    public static void testWrittingToDB(){
        DBWriter dbWriter = new DBWriter();
        Map<CategoriesToDB, LibraryDB> map = new HashMap<>();

        CategoriesToDB category = new CategoriesToDB();
        category.setCategory("Horror");
        BookToDB book = new BookToDB("The Black Cat", "E.A. Poe", "Empik");

        LibraryDB libraryDB = new LibraryDB();
        libraryDB.add(book);

        map.put(category, libraryDB);

        dbWriter.write(map, new DBTestsUtil());

        Session session = new DBTestsUtil().getSessionFactory().openSession();
        session.beginTransaction();

        List<BookToDB> list = session.createCriteria(BookToDB.class)
                .add(Restrictions.eq("title","The Black Cat"))
                .add(Restrictions.eq("author", "E.A. Poe"))
                .add(Restrictions.eq("bookstore", "Empik"))
        .list();
        session.getTransaction().commit();
        session.close();

        assertFalse(list.isEmpty());
    }

}
