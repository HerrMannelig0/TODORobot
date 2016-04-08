package com.epam.robot;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Keywords {
	private Set <String> keywords;
	
	public Keywords(String [] keywordsTable){
		keywords = new HashSet<>(Arrays.asList(keywordsTable));
	}
	
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
