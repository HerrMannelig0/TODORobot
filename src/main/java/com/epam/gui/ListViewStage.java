package com.epam.gui;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * Created by damian on 21.03.16.
 */
public class ListViewStage {
    private final String MESSAGE_WHEN_LINE_ADDED = "Added Line";
    private final String ADD_BUTTON_LABEL = "Add Line";
    private final String REMOVE_BUTTON_LABEL = "Remove Line";

    private Stage stage;
    private ObservableList<String> observableLinkList;

    private ListView linkListView;
    private HBox hBox;

    public ListViewStage(final ObservableList<String> observableLinkList) {
        this.observableLinkList = observableLinkList;

        createButtons();

        createListView();

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(hBox);
        borderPane.setCenter(linkListView);

        Scene scene = new Scene(borderPane, 500D, 500D);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }


    @SuppressWarnings("restriction")
	private void createButtons() {
		Button addLineButton = new Button(ADD_BUTTON_LABEL);
        addLineButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                observableLinkList.add(MESSAGE_WHEN_LINE_ADDED);
            }
        });

        Button removeLineButton = new Button(REMOVE_BUTTON_LABEL);
        removeLineButton.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                int focusedIndex = linkListView.getFocusModel().getFocusedIndex();
                if (focusedIndex != -1)
                    observableLinkList.remove(focusedIndex);
            }
        });

        hBox = new HBox();
        hBox.getChildren().addAll(addLineButton, removeLineButton);
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setSpacing(40D);
    }

    private void createListView() {
        linkListView = new ListView();
        linkListView.setEditable(true);
        linkListView.setItems(observableLinkList);
        linkListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        linkListView.setCellFactory(new Callback<ListView, ListCell>() {
            public ListCell call(ListView param) {
                return new TextCellList();
            }
        });
    }
}
