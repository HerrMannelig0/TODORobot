package com.epam.robot;
import static org.assertj.core.api.Assertions.*;
import org.testng.annotations.Test;

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

}
