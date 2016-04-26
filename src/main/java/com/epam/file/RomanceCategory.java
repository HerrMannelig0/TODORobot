package com.epam.file;

import java.util.Arrays;
import java.util.HashSet;

public class RomanceCategory  {
	
	static HashSet<String> keywords;
	
	public RomanceCategory() {
		keywords = new HashSet<>();
	}
	
	public void create(){
		RomanceCategory romanceCategory = new RomanceCategory();
		romanceCategory.addKeyword("love");
	}

	public boolean containsKeyword(String keywordToCheck) {
		return keywords.contains(keywordToCheck);
	}

	public void addKeyword(String... category) {
		keywords.addAll(Arrays.asList(category));		
	}

}
