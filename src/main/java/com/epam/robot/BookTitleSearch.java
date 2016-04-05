package com.epam.robot;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by damian on 22.03.16.
 */
public class BookTitleSearch {
	private static Logger logger = Logger.getLogger("BookTitleSearch");
	private static StringBuilder titleBookContainer = new StringBuilder();
	static private HashSet<String> addressHashSet = new HashSet<String>();
	private static List<Book> library = new ArrayList<>();
	
	
	public static void main(String[] args) {
		BookTitleSearch obj = new BookTitleSearch();
		HowItWorks test = obj.new HowItWorks();
		test.testSearchLinksToNextPages();
	}

	public static String searchTitlesInPageAndSubPages(String bookstoreAddressFromTextfile, String typeOfElement,
			String titleTag, String authorTag, String priceTag, String keywordsTag) {
		logger.info("Started searching Titles for adress =  " + bookstoreAddressFromTextfile);

		resetClassVariables();

		//creates addressHashSet
		searchLinksToNextPages(bookstoreAddressFromTextfile); 

		final Iterator<String> iterator = addressHashSet.iterator();

		logger.info("Started iterating over links to search titles");
		logger.info("a" + titleTag + "a");

		while (iterator.hasNext()) {
			final String next = iterator.next();
			System.out.println(next);
			try {
				BookTitleSearch.searchTitles(next, typeOfElement, titleTag, authorTag, priceTag, keywordsTag);
			} catch (Exception e) {
				logger.error(e.getClass() + " " + next);
			}
		}

		logger.info("Finished iterating over links to search titles");

		return titleBookContainer.toString();
	}

	private static void resetClassVariables() {
		titleBookContainer = new StringBuilder();
		addressHashSet = new HashSet<>();
	}
	
	public static void searchTitles(String bookstoreAddressFromTextfile, String typeOfElement,
			String titleTag, String authorTag, String priceTag, String keywordsTag) throws MalformedURLException {
			
			Document document = parseHTMLtoDoc(bookstoreAddressFromTextfile);

			Element body = document.body();
			Elements items = body.getElementsByClass(typeOfElement);
			
			for(Element item : items){
				try{
					Elements titleElements = item.getElementsByClass("item-title");	
					Elements authorElements = item.getElementsByClass(authorTag);				
					Elements priceElements = null;
					Elements keywordsElements = null;
					
					try{
						priceElements = item.getElementsByClass(priceTag);
					} catch(IllegalArgumentException e){}
					
					try{
						keywordsElements = item.getElementsByClass(keywordsTag);
					} catch(IllegalArgumentException e)  {}
					
					
					String title = titleElements.text();
					String author = authorElements.text();
					String price = null;
					String keywordsAsStringFromSite = null;
					
					if(priceElements != null){
						price = priceElements.text();
					} else{
						price = "not inclued";
					}
					
					if(keywordsElements != null){
						keywordsAsStringFromSite = keywordsElements.get(0).text();
					} else{
						keywordsAsStringFromSite = "";
					}
					
					if(BookTitleSearch.areKeywords(keywordsAsStringFromSite)){
						Keywords keywords = new Keywords(BookTitleSearch.extractKeywords(keywordsAsStringFromSite));
						library.add(new Book(title, author, price, keywords, new URL(bookstoreAddressFromTextfile)));
					} else{
						library.add(new Book(title, author, price, new URL(bookstoreAddressFromTextfile)));
					}
				} catch(Exception e){
					logger.error(e.getClass() + " " + "Exception while site parsing: " + bookstoreAddressFromTextfile);
				}
				
				
			}
		
		
		}

