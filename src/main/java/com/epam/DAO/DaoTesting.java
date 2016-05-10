package com.epam.DAO;

import com.epam.DAO.DBmanagment.DBReader;

public class DaoTesting {
	public static void main(String[] args) {
		
		DBReader dbReader = new DBReader();
		dbReader.readAll();
	}
}
