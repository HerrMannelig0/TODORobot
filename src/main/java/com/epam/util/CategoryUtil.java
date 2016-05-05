package com.epam.util;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.epam.file.Category;
import com.epam.robot.Book;
import com.epam.robot.LibrariesMap;
import com.epam.robot.Library;

public class CategoryUtil {

	public static LibrariesMap generateLibrariesMapfromLibrary(Library library){
		return LibrariesMap.generateFrom(generateMapFromAllBooksLibrary(library));
	}

	static Map<Category, List<Book>> generateMapFromAllBooksLibrary(Library library) {
		
		Library properLib = new Library();
		for (Book book : library) {
			if(book.getBookCategory() != null) properLib.add(book);
		}
		
		Map<Category, List<Book>> map = properLib.stream().collect(Collectors.groupingBy(Book::getBookCategory));
		
		
		return map;
	}
}
