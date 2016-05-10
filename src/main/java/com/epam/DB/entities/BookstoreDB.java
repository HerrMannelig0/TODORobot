package com.epam.DB.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by aga on 27.04.16.
 */
@Entity
@Table(name = "bookstoreDB")
public class BookstoreDB implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "bookstoreDB")
    String name;

    @Column(name = "address")
    String urlAddress;

    @Column(name = "books")
   // @ElementCollection
    ArrayList<BookDB> listOfBooks;

    public BookstoreDB(String bookstoreName) {
        this.name = bookstoreName;
    }

    public BookstoreDB() {
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

    public String getUrlAddress() {
        return urlAddress;
    }

    public void setUrlAddress(String urlAddress) {
        this.urlAddress = urlAddress;
    }

    @OneToMany
    @JoinColumn(name = "books_store")
    public ArrayList<BookDB> getListOfBooks() {
        return listOfBooks;
    }

    public void setListOfBooks(ArrayList<BookDB> listOfBooks) {
        this.listOfBooks = listOfBooks;
    }

    public void addBook(BookDB bookDB) {
    }


    public void init() {

        if (listOfBooks == null) listOfBooks = new ArrayList<>();
    }

    public boolean contains(BookDB bookDB) {
        return listOfBooks.contains(bookDB);
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((listOfBooks == null) ? 0 : listOfBooks.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((urlAddress == null) ? 0 : urlAddress.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BookstoreDB other = (BookstoreDB) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (listOfBooks == null) {
			if (other.listOfBooks != null)
				return false;
		} else if (!listOfBooks.equals(other.listOfBooks))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (urlAddress == null) {
			if (other.urlAddress != null)
				return false;
		} else if (!urlAddress.equals(other.urlAddress))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BookstoreDB [id=" + id + ", name=" + name + ", urlAddress=" + urlAddress +"]";
	}

	public String toStringGUI() {
		return toString();
	}
    
    
}