	public static void searchTitlesInBookrixTypeSite(String bookstoreAddressFromTextfile, String typeOfElement, String elementName) throws MalformedURLException {
		Document document = parseHTMLtoDoc(bookstoreAddressFromTextfile);

		Element body = document.body();
		
		Elements items = body.getElementsByClass("item");
		
		for(Element item : items){
			Elements titleElements = item.getElementsByClass("item-title");
			Elements authorElements = item.getElementsByClass("item-author");
			Elements priceElements = item.getElementsByClass("item-price");
			Elements keywordsElements = item.getElementsByClass("item-keywords");
			
			String title = titleElements.get(0).text();
			String author = authorElements.get(0).text();
			String price = priceElements.get(0).text();
			
			String keywordsAsStringFromSite = keywordsElements.get(0).text();
			
			if(BookTitleSearch.areKeywords(keywordsAsStringFromSite)){
				Keywords keywords = new Keywords(BookTitleSearch.extractKeywords(keywordsAsStringFromSite));
				library.add(new Book(title, author, price, keywords, new URL(bookstoreAddressFromTextfile)));
			} else{
				library.add(new Book(title, author, price, new URL(bookstoreAddressFromTextfile)));
			}
			
		}
		
		/*
		 * if (typeOfElement.equals("tag")) { elements =
		 * body.getElementsByTag(elementName); } else { elements =
		 * body.getElementsByClass(elementName); }
		 */

		/*elements = body.getElementsByClass(elementName);

		Iterator<Element> iterator = elements.iterator();

		while (iterator.hasNext()) {
			Element next = iterator.next();
			String valueFromSite = next.text();
			
			if(areKeywords(valueFromSite)){
				String[] keywordsTable = extractKeywords(valueFromSite);
				Keywords keywords = new Keywords(keywordsTable);
				System.err.println(Arrays.toString(keywordsTable));
			}
			
			System.out.println(next.text());
			titleBookContainer.append(next.text() + "\n");
		}*/
	}

	public static void searchLinksToNextPages(String bookstoreAddressFromTextfile) {

		Document websiteContent = parseHTMLtoDoc(bookstoreAddressFromTextfile);

		Elements links = websiteContent.select("a[abs:href]");
		Iterator<Element> iterator = links.iterator();
		addressHashSet.add(bookstoreAddressFromTextfile);

		while (iterator.hasNext()) {
			Element tag = iterator.next();

			if (tagContainsNumberAndWasNotFoundBefore(tag)) {
				addLinkToSetAndSearchInLinkForPages(tag.attr("abs:href"));
			}
		}
	}

	private static boolean tagContainsNumberAndWasNotFoundBefore(Element element) {
		return NumberUtils.isNumber(element.text()) && !addressHashSet.contains(element.attr("abs:href"))
				&& (addressHashSet.size() < 30);
	}

	private static void addLinkToSetAndSearchInLinkForPages(String linkToSubPage) {
		System.out.println(linkToSubPage);
		addressHashSet.add(linkToSubPage);
		searchLinksToNextPages(linkToSubPage);
	}

	private static Document parseHTMLtoDoc(String adress) {
		Document document = null;
		try {
			document = Jsoup.parse(new URL(adress), 100000);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return document;
	}

	class HowItWorks {
		
		public void testSearchLinksToNextPages() {
			
			HashSet <String> initial = new HashSet <>();
			String bookstore = "http://www.bookrix.com/books.html";
			initial.add(bookstore);
			Document websiteContent = parseHTMLtoDoc(bookstore);
			Elements links = websiteContent.select("a[abs:href]");
			Iterator<Element> iterator = links.iterator();
			while (iterator.hasNext()) {
				Element tag = iterator.next();
				if (tagContainsNumberAndWasNotFoundBefore(tag))
				System.out.println(tag.toString());
				
			}
			
		}
	}

	protected static String[] extractKeywords(String valueFromSite) {
		int indexOfStart = valueFromSite.indexOf(':') + 2;
		String [] keywords = valueFromSite.substring(indexOfStart).toLowerCase().split(", ");
		return keywords;
	}

	public static boolean areKeywords(String valueFromSite) {
		return valueFromSite.startsWith("Keywords:");
	}
	
	public static void showLibrary(){
		System.out.println("Library size: " + library.size());
		for(Book book : library){
			System.out.println(book);
		}
	}
}
