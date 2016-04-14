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

@Entity
@Table(name = "CATEGORY")
public class Category {
	
	@Id
	@GeneratedValue
	private int categoryId;
	
	
	@Column(name = "CATEGORY_NAME")
	private String catName;

	@OneToMany  
	@JoinColumn(name = "category_id")
	private List<Book> books;

	public String getCategoryName() {
		return this.catName;
	}

	public void setCategoryName(String catName) {
		this.catName = catName;
	}

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

}
