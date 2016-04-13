package com.epam.DAO;

import org.hibernate.Session;

public class AddingRecords {

	public static void main(String[] args) {
		System.out.println("Maven + Hibernate + Oracle");
		Session session = HibernateUtil.getSessionFactory().openSession();

		session.beginTransaction();
		Bookstore bookstore = new Bookstore();

		bookstore.setBookstorename("empik");
		bookstore.setURL("www.empik.com");

		Book grzedowicz = new Book();
		grzedowicz.setAuthor("Jarosław Grzędowicz");
		grzedowicz.setTitle("Popiół i kurz");
		grzedowicz.setBookstore(bookstore);

		session.save(bookstore);
		session.getTransaction().commit();
		session.beginTransaction();
		session.save(grzedowicz);
		session.getTransaction().commit();
	}

}
