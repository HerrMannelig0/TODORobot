package com.epam.robot;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.epam.file.Category;
import com.epam.file.FileLinkHandler;

@Configuration
public class CrawlerBeans {

	@Bean(name = "bookstores")
	public Bookstores bookstores(){
		return new Bookstores();
	}
	
	@Bean(name = "addressFile")
	public File addressFile(){
		return new File("src/main/resources/FreeBooksAdressSite.txt");
	}
	
	@Bean(name = "logger")
	public Logger logger(){
		return Logger.getLogger("Robot");
	}
	
	@Bean
	public FileLinkHandler fileLinkHandler(){
		return new FileLinkHandler();
	}
	
	@Bean
	@Autowired
	public Library library(){
		return new Library();
	}
	
	@Bean
	public BookTitleSearch bookTitleSearch(){
		return new BookTitleSearch();
	}
	
	@Bean(name = "categoriesFile")
	public File categoriesFile(){
		return new File("src/main/resources/Keywords/Categories.txt");
	}
	
}
