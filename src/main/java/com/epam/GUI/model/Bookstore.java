package com.epam.GUI.model;

import java.util.ArrayList;

import com.epam.DB.DBDAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Created by aga on 20.04.16.
 */
public class Bookstore {
    private static ArrayList<String> bookstores = new ArrayList<>();
    public ObservableList<String> initBookstoreList = FXCollections.observableList(bookstores);


    public Bookstore() {
        bookstores.addAll(initBookstores());
    }

    /**
     * it returns list of available bookstoreDBs
     *
     * @return
     */
    ArrayList<String> initBookstores() {
        DBDAO dao = new DBDAO();
        return (ArrayList<String>) dao.listOfBookstoresForGUI();
    }


}
