package com.epam.robot;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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

	protected static List<Book> library = new ArrayList<>();
	
	static private Set<String> addressHashSet = new HashSet<String>();

	/*
	 * creates String containing titles from all pages from bookstore web site
	 * 
	 * @param bookstoreAddressFromTextfile the main bookstore address
	 * typeOfElement elementName a specified tag name to search for
	 * 
	 * @return String object with book titles and keywords (if they exist)
	 */
	public static String searchTitlesInPageAndSubPages(String bookstoreAddressFromTextfile, String typeOfElement,
			String titleTag, String authorTag, String priceTag, String keywordsTag) {
		logger.info("Started searching Titles for adress =  " + bookstoreAddressFromTextfile);

		resetClassVariables();


		//creates addressHashSet
		searchLinksToNextPages(bookstoreAddressFromTextfile); 

		searchLinksToNextPages(bookstoreAddressFromTextfile);

		final Iterator<String> iterator = addressHashSet.iterator();

		logger.info("Started iterating over links to search titles");

		while (iterator.hasNext()) {
			final String next = iterator.next();
			try {
				BookTitleSearch.searchTitles(next, typeOfElement, titleTag, authorTag, priceTag, keywordsTag);
			} catch (Exception e) {
				logger.error(e.getClass() + " " + next);
			}
		}

		logger.info("Finished iterating over links to search titles");

		return titleBookContainer.toString();
	}

	public static void searchTitles(String bookstoreAddressFromTextfile, String typeOfElement,
			String titleTag, String authorTag, String priceTag, String keywordsTag) throws MalformedURLException {
			
			Document document = parseHTMLtoDoc(bookstoreAddressFromTextfile);

			Element body = document.body();
			Elements items = body.getElementsByClass(typeOfElement);
			
			for(Element item : items){
				
					String title = extractElementFromSite(item, titleTag); 
					String author = omitByInAuthor(extractElementFromSite(item, authorTag));
					String price = extractElementFromSite(item, priceTag);
					String keywordsAsStringFromSite = extractElementFromSite(item, keywordsTag);

					
					
					if(BookTitleSearch.areKeywords(keywordsAsStringFromSite)){
						Keywords keywords = new Keywords(BookTitleSearch.extractKeywords(keywordsAsStringFromSite));
						library.add(new Book(title, author, price, keywords, new URL(bookstoreAddressFromTextfile)));
					} else{
						library.add(new Book(title, author, price, new URL(bookstoreAddressFromTextfile)));		
					}
			}
	}

	
	protected static String omitByInAuthor(String extractElementFromSite) {
		if(extractElementFromSite.length() < 3) return extractElementFromSite;
		if(extractElementFromSite.startsWith("By:"))
			return extractElementFromSite.substring(extractElementFromSite.indexOf(" ")+1);
		return extractElementFromSite;
	}

	static String extractElementFromSite(Element item, String tag){
		Elements elements = null;
		try{
			elements = item.getElementsByClass(tag);
		} catch(IllegalArgumentException e){}
		return elements.text();
	}


	/*
	 * searches for book titles and tags, if available
	 * 
	 * @param bookstoreAddressFromTextfile the main bookstore address
	 * typeOfElement type of element to be searched elementName a specified tag
	 * name to search for
	 * 
	 */
	public static void searchTitles(String bookstoreAddressFromTextfile, String typeOfElement, String elementName) {

		Document document = parseHTMLtoDoc(bookstoreAddressFromTextfile);

		Element body = document.body();

		Elements elements;

		if (typeOfElement.equals("tag")) {
			elements = body.getElementsByTag(elementName);
		} else {
			elements = body.getElementsByClass(elementName);
		}

		elements = body.getElementsByClass(elementName);

		Iterator<Element> iterator = elements.iterator();

		while (iterator.hasNext()) {
			Element next = iterator.next();
			System.out.println(next.text());
			titleBookContainer.append(next.text() + "\n");
			
		}
	}

	/*
	 * searches for sub - pages and puts found links into set
	 * 
	 * @param bookstoreAddressFromTextfile the main bookstore address
	 * 
	 */

	public static void searchLinksToNextPages(String bookstoreAddressFromTextfile) {

		Document websiteContent = parseHTMLtoDoc(bookstoreAddressFromTextfile);

		addressHashSet.add(bookstoreAddressFromTextfile);

		Elements links = websiteContent.select("a[abs:href]");
		Iterator<Element> iterator = links.iterator();
		while (iterator.hasNext()) {
			Element tag = iterator.next();

			if (tagContainsNumberAndWasNotFoundBefore(tag)) {
				addLinkToSetAndSearchInLinkForPages(tag.attr("abs:href"));
			}
		}
	}

	private static void resetClassVariables() {
		titleBookContainer = new StringBuilder();
		addressHashSet = new HashSet<>();
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

			HashSet<String> initial = new HashSet<>();
			String bookstore = "http://www.bookrix.com/books.html";
			initial.add(bookstore);
			Document websiteContent = parseHTMLtoDoc(bookstore);
			Elements links = websiteContent.select("a[abs:href]");
			System.out.println(links.size());
			Iterator<Element> iterator = links.iterator();
			while (iterator.hasNext()) {
				Element tag = iterator.next();
				if (tagContainsNumberAndWasNotFoundBefore(tag)) {
					addLinkToSetAndSearchInLinkForPages(tag.attr("abs:href"));
					System.out.println(tag.toString());
				}

			}

		}

		public void testOfSearchBooks() {
			String typeOfElement = "class";
			String elementName = "word-break";
			StringBuilder titleBookContainer = new StringBuilder();
			Document document = parseHTMLtoDoc("http://www.bookrix.com/books;page:2.html");

			Element body = document.body();

			Elements elements;

			if (typeOfElement.equals("tag")) {
				elements = body.getElementsByTag(elementName);
			} else {
				elements = body.getElementsByClass(elementName);
			}

			elements = body.getElementsByClass(elementName);

			Iterator<Element> iterator = elements.iterator();

			while (iterator.hasNext()) {
				Element next = iterator.next();
				System.out.println(next.text());
				titleBookContainer.append(next.text() + "\n");
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
