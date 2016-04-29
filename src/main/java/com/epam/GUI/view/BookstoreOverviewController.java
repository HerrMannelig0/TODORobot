package com.epam.GUI.view;

import com.epam.GUI.model.Bookstore;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.HashSet;
import java.util.Set;
/**
 * Created by aga on 25.04.16.
 */
public class BookstoreOverviewController {
    Bookstore data = new Bookstore();
    Set <String> categoriesToShow = new HashSet<>();
    @FXML
    private CheckBox romanceCat;
    @FXML
    private CheckBox itCat;
    @FXML
    private CheckBox fantasyCat;

    @FXML // fx:id="bookstoreDBs"
    private ListView<String> bookstores; // Value injected by FXMLLoader
    private ObservableList<String> listViewBookstores = FXCollections.observableArrayList(data.initBookstoreList);


    @FXML // fx:id="activeLibraries"
    private ListView<String> activeLibraries; // books according to init list, items added/rm via buttons
    private ObservableList<String> listViewActiveBookstores = FXCollections.observableArrayList();


    @FXML // fx:id="listOfBooks"
    private TextArea listOfBooks;

    @FXML // fx:id="libraryURL"
    private TextField libraryURL;


    @FXML
    private void rightButtonAction() {
        String selectedItem = bookstores.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            listViewBookstores.remove(selectedItem);
            listViewActiveBookstores.add(selectedItem);
        }
    }

    @FXML
    private void leftButtonAction() {
        String selectedItem = activeLibraries.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            listViewActiveBookstores.remove(selectedItem);
            listViewBookstores.add(selectedItem);
        }
    }

    @FXML
    private void delButtonAction() {
        String selectedItem = bookstores.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            listViewBookstores.remove(selectedItem);
        }
    }

    @FXML
    private void addButtonAction(ActionEvent action) {
        String typedURL = libraryURL.getText();
        if (typedURL != null) {
            listViewBookstores.add(typedURL);
            libraryURL.clear();
        }
    }

    @FXML
    private void handleCheckBox(ActionEvent action) {
        if (romanceCat.isSelected()) {
            categoriesToShow.add(romanceCat.getText());
        } else categoriesToShow.remove(romanceCat.getText());
        if (fantasyCat.isSelected()) {
            categoriesToShow.add(fantasyCat.getText());

        } else categoriesToShow.remove(fantasyCat.getText());
        if (itCat.isSelected()) {
            categoriesToShow.add(itCat.getText());

        } else categoriesToShow.remove(itCat.getText());
    }


    @FXML
    private void showBooksButtonAction(ActionEvent action) {
        setText(categoriesToShow.toString());
    }
    public void setText(String text) {
        listOfBooks.setText(text);
    }

    @FXML
    void initialize() {
        bookstores.setItems(listViewBookstores);
        activeLibraries.setItems(listViewActiveBookstores);
        romanceCat.setOnAction(e -> handleCheckBox(e));
    }

}
