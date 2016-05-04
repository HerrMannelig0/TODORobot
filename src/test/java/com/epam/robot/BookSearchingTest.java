package com.epam.robot;

import org.testng.annotations.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * Tests of BookTitleSearch class
 */
public class BookSearchingTest {

	
		private BookTitleSearch bookTitleSearch = new BookTitleSearch();
	
	
	/**
	 * Test of creating keywords from given string table
	 */
	@Test
	public void testCreatingKeywords() {
		String valueFromSite = "Keywords: Arrange Marriage, Drama, Werewolf, Hate, Vampire, Cursing, Marriage, Family";
		String[] expectedTab = {"arrange marriage", "drama", "werewolf", "hate", "vampire", 
				"cursing", "marriage", "family"
				};
		Set<String> expected = Arrays.asList(expectedTab).stream().collect(Collectors.toCollection(HashSet::new));
		
		Keywords keywords = bookTitleSearch.extractKeywords(valueFromSite);
		assertThat(keywords.contains(expected)).isTrue();
	}
	
	/**
	 * Test of {@code areKeywords()} method - {@code true} branch
	 */
	@Test
	public void testIfStringStartsWithKeyword() throws Exception {
		String valueFromSite = "Keywords: Arrange Marriage, Drama, Werewolf, Hate, Vampire, Cursing, Marriage, Family";
		boolean result = bookTitleSearch.areKeywords(valueFromSite);
		assertThat(result).isTrue();
	}
	
	/**
	 * Test of {@code areKeywords()} method - {@code false} branch
	 */
	@Test
	public void testIfStringNotStartsWithKeyword() throws Exception {
		String valueFromSite = "Any other string";
		boolean result = bookTitleSearch.areKeywords(valueFromSite);
		assertThat(result).isFalse();
	}
	
	/**
	 * Test of omitting {@code By:} when it occurs
	 */
	@Test
	public void testOmmitingByInAuthorWnenItOccures() throws Exception {
		String testString = "By: Any Author";
		String expected = "Any Author";
		String result = bookTitleSearch.omitByInAuthor(testString);
		assertThat(result).isEqualTo(expected);
	}
	
	/**
	 * Test of omitting {@code By:} when it not occurs
	 */
	@Test
	public void testOmmitingByInAuthorWnenItNotOccures() throws Exception {
		String testString = "Any Author";
		String expected = "Any Author";
		String result = bookTitleSearch.omitByInAuthor(testString);
		assertThat(result).isEqualTo(expected);
	}
	
	/**
	 * Test of omitting {@code By:} when the string is too short
	 */
	@Test
	public void testOmmitingByInAuthorInShortString() throws Exception {
		String testString = "JJ";
		String expected = "JJ";
		String result = bookTitleSearch.omitByInAuthor(testString);
		assertThat(result).isEqualTo(expected);
	}
	
	/**
	 * Test of searching books on given page with {@code searchTitle()} method
	 */
	@Test
	public void testSearchingBooksInPage() throws Exception {
		String urlString = "http://www.bookrix.com/books;page:28.html";
		String[] tags = new String[]{"item", "item-title", "item-author", "item-price", "item-keywords"};
		Library library = bookTitleSearch.searchTitles(urlString, tags[0], tags[1], tags[2], tags[3], tags[4]);
		assertThat(library.size()).isGreaterThan(0);	
	}
	
	/**
	 * Test of searching books on given page and its subpages with {@code searchTitleInPageAndSubpages()} method
	 */
	@Test
	public void testSearchingBooksInPageAndSubpages() throws Exception {
		String urlString = "http://www.bookrix.com/books;page:28.html";
		String[] tags = new String[]{"item", "item-title", "item-author", "item-price", "item-keywords"};
		Library library = bookTitleSearch.searchTitlesInPageAndSubPages(urlString, tags[0], tags[1], tags[2], tags[3], tags[4]);
		assertThat(library.size()).isGreaterThan(0);	
	}

	@Test
	public void testParsingHTMLToDoc() throws Exception {
		String address = "https://www.gutenberg.org/ebooks/search/?query=free+book&go=Go";
		Document doc = Jsoup.parse(new URL(address), 100000);
	}
	
	
}
