package com.epam.DAO;

import java.util.ArrayList;

import javax.persistence.*;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table (name = "book")
public class Book implements java.io.Serializable {

	// not sure if book should have unique id
	private int id;
	private int bookstoreId;
	private String author;
	private String title;
	private int price;
	private ArrayList<String> taglist;
	private Bookstore bookstore;

	public Book() {
	}

	public Book(String author, String title, int price) {

		this.setAuthor(author);
		this.setTitle(title);
		this.setPrice(price);

	}
	
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "BOOKSTORE_ID", unique = true, nullable = false)
	public Integer getBookstoreId() {
		return this.bookstoreId;
	}

	public void setBookstoreId(int bookstoreId) {
		this.bookstoreId = bookstoreId;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BOOK_ID", nullable = false)
	public Bookstore getBookstore() {
		return this.bookstore;
	}

	public void setBookstore(Bookstore bookstore) {
		this.bookstore = bookstore;
	}
	
	
	@Column(name = "AUTHOR")
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Column(name = "TITLE")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "PRICE")
	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
	
	@Override
	public String toString() {
		return author + " " + title;
	}

}
