package com.epam.DB;

import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests of DBDAO class. Testing connections between DAO and DB.
 */
public class DBDAOTests {

    public static void main(String[] args) {
        DBDAO dbdao = new DBDAO();
        String bookstore = "bookrix";
        Set<String> set = new HashSet<>();
        set.add("Romance");
        set.add("Other");
        List<String> list = dbdao.prepareBooksAfterClickButton(bookstore, set);
        System.out.println(list);
    }

}
