package com.epam.DAO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.epam.DB.entities.BookToDB;
import com.epam.library.Library;

public class LibraryDB implements List<BookToDB> {

	List<BookToDB> list;

	public LibraryDB() {
		list = new ArrayList<>();
	}
	
	public LibraryDB(Library library){
		list = library.convertToListToDB();
	}
	
	@Override
	public int size() {
		return list.size();
	}

	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return list.contains(o);
	}

	@Override
	public Iterator<BookToDB> iterator() {
		return list.iterator();
	}

	@Override
	public Object[] toArray() {
		return list.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return list.toArray(a);
	}

	@Override
	public boolean add(BookToDB e) {
		return list.add(e);
	}

	@Override
	public boolean remove(Object o) {
		return list.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return list.contains(c);
	}

	@Override
	public boolean addAll(Collection<? extends BookToDB> c) {
		return list.addAll(c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends BookToDB> c) {
		return list.addAll(index, c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return list.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return list.retainAll(c);
	}

	@Override
	public void clear() {
		list.clear();
	}

	@Override
	public BookToDB get(int index) {
		return list.get(index);
	}

	@Override
	public BookToDB set(int index, BookToDB element) {
		return list.set(index, element);
	}

	@Override
	public void add(int index, BookToDB element) {
		list.add(index, element);
	}

	@Override
	public BookToDB remove(int index) {
		return list.remove(index);
	}

	@Override
	public int indexOf(Object o) {
		return list.indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o) {
		return list.lastIndexOf(o);
	}

	@Override
	public ListIterator<BookToDB> listIterator() {
		return list.listIterator();
	}

	@Override
	public ListIterator<BookToDB> listIterator(int index) {
		return list.listIterator(index);
	}

	@Override
	public List<BookToDB> subList(int fromIndex, int toIndex) {
		return list.subList(fromIndex, toIndex);
	}

	@Override
	public String toString() {
		return "LibraryDB [list=" + list + "]";
	}


	
	
}
