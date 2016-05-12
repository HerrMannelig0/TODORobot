package com.epam.DB.entities;

import javax.persistence.*;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * This class represents category as DB entity.
 */
@Entity
public class CategoriesToDB {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String category;
	
	@OneToMany(cascade=CascadeType.ALL)
	private List<BookToDB> library;
	
	public CategoriesToDB() {
	}
	
	public CategoriesToDB(String category, List<BookToDB> library) {
		this.category = category;
		this.library = library;
	}

	public CategoriesToDB(String category) {
		this.category = category;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public List<BookToDB> getLibrary() {
		return library;
	}

	public void setLibrary(List<BookToDB> library) {
		this.library = library;
	}


	@Override
	public String toString() {
		return "CategoriesToDB{" +
				"id=" + id +
				", category='" + category + '\'' +
				", library=" + library +
				'}';
	}
}
