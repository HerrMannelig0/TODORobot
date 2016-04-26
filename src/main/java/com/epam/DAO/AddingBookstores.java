package com.epam.DAO;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AddingBookstores {
    private static Set<String> bookstoresList = new HashSet<>();


    static Set<String> bookstoresFetcher() {
        String fileName = "";

        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            bookstoresList = stream.collect(Collectors.toSet());
        } catch (IOException e) {
            System.err.print("File with list of Bookstores is not found.");
            e.printStackTrace();
        }
        return bookstoresList;
    }
}
