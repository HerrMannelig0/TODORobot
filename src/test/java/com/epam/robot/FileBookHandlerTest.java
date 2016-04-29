package com.epam.robot;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URL;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.testng.annotations.Test;

import com.epam.DB.HibernateUtil;
import com.epam.file.FileBookHandler;

public class FileBookHandlerTest {

	@Test
	public void testWritingToDatabase() throws Exception {

		Book book = new Book("anyTitle", "AnyAuthor", "Free", new Keywords(new String[]{"vampire"}), new URL("https://www.gutenberg.org/ebooks/search/?query=free+book&go=Go"));
		book.setBookCategory("Fantasy");
		
		FileBookHandler.writeBookToDatabase(book);
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		Query query = session.createQuery("from BookDAO");
		@SuppressWarnings("unchecked")
		List<String> list = query.list();
		assertThat(list.size()).isGreaterThan(0);
		session.getTransaction().commit();
		session.close();
		
	}
	
}
