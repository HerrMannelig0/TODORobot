package com.epam.DAO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class BookToDB {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String title;
	private String author;
	private String bookstore;
	
	public BookToDB() {
	}

	public BookToDB(String title, String author, String bookstore) {
		this.title = title;
		this.author = author;
		this.bookstore = bookstore;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}

	public String getBookstore() {
		return bookstore;
	}

	public void setBookstore(String bookstore) {
		this.bookstore = bookstore;
	}

	@Override
	public String toString() {
		return "BookToDB [id=" + id + ", title=" + title + ", author=" + author + ", bookstore=" + bookstore + "]";
	}
	
	
	
	
}
