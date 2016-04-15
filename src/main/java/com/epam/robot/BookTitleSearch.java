package com.epam.robot;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
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
	protected static List<Book> library = new ArrayList<>();
	static private Set<String> addressHashSet = new HashSet<String>();

	private static Logger logger = Logger.getLogger("BookTitleSearch");
	
	private static StringBuilder titleBookContainer = new StringBuilder();

	/**
	 * Finds out if given string starts with "Keywords: " phrase.
	 * @param String, that should contains keywords of book.
	 * @return True if given string starts with "Keywords: ", else otherwise. 
	 */
	public static boolean areKeywords(String valueFromSite) {
		return valueFromSite.startsWith("Keywords:");
	}

	/**
	 * Parsing website and searchnig for links there.
	 * @param bookstoreAddressFromTextfile
	 * @return nothing
	 */
	public static void searchLinksToNextPages(String bookstoreAddressFromTextfile) {

		try {
			Document websiteContent  = null;
			try {
				websiteContent = parseHTMLtoDoc(bookstoreAddressFromTextfile);
			} catch (Exception e) {
				logger.error("Cannot parse HTMP to DOC: " + bookstoreAddressFromTextfile);
			}

			addressHashSet.add(bookstoreAddressFromTextfile);

			Elements links = null;
			try {
				links = websiteContent.select("a[abs:href]");
			} catch (Exception e) {
				logger.error("Cannot select link from site: " + bookstoreAddressFromTextfile);
			}
			Iterator<Element> iterator = links.iterator();
			while (iterator.hasNext()) {
				Element tag = iterator.next();

				if (tagContainsNumberAndWasNotFoundBefore(tag)) {
					addLinkToSetAndSearchInLinkForPages(tag.attr("abs:href"));
				}
			}
		} catch (Exception e) {
			
		}
	}

	
	/**
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

		elements = body.getElementsByClass(elementName);

		Iterator<Element> iterator = elements.iterator();

		while (iterator.hasNext()) {
			Element next = iterator.next();
			System.out.println(next.text());
			titleBookContainer.append(next.text() + "\n");
			
		}
	}

	/**
	 * Searches for book titles and tags, if available
	 *
	 * @param Bookstore address
	 * @param type of element
	 * @param tag for title
	 * @param tag for author
	 * @param tag for price
	 * @param tag for keywords
	 * @throws MalformedURLException
	 */
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


	/**
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

	/**
	 * Printing library to standard output.
	 * @param nothing
	 * @return nothing
	 */
	public static void showLibrary(){
		System.out.println("Library size: " + library.size());
		for(Book book : library){
			System.out.println(book);
		}
	}

	/**
	 * Parse string from site to array of keywords.
	 * @param keywords as a string
	 * @return Array of keywords
	 */
	protected static String[] extractKeywords(String valueFromSite) {
		int indexOfStart = valueFromSite.indexOf(':') + 2;
		String [] keywords = valueFromSite.substring(indexOfStart).toLowerCase().split(", ");
		return keywords;
	}

	/**
	 * Delete "By: " in the author's element from site.
	 * @param extractElementFromSite
	 * @return Author without "By: "
	 */
	protected static String omitByInAuthor(String extractElementFromSite) {
		if(extractElementFromSite.length() < 3) return extractElementFromSite;
		if(extractElementFromSite.startsWith("By:"))
			return extractElementFromSite.substring(extractElementFromSite.indexOf(" ")+1);
		return extractElementFromSite;
	}

	/**
	 * Extracting information from Element.
	 * @param item to search on a site
	 * @param tag connected with element and searching phrase
	 * @return extracted element as a string
	 */
	static String extractElementFromSite(Element item, String tag){
		Elements elements = null;
		try{
			elements = item.getElementsByClass(tag);
		} catch(IllegalArgumentException e){}
		return elements.text();
	}

	/**
	 * Adding link to HashSet
	 * @param link to Sub-page
	 */
	private static void addLinkToSetAndSearchInLinkForPages(String linkToSubPage) {
		addressHashSet.add(linkToSubPage);
		searchLinksToNextPages(linkToSubPage);
	}

	/**
	 * Parsing site to document.
	 * @param Site address as a string.
	 * @return Document parsed from site.
	 */
	private static Document parseHTMLtoDoc(String adress) {
		Document document = null;
		try {
			document = Jsoup.parse(new URL(adress), 100000);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return document;
	}
	
	/**
	 * Set private variables to new objects.
	 */
	private static void resetClassVariables() {
		titleBookContainer = new StringBuilder();
		addressHashSet = new HashSet<>();
	}
	
	/**
	 * Finds out if given tag contains number.
	 * @param Element to check
	 * @return True, if tag contains number, false otherwise. 
	 */
	private static boolean tagContainsNumberAndWasNotFoundBefore(Element element) {
		return NumberUtils.isNumber(element.text()) && !addressHashSet.contains(element.attr("abs:href"))
				&& (addressHashSet.size() < 30);
	}
}
