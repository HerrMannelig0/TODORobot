package com.epam.DB.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by aga on 27.04.16.
 */
@Entity
@Table (name = "category")
public class CategoryDB implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;

	@Column(name = "category", unique = true)
	String name;

	@Column (name = "books")
	@ElementCollection
	List<BookDB> listOfBooks;

	public CategoryDB(String name) {
		this.name = name;
	}


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

	@OneToMany
	@JoinColumn (name = "books_cat")
	public List<BookDB> getListOfBooks() {
		return listOfBooks;
	}

	public void setListOfBooks(List<BookDB> listOfBooks) {
		this.listOfBooks = listOfBooks;
	}
}
