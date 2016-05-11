package com.epam.file;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashSet;

import com.epam.DB.entities.CategoriesToDB;

/**
 * Class {@code CategoryDB} represents category of the book.
 * @see Keywords
 */
public class Category  {
	
	protected HashSet<String> keywords;
	private String name;
	private String filePath;
	public static final Category NULL_CATEGORY = new Category("No category");
		
	public Category(String name) {
		this.name = name;
		this.filePath = "src/main/resources/Keywords/" + name + ".txt";
		keywords = new HashSet<>();
	}
	
	public Category(String name, String filepath) {
		this.name = name;
		this.filePath = filepath;
		keywords = new HashSet<>();
	}
	
	/**
	 * Creating new category from file.
	 * @return Set of keywords
	 * @throws IOException
	 */
	public HashSet<String> create() throws IOException{
		File file = new File(filePath);
		Category romanceCategory = new Category(name, filePath);
		BufferedReader reader = new BufferedReader(new FileReader(file));
		
		name = reader.readLine();

		HashSet<String> set = new HashSet<>();
		
		String nextKeyword = null;
		while((nextKeyword = reader.readLine()) != null){
			romanceCategory.addKeyword(nextKeyword);
			set.add(nextKeyword);
			keywords.add(nextKeyword);
		}
		reader.close();
		return set;
	}

	/**
	 * Check if given keyword matches to category's keywords.
	 * @param keywordToCheck
	 * @return boolean
	 */
	public boolean containsKeyword(String keywordToCheck) {
		return keywords.contains(keywordToCheck);
	}
	
	/**
	 * CategoryDB's name getter.
	 * @return category
	 */
	public String getName() {
		return name;
	}

	/**
	 * Keywords getter.
	 * @return keywords
	 */
	public HashSet<String> getKeywords(){
		return keywords;
	}
	
	/**
	 * Adding new keyword(s) to category.
	 * @param category
	 */
	public void addKeyword(String... category) {
		keywords.addAll(Arrays.asList(category));		
	}
	
	public CategoriesToDB toDB(){
		return new CategoriesToDB(name);
	}

	@Override
	public String toString(){
		return name;
	}

	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
        if (obj == null) return false;
		return name.equals(((Category)obj).name);
	}
	
	
}
