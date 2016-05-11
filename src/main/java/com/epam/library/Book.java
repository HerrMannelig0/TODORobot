package com.epam.library;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.epam.DB.entities.BookToDB;
import com.epam.file.Category;

/**
 * Class contains book definition. Contains title, author, price, keywords and
 * url of bookstore.
 * 
 * @see Keywords
 */
public class Book {
	private String title, author;
	private String price;
	protected Keywords keywords;
	private URL url;
	Category bookCategory;

	public Book(String title, String author, String price, Keywords keywords, URL url) {
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

	public Book(String title, String author, Keywords keywords) {
		this.title = title;
		this.author = author;
		this.keywords = keywords;
	}

	/**
	 * Assigning category from table with keywords.
	 * 
	 * @param categories
	 *            to assing to keywords.
	 * @return Assigned category as string. If no category has been found it
	 *         returns "No category".
	 * 
	 * @see Category
	 */
	public Category assignCategory(List<Category> categories) {
		//final Category otherCategory = new Category("Other");
		if (extractBookstoreFromURL() == "allitebooks") return new Category("IT");
		if (keywords == null)
			return Category.NULL_CATEGORY;
		Category resultCategory = Category.NULL_CATEGORY;
		for (Category category : categories) {
			if (resultCategory.equals(Category.NULL_CATEGORY)) {
				resultCategory = checkCategory(category);
			} else
				break;
		}
		bookCategory = resultCategory;
		return resultCategory;
	}

	/**
	 * Checking if given category match to keywords.
	 * 
	 * @param category
	 *            to check
	 * @return Found category as string. If no category has been found it
	 *         returns "No category".
	 */
	public Category checkCategory(Category category) {
		if (keywords.contains(category.getKeywords())) {
			return category;
		}
		return Category.NULL_CATEGORY;
	}

	/**
	 * Create list of categories, reads them from given file.
	 * 
	 * @param file
	 *            contains list of categories
	 * @return List of categories
	 * @throws IOException
	 */
	List<Category> createCategoryList(File file) throws IOException {
		Scanner scanner = new Scanner(file, "UTF-8");
		List<Category> categories = new ArrayList<>();
		while (scanner.hasNextLine()) {
			String nextCategory = scanner.nextLine();
			String filepath = "src/main/resources/Keywords/" + nextCategory + ".txt";
			Category category = new Category(nextCategory, filepath);
			category.create();
			categories.add(category);
		}
		scanner.close();
		return categories;
	}

	/**
	 * This method converts Book object into BookDB
	 * 
	 * @return BookDB
	 */
	public BookToDB convertToBookDB() {
		BookToDB bookDB = new BookToDB();
		bookDB.setTitle(title);
		bookDB.setAuthor(author);
		return bookDB;
	}

	
	/**
	 * Extracting BookstoreDB from book's URL.
	 * 
	 * @return BookstoreDB
	 */
	public String extractBookstoreFromURL() {
		if(url == null) return "";
		String urlPath = url.toString();
		int indexOFFirstDotAppearance = urlPath.indexOf('.');
		String bookstoreName = urlPath.substring(indexOFFirstDotAppearance + 1,
				urlPath.indexOf('.', indexOFFirstDotAppearance + 1));
		return bookstoreName;
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

	public Category getBookCategory() {
		return bookCategory;
	}

	public void setBookCategory(Category category) {
		this.bookCategory = category;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + ((bookCategory == null) ? 0 : bookCategory.hashCode());
		result = prime * result + ((keywords == null) ? 0 : keywords.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;
		if (bookCategory == null) {
			if (other.bookCategory != null)
				return false;
		} else if (!bookCategory.equals(other.bookCategory))
			return false;
		if (keywords == null) {
			if (other.keywords != null)
				return false;
		} else if (!keywords.equals(other.keywords))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}

	@Override
	public String toString() {
		if (url == null)
			return "[" + author + "; " + title + " (price: " + price + ") " + keywords + " : " + bookCategory + "]";
		return "[" + author + ";" + title + " (price: " + price + ") " + keywords + " " + url.toString() + " : "
				+ bookCategory + "]";
	}

	public BookToDB toDB() {
		BookToDB bookToDB = new BookToDB(title, author, extractBookstoreFromURL());
		return bookToDB;
		
	}

}
