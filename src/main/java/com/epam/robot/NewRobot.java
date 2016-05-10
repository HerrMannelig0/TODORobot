package com.epam.robot;

import com.epam.DAO.DBWriter;
import com.epam.file.FileLinkHandler;
import com.epam.file.Link;
import com.epam.util.CategoryUtil;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

public class NewRobot {

	static Crawler crawler;

	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext(CrawlerBeans.class);
		Logger logger = (Logger) context.getBean("logger");
		NewRobot robot = new NewRobot();
		crawler = robot.new Crawler();
		try {
			crawler.crawl();
		} catch (InterruptedException | ExecutionException e) {
			logger.error(e.getClass() + " Crawling interrupted");
		}
	}

	Bookstores bookstores() {
		NewRobot robot = new NewRobot();
		crawler = robot.new Crawler();
		return crawler.safeInitializationOfBookstores();
	}

	private class Crawler {

		ApplicationContext context = new AnnotationConfigApplicationContext(CrawlerBeans.class);
		Logger logger = (Logger) context.getBean("logger");

		public void crawl() throws InterruptedException, ExecutionException {

			logger.info("Start of crawling");

			Library library = context.getBean(Library.class);
			ConcurrentLibrary concurrentLibrary = new ConcurrentLibrary();
			
			List<Link> links = generateListOfLinksFromDefaultAddressFile();
			
			Set<Future<ConcurrentLibrary>> set = executeCrawling(links);
			
			for (Future<ConcurrentLibrary> next : set){
				concurrentLibrary.addAll(next.get());
			}

			logger.info("Crawling finished");

			library.addAll(concurrentLibrary);
			
			LibrariesMap librariesMap = CategoryUtil.generateLibrariesMapfromLibrary(library);
			DBWriter dbWriter = new DBWriter();

			dbWriter.write(librariesMap.toDB());
		}

		private Set<Future<ConcurrentLibrary>> executeCrawling(List<Link> links) {
			ExecutorService executorService = Executors.newFixedThreadPool(3);
			Set<Future<ConcurrentLibrary>> set = new HashSet<>();
			
			
			for (Link link : links) {
				Callable<ConcurrentLibrary> callable = new CrawlerCaller();
				((CrawlerCaller)callable).setLink(link);
				Future<ConcurrentLibrary> future = executorService.submit(callable);
				set.add(future);
			}
			
			executorService.shutdown();
			
			return set;
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

		private class CrawlerCaller implements Callable<ConcurrentLibrary> {

			private Link link;
			
			public CrawlerCaller() {
			}
			
			public void setLink(Link link) {
				this.link = link;
			}

			@Override
			public ConcurrentLibrary call() throws Exception {
				//Library library = context.getBean(Library.class);
				Library library = new Library();
				//crawling through sites and appending founded books to library
				library.addAll(context.getBean(BookTitleSearch.class)
						.searchTitlesInPageAndSubPages(link.getLinkAdress(), link.getElementType(), link.getTitleTag(),
								link.getAuthorTag(), link.getPriceTag(), link.getKeywordsTag()));
				
				library.assignCategoryForAllBooks();
				ConcurrentLibrary concurrentLibrary = new ConcurrentLibrary();
				concurrentLibrary.addAll(library);
				return concurrentLibrary;
			}

		}

		private class LackOfAddressFileException extends FileNotFoundException {

			private static final long serialVersionUID = 4656355786179358422L;

			public LackOfAddressFileException() {
			}
		}

	}

}
