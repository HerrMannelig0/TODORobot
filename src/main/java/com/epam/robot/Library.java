package com.epam.robot;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.epam.DAO.BookToDB;
import com.epam.DAO.LibraryDB;
import com.epam.DB.entities.BookDB;
import com.epam.file.Category;

public class Library implements List<Book> {
	private List<Book> library;

	public Library() {
		library = new ArrayList<>();
	}
	
	public Library(List<Book> library){
		this.library = library;
	}

	public List<BookDB> convertToDBList(){
		List<BookDB> dbList = new ArrayList<>();
		for(Book book : library){
			dbList.add(book.convertToBookDB());
		}
		return dbList;
	}

	@Override
	public boolean add(Book book) {
		return library.add(book);
	}

	@Override
	public Book get(int index) {
		return library.get(index);
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
	public boolean addAll(int index, Collection<? extends Book> c) {
		return library.addAll(index, c);
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
	public Book set(int index, Book element) {
		return library.set(index, element);
	}

	@Override
	public void add(int index, Book element) {
		library.add(index, element);
	}

	@Override
	public Book remove(int index) {
		return library.remove(index);
	}

	@Override
	public int indexOf(Object o) {
		return library.indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o) {
		return library.lastIndexOf(o);
	}

	@Override
	public ListIterator<Book> listIterator() {
		return library.listIterator();
	}

	@Override
	public ListIterator<Book> listIterator(int index) {
		return library.listIterator(index);
	}

	@Override
	public List<Book> subList(int fromIndex, int toIndex) {
		return library.subList(fromIndex, toIndex);
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
