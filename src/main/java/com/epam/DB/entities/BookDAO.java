package com.epam.DB.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * Entity of Book for DB. Contains author, title.
 *
 */

@Entity
@Table(name = "BOOK")
public class BookDAO {

	
	private String author;
	private String title;
	private String category;


	@Id
	@GeneratedValue
	private int bookId;

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	@Column (name = "CATEGORY")
	public String getCategory() {
		return category;
	}
	
	public void setCategory(String category) {
		this.category = category;
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

	@Override
	public String toString() {
		return "<author=" + author + ", title=" + title + ", category=" + category + ">";
	}

	
}
