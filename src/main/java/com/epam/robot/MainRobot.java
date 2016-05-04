package com.epam.robot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

import org.apache.log4j.Logger;

import com.epam.file.Category;
import com.epam.file.FileBookHandler;
import com.epam.file.FileLinkHandler;
import com.epam.file.Link;
import com.epam.util.CategoryUtil;
import com.epam.util.UrlUtils;

/**
 * Program to search free books in the internet bookstoreDBs.
 */
public class MainRobot {
	private static Logger logger = Logger.getLogger("MainRobot");
	private static String fileName = "src/main/resources/FreeBooksAdressSite.txt";


		static Library library = new Library();
		static Bookstores bookstores = new Bookstores();
		static PrintWriter printWriter = null;
		static FileLinkHandler fileLinkHandler = new FileLinkHandler();
		static LibrariesMap map = new LibrariesMap();
		static BookTitleSearch bookTitleSearch = new BookTitleSearch();
	
	
	/**
	 * Main method of crawler.
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) {

		prepareBookstores(bookstores);
		
		List<Link> linksList = createListOfLinks();

		logger.info("Started Main Robot");
		
		for (int i = 0; i < linksList.size(); i++) {
			logger.info("Iterating over links by file");

			Link link = linksList.get(i);

			logger.info("Started Searching titles by Tag");
			
			System.out.println(link.getLinkAdress()+" "+
					link.getElementType() +" "+ link.getTitleTag() + " "+ link.getAuthorTag() +" " + link.getPriceTag() + " "+ link.getKeywordsTag());
			
			
			String fileName = null;
			
			
				fileName = UrlUtils.getFileName(link.getLinkAdress());
				try {
					printWriter = new PrintWriter("src/main/resources/" + fileName);
				} catch (FileNotFoundException e) {
					logger.error(e.getClass() + " in MainRobot, problems with filename");
				}
				
				library.addAll(bookTitleSearch.searchTitlesInPageAndSubPages(link.getLinkAdress(), 
						link.getElementType(), link.getTitleTag(), link.getAuthorTag(), link.getPriceTag(), 
						link.getKeywordsTag()));
	
				for (Book book : library) {
				
					try {
						book.assignCategory(book.createCategoryList(new File("src/main/resources/Keywords/Categories.txt")));
						//add category to book, filename is a bookstore, book has all fields to be put in DB
					} catch (Exception e) {
						e.printStackTrace();
						logger.error(e.getClass() + " in MainRobot, problems with category assignment");
					} 
					
					
					FileBookHandler fileBookHandler = new FileBookHandler(fileName);
				
					//writing books to database
					try {
						FileBookHandler.writeBookToDatabase(book);
					} catch (FileNotFoundException e) {
						logger.error("Cannot write into databse: " + book);
					}
					printWriter.println("");
				}
				
				//Creating map of categories and libraries
				CategoryUtil.generateLibrariesMapfromLibrary(library);
				
				
				logger.info("Finished searching titles by Tag");
			
		}
		logger.info("Robot has finished his work");
		
	}



	private static List<Link> createListOfLinks() {
		try {
			fileLinkHandler.createListsFromFile(new File(fileName));
		} catch (FileNotFoundException e) {
			logger.error("Cannot find file: " + fileName);
		}
		
		List<Link> linksList = fileLinkHandler.getLinksList();
		return linksList;
	}


	/**
	 * you totally need to check that!! Dont know usage of this, not to mention mocked, empty method @generateBookstoreSet !
	 * @param bookstores
     */
	private static void prepareBookstores(Bookstores bookstores){
		try {
			bookstores.generateBookstoreSet(new File(fileName));
		} catch (FileNotFoundException e) {
			logger.error("Cannot find file: " + fileName);
		}
	}

}
