package com.epam.library;

import java.util.HashSet;
import java.util.Set;
import static org.assertj.core.api.Assertions.*;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.epam.library.Keywords;

/**
 * Tests of utilities connected with Keywords
 *
 */
public class KeywordsTest {

	/**
	 * Test if {@code contains()} method returns true if we pass a proper keywords as a parameter
	 */
	@Test
	public void testContainsMethodReturnsTrueWhenItShould() throws Exception {
		String [] keywordsTable = {"love"};
		Keywords keywords = new Keywords(keywordsTable);
		Set<String> keywordsToCheck = new HashSet<>();
		keywordsToCheck.add("love");
		
		boolean keywordsContainLove = keywords.contains(keywordsToCheck);
		assertThat(keywordsContainLove).isTrue();
	}
	
	/**
	 * Test if {@code contains()} method returns false if we pass a wrong keywords as a parameter
	 */
	@Test
	public void testContainsMethodReturnsFalseWhenItShould() throws Exception {
		String [] keywordsTable = {"love"};
		Keywords keywords = new Keywords(keywordsTable);
		Set<String> keywordsToCheck = new HashSet<>();
		keywordsToCheck.add("hate");
		
		boolean keywordsContainLove = keywords.contains(keywordsToCheck);
		assertThat(keywordsContainLove).isFalse();
	}
	
	
	@DataProvider(name = "keywords")
	public Object[][] keywords(){
		Keywords k1 = new Keywords(new String[]{"uno", "dos", "three"});
		Keywords k2 = new Keywords(new String[]{"", "", ""});
		Keywords k3 = new Keywords(new String[]{});
		return new Object[][]{
			{k1}, {k2}, {k3}
		};
	}
	
	@Test(dataProvider = "keywords")
	public void testIneqality(Keywords otherKeywords){
		Keywords keywords = new Keywords(new String[]{"uno", "dos", "quatro"});
		assertThat(keywords).isNotEqualTo(otherKeywords);
		
	}
	
	@Test
	public void testEquality() {
		Keywords keywords1 = new Keywords(new String[]{"uno", "dos", "quatro"});
		Keywords keywords2 = new Keywords(new String[]{"uno", "dos", "quatro"});
		
		assertThat(keywords1).isEqualTo(keywords2);
	}
}
