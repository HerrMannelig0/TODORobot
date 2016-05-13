package com.epam.library;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.epam.file.Category;

/**
 * Container for beans for Library class.
 */
@Configuration
public class LibraryBeans {

	@Bean(name = "listofcategories")
	public List<Category> categoryList(Book book, File file) throws IOException {
		return book.createCategoryList(file);
	}
	
	@Bean
	public Book dumbBook(){
		String title = "title"; 
		String author = "author";
		String price = "free";
		Keywords keywords = new Keywords(new String[]{"keyword"});
		URL url = null;
		try {
			url = new URL("http://www.java.com/pl/");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		Book book = new Book(title, author, price, keywords, url);
		book.setBookCategory(new Category("No category"));
		
		return book;
	}
	
	@Bean(name = "categoriesFile")
	public File categoriesFile(){
		return new File("src/main/resources/Keywords/Categories.txt");
	}
}
