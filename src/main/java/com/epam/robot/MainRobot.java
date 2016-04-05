package com.epam.robot;

import com.epam.file.FileBookHandler;
import com.epam.file.FileLinkHandler;
import com.epam.file.Link;
import com.epam.util.UrlUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by damian on 23.03.16.
 */
public class MainRobot {
	private static Logger logger = Logger.getLogger("MainRobot");
	private static String fileName = "src/main/resources/FreeBooksAdressSite.txt";

	public static void main(String[] args) {

		PrintWriter printWriter;
		FileLinkHandler fileLinkHandler = new FileLinkHandler();
		BookTitleSearch bookTitleSearch = new BookTitleSearch();
		
		try {
			fileLinkHandler.createListsFromFile(new File(fileName));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		List<Link> linksList = fileLinkHandler.getLinksList();

		logger.info("Started Main Robot");
		System.out.println(linksList.size());
		
		for (int i = 0; i < linksList.size(); i++) {
			logger.info("Iterating over links by file");

			Link link = linksList.get(i);

			logger.info("Started Searching titles by Tag");
			System.out.println(link.getLinkAdress()+" "+
					link.getElementType() +" "+ link.getTitleTag() + " "+ link.getAuthorTag() +" " + link.getPriceTag() + " "+ link.KeywordsTag());
			String bookTitles = BookTitleSearch.searchTitlesInPageAndSubPages(link.getLinkAdress(),
					link.getElementType(), link.getTitleTag(), link.getAuthorTag(), link.getPriceTag(), link.KeywordsTag());
			String fileName = null;
			try {
				fileName = UrlUtils.getFileName(link.getLinkAdress());
				printWriter = new PrintWriter("src/main/resources/" + fileName);
				new FileBookHandler(fileName).writeBookTitlesToFile(bookTitles, printWriter);
				logger.info("Finished Searching titles by Tag");
			} catch (Exception exception) {
				logger.error(exception.getClass() + "in MainRobot, problems with filename");
			}
			
		BookTitleSearch.showLibrary();
		}
		
		
	}

}
