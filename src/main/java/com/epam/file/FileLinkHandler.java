package com.epam.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.apache.log4j.Logger;

import com.epam.gui.MainClass;

public class FileLinkHandler {

	protected List<Link> linkList = new ArrayList<Link>();
	protected List<String> urlList = new ArrayList<String>();
	private static Logger logger = Logger.getLogger(FileLinkHandler.class);

	public void createListsFromFile(File fileWithLinksToBookstores) throws FileNotFoundException {
		logger.info("Start of creating List from File: " + fileWithLinksToBookstores.getPath());
		updateLinkList(fileWithLinksToBookstores);
		updateURLList();
	}

	// not used anywhere (for now, important method, update list of bookstores
	// in file)
	public void writeLinksToFile(List<Link> linkList, File fileWithLinksToBookstores) {
		try (PrintWriter printWriter = new PrintWriter(fileWithLinksToBookstores)) {
			for (int i = 0; i < linkList.size(); i++) {
				printWriter.println(linkList.get(i).toString());
			}
			printWriter.flush();
		} catch (FileNotFoundException e) {
			logger.error("Exception occured during writing file to file (File not Found): "
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

	public List<Link> readLinksFromFile(File fileWithLinksToBookstores) throws FileNotFoundException {
		Scanner scanner = new Scanner(fileWithLinksToBookstores);
		List<Link> links = new ArrayList<>();

		while (scanner.hasNextLine()) {
			String[] partsOfRowFromFileWithBookstores = scanner.nextLine().split(" ");
			if (doesLinkContainThreeParts(partsOfRowFromFileWithBookstores)) {
				Link link = new Link(partsOfRowFromFileWithBookstores[0], partsOfRowFromFileWithBookstores[1],
						partsOfRowFromFileWithBookstores[2]);
				links.add(link);

				logger.info("New link get from file: " + link.toString());
			}
		}
		scanner.close();
		return links;

	}

	protected static boolean doesLinkContainThreeParts(String[] partsOfRowFromFileWithBookstores) {
		return partsOfRowFromFileWithBookstores.length == 3;
	}

}
