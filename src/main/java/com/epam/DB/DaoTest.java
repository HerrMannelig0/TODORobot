package com.epam.DB;

import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * Created by michalr on 11.05.16.
 */
public class DaoTest {
    public static void main(String[] args) {
        DBDAO dbdao = new DBDAO();
        Set<String> set = new HashSet<>();
        set.add("Romance");
        List<String> list = dbdao.prepareBooksAfterClickButton("bookrix", set);
        System.out.println(list);
    }

}
