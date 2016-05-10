package com.epam.robot;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.xml.ws.http.HTTPException;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.epam.library.Book;
import com.epam.library.Keywords;
import com.epam.library.Library;


public class BookTitleSearch {
    static private Set<String> addressHashSet = new HashSet<>();

    private static Logger logger = Logger.getLogger("BookTitleSearch");

    /**
     * Parsing website and searching for links there.
     * @param bookstoreAddressFromTextfile
     */
    public void searchLinksToNextPages(String bookstoreAddressFromTextfile) {

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
            logger.error("Other exception while site parsing: " + bookstoreAddressFromTextfile);
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
    public Library searchTitles(String bookstoreAddressFromTextfile, String typeOfElement,
                                String titleTag, String authorTag, String priceTag, String keywordsTag) throws MalformedURLException {

        Document document = parseHTMLtoDoc(bookstoreAddressFromTextfile);
        Library library = new Library();

        Element body = document.body();
        Elements items = body.getElementsByClass(typeOfElement);

        for(Element item : items){
            String title = extractElementFromSite(item, titleTag);
            String author = omitByInAuthor(extractElementFromSite(item, authorTag));
            String price = extractElementFromSite(item, priceTag);
            String keywordsAsStringFromSite = extractElementFromSite(item, keywordsTag);

            Keywords keywords = extractKeywords(keywordsAsStringFromSite);
            Book book = new Book(title, author, price, keywords, new URL(bookstoreAddressFromTextfile));

            library.add(book);
        }
        return library;
    }


    /**
     * creates String containing titles from all pages from bookstore web site
     *
     * @param bookstoreAddressFromTextfile the main bookstore address
     * typeOfElement elementName a specified tag name to search for.
     *
     * @return String object with book titles and keywords (if they exist)
     */
    public synchronized Library searchTitlesInPageAndSubPages(String bookstoreAddressFromTextfile, String typeOfElement,
                                                              String titleTag, String authorTag, String priceTag, String keywordsTag) {

        System.err.println("ITERATION : " + bookstoreAddressFromTextfile);
        logger.info("Started searching Titles for adress =  " + bookstoreAddressFromTextfile);

        resetClassVariables();

        searchLinksToNextPages(bookstoreAddressFromTextfile);

        final Iterator<String> iterator = addressHashSet.iterator();
        Library library = new Library();
        logger.info("Started iterating over links to search titles");

        while (iterator.hasNext()) {
            final String next = iterator.next();
            try {
                library = searchTitles(next, typeOfElement, titleTag, authorTag, priceTag, keywordsTag);
            } catch (Exception e) {
                logger.error(e.getClass() + " " + next);
            }
        }

        logger.info("Finished iterating over links to search titles");


        return library;
    }

    /**
     * Finds out if given string starts with "Keywords: " phrase.
     * @param String, that should contains keywords of book.
     * @return True if given string starts with "Keywords: ", else otherwise.
     */
    public  boolean areKeywords(String valueFromSite) {
        return valueFromSite.startsWith("Keywords:");
    }

    /**
     * Parse string from site to array of keywords.
     * @param keywords as a string
     * @return Array of keywords
     */
    protected Keywords extractKeywords(String valueFromSite) {
        if(valueFromSite.length()<1) return null;
        int indexOfStart = valueFromSite.indexOf(':') + 2;
        String [] keywords = valueFromSite.substring(indexOfStart).toLowerCase().split(", ");
        return new Keywords(keywords);
    }

    /**
     * Delete "By: " in the author's element from site.
     * @param extractElementFromSite
     * @return Author without "By: "
     */
    protected  String omitByInAuthor(String extractElementFromSite) {
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
    String extractElementFromSite(Element item, String tag){
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
    private void addLinkToSetAndSearchInLinkForPages(String linkToSubPage) {
        addressHashSet.add(linkToSubPage);
        searchLinksToNextPages(linkToSubPage);
    }

    /**
     * Parsing site to document.
     * @param Site address as a string.
     * @return Document parsed from site.
     */
    private Document parseHTMLtoDoc(String address) {
        Document document = null;


        try {
            document = Jsoup.parse(new URL(address), 100000);
        } catch(HttpStatusException e){
            logger.error(e.getClass() + " HTTP error fetching URL. Status=403 on site: " + address);

        } catch(HTTPException e){
            logger.error(e.getClass() + " Problem with access to the page: " + address);

        } catch (IOException e) {
            logger.error(e.getClass() + " Other problem with parsing page to Document");
        }
        return document;
    }

    /**
     * Set private variables to new objects.
     */
    private void resetClassVariables() {
        new StringBuilder();
        addressHashSet = new HashSet<>();
    }

    /**
     * Finds out if given tag contains number.
     * @param Element to check
     * @return True, if tag contains number, false otherwise.
     */
    private boolean tagContainsNumberAndWasNotFoundBefore(Element element) {
        return NumberUtils.isNumber(element.text()) && !addressHashSet.contains(element.attr("abs:href"))
                && (addressHashSet.size() < 30);
    }
}