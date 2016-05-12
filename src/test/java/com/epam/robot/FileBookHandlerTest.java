package com.epam.robot;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test of {@code FileBookHandler} class
 *
 */
public class FileBookHandlerTest {

	/**
	 * Writing to database test
	 *//*
	@Test
	public void testWritingToDatabase() throws Exception {

		Book book = new Book("anyTitle", "AnyAuthor", "Free", new Keywords(new String[]{"vampire"}), new URL("https://www.gutenberg.org/ebooks/search/?query=free+book&go=Go"));
		book.setBookCategory(new Category("Fantasy"));
		
		FileBookHandler.writeBookToDatabase(book);
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		Query query = session.createQuery("from BookDB");
		@SuppressWarnings("unchecked")
		List<String> list = query.list();
		assertThat(list.size()).isGreaterThan(0);
		session.getTransaction().commit();
		session.close();
		
	}*/
	
}
