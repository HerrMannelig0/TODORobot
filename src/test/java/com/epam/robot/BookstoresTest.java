package com.epam.robot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import org.testng.annotations.Test;

import com.epam.DAO.BookstoreToDB;

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
		Set<BookstoreToDB> set = new HashSet<>();
		set.add(new BookstoreToDB("other"));
		set.add(new BookstoreToDB("gutenberg"));
		set.add(new BookstoreToDB("different"));
		BookstoreToDB result = bookstores.getBookstoreFromSet("gutenberg", set);
		assertThat(result.getName()).isEqualTo("gutenberg");
	}
	@Test
	public void testGeneratingBookstoreListFromFile() throws Exception {
		File file = new File("src/main/resources/FreeBooksAdressSite.txt");
		Set<BookstoreToDB> set = bookstores.generateBookstoreSet(file);
		for (BookstoreToDB bookstoreDB : set) {
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
