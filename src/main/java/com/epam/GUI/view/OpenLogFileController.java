package com.epam.GUI.view;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import org.apache.commons.io.IOUtils;
import sun.nio.ch.IOUtil;

import java.io.IOException;
import java.io.InputStream;
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
        String fileName = "logging.log";
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        //read file into stream, try-with-resources
        try (InputStream stream = classloader.getResourceAsStream(fileName)) {
            result = IOUtils.toString(stream);
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
