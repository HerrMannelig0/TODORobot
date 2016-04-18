package com.epam.DAO;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

public class AddingRecords {

	public static void main(String[] args) {

		setCategories();

		
		
/*		Bookstore bookstore = new Bookstore();
		
		bookstore.setBookstorename("empik");
		bookstore.setURL("www.empik.com");
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
		
		
		session.beginTransaction();
		
		session.save(bookstore); //saving bookstore
		session.save(grzedowicz); //three below: saving books
		session.save(lukajenko);
		session.save(witcher);
		
		session.getTransaction().commit();

		session.close();*/

	}

	private static void setCategories() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		ArrayList <String> listOfCategories = (ArrayList<String>)AddingCategories.keywordFetcher();
		for (String string : listOfCategories) {
			Category category = new Category();
			category.setCategoryName(string);
			
			session.beginTransaction();
			session.save(category);
			session.getTransaction().commit();
		}
		session.close();
	}

}
