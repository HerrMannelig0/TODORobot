package com.epam.DAO;

import org.hibernate.Session;

public class AddingRecords {

	public static void main(String[] args) {

		System.out.println("Hibernate one to many (Annotation)");
		Session session = HibernateUtil.getSessionFactory().openSession();

		session.beginTransaction();
		Book grzedowicz = new Book("Jarosław Grzędowicz", "Popiół i kurz", 49);
		System.out.println(grzedowicz);
		session.save(grzedowicz);
		
		Book lukajenko = new Book("Siergiej Łukajenko", "Czystopis", 49);
		session.save(lukajenko);

		Bookstore empik = new Bookstore("empik", "http://");
		/*
		 * grzedowicz.setBookstore(empik);
		 * empik.getBookRecords().add(lukajenko);
		 */

		session.save(empik);

		session.getTransaction().commit();
		System.out.println("Done");
	}

}
