package com.epam.DAO;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by aga on 27.04.16.
 */
public class DealingBookstores {

    public static List<String> bookstores;

    public DealingBookstores() {
        this.bookstores = bookstoresFetcher();
    }

    /**
     * @return returns list of categories from file.
     */
    static List<String> bookstoresFetcher() {
        String fileName = "src/main/resources/FreeBooksAdressSite.txt";

        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            bookstores = stream.collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bookstores;
    }

    public List<String> deleteBookstore(String bookstoreName) {
        //tutaj powinna byc kwerenda delete from BOOKSTORES where bookstoreName = bookstoreName

        if (bookstores.contains(bookstoreName)) bookstores.remove(bookstoreName);

        return this.bookstores;
    }


    public List<String> addBookstore(String newBookstore) {
        if (newBookstore != null) bookstores.add(newBookstore);
        return this.bookstores;
    }
}

