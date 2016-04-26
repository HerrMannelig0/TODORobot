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
    @Deprecated
    private String readReadme() {
        String result = "";
        String fileName = "README.md";

        //read file into stream, try-with-resources
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {

            stream.forEach(e -> readmeFile.append(e + "\n"));
            result = readmeFile.toString();
            return result;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private String readReadmeImper (){
        String result = "kotek";

        return result;
    }

    @FXML
    void initialize() {
        ReadmeController initReadme = new ReadmeController();
        String readmeContent = initReadme.readReadmeImper();
        readme.setWrapText(true);
        System.out.println(readmeFile);
        readme.setText(readmeContent);
        readme.setEditable(false);

    }
}
