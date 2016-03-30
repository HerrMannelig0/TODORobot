package com.epam.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by damian on 21.03.16.
 */
public class FileLinkHandler {

    protected List<Link> linkList = new ArrayList<Link>();
    protected List<String> urlList = new ArrayList<String>();
    
    public void createListsFromFile(File file) throws FileNotFoundException{
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
            e.printStackTrace();
        }
    }


    public List<Link> getLinksList() {
        return linkList;
    }

    public List<String> getUrlList() {
        return urlList;
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
	
	protected void updateLinkList(File file) throws FileNotFoundException {
		linkList.addAll(readLinksFromFile(file));
		
	}

	public List<Link> readLinksFromFile(File file) throws FileNotFoundException {
		Scanner scanner = new Scanner(file);
		List<Link> links = new ArrayList<>();
		
		while(scanner.hasNextLine()){
			String[] splittedLine = scanner.nextLine().split(" ");
			if (doesLinkContainThreeParts(splittedLine)) {
                Link link = new Link(splittedLine[0], splittedLine[1], splittedLine[2]);
                links.add(link);
            }
		}
		scanner.close();
    	return links;

    }  

    protected static boolean doesLinkContainThreeParts(String[] splittedLine) {
    	return splittedLine.length == 3;
    }

}
