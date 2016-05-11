package com.epam.file;

import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Tests of {@code CategoryBD} class
 *
 */
public class CategoryDBTest {
	
	/**
	 * Test of adding keyword to category
	 */
	@Test
	public void testAddingKeywordToRomanceCategory() throws Exception {
		Category romanceCategory = new Category("Romance", "a");
		romanceCategory.addKeyword("romance");
		
		String keywordToCheck = "romance";
		
		boolean result = romanceCategory.containsKeyword(keywordToCheck);
		
		assertThat(result).isTrue();
	}
	
	/**
	 * Test of crating new category
	 */
	@Test
	public void testRomanceCategoryCreation() throws Exception {
		Category romanceCategory = new Category("Romance", "src/main/resources/Keywords/Romance.txt");
		romanceCategory.create();
		String keywordToCheck = "love";
		
		boolean result = romanceCategory.containsKeyword(keywordToCheck);
		
		assertThat(result).isTrue();
	}
	
	/**
	 * Test if created category does not contains wrong keywords
	 */
	@Test
	public void testRomanceCategoryCreationNotContainsFalseKeyword() throws Exception {
		Category romanceCategory = new Category("Romance", "src/main/resources/Keywords/Romance.txt");
		romanceCategory.create();
		String keywordToCheck = "randomKeyword";
		
		boolean result = romanceCategory.containsKeyword(keywordToCheck);
		
		assertThat(result).isFalse();
	}
}
