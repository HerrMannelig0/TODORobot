package com.epam.DAO;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

public class AddingRecords {

	public static void main(String[] args) {

		Session session = HibernateUtil.getSessionFactory().openSession();

		Bookstore bookstore = new Bookstore();

		bookstore.setBookstorename("empik");
		bookstore.setURL("www.empik.com");
		
		Book grzedowicz = new Book();
		grzedowicz.setAuthor("Jarosław Grzędowicz");
		grzedowicz.setTitle("Popiół i kurz");
		grzedowicz.setBookId(11);

		Book lukajenko = new Book();
		lukajenko.setAuthor("Siergiej Lukajenko");
		lukajenko.setTitle("Czystiopis");
		lukajenko.setBookId(13);

		List<Book> books = new ArrayList<>();
		books.add(lukajenko);
		books.add(grzedowicz);

		bookstore.setBooks(books);

		session.beginTransaction();
		session.save(bookstore);
		session.save(grzedowicz);
		session.save(lukajenko);
		session.getTransaction().commit();

		session.close();

	}

}
