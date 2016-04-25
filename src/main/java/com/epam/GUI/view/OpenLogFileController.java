package com.epam.GUI.view;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * Created by aga on 25.04.16.
 */
public class OpenLogFileController {
    @FXML
    private TextArea logFileArea;

    private StringBuilder logFile = new StringBuilder();

    private String readLogs() {
        String result = "";
        String fileName = "resources/logs.log";

        //read file into stream, try-with-resources
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {

            stream.forEach(e -> logFile.append(e + "\n"));
            result = logFile.toString();
            return result;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @FXML
    void initialize() {
        OpenLogFileController initReadme = new OpenLogFileController();
        String logFileContent = initReadme.readLogs();
        logFileArea.setWrapText(true);
        logFileArea.setText(logFileContent);
        logFileArea.setEditable(false);

    }
}
