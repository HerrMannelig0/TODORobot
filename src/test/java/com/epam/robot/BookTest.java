package com.epam.robot;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;

import org.testng.annotations.Test;

import com.epam.file.Category;

public class BookTest {

	@Test
	public void testCategoryCheckingGivesRealCategory() throws Exception {
		Book book = new Book("title", "author", new Keywords(new String[]{"love"}));
		HashSet<String> set = new HashSet<>();
		set.add("love");
		Category romanceCategory = mock(Category.class);
		
		when(romanceCategory.getKeywords()).thenReturn(set);
		when(romanceCategory.getCategory()).thenReturn("Romance");
		String realCategory = book.checkCategory(romanceCategory);
		
		verify(romanceCategory).getCategory();
		assertThat(realCategory).isEqualTo("Romance");
	}
	
	@Test
	public void testCategoryCheckingGivesFalseCategory() throws Exception {
		Book book = new Book("title", "author", new Keywords(new String[]{"love"}));
		HashSet<String> set = new HashSet<>();
		set.add("hate");
		Category romanceCategory = mock(Category.class);
		
		when(romanceCategory.getKeywords()).thenReturn(set);
		when(romanceCategory.getCategory()).thenReturn("Romance");
		String realCategory = book.checkCategory(romanceCategory);
		
		verify(romanceCategory, times(0)).getCategory();
		assertThat(realCategory).isEqualTo("No category");
	}
	

	
}
