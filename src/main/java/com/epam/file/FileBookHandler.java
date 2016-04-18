package com.epam.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;

import com.epam.DAO.BookDAO;
import com.epam.DAO.Bookstore;
import com.epam.DAO.HibernateUtil;
import com.epam.robot.Book;


public class FileBookHandler {


	private String fileName;
	private File freeBooksTitlesUrl;
	private StringBuilder bookTitles;

	public FileBookHandler(String fileName) {
		this.fileName = fileName;
		this.bookTitles = new StringBuilder();

		loadFile();
		readBookTitlesFromFile();
	}

	/**
	 * method providing a file to put into it books later.
	 */
	private void loadFile() {
		ClassLoader classLoader = getClass().getClassLoader();
		freeBooksTitlesUrl = new File(classLoader.getResource(fileName).getFile());
		
	}

	/**
	 * @return title of book
	 */
	public String readBookTitlesFromFile() {
		try (Scanner scanner = new Scanner(freeBooksTitlesUrl)) {

			while (scanner.hasNextLine()) {
				bookTitles.append(scanner.nextLine() + "\n");
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return bookTitles.toString();
	}

	/**
	 * puts Books fields into a textfile.
	 * @param book
	 * @param printWriter
	 */
	public static void writeBookToFile(Book book, PrintWriter printWriter) {
		
		printWriter.print(book);
		printWriter.flush();
		
		Bookstore bookstore = new Bookstore();
		
		bookstore.setBookstorename(book.getUrl().toString());
		bookstore.setURL("www.empik.com");
		BookDAO bookToDB = new BookDAO();
		bookToDB.setAuthor(book.getAuthor());
		bookToDB.setTitle(book.getTitle());

		List<BookDAO> booksInBookstore = new ArrayList<>();
		booksInBookstore.add(bookToDB);
	
		bookstore.setBooks(booksInBookstore);
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		session.save(bookstore); //saving bookstore
		session.save(bookToDB); //three below: saving books
		
		session.getTransaction().commit();
		session.close();
	}

}