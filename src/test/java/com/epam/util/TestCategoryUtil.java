package com.epam.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import java.util.Set;

import org.testng.annotations.Test;

import com.epam.file.Category;
import com.epam.library.Book;
import com.epam.library.Keywords;
import com.epam.library.LibrariesMap;
import com.epam.library.Library;

public class TestCategoryUtil {

	@Test
	public void testGeneratingMapFromLibrary() throws Exception {
		Library library = new Library();
		Book book1 = new Book("title1", "author1", new Keywords(new String[]{"love"}));
		Book book2 = new Book("title2", "author2", new Keywords(new String[]{"bad"}));
		Book book3 = new Book("title3", "author3", new Keywords(new String[]{"ugly"}));
		
		book1.setBookCategory(new Category("Romance"));
		book2.setBookCategory(new Category("Romance"));
		book3.setBookCategory(new Category("Horror"));
		
		
		library.add(book1);
		library.add(book2);
		library.add(book3);
		
		Map<Category, Set<Book>> map = CategoryUtil.generateMapFromAllBooksLibrary(library);
		assertThat(map.size()).isEqualTo(2);
	}
	
	@Test
	public void testGeneratingLibrariesMapFromLibrary() throws Exception {
		Library library = new Library();
		Book book1 = new Book("title1", "author1", new Keywords(new String[]{"love"}));
		Book book2 = new Book("title2", "author2", new Keywords(new String[]{"bad"}));
		Book book3 = new Book("title3", "author3", new Keywords(new String[]{"ugly"}));
		
		book1.setBookCategory(new Category("Romance"));
		book2.setBookCategory(new Category("Romance"));
		book3.setBookCategory(new Category("Horror"));
		
		
		library.add(book1);
		library.add(book2);
		library.add(book3);
		
		LibrariesMap map = CategoryUtil.generateLibrariesMapfromLibrary(library);
		assertThat(map.size()).isEqualTo(2);
	}
	
}
