package com.epam.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.epam.file.Category;
import com.epam.library.Book;
import com.epam.library.LibrariesMap;
import com.epam.library.Library;

public class CategoryUtil {

	public static LibrariesMap generateLibrariesMapfromLibrary(Set<Book> library){
		
		return LibrariesMap.generateFrom(generateMapFromAllBooksLibrary(library));
	}

	static Map<Category, Set<Book>> generateMapFromAllBooksLibrary(Set<Book> library) {
		
		Library properLib = new Library();
		for (Book book : library) {
			if(book.getBookCategory() == null) book.setBookCategory(new Category("No category"));
			if(book.getBookCategory() != null) properLib.add(book);
		}
		
		Map<Category, List<Book>> map = properLib.stream().collect(Collectors.groupingBy(Book::getBookCategory));
		
		Map<Category, Set<Book>> resultMap = new HashMap<>();
		
		for (Map.Entry<Category, List<Book>> entry : map.entrySet()) {
			Set<Book> set = new HashSet<>();
			for (Book book : entry.getValue()) {
				set.add(book);
			}
			resultMap.put(entry.getKey(), set);
		}
		
		return resultMap;
	}
}
