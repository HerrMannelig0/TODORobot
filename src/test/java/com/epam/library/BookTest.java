package com.epam.library;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.epam.DB.entities.BookToDB;
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
	public void testCategoryCheckingGivesRealCategory() {
		Book book = new Book("title", "author", new Keywords(new String[]{"love"}));
		HashSet<String> set = new HashSet<>();
		set.add("love");
		
		Category romanceCategory = new Category("Romance");
		romanceCategory.addKeyword("love");
		
		Category realCategory = book.checkCategory(romanceCategory);
		
		assertThat(realCategory).isEqualTo(new Category("Romance"));
	}
	
	/**
	 * Test of category checking. It should returns a proper category because a proper keyword is passing
	 */
	@Test
	public void testCategoryCheckingGivesFalseCategory() {
		Book book = new Book("title", "author", new Keywords(new String[]{"love"}));
		HashSet<String> set = new HashSet<>();
		set.add("hate");
		Category romanceCategory = mock(Category.class);
		
		when(romanceCategory.getKeywords()).thenReturn(set);
		when(romanceCategory.getName()).thenReturn("Romance");
		Category realCategory = book.checkCategory(romanceCategory);
		
		verify(romanceCategory, times(0)).getName();
		assertThat(realCategory).isEqualTo(Category.NULL_CATEGORY);
	}
	
	/**
	 * Test of assigning category to a book.
	 */
	@Test
	public void testAssigningProperCategory() {
		Book book = new Book("title", "author", new Keywords(new String[]{"love"}));
		
		Category[] categories = new Category[]{new Category("IT"), new Category("Comedy"), new Category("Romance"), new Category("Horror")};
		categories[0].addKeyword("computer");
		categories[1].addKeyword("clown");
		categories[2].addKeyword("love");
		categories[3].addKeyword("zombie");
		
		Category resultCategory = book.assignCategory(Arrays.asList(categories));
		Category expectedCategory = new Category("Romance");
		
		assertThat(resultCategory).isEqualTo(expectedCategory);
	}

	@Test
	public void testAssignITCategory() throws MalformedURLException {
		Book book = new Book("title", "author", "Free", new URL("http://www.allitebooks.com/"));
		List<Category> list = new ArrayList<>();
		list.add(new Category("IT"));
		list.add(new Category("Romance"));
		book.assignCategory(new ArrayList<>());
		assertThat(book.getBookCategory()).isEqualTo(new Category("IT"));
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
		Category expectedCategory = new Category("Other");
		
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
		BookToDB bookDB = book.toDB();
		SoftAssert softAssert = new SoftAssert();
		softAssert.assertEquals(bookDB.getTitle(), title);
		softAssert.assertEquals(bookDB.getAuthor(), author);
		softAssert.assertAll();
	}

	@DataProvider(name="books")
	public Object[][] books(){
		Book bookA = new Book("any other title", "author", new Keywords(new String[]{"vampire"}));
		Book bookB = new Book("", "author", new Keywords(new String[]{""}));
		Book bookC = new Book("other title", "author", new Keywords(new String[]{"love"}));
		Book bookD = new Book(null, null, new Keywords(new String[]{"vampire"}));
		Book bookE = null;

		return new Object[][]{
				{bookA}, {bookB}, {bookC}, {bookD}, {bookE}
		};
	}

	@Test(dataProvider = "books")
	public void testBookInEquality(Book book){
		Book thisBook = new Book("title", "author", new Keywords(new String[]{"keyword"}));
		assertThat(thisBook).isNotEqualTo(book);
	}
	
}
