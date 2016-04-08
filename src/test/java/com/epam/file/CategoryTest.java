package com.epam.file;

import org.testng.annotations.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import org.testng.annotations.Test;


public class CategoryTest {
	
	@Test
	public void testAddingKeywordToRomanceCategory() throws Exception {
		Category romanceCategory = new Category();
		romanceCategory.addKeyword("romance");
		
		String keywordToCheck = "romance";
		
		boolean result = romanceCategory.containsKeyword(keywordToCheck);
		
		assertThat(result).isTrue();
	}
	
	@Test
	public void testRomanceCategoryCreation() throws Exception {
		Category romanceCategory = new Category();
		romanceCategory.create();
		String keywordToCheck = "love";
		
		boolean result = romanceCategory.containsKeyword(keywordToCheck);
		
		assertThat(result).isTrue();
	}
	
	@Test
	public void testRomanceCategoryCreationNotContainsFalseKeyword() throws Exception {
		Category romanceCategory = new Category();
		romanceCategory.create();
		String keywordToCheck = "randomKeyword";
		
		boolean result = romanceCategory.containsKeyword(keywordToCheck);
		
		assertThat(result).isFalse();
	}
}
