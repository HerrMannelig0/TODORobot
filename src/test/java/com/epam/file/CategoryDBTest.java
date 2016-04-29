package com.epam.file;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;


public class CategoryDBTest {
	
	@Test
	public void testAddingKeywordToRomanceCategory() throws Exception {
		Category romanceCategory = new Category("Romance", "a");
		romanceCategory.addKeyword("romance");
		
		String keywordToCheck = "romance";
		
		boolean result = romanceCategory.containsKeyword(keywordToCheck);
		
		assertThat(result).isTrue();
	}
	
	@Test
	public void testRomanceCategoryCreation() throws Exception {
		Category romanceCategory = new Category("Romance", "src/main/resources/Keywords/Romance.txt");
		romanceCategory.create();
		String keywordToCheck = "love";
		
		boolean result = romanceCategory.containsKeyword(keywordToCheck);
		
		assertThat(result).isTrue();
	}
	
	@Test
	public void testRomanceCategoryCreationNotContainsFalseKeyword() throws Exception {
		Category romanceCategory = new Category("Romance", "src/main/resources/Keywords/Romance.txt");
		romanceCategory.create();
		String keywordToCheck = "randomKeyword";
		
		boolean result = romanceCategory.containsKeyword(keywordToCheck);
		
		assertThat(result).isFalse();
	}
}
