package com.epam.robot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import com.epam.DB.entities.BookstoreDB;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Test;

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
	public void testAddingBookToBookstoreInBookstoresList() throws Exception {
		BookstoreDB gutenberg = new BookstoreDB("gutenberg");
		BookstoreDB next = new BookstoreDB("next");
		gutenberg.init();
		next.init();
		bookstores.add(gutenberg);
		bookstores.add(next);
		
		String url = "https://www.gutenberg.org/ebooks/search/?query=free+book&go=Go";
		String bookstoreName = bookstores.extractBookstoreName(url);
		Book book = new Book("title", "author", "Free", new Keywords(new String[]{"love"}), new URL(url));
		bookstores.addBookToBookstoreInBookstoresList(url, book);
		SoftAssertions soft = new SoftAssertions();
		soft.assertThat(bookstores.bookstores.size()).isGreaterThan(0);
		
		for (BookstoreDB bookstoreDB : bookstores) {
			boolean result = bookstoreDB.contains(book.convertToBookDAO());
			if(bookstoreDB.getName() == bookstoreName) soft.assertThat(result).isTrue();
		}
		soft.assertAll();
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
