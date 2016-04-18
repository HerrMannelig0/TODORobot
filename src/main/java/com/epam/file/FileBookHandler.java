package com.epam.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import com.epam.DAO.Book;


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
	public static void writeBookToFile(com.epam.robot.Book book, PrintWriter printWriter) {
	
		printWriter.print(book);
		printWriter.flush();
	}

}