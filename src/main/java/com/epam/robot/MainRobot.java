package com.epam.robot;

import com.epam.file.FileBookHandler;
import com.epam.file.FileLinkHandler;
import com.epam.file.Link;
import com.epam.util.UrlUtils;
import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by damian on 23.03.16.
 */
public class MainRobot {
    private static Logger logger = Logger.getLogger("MainRobot");

    
    
    public static void main(String[] args) {
    	
    	PrintWriter printWriter; 
        FileLinkHandler fileLinkHandler = new FileLinkHandler();
        List<Link> linksList = fileLinkHandler.getLinksList();
        BookTitleSearch bookTitleSearch = new BookTitleSearch();

        logger.info("Started Main Robot");

        for (int i = 0; i < linksList.size(); i++) {
            logger.info("Iterating over links by file");

            Link link = linksList.get(i);

            logger.info("Started Searching titles by Tag");
            String bookTitles = BookTitleSearch.searchTitlesInPageAndSubPages(link.getLinkAdress(), link.getElementType(), link.getElementName());
            String fileName = UrlUtils.getFileName(link.getLinkAdress());

           try {
				printWriter = new PrintWriter("src/main/resources/" + fileName);
				new FileBookHandler(fileName).writeBookTitlesToFile(bookTitles, printWriter);
		        logger.info("Finished Searching titles by Tag");
			} catch (FileNotFoundException e) {
				logger.error("File no found: " + fileName);
			}
           
            

        }
    }

}
