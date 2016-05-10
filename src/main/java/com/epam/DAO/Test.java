package com.epam.DAO;

import org.hibernate.Query;
import org.hibernate.Session;

public class Test {
	public static void main(String[] args) {
		Session session = HibernateUtil.getSession();
		
		
		
		Query query = session.createSQLQuery("select * into newtable from CategoriesToDB_BookToDB ");
		query.executeUpdate();
		
		HibernateUtil.closeSession();
	}
}
