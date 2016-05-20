package com.epam.DB;

import com.epam.DAO.DBDAO;
import com.epam.DAO.HibernateUtil;
import com.epam.GUI.view.NoBooksOfGivenCategoryException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests of DBDAO class. Testing connections between DAO and DB.
 */
public class DBDAOTests {

    public static void main(String[] args) throws NoBooksOfGivenCategoryException {
        DBDAO dbdao = new DBDAO();
        String bookstore = "bookrix";
        Set<String> set = new HashSet<>();
        set.add("Romance");
        set.add("Other");
        List<String> list = dbdao.prepareBooksAfterClickButton (bookstore, set, new HibernateUtil());
        System.out.println(list);
    }

}
