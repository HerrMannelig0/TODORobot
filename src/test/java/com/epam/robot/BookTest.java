package com.epam.robot;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

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
	
	@Test
	public void testAssigningProperCategory() {
		Book book = new Book("title", "author", new Keywords(new String[]{"love"}));
		Category category = mock(Category.class);
		
		
		HashSet<String> romanceSet = new HashSet<>();
		romanceSet.add("love");
		
		HashSet<String> itSet = new HashSet<>();
		itSet.add("computer");
		
		HashSet<String> comedySet = new HashSet<>();
		comedySet.add("fun");
		
		HashSet<String> horrorSet = new HashSet<>();
		horrorSet.add("scary");
		
		String[] names = {"IT", "Comedy", "Romance", "Horror"};
		
		Category[] categories = new Category[4];
		
		for(int i=0; i<names.length; i++){
			categories[i] = mock(Category.class);
		}
					
		when(categories[0].getKeywords()).thenReturn(itSet);
		when(categories[0].getCategory()).thenReturn("IT");
		
		when(categories[1].getKeywords()).thenReturn(comedySet);
		when(categories[1].getCategory()).thenReturn("Comedy");
		
		when(categories[2].getKeywords()).thenReturn(romanceSet);
		when(categories[2].getCategory()).thenReturn("Romance");
		
		when(categories[3].getKeywords()).thenReturn(horrorSet);
		when(categories[3].getCategory()).thenReturn("Horror");
		
		String resultCategory = book.assignCategory(categories);
		String expectedCategory = "Romance";
		
		assertThat(resultCategory).isEqualTo(expectedCategory);
	}
	
	@Test
	public void testAssigningNoCategory() {
		Book book = new Book("title", "author", new Keywords(new String[]{"other"}));
		Category category = mock(Category.class);
		
		HashSet<String> romanceSet = new HashSet<>();
		romanceSet.add("love");
		
		HashSet<String> itSet = new HashSet<>();
		itSet.add("computer");
		
		HashSet<String> comedySet = new HashSet<>();
		comedySet.add("fun");
		
		HashSet<String> horrorSet = new HashSet<>();
		horrorSet.add("scary");
		
		String[] names = {"IT", "Comedy", "Romance", "Horror"};
		
		Category[] categories = new Category[4];
		
		for(int i=0; i<names.length; i++){
			categories[i] = mock(Category.class);
		}
					
		when(categories[0].getKeywords()).thenReturn(itSet);
		when(categories[0].getCategory()).thenReturn("IT");
		
		when(categories[1].getKeywords()).thenReturn(comedySet);
		when(categories[1].getCategory()).thenReturn("Comedy");
		
		when(categories[2].getKeywords()).thenReturn(romanceSet);
		when(categories[2].getCategory()).thenReturn("Romance");
		
		when(categories[3].getKeywords()).thenReturn(horrorSet);
		when(categories[3].getCategory()).thenReturn("Horror");
		
		String resultCategory = book.assignCategory(categories);
		String expectedCategory = "No category";
		
		assertThat(resultCategory).isEqualTo(expectedCategory);
	}
	
}
