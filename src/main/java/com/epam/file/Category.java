package com.epam.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;

public class Category  {
	
	protected HashSet<String> keywords;
	private String filePath = "src/main/resources/Keywords/RomanceKeywords.txt";
	private String category;
	
	public Category() {
		keywords = new HashSet<>();
	}
	
	public HashSet<String> create() throws IOException{
		File file = new File(filePath);
		Category romanceCategory = new Category();
		BufferedReader reader = new BufferedReader(new FileReader(file));
		
		category = reader.readLine();
		
		HashSet<String> set = new HashSet<>();
		String nextKeyword = null;
		while((nextKeyword = reader.readLine()) != null){
			romanceCategory.addKeyword(nextKeyword);
			set.add(nextKeyword);
			keywords.add(nextKeyword);
		}
		return set;
	}

	public boolean containsKeyword(String keywordToCheck) {
		return keywords.contains(keywordToCheck);
	}
	
	public String getCategory() {
		return category;
	}

	public HashSet<String> getKeywords(){
		return keywords;
	}
	
	public void addKeyword(String... category) {
		keywords.addAll(Arrays.asList(category));		
	}

	public String toString() {
		String result = "";
		for (String keyword : keywords) {
			result += (" " + keyword); 
		}
		return result;
	}
}
