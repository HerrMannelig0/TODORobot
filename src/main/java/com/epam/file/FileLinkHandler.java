package com.epam.file;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.log4j.Logger;

/**
 * Handling crawling through pages.
 */
public class FileLinkHandler {

	protected List<Link> linkList = new CopyOnWriteArrayList<Link>();
	protected List<String> urlList = new ArrayList<String>();
	private static Logger logger = Logger.getLogger(FileLinkHandler.class);

	public void createListsFromFile(File fileWithLinksToBookstores) throws FileNotFoundException {
		
		logger.trace("Start of creating List from File: " + fileWithLinksToBookstores.getPath());
		updateLinkList(fileWithLinksToBookstores);
		updateURLList();
	}

	/**
	 * Writing links into File
	 * @param linkList to write
	 * @param fileWithLinksToBookstores
     */
	public void writeLinksToFile(List<Link> linkList, File fileWithLinksToBookstores) {


		try (PrintWriter printWriter = new PrintWriter(fileWithLinksToBookstores)) {
			for (int i = 0; i < linkList.size(); i++) {
				printWriter.println(linkList.get(i).toString());
			}
			printWriter.flush();
		} catch (FileNotFoundException e) {
			logger.error("Exception occurred during writing file to file (File not Found): "
					+ fileWithLinksToBookstores.getPath());
			e.printStackTrace();
		}
		logger.info("Link list has been written into file");
	}

	public List<Link> getLinksList() {
		return linkList;
	}

	public List<String> getUrlList() {
		return urlList;
	}

	/**
	 * Adding all read
	 * @param fileWithLinksToBookstores
	 * @throws FileNotFoundException
     */
	protected void updateLinkList(File fileWithLinksToBookstores) throws FileNotFoundException {
		linkList.addAll(readLinksFromFile(fileWithLinksToBookstores));
	}

	protected void updateURLList() {
		for (Link link : linkList) {
			urlList.add(link.getLinkAdress());
		}
	}

	protected void clearLinkList() {
		linkList.clear();
	}

	protected void clearURLList() {
		urlList.clear();
	}

	public Set<Link> readLinksFromFile(File fileWithLinksToBookstores) throws FileNotFoundException {
		Scanner scanner = new Scanner(fileWithLinksToBookstores, "UTF-8");
		Set<Link> links = new HashSet<>();

		while (scanner.hasNextLine()) {
			String[] partsOfRowFromFileWithBookstores = scanner.nextLine().split(" ");
			if (doesLinkContainsSixParts(partsOfRowFromFileWithBookstores)) {
				
				Link link = new Link(partsOfRowFromFileWithBookstores[0], partsOfRowFromFileWithBookstores[1],
						partsOfRowFromFileWithBookstores[2], partsOfRowFromFileWithBookstores[3], 
						partsOfRowFromFileWithBookstores[4], partsOfRowFromFileWithBookstores[5]);
				links.add(link);

				logger.info("New link get from file: " + link.toString());
			}
			
		}
		scanner.close();
		return links;

	}

	private String addSlashesToUrl(String url) {
		int index = url.indexOf(':')+1;
		return url.substring(0,index) + "//" + url.substring(index);
	}

	protected static boolean doesLinkContainsSixParts(String[] partsOfRowFromFileWithBookstores) {
		return partsOfRowFromFileWithBookstores.length == 6;
	}

}
