package com.epam.GUI.view;

/**
 * Exception helping to inform within GUI when there are no books of given categories.
 */
public class NoBooksOfGivenCategoryException extends Exception {
    final static String WARNING = "There is no book of category ";

    public NoBooksOfGivenCategoryException(String category) {
        super(WARNING + category);

    }
}
