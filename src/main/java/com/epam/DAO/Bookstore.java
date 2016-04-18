package com.epam.DAO;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
	
	@Id
	@GeneratedValue
	private int bookstoreId;
	
	
	@Column(name = "BOOKSTORE_NAME")
	private String bookstorename;
	private String url;

	@OneToMany  
	@JoinColumn(name = "bookstore_id")
	private List<Book> books;

	public String getBookstorename() {
		return this.bookstorename;
	}

	public void setBookstorename(String bookstorename) {
		this.bookstorename = bookstorename;
	}

	@Column(name = "URL")
	public String getURL() {
		return this.url;
	}

	public void setURL(String URL) {
		this.url = URL;
	}

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

}
