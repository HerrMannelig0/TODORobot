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

import com.epam.DAO.Bookstore;

public class BookSearchingTest {

	@Test
	public void testCreatingKeywordsTable() throws Exception {
		String valueFromSite = "Keywords: Arrange Marriage, Drama, Werewolf, Hate, Vampire, Cursing, Marriage, Family";
		String[] expected = {"arrange marriage", "drama", "werewolf", "hate", "vampire", 
				"cursing", "marriage", "family"
				};
		String[] keywords = BookTitleSearch.extractKeywords(valueFromSite);
		assertThat(keywords).isEqualTo(expected);
	}
	
	@Test
	public void testIfStringStartsWithKeyword() throws Exception {
		String valueFromSite = "Keywords: Arrange Marriage, Drama, Werewolf, Hate, Vampire, Cursing, Marriage, Family";
		boolean result = BookTitleSearch.areKeywords(valueFromSite);
		assertThat(result).isTrue();
	}
	
	@Test
	public void testIfStringNotStartsWithKeyword() throws Exception {
		String valueFromSite = "Any other string";
		boolean result = BookTitleSearch.areKeywords(valueFromSite);
		assertThat(result).isFalse();
	}
	
	@Test
	public void testOmmitingByInAuthorWnenItOccures() throws Exception {
		String testString = "By: Any Author";
		String expected = "Any Author";
		String result = BookTitleSearch.omitByInAuthor(testString);
		assertThat(result).isEqualTo(expected);
	}
	
	@Test
	public void testOmmitingByInAuthorWnenItNotOccures() throws Exception {
		String testString = "Any Author";
		String expected = "Any Author";
		String result = BookTitleSearch.omitByInAuthor(testString);
		assertThat(result).isEqualTo(expected);
	}
	
	@Test
	public void testOmmitingByInAuthorInShortString() throws Exception {
		String testString = "JJ";
		String expected = "JJ";
		String result = BookTitleSearch.omitByInAuthor(testString);
		assertThat(result).isEqualTo(expected);
	}
	
	@Test
	public void testSearchingBooksInPage() throws Exception {
		String urlString = "http://www.bookrix.com/books;page:28.html";
		String[] tags = new String[]{"item", "item-title", "item-author", "item-price", "item-keywords"};
		BookTitleSearch.searchTitles(urlString, tags[0], tags[1], tags[2], tags[3], tags[4]);
		assertThat(BookTitleSearch.library.size()).isGreaterThan(0);	
	}
	
	@Test
	public void testSearchingBooksInPageAndSubpages() throws Exception {
		String urlString = "http://www.bookrix.com/books;page:28.html";
		String[] tags = new String[]{"item", "item-title", "item-author", "item-price", "item-keywords"};
		BookTitleSearch.searchTitlesInPageAndSubPages(urlString, tags[0], tags[1], tags[2], tags[3], tags[4]);
		assertThat(BookTitleSearch.library.size()).isGreaterThan(0);	
	}

	@Test
	public void testExtractingBookstoreName() throws Exception {
		String url = "https://www.gutenberg.org/ebooks/search/?query=free+book&go=Go";
		String expected = "gutenberg";
		String result = BookTitleSearch.extractBookstoreName(url);
		assertThat(result).isEqualTo(expected);
	}
	
	@Test
	public void testGettingBookstoreFrmBookstoresSet() throws Exception {
		Set<Bookstore> set = new HashSet<>();
		set.add(new Bookstore("other"));
		set.add(new Bookstore("gutenberg"));
		set.add(new Bookstore("different"));
		Bookstore result = BookTitleSearch.getBookstoreFromSet("gutenberg", set);
		assertThat(result.getBookstorename()).isEqualTo("gutenberg");
	}
	
	@Test
	public void testAddingBookToBookstoreInBookstoresList() throws Exception {
		Bookstore gutenberg = new Bookstore("gutenberg");
		Bookstore next = new Bookstore("next");
		gutenberg.init();
		next.init();
		BookTitleSearch.bookstores.add(gutenberg);
		BookTitleSearch.bookstores.add(next);
		
		String url = "https://www.gutenberg.org/ebooks/search/?query=free+book&go=Go";
		String bookstoreName = BookTitleSearch.extractBookstoreName(url);
		Book book = new Book("title", "author", "Free", new Keywords(new String[]{"love"}), new URL(url));
		BookTitleSearch.addBookToBookstoreInBookstoresList(url, book);
		SoftAssertions soft = new SoftAssertions();
		soft.assertThat(BookTitleSearch.bookstores.size()).isGreaterThan(0);
		
		for (Bookstore bookstore : BookTitleSearch.bookstores) {
			boolean result = bookstore.contains(book.convertToBookDAO());
			if(bookstore.getBookstorename() == bookstoreName) soft.assertThat(result).isTrue();
		}
		soft.assertAll();
	}
	
	@Test
	public void testGeneratingBookstoreListFromFile() throws Exception {
		File file = new File("src/main/resources/FreeBooksAdressSite.txt");
		Set<Bookstore> set = BookTitleSearch.generateBookstoreSet(file);
		for (Bookstore bookstore : set) {
			System.out.println(bookstore.getBookstorename());
		}
		assertThat(set.size()).isGreaterThan(0);
	}
	
	@Test
	public void testBookstoreGettingFromBook() throws Exception {
		Book book = mock(Book.class);
		when(book.getUrl()).thenReturn(new URL("http://www.gutenberg.com"));
		String result = BookTitleSearch.extractBookstoreName(book);
		assertThat(result).isEqualTo("gutenberg");
		
	}
}
