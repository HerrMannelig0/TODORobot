package com.epam.library;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.epam.DAO.LibraryDB;
import com.epam.DB.entities.BookToDB;
import com.epam.file.Category;

public class Library implements Set<Book> {
	private Set<Book> library;

	public Library() {
		library = new HashSet<>();
	}
	
	public Library(Set<Book> library){
		this.library = library;
	}

	public List<BookToDB> convertToDBList(){
		List<BookToDB> dbList = new ArrayList<>();
		for(Book book : library){
			dbList.add(book.toDB());
		}
		return dbList;
	}


	@Override
	public boolean add(Book book) {
		return library.add(book);
	}

	@Override
	public int size() {
		return library.size();
	}

	@Override
	public boolean isEmpty() {
		return library.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return library.contains(o);
	}

	@Override
	public Iterator<Book> iterator() {
		return library.iterator();
	}

	@Override
	public Object[] toArray() {
		return library.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return library.toArray(a);
	}

	@Override
	public boolean remove(Object o) {
		return library.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return library.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends Book> c) {
		return library.addAll(c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return library.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return library.retainAll(c);
	}

	@Override
	public void clear() {
		library.clear();
	}

	@Override
	public String toString() {
		return "Library [library=" + library + "]";
	}

	public void assignCategoryForAllBooks() {
		if(library == null) throw new IllegalStateException();
		
		ApplicationContext context = new AnnotationConfigApplicationContext(LibraryBeans.class);
		Book dumbBook = context.getBean(Book.class);
		File categoriesFile = (File) context.getBean("categoriesFile");
		
		@SuppressWarnings("unchecked")
		List<Category> categories = (List<Category>) context.getBean("listofcategories", dumbBook, categoriesFile); 
		
		library.stream().forEach(book -> book.assignCategory(categories));
		((AnnotationConfigApplicationContext)context).close();
	}




	public LibraryDB convertToLibraryDB(){
		LibraryDB libraryDB = new LibraryDB();
		for (Book book : library) {
			libraryDB.add(book.toDB());
		}
		return libraryDB;
	}
	
	public List<BookToDB> convertToListToDB(){
		List<BookToDB> list = new ArrayList<>();
		for (Book book : library) {
			list.add(book.toDB());
		}
		return list;
	}


	
	
}
