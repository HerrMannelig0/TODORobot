package com.epam.GUI.view;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * Created by aga on 25.04.16.
 */
public class ReadmeController {
    @FXML
    private TextArea readme;

    private StringBuilder readmeFile = new StringBuilder();

    /**
     * Maybe not Deprecated, but I wanted to keep that method. It results with weird exception:
     * java.nio.file.NoSuchFileException: README.md
     * It is not thrown at my home computer (Win7). Need to examine that.
     * @return
     */
    private String readReadme() {
        String result = "";
        String fileName = "README.md";

        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        //InputStream and IOUtils used because files are in resources dir. Without that you get exception java.nio.file.NoSuchFileException
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
        ReadmeController initReadme = new ReadmeController();
        String readmeContent = initReadme.readReadme();
        readme.setWrapText(true);
        System.out.println(readmeFile);
        readme.setText(readmeContent);
        readme.setEditable(false);

    }
}
