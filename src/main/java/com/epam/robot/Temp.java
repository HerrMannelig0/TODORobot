package com.epam.robot;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.List;

import com.epam.file.FileLinkHandler;
import com.epam.file.Link;

//End-to-end test for BookTitleSearch
public class Temp {

	public static void main(String[] args) throws MalformedURLException {
		
		String fileName = "src/main/resources/FreeBooksAdressSite.txt";
		FileLinkHandler fileLinkHandler = new FileLinkHandler();
		BookTitleSearch bookTitleSearch = new BookTitleSearch();
		
		
		try {
			fileLinkHandler.createListsFromFile(new File(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		List<Link> linksList = fileLinkHandler.getLinksList();
		System.out.println(Arrays.toString(linksList.toArray()));
		
		Link link = linksList.get(0);
		System.out.println(link);
		System.out.println(link.getLinkAdress());
		System.out.println(link.getTitleTag());
		System.out.println(link.getAuthorTag());
		
		BookTitleSearch.searchTitlesInPageAndSubPages(link.getLinkAdress(),
				link.getElementType(), link.getTitleTag(), link.getAuthorTag(), link.getPriceTag(), link.KeywordsTag());
		BookTitleSearch.showLibrary();
	}

}
