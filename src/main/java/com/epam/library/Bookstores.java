package com.epam.library;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.AbstractSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;

import com.epam.DB.entities.BookstoreToDB;
import com.epam.file.FileLinkHandler;
import com.epam.file.Link;

public class Bookstores extends AbstractSet<BookstoreToDB> {
	public Set<BookstoreToDB> bookstores;

	public Bookstores() {
		bookstores = new HashSet<>();
	}

	private static Logger logger = Logger.getLogger("Bookstores");


	/**
	 * Searching BookstoreDB in given set.
	 * 
	 * @param bookstoreName
	 *            of BookstoreDB
	 * @param set
	 *            of Bookstores
	 * @return BookstoreDB
	 */
	public BookstoreToDB getBookstoreFromSet(String bookstoreName, Set<BookstoreToDB> set) {
		for (BookstoreToDB bookstoreDB : set) {
			if (bookstoreName.equals(bookstoreDB.getName()))
				return bookstoreDB;
		}
		return null;
	}

	/**
	 * Getting bookstore name from book.
	 * 
	 * @param book
	 * @return BookstoreDB's name as string
	 */
	public String extractBookstoreName(Book book) {
		return extractBookstoreName(book.getUrl().toString());
	}

	/**
	 * Extracting BookstoreDB's name from URL.
	 * 
	 * @param url
	 * @return BookstoreDB's name as string
	 */
	public String extractBookstoreName(String url) {
		int indexOFFirstDotAppearance = url.indexOf('.');
		return url.substring(indexOFFirstDotAppearance + 1, url.indexOf('.', indexOFFirstDotAppearance + 1));
	}

	@Override
	public Iterator<BookstoreToDB> iterator() {
		return bookstores.iterator();
	}

	@Override
	public int size() {
		return bookstores.size();
	}

	@Override
	public boolean add(BookstoreToDB bookstoreDB) {
		return bookstores.add(bookstoreDB);
	}

	/**
	 * Generating bookstoreDBs' set from file with links.
	 * 
	 * @param file
	 *            with bookstoreDBs' links
	 * @return Set of Bookstores
	 * @throws FileNotFoundException
	 */
	public Set<BookstoreToDB> generateBookstoreSet(File file) throws FileNotFoundException {

		Set<BookstoreToDB> resultBokstores = new HashSet<>();
		FileLinkHandler fileLinkHandler = new FileLinkHandler();
		Set<Link> linksWithTags = fileLinkHandler.readLinksFromFile(file);

		for (Link link : linksWithTags) {
			String address = link.getLinkAdress();
			BookstoreToDB bookstore = new BookstoreToDB(extractBookstoreName(address), null);
			resultBokstores.add(bookstore);
		}
		bookstores = resultBokstores;
		return bookstores;
	}

	@Override
	public String toString() {
		return "Bookstores [bookstores=" + bookstores + "]";
	}

}
