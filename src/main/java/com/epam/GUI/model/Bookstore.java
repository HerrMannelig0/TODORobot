package com.epam.GUI.model;

import java.util.ArrayList;

import com.epam.DAO.HibernateUtil;
import com.epam.DAO.DBDAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Used in @{@link com.epam.GUI.view.BookstoreOverviewController} to create list of bookstores listed in GUI
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
        return (ArrayList<String>) dao.listOfBookstoresForGUI((new HibernateUtil()));
    }


}
