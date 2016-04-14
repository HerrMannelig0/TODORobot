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
		
		Category romance = new Category();
		romance.setCategoryName("Romance");
		
		Category fantasy = new Category();
		fantasy.setCategoryName("fantasy");
		
		Book grzedowicz = new Book();
		grzedowicz.setAuthor("Jarosław Grzędowicz");
		grzedowicz.setTitle("Popiół i kurz");

		Book lukajenko = new Book();
		lukajenko.setAuthor("Siergiej Lukajenko");
		lukajenko.setTitle("Czystiopis");

		Book witcher = new Book();
		witcher.setAuthor("Andrzej Sapkowski");
		witcher.setTitle("Ostatnie Życzenie");
		
		List<Book> booksInEmpik = new ArrayList<>();
		booksInEmpik.add(lukajenko);
		booksInEmpik.add(grzedowicz);
	
		bookstore.setBooks(booksInEmpik);

		List <Book> fantasyBooks = new ArrayList<>();
		fantasyBooks.add(grzedowicz);
		fantasyBooks.add(witcher);
		
		fantasy.setBooks(fantasyBooks);
		
		session.beginTransaction();
		
		session.save(bookstore); //saving bookstore
		session.save(fantasy); //saving category
		session.save(grzedowicz); //three below: saving books
		session.save(lukajenko);
		session.save(witcher);
		
		session.getTransaction().commit();

		session.close();

	}

}
