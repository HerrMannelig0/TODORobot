package com.epam.DAO;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AddingCategories {

	private static List<String> categories;

	/**
	 * @return returns list of categories from file.
	 */
	static List<String> categoriesFetcher() {
		String fileName = "src/main/resources/Keywords/Categories.txt";

		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			categories = stream.collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return categories;
	}
}