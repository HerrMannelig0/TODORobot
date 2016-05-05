package com.epam.robot;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.epam.DB.entities.BookDB;
import com.epam.file.Category;

/**
 * Tests of Book class
 *
 */
public class BookTest {

	/**
	 * Test of category checking. It should returns a proper category because a proper keyword is passing
	 */
	@Test
	public void testCategoryCheckingGivesRealCategory() throws Exception {
		Book book = new Book("title", "author", new Keywords(new String[]{"love"}));
		HashSet<String> set = new HashSet<>();
		set.add("love");
		Category romanceCategory = mock(Category.class);
		
		when(romanceCategory.getKeywords()).thenReturn(set);
		when(romanceCategory.getName()).thenReturn("Romance");
		Category realCategory = book.checkCategory(romanceCategory);
		
		verify(romanceCategory).getName();
		assertThat(realCategory).isEqualTo("Romance");
	}
	
	/**
	 * Test of category checking. It should returns a proper category because a proper keyword is passing
	 */
	@Test
	public void testCategoryCheckingGivesFalseCategory() throws Exception {
		Book book = new Book("title", "author", new Keywords(new String[]{"love"}));
		HashSet<String> set = new HashSet<>();
		set.add("hate");
		Category romanceCategory = mock(Category.class);
		
		when(romanceCategory.getKeywords()).thenReturn(set);
		when(romanceCategory.getName()).thenReturn("Romance");
		Category realCategory = book.checkCategory(romanceCategory);
		
		verify(romanceCategory, times(0)).getName();
		assertThat(realCategory).isEqualTo("No category");
	}
	
	/**
	 * Test of assigning category to a book.
	 */
	@Test
	public void testAssigningProperCategory() {
		Book book = new Book("title", "author", new Keywords(new String[]{"love"}));
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
		when(categories[0].getName()).thenReturn("IT");
		
		when(categories[1].getKeywords()).thenReturn(comedySet);
		when(categories[1].getName()).thenReturn("Comedy");
		
		when(categories[2].getKeywords()).thenReturn(romanceSet);
		when(categories[2].getName()).thenReturn("Romance");
		
		when(categories[3].getKeywords()).thenReturn(horrorSet);
		when(categories[3].getName()).thenReturn("Horror");
		
		Category resultCategory = book.assignCategory(Arrays.asList(categories));
		String expectedCategory = "Romance";
		
		assertThat(resultCategory).isEqualTo(expectedCategory);
	}
	
	/**
	 * Test of assigning no category to a book
	 */
	@Test
	public void testAssigningNoCategory() {
		Book book = new Book("title", "author", new Keywords(new String[]{"other"}));
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
		when(categories[0].getName()).thenReturn("IT");

		when(categories[1].getKeywords()).thenReturn(comedySet);
		when(categories[1].getName()).thenReturn("Comedy");

		when(categories[2].getKeywords()).thenReturn(romanceSet);
		when(categories[2].getName()).thenReturn("Romance");

		when(categories[3].getKeywords()).thenReturn(horrorSet);
		when(categories[3].getName()).thenReturn("Horror");
		
		
		
		Category resultCategory = book.assignCategory(Arrays.asList(categories));
		Category expectedCategory = new Category("No category");
		
		assertThat(resultCategory).isEqualTo(expectedCategory);
	}
	
	/**
	 * Test of creating list of categories
	 */
	@Test
	public void testCategoriesListCreator() throws Exception {
		Book book = new Book("anyTile", "anyAuthor", new Keywords(new String[]{"vamipre"}));
		
		File file = new File("src/main/resources/Keywords/Categories.txt");
		
		List<Category> categories = book.createCategoryList(file);
		assertThat(categories.size()).isGreaterThan(0);
	}
	
	/**
	 * Test of assigning category from list to a book
	 * @throws IOException 
	 */
	@Test
	public void testAssignCategoryToBook() throws IOException{
		Book book = new Book("anyTile", "anyAuthor", new Keywords(new String[]{"vampire"}));
		File file = new File("src/main/resources/Keywords/Categories.txt");
		List<Category> list = book.createCategoryList(file);
		Category category = book.assignCategory(list);
		assertThat(category).isEqualTo(new Category("Fantasy"));
	}
	
	/**
	 * Test of converting book to bookDAO
	 */
	@Test
	public void testConvertingToBookDAO() throws Exception {
		String title = "anyTile";
		String author = "anyAuthor";
		Book book = new Book(title, author, new Keywords(new String[]{"vampire"}));
		File file = new File("src/main/resources/Keywords/Categories.txt");
		Category category = book.assignCategory(book.createCategoryList(file));
		BookDB bookDB = book.convertToBookDB();
		SoftAssert softAssert = new SoftAssert();
		softAssert.assertEquals(bookDB.getTitle(), title);
		softAssert.assertEquals(bookDB.getAuthor(), author);
		softAssert.assertEquals(bookDB.getCategory(), category);
		softAssert.assertAll();
	}
	
}
