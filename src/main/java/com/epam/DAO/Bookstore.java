package com.epam.DAO;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "BOOKSTORE")
public class Bookstore implements java.io.Serializable {


	private static final long serialVersionUID = 1L;
	private int bookId;
	private String bookstorename;
	private String url;

	public Bookstore() {
	}

	public Bookstore(int userId, String username, String createdBy) {
		this.bookId = userId;
		this.bookstorename = username;
		this.url = createdBy;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "BOOKSTORE_ID", unique = true, nullable = false, precision = 5, scale = 0)
	public int getBookstoreId() {
		return this.bookId;
	}

	public void setBookstoreId(int bookstoreId) {
		this.bookId = bookstoreId;
	}

	@Column(name = "BOOKSTORE_NAME", nullable = false)
	public String getBookstorename() {
		return this.bookstorename;
	}

	public void setBookstorename(String bookstorename) {
		this.bookstorename = bookstorename;
	}

	@Column(name = "URL", nullable = false)
	public String getURL() {
		return this.url;
	}

	public void setURL(String URL) {
		this.url = URL;
	}



}
