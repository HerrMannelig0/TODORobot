package com.epam.DB.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by aga on 27.04.16.
 */
@Entity
@Table (name = "bookstoreDB")
public class BookstoreDB implements Serializable{

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	Integer id;

	@Column(name = "bookstoreDB")
	String name;

	@Column (name = "address")
	String urlAddress;

	@Column (name = "books")
	ArrayList<BookDB> listOfBooks;

	public BookstoreDB(String bookstoreName) {
		this.name = bookstoreName;
	}
	public BookstoreDB(){}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUrlAddress() {
		return urlAddress;
	}

	public void setUrlAddress(String urlAddress) {
		this.urlAddress = urlAddress;
	}

	@OneToMany
	@JoinColumn (name = "books_store")
	public ArrayList<BookDB> getListOfBooks() {
		return listOfBooks;
	}

	public void setListOfBooks(ArrayList<BookDB> listOfBooks) {
		this.listOfBooks = listOfBooks;
	}

	public void addBook(BookDB bookDB) {
	}


	public void init() {

		if(listOfBooks == null) listOfBooks = new ArrayList<>();
	}

	public boolean contains(BookDB bookDB) {
		return listOfBooks.contains(bookDB);
	}
}
