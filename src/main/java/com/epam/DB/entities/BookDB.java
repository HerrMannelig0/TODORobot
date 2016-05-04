package com.epam.DB.entities;

import com.epam.file.Category;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "books")
public class BookDB implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;

	@Column(name = "Title")
	String title;

	@Column(name = "Author")
	String author;

	@Column(name = "BookstoreDB")
	BookstoreDB bookstoreDB;

	@Column(name = "CategoryDB")
	CategoryDB category;

	public BookDB() {
		author = "unknown";
		title = "unknown";
		bookstoreDB = new BookstoreDB("unknown");
		category = new CategoryDB("unknown");
	}

	public BookDB(String author, String title, BookstoreDB bookstoreDB, CategoryDB category) {
		this.author = author;
		this.title = title;
		this.bookstoreDB = bookstoreDB;
		this.category = category;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	@ManyToOne
	@Column(name = "books_store")
	public BookstoreDB getBookstoreDB() {
		return bookstoreDB;
	}

	public void setBookstoreDB(BookstoreDB bookstoreDB) {
		this.bookstoreDB = bookstoreDB;
	}

	@ManyToOne
	@Column(name = "books_cat")
	public CategoryDB getCategory() {
		return category;
	}

	public void setCategory(CategoryDB category) {
		this.category = category;
	}


}
