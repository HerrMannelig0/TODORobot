package com.epam.robot;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.epam.file.FileLinkHandler;
import com.epam.file.Link;

public class NewRobot {

	static Crawler crawler;

	public static void main(String[] args) {
		NewRobot robot = new NewRobot();
		crawler = robot.new Crawler();
		crawler.crawl();
	}

	Bookstores bookstores() {
		NewRobot robot = new NewRobot();
		crawler = robot.new Crawler();
		return crawler.safeInitializationOfBookstores();
	}

	private class Crawler {

		ApplicationContext context = new AnnotationConfigApplicationContext(CrawlerBeans.class);
		Logger logger = (Logger) context.getBean("logger");

		public void crawl() {

			logger.info("Start of crawling");
			
			Library library = context.getBean(Library.class);

			Bookstores bookstores = safeInitializationOfBookstores();

			List<Link> links = generateListOfLinksFromDefaultAddressFile();

			//crawling through sites and appending founded books to library
			links.stream().forEach(link -> library.addAll(context.getBean(BookTitleSearch.class)
					.searchTitlesInPageAndSubPages(link.getLinkAdress(), link.getElementType(), link.getTitleTag(),
							link.getAuthorTag(), link.getPriceTag(), link.getKeywordsTag())));
			
			library.assignCategoryForAllBooks();
			
			logger.info("Crawling finished");
		}

		private List<Link> generateListOfLinksFromDefaultAddressFile() {
			FileLinkHandler fileLinkHandler = context.getBean(FileLinkHandler.class);

			try {
				fileLinkHandler.createListsFromFile((File) context.getBean("addressFile"));
			} catch (FileNotFoundException e) {
				logger.error("Cannot find default address File");
			}
			return fileLinkHandler.getLinksList();
		}

		private Bookstores safeInitializationOfBookstores() {
			try {
				return createBookstoresFromDefaultFile();
			} catch (LackOfAddressFileException e) {
				logger.error("Tries of read sites address from default file has failed");
				return new Bookstores();
			}
		}

		private Bookstores createBookstoresFromDefaultFile() throws LackOfAddressFileException {
			Bookstores bookstores = (Bookstores) context.getBean("bookstores");
			File file = (File) context.getBean("addressFile");

			try {
				bookstores.generateBookstoreSet(file);
			} catch (FileNotFoundException e) {
				throw new LackOfAddressFileException();
			}
			return bookstores;
		}

		private class LackOfAddressFileException extends FileNotFoundException {

			private static final long serialVersionUID = 4656355786179358422L;

			public LackOfAddressFileException() {
			}
		}

	}

}
