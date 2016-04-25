package com.epam.GUI.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.input.InputEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ResourceBundle;

/**
 * Created by aga on 25.04.16.
 */
public class RootLayoutController {
    @FXML
    private MenuBar menuBar;

    /**
     * Handle action related to "Readme" menu item.
     *
     * @param event Event on "Readme" menu item.
     */

    @FXML
    private void handleReadme(final ActionEvent event) {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Readme.fxml"));
            Parent root1 = (Parent) loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handle action related to "File->Open log file" menu item.
     *
     * @param event Event on "open log file" menu item.
     */
    @FXML
    private void handleOpenLogFile(final ActionEvent event) {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader(getClass().getResource("OpenLogFile.fxml"));
            Parent root2 = (Parent) loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root2));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleClose(final InputEvent event) {

    }


    @FXML
    public void initialize(java.net.URL arg0, ResourceBundle arg1) {
        //menuBar.setFocusTraversable(true);
    }

}
