package com.epam.DAO;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table (name = "bookstore")
public class Bookstore implements java.io.Serializable{

private static final long serialVersionUID = -3960396761249882840L;
	
	private int bookstoreId;
	
	private String bookstoreName;
	private String url;

	private Set<Book> bookRecords = new HashSet <>();

	public Bookstore() {
	}

	public Bookstore(String boosktoreName, String url) {
		this.bookstoreName = boosktoreName;
		this.url = url;
	}	

	public Bookstore(String boosktoreName, String url, Set<Book> bookRecords) {
		this.bookstoreName = boosktoreName;
		this.url = url;
		this.bookRecords = bookRecords;
	}

	
	@Id  
	@GeneratedValue(strategy=GenerationType.IDENTITY)  
	@Column(name = "BOOKSTORE_ID", unique = true, nullable = false, precision = 5, scale = 0)
	public int getBookstoreId() {
		return this.bookstoreId;
	}

	public void setBookstoreId(int userId) {
		this.bookstoreId = userId;
	}

	@Column(name = "BOOKSTORENAME", nullable = false, length = 20)
	public String getBookstorename() {
		return this.bookstoreName;
	}

	public void setBookstorename(String username) {
		this.bookstoreName = username;
	}

	@Column(name = "URL", nullable = false, length = 20)
	public String getURL() {
		return this.url;
	}

	public void setURL(String createdBy) {
		this.url = createdBy;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "bookstore")
	public Set<Book> getBookRecords() {
		return this.bookRecords;
	}

	public void setBookRecords(Set<Book> bookRecords) {
		this.bookRecords = bookRecords;
	}
	
}
