package com.epam.robot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Test;

import com.epam.DB.entities.Bookstore;

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
		Set<Bookstore> set = new HashSet<>();
		set.add(new Bookstore("other"));
		set.add(new Bookstore("gutenberg"));
		set.add(new Bookstore("different"));
		Bookstore result = bookstores.getBookstoreFromSet("gutenberg", set);
		assertThat(result.getBookstorename()).isEqualTo("gutenberg");
	}
	
	@Test
	public void testAddingBookToBookstoreInBookstoresList() throws Exception {
		Bookstore gutenberg = new Bookstore("gutenberg");
		Bookstore next = new Bookstore("next");
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
		
		for (Bookstore bookstore : bookstores) {
			boolean result = bookstore.contains(book.convertToBookDAO());
			if(bookstore.getBookstorename() == bookstoreName) soft.assertThat(result).isTrue();
		}
		soft.assertAll();
	}
	
	@Test
	public void testGeneratingBookstoreListFromFile() throws Exception {
		File file = new File("src/main/resources/FreeBooksAdressSite.txt");
		Set<Bookstore> set = bookstores.generateBookstoreSet(file);
		for (Bookstore bookstore : set) {
			System.out.println(bookstore.getBookstorename());
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
