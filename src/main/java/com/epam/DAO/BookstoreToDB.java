package com.epam.DAO;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class BookstoreToDB {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String name;
	@OneToMany(cascade=CascadeType.ALL)
	private List<BookToDB> booksList;
	
	public BookstoreToDB() {
	}
	
	public BookstoreToDB(String name) {
		this.name = name;
	}
	
	public BookstoreToDB(String name, List<BookToDB> booksList) {
		this.name = name;
		this.booksList = booksList;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<BookToDB> getBooksList() {
		return booksList;
	}
	public void setBooksList(List<BookToDB> booksList) {
		this.booksList = booksList;
	}
	
}
