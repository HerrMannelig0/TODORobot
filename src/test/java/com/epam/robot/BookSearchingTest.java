package com.epam.robot;
import static org.assertj.core.api.Assertions.*;
import org.testng.annotations.Test;

public class BookSearchingTest {

	@Test
	public void testCreatingKeywordsTable() throws Exception {
		String valueFromSite = "Keywords: Arrange Marriage, Drama, Werewolf, Hate, Vampire, Cursing, Marriage, Family";
		String[] expected = {"Arrange Marriage", "Drama", "Werewolf", "Hate", "Vampire", 
				"Cursing", "Marriage", "Family"
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
}
