package com.epam.DAO;

import com.epam.DAO.DBmanagment.DBDropper;

public class test {

	public static void main(String[] args) {
		DBDropper dbDropper = new DBDropper();
		
		dbDropper.drop();
	}
}
