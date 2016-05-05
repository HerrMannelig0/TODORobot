package com.epam.DAO;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.epam.file.Category;

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

	
	
	
	
}
