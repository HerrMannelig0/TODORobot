package com.epam.robot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.epam.file.Category;

public class Book {
	private String title, author;
	private String price;
	protected Keywords keywords;
	private URL url;
	String bookCategory;
	
	
	
	Book(String title, String author, String price, Keywords keywords, URL url){
		this.title = title;
		this.author = author;
		this.price = price;
		this.keywords = keywords;
		this.url = url;
	}

	Book(String title, String author, String price, URL url) {
		this.title = title;
		this.author = author;
		this.price = price;
		this.url = url;
	}
	
	Book(String title, String author, Keywords keywords){
		this.title = title;
		this.author = author;
		this.keywords = keywords;
	}
	
	public String assignCategory(List<Category> categories) {
		String resultCategory = "No category";
		for (Category category : categories) {
			System.out.println(category);
			if(resultCategory.equals("No category")){
				resultCategory = checkCategory(category);
			}
			else break;
		}
		bookCategory = resultCategory;
		return resultCategory;
	}

	public String checkCategory(Category category){
		if(keywords.contains(category.getKeywords())){ 
				return category.getCategory();}
		return "No category";
	}	
	
	List<Category> createCategoryList(File file) throws IOException{
		Scanner scanner = new Scanner(file);
		List<Category> categories = new ArrayList<>();
		while(scanner.hasNextLine()){
			String nextCategory = scanner.nextLine();
			String filepath = "src/main/resources/Keywords/" + nextCategory +".txt";
			Category category = new Category(nextCategory, filepath);
			category.create();
			categories.add(category);
		}
		return categories;
		
	}
	
	@Override
	public String toString() {
		if(url == null)
			return "[" + author + "; " + title + " (price: " + price + ") "  + keywords + " : " + bookCategory +"]" ;
		return "[" + author + ";" + title + " (price: " + price + ") "  + keywords + " " + url.toString() + " : " + bookCategory + "]" ;	
	}	
}
