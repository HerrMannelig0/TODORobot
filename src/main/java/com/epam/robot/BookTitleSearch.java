package com.epam.robot;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Created by damian on 22.03.16.
 */
public class BookTitleSearch {
    private static Logger logger = Logger.getLogger("BookTitleSearch");
    private static StringBuilder titleBookContainer = new StringBuilder();


    public static String searchTitlesInPageAndSubPages(String adress, String typeOfElement, String elementName) {
        logger.info("Started searching Titles for adress =  " + adress);
        resetClassVariables();

        searchSubPagesInPageByNumber(adress);

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


    public static void searchTitles(String adress, String typeOfElement, String elementName) {
        Document document = createDocument(adress);

        Element body = document.body();

        Elements elements;

        if (typeOfElement.equals("tag")) {
            elements = body.getElementsByTag(elementName);
        } else {
            elements = body.getElementsByClass(elementName);
        }

        Iterator<Element> iterator = elements.iterator();

        while (iterator.hasNext()) {
            Element next = iterator.next();
            System.out.println(next.text());
            titleBookContainer.append(next.text() + "\n");
        }
    }

    static private HashSet<String> addressHashSet = new HashSet<String>();

    public static void searchSubPagesInPageByNumber(String adress) {
        Document doc = createDocument(adress);

        Elements links = doc.select("a[abs:href]");
        Iterator<Element> iterator = links.iterator();
        addressHashSet.add(adress);

        while (iterator.hasNext()) {
            Element element = iterator.next();

            if (isNumberInTagAndIsLinkWasSearch(element)) {
                addLinkToSetAndSearchInLinkForPages(element.attr("abs:href"));
            }
        }
    }

    private static boolean isNumberInTagAndIsLinkWasSearch(Element element) {
        return NumberUtils.isNumber(element.text().trim()) && !addressHashSet.contains(element.attr("abs:href")) && (addressHashSet.size() < 30);
    }

    private static void addLinkToSetAndSearchInLinkForPages(String linkToSubPage) {
        System.out.println(linkToSubPage);
        addressHashSet.add(linkToSubPage);
        searchSubPagesInPageByNumber(linkToSubPage);
    }


    private static Document createDocument(String adress) {
        Document document = null;
        try {
             document = Jsoup.parse(new URL(adress), 100000);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return document;
    }

}
