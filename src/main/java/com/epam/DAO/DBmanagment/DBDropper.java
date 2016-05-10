package com.epam.DAO.DBmanagment;

import org.hibernate.Session;

import com.epam.DAO.HibernateUtil;

public class DBDropper {

	public void drop(){
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
				
		session.createSQLQuery("drop table CategoriesToDB_BookToDB;").executeUpdate();
		session.createSQLQuery("drop table BookToDB;").executeUpdate();
		session.createSQLQuery("drop table CategoriesToDB;").executeUpdate();
	}
	
}
