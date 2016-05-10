package com.epam.robot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Test;

import com.epam.DAO.BookstoreToDB;
import com.epam.DB.entities.BookstoreDB;

public class BookstoresTest {

	Bookstores bookstores = new Bookstores();
	
	@Test
	public void testExtractingBookstoreName() throws Exception {
		String url = "https://www.gutenberg.org/ebooks/search/?query=free+book&go=Go";
		String expected = "gutenberg";
		
		String result = bookstores.extractBookstoreName(url);
		assertThat(result).isEqualTo(expected);
	}
	
	@Test
	public void testGettingBookstoreFrmBookstoresSet() throws Exception {
		Set<BookstoreDB> set = new HashSet<>();
		set.add(new BookstoreDB("other"));
		set.add(new BookstoreDB("gutenberg"));
		set.add(new BookstoreDB("different"));
		BookstoreDB result = bookstores.getBookstoreFromSet("gutenberg", set);
		assertThat(result.getName()).isEqualTo("gutenberg");
	}
	
	@Test
	public void testGeneratingBookstoreListFromFile() throws Exception {
		File file = new File("src/main/resources/FreeBooksAdressSite.txt");
		Set<BookstoreDB> set = bookstores.generateBookstoreSet(file);
		for (BookstoreDB bookstoreDB : set) {
			System.out.println(bookstoreDB.getName());
		}
		assertThat(set.size()).isGreaterThan(0);
	}
	
	@Test
	public void testBookstoreGettingFromBook() throws Exception {
		Book book = mock(Book.class);
		when(book.getUrl()).thenReturn(new URL("http://www.gutenberg.com"));
		String result = bookstores.extractBookstoreName(book);
		assertThat(result).isEqualTo("gutenberg");
		
	}
}
