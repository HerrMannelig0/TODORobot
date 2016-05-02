package com.epam.robot;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.AbstractSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.epam.DB.entities.BookstoreDB;
import org.apache.log4j.Logger;

import com.epam.file.FileLinkHandler;
import com.epam.file.Link;

public class Bookstores extends AbstractSet<BookstoreDB>{
	public Set<BookstoreDB> bookstores;
	
	public Bookstores(){
		 bookstores = new HashSet<>();
	}
	
	private static Logger logger = Logger.getLogger("Bookstores");

	/**
	 * Adds Book to bookstoreDBs' list.
	 * @param url to bookstore
	 * @param book
	 */
	 void addBookToBookstoreInBookstoresList(String url, Book book) {
		String bookstoreName = extractBookstoreName(url);
		BookstoreDB bookstoreDB = getBookstoreFromSet(bookstoreName, bookstores);
		if(bookstoreDB == null) logger.error("There is no such BookstoreDB: " + bookstoreName);
		else bookstoreDB.addBook(book.convertToBookDAO());
	}
	
	/**
	 * Searching BookstoreDB in given set.
	 * @param bookstoreName of BookstoreDB
	 * @param set of Bookstores
	 * @return BookstoreDB
	 */
	public BookstoreDB getBookstoreFromSet(String bookstoreName, Set<BookstoreDB> set) {
		for (BookstoreDB bookstoreDB : set) {
			if(bookstoreName.equals(bookstoreDB.getName())) return bookstoreDB;
		}
		return null;
	}
	
	/**
	 * Getting bookstore name from book.
	 * @param book
	 * @return BookstoreDB's name as string
	 */
	public String extractBookstoreName(Book book) {
		return extractBookstoreName(book.getUrl().toString());
	}
	
	/**
	 * Extracting BookstoreDB's name from URL.
	 * @param url
	 * @return BookstoreDB's name as string
	 */
	String extractBookstoreName(String url) {
		int indexOFFirstDotAppearance = url.indexOf('.');
		return url.substring(indexOFFirstDotAppearance+1, url.indexOf('.', indexOFFirstDotAppearance+1));
	}

	@Override
	public Iterator<BookstoreDB> iterator() {
		return bookstores.iterator();
	}

	@Override
	public int size() {
		return bookstores.size();
	}
	
	@Override
	public boolean add(BookstoreDB bookstoreDB) {
		return bookstores.add(bookstoreDB);
	}


	/**
	 * Generating bookstoreDBs' set from file with links.
	 * @param file with bookstoreDBs' links
	 * @return Set of Bookstores
	 * @throws FileNotFoundException
	 */
	public Set<BookstoreDB> generateBookstoreSet(File file) throws FileNotFoundException{

		Set<BookstoreDB> resultBokstores = new HashSet<>();
		FileLinkHandler fileLinkHandler = new FileLinkHandler();
		Set<Link> linksWithTags = fileLinkHandler.readLinksFromFile(file);

		for (Link link : linksWithTags) {
			String address = link.getLinkAdress();
			BookstoreDB bookstore = new BookstoreDB(extractBookstoreName(address));
			bookstore.init();
			resultBokstores.add(bookstore);
		}
		bookstores = resultBokstores;
		return bookstores;
	}


}
