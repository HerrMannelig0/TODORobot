package com.epam.robot;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;
import org.eclipse.jetty.util.ConcurrentHashSet;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.epam.DAO.DBWriter;
import com.epam.file.FileLinkHandler;
import com.epam.file.Link;
import com.epam.util.CategoryUtil;

/**
 * Main class of the program, it controls crawling trough sites and finding books.
 *
 */
public class Robot {

	static Crawler crawler;
	
	/**
	 * Method, that starts the program.
	 */
	public static void main(String[] args) {
		
		ApplicationContext context = new AnnotationConfigApplicationContext(CrawlerBeans.class);
		Logger logger = (Logger) context.getBean("logger");
		
		Robot robot = new Robot();
		crawler = robot.new Crawler();
		try {
			crawler.crawl();
		} catch (InterruptedException | ExecutionException e) {
			logger.error(e.getClass() + "Crawling interrupted.");
		}
	}

	/**
	 * Private inner class, that is responsible for crawling through sites.
	 *
	 */
	private class Crawler {

		ApplicationContext context = new AnnotationConfigApplicationContext(CrawlerBeans.class);
		Logger logger = (Logger) context.getBean("logger");
		
		public void crawl() throws InterruptedException, ExecutionException {

			logger.info("Start of crawling");

			Library library = context.getBean(Library.class);

			ConcurrentHashSet<Book> concurrentLibrary = new ConcurrentHashSet<>();

			List<Link> links = generateListOfLinksFromDefaultAddressFile();

			ConcurrentHashSet<Link> concurrentLinks = new ConcurrentHashSet<>();
			concurrentLinks.addAll(links);
			
			ExecutorService executorService = Executors.newFixedThreadPool(3);
			Set<Future<ConcurrentHashSet<Book>>> set = new HashSet<>();

			

			for (Link link : concurrentLinks) {
				Callable<ConcurrentHashSet<Book>> callable = new CrawlerCaller();
				((CrawlerCaller) callable).setLink(link);
				Future<ConcurrentHashSet<Book>> future = executorService.submit(callable);
				set.add(future);
			}

			for (Future<ConcurrentHashSet<Book>> next : set) {
				/*ConcurrentHashSet<Book> n = next.get();
				System.err.println(n)*/;
				concurrentLibrary.addAll(next.get());
			}

			logger.info("Crawling finished");

			library.addAll(concurrentLibrary);
			executorService.shutdown();

			LibrariesMap librariesMap = CategoryUtil.generateLibrariesMapfromLibrary(library);
			DBWriter dbWriter = new DBWriter();

			dbWriter.write(librariesMap.toDB());
		}

		/**
		 * Method to generate links from default file with addresses and tags.
		 * @return List<Links> list of links to bookstores.
		 */
		private List<Link> generateListOfLinksFromDefaultAddressFile() {
			FileLinkHandler fileLinkHandler = context.getBean(FileLinkHandler.class);

			try {
				fileLinkHandler.createListsFromFile((File) context.getBean("addressFile"));
			} catch (FileNotFoundException e) {
				logger.error("Cannot find default address File");
			}
			return fileLinkHandler.getLinksList();
		}

		/**
		 * This private inner class implements Callable interface and in call() method 
		 * returns concurrent library of books that has been found
		 * during crawling.
		 */
		private class CrawlerCaller implements Callable<ConcurrentHashSet<Book>> {

			private Link link;

			public CrawlerCaller() {
			}

			/**
			 * Link setter.
			 * @param link
			 */
			public void setLink(Link link) {
				this.link = link;
			}

			/* (non-Javadoc)
			 * @see java.util.concurrent.Callable#call()
			 */
			@Override
			public ConcurrentHashSet<Book> call() throws Exception {
				Library library = context.getBean(Library.class);
				
				// crawling through sites and appending founded books to library
				library.addAll(context.getBean(BookTitleSearch.class).searchTitlesInPageAndSubPages(
						link.getLinkAdress(), link.getElementType(), link.getTitleTag(), link.getAuthorTag(),
						link.getPriceTag(), link.getKeywordsTag()));

				library.assignCategoryForAllBooks();
				ConcurrentHashSet<Book> concurrentLibrary = new ConcurrentHashSet<>();
				concurrentLibrary.addAll(library);
				return concurrentLibrary;
			}

		}

	}

}
