package com.epam.DB.entities;

import com.epam.DB.entities.BookDAO;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 
 * Entity of Bookstore for DB. Contains name of bookstore and its URL and list of books
 *
 */

@Entity
@Table(name = "BOOKSTORE")
public class Bookstore {
	
	
	public Bookstore() {
	}
	
	public Bookstore(String bookstorename){
		this.bookstoreName = bookstorename;
	}
	
	@Id
	@GeneratedValue
	private int bookstoreId;
	
	
	@Column(name = "BOOKSTORE_NAME")
	private String bookstoreName;
	private String url;

	@OneToMany  
	@JoinColumn(name = "bookstore_id")
	private List<BookDAO> books;

	public String getBookstorename() {
		return this.bookstoreName;
	}

	public void setBookstorename(String bookstorename) {
		this.bookstoreName = bookstorename;
	}

	@Column(name = "URL")
	public String getURL() {
		return this.url;
	}

	public void setURL(String URL) {
		this.url = URL;
	}

	public List<BookDAO> getBooks() {
		return books;
	}

	public void setBooks(List<BookDAO> books) {
		this.books = books;
	}

	public void init(){
		if(books == null) books = new ArrayList<>();
	}
	
	public void addBookDAO(BookDAO bookDAO){
		books.add(bookDAO);
	}
	
	public boolean contains(BookDAO bookDAO){
		return books.contains(bookDAO);
	}
	
	@Override
	public String toString() {
		return "[" + bookstoreName + " books: " + books + "]";
	}
}
