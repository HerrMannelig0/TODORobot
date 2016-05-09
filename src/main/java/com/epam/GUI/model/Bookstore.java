package com.epam.GUI.model;

import com.epam.DB.DAO;
import com.epam.DB.ManageCategoriesAndBookstores;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

/**
 * Created by aga on 20.04.16.
 */
public class Bookstore {
    private static ArrayList<String> bookstores = new ArrayList<>();
    public static ObservableList<String> initBookstoreList = FXCollections.observableList(bookstores);


    public Bookstore() {
        bookstores.addAll(initBookstores());
    }

    /**
     * it returns list of available bookstoreDBs
     *
     * @return
     */
    ArrayList<String> initBookstores() {
        DAO dao = new DAO();
        return (ArrayList<String>) dao.listOfBookstoresForGUI();
    }


}
