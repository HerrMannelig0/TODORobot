package com.epam.robot;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.AbstractSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;

import com.epam.DAO.Bookstore;
import com.epam.file.FileLinkHandler;
import com.epam.file.Link;

public class Bookstores extends AbstractSet<Bookstore>{
	public Set<Bookstore> bookstores;
	
	public Bookstores(){
		 bookstores = new HashSet<>();
	}
	
	private static Logger logger = Logger.getLogger("Bookstores");
	/**
	 * Generating bookstores' set from file with links.
	 * @param File with bookstores' links
	 * @return Set of Bookstores
	 * @throws FileNotFoundException
	 */
	public Set<Bookstore> generateBookstoreSet(File file) throws FileNotFoundException{
		
		Set<Bookstore> resultBokstores = new HashSet<>();
		FileLinkHandler fileLinkHandler = new FileLinkHandler();
		Set<Link> linksWithTags = fileLinkHandler.readLinksFromFile(file);
		
		for (Link link : linksWithTags) {
			String address = link.getLinkAdress();
			Bookstore bookstore = new Bookstore(extractBookstoreName(address));
			bookstore.init();
			resultBokstores.add(bookstore);
		}
		bookstores = resultBokstores;
		return resultBokstores;
	}
	
	/**
	 * Adds Book to bookstores' list.
	 * @param URL to bookstore
	 * @param book
	 */
	 void addBookToBookstoreInBookstoresList(String url, Book book) {
		String bookstoreName = extractBookstoreName(url);
		Bookstore bookstore = getBookstoreFromSet(bookstoreName, bookstores);	
		if(bookstore == null) logger.error("There is no such Bookstore: " + bookstoreName);
		else bookstore.addBookDAO(book.convertToBookDAO());
	}
	
	/**
	 * Searching Bookstore in given set.
	 * @param Name of Bookstore
	 * @param Set of Bookstores
	 * @return Bookstore
	 */
	public Bookstore getBookstoreFromSet(String bookstoreName, Set<Bookstore> set) {
		for (Bookstore bookstore : set) {
			if(bookstoreName.equals(bookstore.getBookstorename())) return bookstore;
		}
		return null;
	}
	
	/**
	 * Getting bookstore name from book.
	 * @param Book
	 * @return Bookstore's name as string
	 */
	public String extractBookstoreName(Book book) {
		return extractBookstoreName(book.getUrl().toString());
	}
	
	/**
	 * Extracting Bookstore's name from URL.
	 * @param URL
	 * @return Bookstore's name as string
	 */
	String extractBookstoreName(String url) {
		int indexOFFirstDotAppearance = url.indexOf('.');
		return url.substring(indexOFFirstDotAppearance+1, url.indexOf('.', indexOFFirstDotAppearance+1));
	}

	@Override
	public Iterator<Bookstore> iterator() {
		return bookstores.iterator();
	}

	@Override
	public int size() {
		return bookstores.size();
	}
	
	@Override
	public boolean add(Bookstore bookstore) {
		return bookstores.add(bookstore);
	}
}
