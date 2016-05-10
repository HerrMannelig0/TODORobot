package com.epam.DAO.DBmanagment;

import org.hibernate.Session;

import com.epam.DAO.HibernateUtil;

public class DBCreator {

	public void create() {
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
				
		session.createSQLQuery("create table BookstoreToDB(name varchar(255), id int;").executeUpdate();
		session.createSQLQuery("create table BookToDB (id int, title varchar (255), author varchar (255), bookstore varchar (255);").executeUpdate();
		session.createSQLQuery("create table CategoriesToDB (id int, category varchar(255);").executeUpdate();
	}


}
