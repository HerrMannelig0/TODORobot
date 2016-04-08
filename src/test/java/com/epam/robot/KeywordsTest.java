package com.epam.robot;

import java.util.HashSet;
import java.util.Set;
import static org.assertj.core.api.Assertions.*;
import org.testng.annotations.Test;

public class KeywordsTest {

	@Test
	public void testContainsMethodReturnsTrueWhenItShould() throws Exception {
		String [] keywordsTable = {"love"};
		Keywords keywords = new Keywords(keywordsTable);
		Set<String> keywordsToCheck = new HashSet<>();
		keywordsToCheck.add("love");
		
		boolean keywordsContainLove = keywords.contains(keywordsToCheck);
		assertThat(keywordsContainLove).isTrue();
	}
	
	@Test
	public void testContainsMethodReturnsFalseWhenItShould() throws Exception {
		String [] keywordsTable = {"love"};
		Keywords keywords = new Keywords(keywordsTable);
		Set<String> keywordsToCheck = new HashSet<>();
		keywordsToCheck.add("hate");
		
		boolean keywordsContainLove = keywords.contains(keywordsToCheck);
		assertThat(keywordsContainLove).isFalse();
	}
}
