package com.epam.robot;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;
import org.eclipse.jetty.util.ConcurrentArrayQueue;
import org.eclipse.jetty.util.ConcurrentHashSet;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.epam.DAO.DBWriter;
import com.epam.file.FileLinkHandler;
import com.epam.file.Link;
import com.epam.util.CategoryUtil;

public class NewRobot {

	static Crawler crawler;

	public static void main(String[] args) {
		NewRobot robot = new NewRobot();
		crawler = robot.new Crawler();
		try {
			crawler.crawl();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
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

			ConcurrentHashSet<Book> concurrentLibrary = new ConcurrentHashSet<>();
			
			Bookstores bookstores = safeInitializationOfBookstores();

			List<Link> links1 = generateListOfLinksFromDefaultAddressFile();
			
			ConcurrentHashSet<Link> links = new ConcurrentHashSet<>();
 			links.addAll(links1);
			
			//ConList<Link> links = generateListOfLinksFromDefaultAddressFile();

			// crawling through sites and appending founded books to library
			links.stream().forEach(link -> library.addAll(context.getBean(BookTitleSearch.class)
					.searchTitlesInPageAndSubPages(link.getLinkAdress(), link.getElementType(), link.getTitleTag(),
							link.getAuthorTag(), link.getPriceTag(), link.getKeywordsTag())));

			//links.stream().forEach(link -> new CrawlerCaller(link));
			
			ExecutorService executorService = Executors.newFixedThreadPool(3); //newCachedThreadPool()
			Set<Future<ConcurrentHashSet<Book>>> set = new HashSet<>();
			
			Callable<ConcurrentHashSet<Book>> callable = new CrawlerCaller();
			
			for (Link link : links) {
				((CrawlerCaller)callable).setLink(link);
				Future<ConcurrentHashSet<Book>> future = executorService.submit(callable);
				set.add(future);
			}
			
			for (Future<ConcurrentHashSet<Book>> next : set){
				ConcurrentHashSet<Book> n = next.get();
				System.err.println(n);
				concurrentLibrary.addAll(n);
			}

			logger.info("Crawling finished");

			for (Book book : concurrentLibrary) {
				System.out.println("$$$ " + book + " " + book.extractBookstoreFromURL());
			}
			
			System.err.println("&&&&&& " + concurrentLibrary);
			library.addAll(concurrentLibrary);
			System.err.println("****** " + library);
			
			executorService.shutdown();
			
			LibrariesMap librariesMap = CategoryUtil.generateLibrariesMapfromLibrary(library);
			DBWriter dbWriter = new DBWriter();

			dbWriter.write(librariesMap.toDB());
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

		private class CrawlerCaller implements Callable<ConcurrentHashSet<Book> > {

			private Link link;
			
			public CrawlerCaller() {
			}
			
			public CrawlerCaller(Link link) {
				this.link = link;
			}
			
			public void setLink(Link link) {
				this.link = link;
			}

			@Override
			public ConcurrentHashSet<Book>  call() throws Exception {
				//Library library = context.getBean(Library.class);
				Library library = new Library();
				//crawling through sites and appending founded books to library
				library.addAll(context.getBean(BookTitleSearch.class)
						.searchTitlesInPageAndSubPages(link.getLinkAdress(), link.getElementType(), link.getTitleTag(),
								link.getAuthorTag(), link.getPriceTag(), link.getKeywordsTag()));
				
				library.assignCategoryForAllBooks();
				ConcurrentHashSet<Book> chs = new ConcurrentHashSet<>();
				chs.addAll(library);
				System.err.println("INSIDE CALLER: " + library + "///" + link);
				return chs;
			}

		}

		private class LackOfAddressFileException extends FileNotFoundException {

			private static final long serialVersionUID = 4656355786179358422L;

			public LackOfAddressFileException() {
			}
		}

	}

}
