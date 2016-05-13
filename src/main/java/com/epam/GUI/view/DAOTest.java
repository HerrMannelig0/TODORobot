package com.epam.GUI.view;

import com.epam.DB.DBDAO;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by michalr on 13.05.16.
 */
public class DAOTest {

    public static void main(String[] args) {
        try {
            Process p =Runtime.getRuntime().exec("open -a TextEdit src/main/resources/FreeBooksAdressSite.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
