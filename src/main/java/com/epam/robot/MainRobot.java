package com.epam.robot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

import org.apache.log4j.Logger;

import com.epam.file.FileBookHandler;
import com.epam.file.FileLinkHandler;
import com.epam.file.Link;
import com.epam.util.UrlUtils;

/**
 * Program to search free books in the internet bookstores.
 */
public class MainRobot {
	private static Logger logger = Logger.getLogger("MainRobot");
	private static String fileName = "src/main/resources/FreeBooksAdressSite.txt";

	/**
	 * Main method of crawler.
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {

		Library library = new Library();
		Bookstores bookstores = new Bookstores();
		bookstores.generateBookstoreSet(new File(fileName));
		
		
		PrintWriter printWriter = null;
		FileLinkHandler fileLinkHandler = new FileLinkHandler();
		
		try {
			fileLinkHandler.createListsFromFile(new File(fileName));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		List<Link> linksList = fileLinkHandler.getLinksList();

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
				
	
				for (Book book : library) {
				
					try {
						book.assignCategory(book.createCategoryList(new File("src/main/resources/Keywords/Categories.txt")));
						//add category to book, filename is a bookstore, book has all fields to be put in DB
					} catch (Exception e) {
						logger.error(e.getClass() + " in MainRobot, problems with category assignment");
					} 
					new FileBookHandler(fileName);
					FileBookHandler.writeBookToDatabase(book);
					printWriter.println("");
				}
				
				logger.info("Finished Searching titles by Tag");
			
		}
	}

}
