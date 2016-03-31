package com.epam.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.apache.log4j.Logger;

import com.epam.gui.MainClass;
/**
 * Created by damian on 21.03.16.
 */
public class FileLinkHandler {

    protected List<Link> linkList = new ArrayList<Link>();
    protected List<String> urlList = new ArrayList<String>();
    private static Logger logger = Logger.getLogger(FileLinkHandler.class);
    
    public void createListsFromFile(File file) throws FileNotFoundException{
    	logger.info("Start of creating List from File: " + file.getPath());
    	updateLinkList(file);
    	updateURLList();
    }
    
    public void writeLinksToFile(List<Link> linkList, File file) {
        try (PrintWriter printWriter = new PrintWriter(file)) {
            for (int i = 0; i < linkList.size(); i++) {
                printWriter.println(linkList.get(i).toString());
            }
            printWriter.flush();
        } catch (FileNotFoundException e) {
        	logger.error("Exception occured during writing file to file (File not Found): " + file.getPath());
            e.printStackTrace();
        }
        logger.info("Link list has been written into file");
    }


    public List<Link> getLinksList() {
        return linkList;
    }

    public List<String> getUrlList() {
        return urlList;
    }
    
    protected void updateLinkList(File file) throws FileNotFoundException {
		linkList.addAll(readLinksFromFile(file));
	}
    
	protected void updateURLList() {
    	for(Link link : linkList){
    		urlList.add(link.getLinkAdress());
    	}
	}
	
	protected void clearLinkList() {
		linkList.clear();
	}

	protected void clearURLList() {
		urlList.clear();
	}

	public List<Link> readLinksFromFile(File file) throws FileNotFoundException {
		Scanner scanner = new Scanner(file);
		List<Link> links = new ArrayList<>();
		
		while(scanner.hasNextLine()){
			String[] splittedLine = scanner.nextLine().split(" ");
			if (doesLinkContainThreeParts(splittedLine)) {
                Link link = new Link(splittedLine[0], splittedLine[1], splittedLine[2]);
                links.add(link);
                
                logger.info("New link get from file: " + link.toString());
            }
		}
		scanner.close();
    	return links;

    }  

    protected static boolean doesLinkContainThreeParts(String[] splittedLine) {
    	return splittedLine.length == 3;
    }

}
