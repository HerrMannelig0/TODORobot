package com.epam.robot;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;

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
	
	public static void main(String[] args) {
		BookTitleSearch obj = new BookTitleSearch();
		HowItWorks test = obj.new HowItWorks();
		test.testSearchLinksToNextPages();
	}

	public static String searchTitlesInPageAndSubPages(String bookstoreAddressFromTextfile, String typeOfElement,
			String elementName) {
		logger.info("Started searching Titles for adress =  " + bookstoreAddressFromTextfile);

		resetClassVariables();

		searchLinksToNextPages(bookstoreAddressFromTextfile); 

		final Iterator<String> iterator = addressHashSet.iterator();

		logger.info("Started iterating over links to search titles");
		logger.info("a" + elementName + "a");

		while (iterator.hasNext()) {
			final String next = iterator.next();
			System.out.println(next);
			BookTitleSearch.searchTitles(next, typeOfElement, elementName);
		}

		logger.info("Finished iterating over links to search titles");

		return titleBookContainer.toString();
	}

	private static void resetClassVariables() {
		titleBookContainer = new StringBuilder();
		addressHashSet = new HashSet<>();
	}

	public static void searchTitles(String bookstoreAddressFromTextfile, String typeOfElement, String elementName) {
		Document document = parseHTMLtoDoc(bookstoreAddressFromTextfile);

		Element body = document.body();

		Elements elements;

		/*
		 * if (typeOfElement.equals("tag")) { elements =
		 * body.getElementsByTag(elementName); } else { elements =
		 * body.getElementsByClass(elementName); }
		 */

		elements = body.getElementsByClass(elementName);

		Iterator<Element> iterator = elements.iterator();

		while (iterator.hasNext()) {
			Element next = iterator.next();
			System.out.println(next.text());
			titleBookContainer.append(next.text() + "\n");
		}
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
}
