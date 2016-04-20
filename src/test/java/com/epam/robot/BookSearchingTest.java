package com.epam.robot;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.assertj.core.api.SoftAssertions;
import org.hibernate.mapping.Collection;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.epam.DAO.Bookstore;

public class BookSearchingTest {

	
		BookTitleSearch bookTitleSearch = new BookTitleSearch();
	
	
	@Test
	public void testCreatingKeywords() throws Exception {
		String valueFromSite = "Keywords: Arrange Marriage, Drama, Werewolf, Hate, Vampire, Cursing, Marriage, Family";
		String[] expectedTab = {"arrange marriage", "drama", "werewolf", "hate", "vampire", 
				"cursing", "marriage", "family"
				};
		Set<String> expected = Arrays.asList(expectedTab).stream().collect(Collectors.toCollection(HashSet::new));
		
		Keywords keywords = bookTitleSearch.extractKeywords(valueFromSite);
		assertThat(keywords.contains(expected)).isTrue();
	}
	
	@Test
	public void testIfStringStartsWithKeyword() throws Exception {
		String valueFromSite = "Keywords: Arrange Marriage, Drama, Werewolf, Hate, Vampire, Cursing, Marriage, Family";
		boolean result = bookTitleSearch.areKeywords(valueFromSite);
		assertThat(result).isTrue();
	}
	
	@Test
	public void testIfStringNotStartsWithKeyword() throws Exception {
		String valueFromSite = "Any other string";
		boolean result = bookTitleSearch.areKeywords(valueFromSite);
		assertThat(result).isFalse();
	}
	
	@Test
	public void testOmmitingByInAuthorWnenItOccures() throws Exception {
		String testString = "By: Any Author";
		String expected = "Any Author";
		String result = bookTitleSearch.omitByInAuthor(testString);
		assertThat(result).isEqualTo(expected);
	}
	
	@Test
	public void testOmmitingByInAuthorWnenItNotOccures() throws Exception {
		String testString = "Any Author";
		String expected = "Any Author";
		String result = bookTitleSearch.omitByInAuthor(testString);
		assertThat(result).isEqualTo(expected);
	}
	
	@Test
	public void testOmmitingByInAuthorInShortString() throws Exception {
		String testString = "JJ";
		String expected = "JJ";
		String result = bookTitleSearch.omitByInAuthor(testString);
		assertThat(result).isEqualTo(expected);
	}
	
	@Test
	public void testSearchingBooksInPage() throws Exception {
		String urlString = "http://www.bookrix.com/books;page:28.html";
		String[] tags = new String[]{"item", "item-title", "item-author", "item-price", "item-keywords"};
		Library library = bookTitleSearch.searchTitles(urlString, tags[0], tags[1], tags[2], tags[3], tags[4]);
		assertThat(library.size()).isGreaterThan(0);	
	}
	
	@Test
	public void testSearchingBooksInPageAndSubpages() throws Exception {
		String urlString = "http://www.bookrix.com/books;page:28.html";
		String[] tags = new String[]{"item", "item-title", "item-author", "item-price", "item-keywords"};
		Library library = bookTitleSearch.searchTitlesInPageAndSubPages(urlString, tags[0], tags[1], tags[2], tags[3], tags[4]);
		assertThat(library.size()).isGreaterThan(0);	
	}

	
	
	
}
