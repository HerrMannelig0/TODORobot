package com.epam.robot;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.epam.DB.entities.BookDB;
import com.epam.DB.entities.BookstoreDB;
import com.epam.file.Category;

/**
 * Class contains book definition. 
 * Contains title, author, price, keywords and url of bookstore. 
 * @see Keywords
 */
public class Book {
	private String title, author;
	private String price;
	protected Keywords keywords;
	private URL url;
	String bookCategory;

	public Book(String title, String author, String price, Keywords keywords, URL url){
		this.title = title;
		this.author = author;
		this.price = price;
		this.keywords = keywords;
		this.url = url;
	}

	public Book(String title, String author, String price, URL url) {
		this.title = title;
		this.author = author;
		this.price = price;
		this.url = url;
	}
	
	public Book(String title, String author, Keywords keywords){
		this.title = title;
		this.author = author;
		this.keywords = keywords;
	}
	
	/**
	 * Assigning category from table with keywords.
	 * @param categories to assing to keywords.
	 * @return Assigned category as string. If no category has been found it returns "No category".
	 * 
	 * @see Category
	 */
	public String assignCategory(List<Category> categories) {
		String resultCategory = "No category";
		for (Category category : categories) {
			if(resultCategory.equals("No category")){
				resultCategory = checkCategory(category);
			}
			else break;
		}
		bookCategory = resultCategory;
		return resultCategory;
	}

	/**
	 * Checking if given category match to keywords.
	 * @param category to check
	 * @return Found category as string. If no category has been found it returns "No category".
	 */
	public String checkCategory(Category category){
		if(keywords == null) return "No category";
		if(keywords.contains(category.getKeywords())){ 
				return category.getCategory();}
		return "No category";
	}	
	
	/**
	 * Create list of categories, reads them from given file.
	 * @param file contains list of categories
	 * @return List of categories
	 * @throws IOException
	 */
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
		scanner.close();
		return categories;
	}
	
	/**
	 * This method converts Book object into BookDB
	 * @return BookDB
	 */
	public BookDB convertToBookDAO(){
		BookDB bookDB = new BookDB();
		bookDB.setTitle(title);
		bookDB.setAuthor(author);
		bookDB.setCategory(bookCategory);
		return bookDB;
	}
	
	/**
	 * Extracting BookstoreDB from book's URL.
	 * @return BookstoreDB
	 */
	public BookstoreDB extractBookstoreFromURL(){
		String urlPath = url.toString();
		int indexOFFirstDotAppearance = urlPath.indexOf('.');
		String bookstoreName = urlPath.substring(indexOFFirstDotAppearance+1, urlPath.indexOf('.', indexOFFirstDotAppearance+1));
		return new BookstoreDB(bookstoreName);
	}
	
	@Override
	public String toString() {
		if(url == null)
			return "[" + author + "; " + title + " (price: " + price + ") "  + keywords + " : " + bookCategory +"]" ;
		return "[" + author + ";" + title + " (price: " + price + ") "  + keywords + " " + url.toString() + " : " + bookCategory + "]" ;	
	}	
	
	
	
	public String getTitle() {
		return title;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public URL getUrl() {
		return url;
	}
	
	public String getBookCategory() {
		return bookCategory;
	}
	
	public void setBookCategory(String category) {
		this.bookCategory = category;
	}
	
}
