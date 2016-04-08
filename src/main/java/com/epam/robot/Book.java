package com.epam.robot;

import java.net.URL;

import com.epam.file.Category;

public class Book {
	private String title, author;
	private String price;
	protected Keywords keywords;
	private URL url;

	
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
	
	public String assignCategory() {
		return null;
	}
	

	
	public String checkCategory(Category category){
		if(keywords.contains(category.getKeywords())) 
				return category.getCategory();
		return "No category";
	}
	
	
	@Override
	public String toString() {
		if(url == null)
			return "[" + author + ", " + title + " (price: " + price + ") "  + keywords + "]" ;
		return "[" + author + ", " + title + " (price: " + price + ") "  + keywords + " " + url.toString() + "]" ;	
	}

	
}
