package com.epam.file;

import java.util.Arrays;
import java.util.HashSet;

public class RomanceCategory implements Category {
	
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

	@Override
	public void addKeyword(String... category) {
		keywords.addAll(Arrays.asList(category));		
	}

}
