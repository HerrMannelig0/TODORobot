package com.epam.DAO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import static org.assertj.core.api.Assertions.assertThat;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

public class AddingCategoriesTest {
	ArrayList <String> expected = new ArrayList<>();

	@Test
	public void ifListContainsCategoriesFromFile() throws FileNotFoundException, IOException {

		expectedListOfCategories();
		ArrayList <String> givenList = (ArrayList<String>)AddingCategories.keywordFetcher();
		
		Assertions.assertThat(givenList).containsAll(expected);
		assertThat(givenList).containsAll(expected);
	}

	private void expectedListOfCategories() {
		expected.add("Romance");		
		expected.add("IT");
		expected.add("Fantasy");
	}
	
}
