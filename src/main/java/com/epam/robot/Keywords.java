package com.epam.robot;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Wrapper class to set of strings, which can be interpreted as book's keywords.
 * 
 * @see Book
 */
public class Keywords {
	private Set <String> keywords;
	
	public Keywords(String [] keywordsTable){
		keywords = new HashSet<>(Arrays.asList(keywordsTable));
	}
	
	/**
	 * Check if given set of strings contains one or more keywords stored in this object.
	 * @param Keywords to check
	 * @return True if keywords contains one or more keywords from given set.
	 */
	public boolean contains(Set<String> keywordsToCheck){
		for(String keyword : keywords){
			for (String keywordToCheck : keywordsToCheck) {
				if(keyword.equals(keywordToCheck)) return true;
			}
		}
		return false;
	}
	
	
	@Override
	public String toString() {
		return "<keywords: " + Arrays.toString(keywords.toArray()) + ">";
	}
	
}
