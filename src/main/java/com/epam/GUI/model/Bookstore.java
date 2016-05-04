package com.epam.GUI.model;

import com.epam.DB.ManageCategoriesAndBookstores;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

/**
 * Created by aga on 20.04.16. Method used to set initBookstoresList with bookstoreDBs provided in "src/resources/FreeBooksAddressSites.txt".
 */
public class Bookstore {
    private static ArrayList<String> bookstores = new ArrayList<>();
    public static ObservableList<String> initBookstoreList = FXCollections.observableList(bookstores);


    public Bookstore(){
        bookstores.addAll(initBookstores());
    }

    /**
     * it returns list of available bookstoreDBs in "src/resources/FreeBooksAddressSites.txt"
     * @return
     */
    ArrayList<String> initBookstores() {

        //return (ArrayList<String>) prepareListOfBookstores.bookstoreDBs;
        return null;
    }



}
