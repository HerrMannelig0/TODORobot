package com.epam.GUI.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

/**
 * Created by aga on 25.04.16.
 */
public class Bookstore {
     private ArrayList<String> bookstores = new ArrayList<>();
    public ObservableList<String> initBookstoreList = FXCollections.observableList(bookstores);

public Bookstore(){
    bookstores.add("Helion");
    bookstores.add("Fantastyka");
    bookstores.add("Świat Dysku");

}
}
