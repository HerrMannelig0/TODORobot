package com.epam.GUI.view;

import com.epam.DB.DAO;
import com.epam.GUI.model.Bookstore;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by aga on 25.04.16.
 */
public class BookstoreOverviewController {
    Bookstore data = new Bookstore();
    Set<String> categoriesToShow = new HashSet<>();
    @FXML
    private CheckBox romanceCat;
    @FXML
    private CheckBox itCat;
    @FXML
    private CheckBox fantasyCat;
    @FXML
    private CheckBox otherCat;
    @FXML
    private CheckBox undefinedCat;
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

   @FXML // fx:id="like hell i should know"
   private Button showBooks;


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
        if (typedURL.isEmpty()) {
            loadErrorWindow("/WrongInputForURLAdding.fxml");
        } else {
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

        if (otherCat.isSelected()) {
            categoriesToShow.add(otherCat.getText());
        } else categoriesToShow.remove(otherCat.getText());

        if (undefinedCat.isSelected()) {
            categoriesToShow.add(undefinedCat.getText());
        } else categoriesToShow.remove(undefinedCat.getText());
    }


    @FXML
    private void showBooksButtonAction(MouseEvent mouseEvent) {
        if (listViewActiveBookstores.isEmpty() || categoriesToShow.isEmpty()){
            loadErrorWindow("/WrongInputForShowBooks.fxml");
        }
        listOfBooks.setText(setText(listViewActiveBookstores, categoriesToShow));
    }

    private void loadErrorWindow(String path) {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Parent root = (Parent) loader.load();
            Stage stage = new Stage();
            stage.setTitle("Error");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Method for writing queries to take from DB books from bookstores listed in listViewActiveBookstores and with category listed in categories to show.
     * @param listViewActiveBookstores
     * @param categoriesToShow
     */
    @FXML
    private String setText(ObservableList<String> listViewActiveBookstores, Set<String> categoriesToShow) {
        String result = "";
        String books;
        DAO dao = new DAO();
        for (String bookstore : listViewActiveBookstores) {
            result = result.concat(bookstore) + "\n\n";
            for (String category : categoriesToShow) {
                result = result.concat(category) + "\n";
                books = dao.listOfBooksForGUI(bookstore, category);
                result = (books.isEmpty() ? result.concat("No books to be showed.") : result.concat(books));
            }
        }
        return result;
    }


    @FXML
    void initialize() {
        bookstores.setItems(listViewBookstores);
        activeLibraries.setItems(listViewActiveBookstores);
        romanceCat.setOnAction(e -> handleCheckBox(e));
        itCat.setOnAction(e -> handleCheckBox(e));
        fantasyCat.setOnAction(e -> handleCheckBox(e));
        otherCat.setOnAction(e -> handleCheckBox(e));
        undefinedCat.setOnAction(e -> handleCheckBox(e));
        showBooks.setOnMouseClicked(e -> showBooksButtonAction(e));
    }

}
