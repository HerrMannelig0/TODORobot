package com.epam.robot;

import java.net.URL;

public class Book {
	private String title, author;
	private String price;
	private Keywords keywords;
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
	
	@Override
	public String toString() {
		return "[" + author + ", " + title + " (price: " + price + ") "  + keywords + " " + url.toString() + "]" ;
		
	}
	
}
