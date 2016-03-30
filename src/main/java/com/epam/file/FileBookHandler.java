package com.epam.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created by damian on 23.03.16.
 */
public class FileBookHandler {

    private String fileName;
    private File freeBooksTitlesUrl;
    private StringBuilder bookTitles;

    public FileBookHandler(String fileName) {
        this.fileName = fileName;
        this.bookTitles = new StringBuilder();

        loadFile();
        readBookTitlesFromFile();
    }

    private void loadFile() {
        ClassLoader classLoader = getClass().getClassLoader();
        freeBooksTitlesUrl = new File(classLoader.getResource(fileName).getFile());
    }

    public String readBookTitlesFromFile() {
        try (Scanner scanner = new Scanner(freeBooksTitlesUrl)) {

            while (scanner.hasNextLine()) {
                bookTitles.append(scanner.nextLine() + "\n");
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bookTitles.toString();
    }

    public void writeBookTitlesToFile(String title, PrintWriter printWriter) {
            printWriter.print(title);
            printWriter.flush();
    }
    
    /*public void writeBookTitlesToFile(String title) {
        try (PrintWriter printWriter = new PrintWriter("src/main/resources/" + fileName)){
			printWriter.print(title);
			printWriter.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }*/
}