package com.epam.robot;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.epam.file.Category;

public class LibraryTest {

	@Test
	public void testAssigningCategoryForAllBooksInLibrary() throws IOException{
		
		Library library = new Library();
		
		Book book1 = new Book("title1", "author1", new Keywords(new String[]{"love"}));
		Book book2 = new Book("title2", "author2", new Keywords(new String[]{"keyword2"}));
		Book book3 = new Book("title3", "author3", new Keywords(new String[]{"keyword1"}));
		
		library.add(book1);
		library.add(book2);
		library.add(book3);
		
		File file = new File("src/main/resources/Keywords/Categories.txt");
		
		Category category1 = mock(Category.class);
		Category category2 = mock(Category.class);
		
		HashSet<String> keywords1 = new HashSet<>();
		HashSet<String> keywords2 = new HashSet<>();
		
		keywords1.add("keyword1");
		keywords2.add("keyword2");
		
		when(category1.getKeywords()).thenReturn(keywords1);
		when(category2.getKeywords()).thenReturn(keywords2);
		
		List<Category> categories = new ArrayList<>();
		categories.add(category1);
		categories.add(category2);
		
		library.assignCategoryForAllBooks();
		
		SoftAssert softAssert = new SoftAssert();
		for (Book book : library) {
			softAssert.assertFalse(book.bookCategory == null);
		}
	
		softAssert.assertAll();
		
	}
	
	
}
